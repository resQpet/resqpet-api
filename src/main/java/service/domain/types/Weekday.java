package service.domain.types;

import java.util.Arrays;
import java.util.Calendar;

public enum Weekday {
    MONDAY(Calendar.MONDAY),
    TUESDAY(Calendar.TUESDAY),
    WEDNESDAY(Calendar.WEDNESDAY),
    THURSDAY(Calendar.THURSDAY),
    FRIDAY(Calendar.FRIDAY),
    SATURDAY(Calendar.SATURDAY),
    SUNDAY(Calendar.SUNDAY),
    HOLIDAY(0);

    private final int day;

    Weekday(int day) {
        this.day = day;
    }

    public static Weekday ofToday() {
        return getWeekday(Calendar.getInstance(), Weekday.values());
    }

    public static Weekday ofYesterday() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return getWeekday(calendar, Weekday.values());
    }

    private static Weekday getWeekday(Calendar calendar, Weekday[] values) {
        final int weekdayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        return Arrays.stream(values).filter(w -> w.day == weekdayIndex).findFirst()
                .orElseThrow(() -> new InternalError("Could not determine current weekday"));
    }
}
