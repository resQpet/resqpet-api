package service.domain.types.aws;

import service.domain.exceptions.BadRequestException;

import java.util.Arrays;

public enum FileMime {

    IMAGES("image/"),
    DOCS("application/", "text/"),
    AUDIOS("audio/"),
    VIDEOS("video/");

    private final String[] mimes;

    FileMime(String... mimes) {
        this.mimes = mimes;
    }

    public static FileMime of(String contentType) {
        return Arrays.stream(values()).filter(fm -> Arrays.stream(fm.mimes).anyMatch(contentType::contains))
                .findFirst().orElseThrow(() -> new BadRequestException("Invalid file content type: " + contentType));
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
