package TemporalAdjustors;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;


public class MoveToClosestWeekdayAdjustor implements TemporalAdjuster {
    @Override
    public Temporal adjustInto(Temporal input) {
        LocalDate date = LocalDate.from(input);
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            date = date.minusDays(1);
        } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        return date;
    }
}
