package service.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;
import service.domain.exceptions.InternalErrorException;
import service.domain.types.aws.FileMime;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Objects;


@UtilityClass
public class FileUtils {

    public String getFileMime(final MultipartFile file) {
        if (Objects.isNull(file) || Objects.isNull(file.getContentType())) {
            throw new InternalErrorException("file is null");
        }
        return FileMime.of(file.getContentType()).toString();
    }

    /**
     * Retrieves the file extension from the given MultipartFile object.
     *
     * @param file the MultipartFile object to retrieve the extension from
     * @return the file extension
     * @throws InternalErrorException if the file or the filename is null,
     *                                or if the filename contains illegal characters
     */
    public String getExtension(final MultipartFile file) {
        if (Objects.isNull(file) || Objects.isNull(file.getOriginalFilename())) {
            throw new InternalErrorException("file is null");
        }
        final String filename = file.getOriginalFilename();
        if (filename.contains(Chars.DOT)) {
            return filename.substring(filename.lastIndexOf(Chars.DOT) + 1);
        }
        throw new InternalErrorException("filename contains illegal character");
    }

    /**
     * Generates a random name for a file by combining a UUID and its extension.
     *
     * @param file the MultipartFile object from which to generate the name
     * @return a random name with the file extension
     * @throws InternalErrorException if the file or the filename is null,
     *                                or if the filename contains illegal characters
     */
    public String getRandomName(MultipartFile file) {
        return String.format("%s/%s", getFileMime(file), APIUtils.genID() + Chars.DOT + getExtension(file));
    }

    /**
     * Calculates the checksum of the given file using the MD5 algorithm.
     *
     * @param file the MultipartFile object representing the file for which to calculate the checksum
     * @return a String representing the hexadecimal checksum of the file content
     * @throws InternalErrorException if there are issues calculating the checksum
     */
    public String getChecksum(MultipartFile file) {
        try {
            // Initialize MessageDigest with MD5 algorithm
            final MessageDigest digest = MessageDigest.getInstance("SHA256");
            // Get the InputStream of the file
            try (InputStream inputStream = file.getInputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                // Read file content and update the digest
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }
            // Get the checksum bytes
            final byte[] checksumBytes = digest.digest();

            // Convert bytes to hexadecimal string
            final StringBuilder hexString = new StringBuilder();
            for (byte b : checksumBytes) {
                hexString.append(String.format("%02x", b));
            }
            // Returns the resulting hexadecimal String.
            return hexString.toString();
        } catch (Throwable e) {
            throw new InternalErrorException("Problemas calculando checksum: " + e.getMessage());
        }
    }
}
