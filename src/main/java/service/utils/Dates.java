package service.utils;

import lombok.experimental.UtilityClass;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;


@UtilityClass
public class Dates {

    /**
     * Adds the specified number of minutes to the current date and time.
     *
     * @param minutes the number of minutes to add
     * @return the date and time after adding the specified number of minutes
     */
    public LocalDateTime addMinutes(int minutes) {
        return LocalDateTime.now().plusMinutes(minutes);
    }

    /**
     * Returns the current year.
     *
     * @return the current year as an Integer
     */
    public static Integer currentYear() {
        return LocalDateTime.now().getYear();
    }

    /**
     * Returns a {@link LocalDateTime} object representing the current date combined with the time
     * specified in the provided {@link Time} object.
     *
     * @param startsAt the {@link Time} object that specifies the time to be combined with the current date
     * @return a {@link LocalDateTime} object representing the combined current date and specified time
     */
    public static LocalDateTime atTime(Time startsAt) {
        return atTime(LocalDate.now(), startsAt);
    }

    /**
     * Generates a {@link LocalDateTime} object representing yesterday's date combined
     * with the time specified in the provided {@link Time} object.
     *
     * @param startsAt the {@link Time} object that specifies the time to be combined with yesterday's date
     * @return a {@link LocalDateTime} object representing the combined date of yesterday
     * and the specified time
     */
    public static LocalDateTime atYesterday(Time startsAt) {
        return atTime(LocalDate.now().minusDays(Numbers.ONE), startsAt);
    }

    /**
     * Combines a given {@link LocalDate} and {@link Time} into a {@link LocalDateTime}.
     *
     * @param date     the {@link LocalDate} to be combined with the time
     * @param startsAt the {@link Time} representing the time to combine with the date
     * @return a {@link LocalDateTime} representing the combination of the provided date and time
     */
    public static LocalDateTime atTime(LocalDate date, Time startsAt) {
        return date.atTime(startsAt.toLocalTime());
    }

    /**
     * Checks if the current date and time falls within the specified start and end date-time range.
     *
     * @param startsAt the start date and time of the range
     * @param endsAt   the end date and time of the range
     * @return true if the current date and time is within the specified range, otherwise false
     */
    public static boolean isInRange(LocalDateTime startsAt, LocalDateTime endsAt) {
        return LocalDateTime.now().isAfter(startsAt) && LocalDateTime.now().isBefore(endsAt);
    }
}
