package Services;

import Exceptions.InvalidRentalAgreementException;
import Models.RentalAgreement;
import TemporalAdjustors.MoveToClosestWeekdayAdjustor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RentalAgreementCalculator {
    public RentalAgreement calculateRentalAgreement(RentalAgreement rentalAgreement) throws InvalidRentalAgreementException {
        //verify proper start state for rental agreement
        if(rentalAgreement.getDiscountPercent() > 100 || rentalAgreement.getDiscountPercent() < 0){
            throw new InvalidRentalAgreementException("Discount percentage "+rentalAgreement.getDiscountPercent()+"% outside of range 0-100");
        }
        if(rentalAgreement.getRentalDays() < 1){
            throw new InvalidRentalAgreementException("Number of rental days needs to be 1 or greater");
        }
        if(rentalAgreement.getToolCode() == null ||
                rentalAgreement.getToolType() == null ||
                rentalAgreement.getToolBrand() == null) {
            throw new InvalidRentalAgreementException("Invalid Tool");
        }
        if(rentalAgreement.getCheckoutDate() == null){
            throw new InvalidRentalAgreementException("Invalid Date");
        }

        LocalDate dueDate = rentalAgreement.getCheckoutDate().plus(Period.ofDays(rentalAgreement.getRentalDays()));
        LocalDate firstChargeDate = rentalAgreement.getCheckoutDate().plusDays(1);
        rentalAgreement.setDueDate(dueDate);
        //This could be done as a generic method on the individual tools, but I decided to keep them as exclusively model classes and move the logic into this calculator
        rentalAgreement.setChargeDays(switch (rentalAgreement.getToolType()){
            case Ladder -> calculateLadderDays(firstChargeDate, rentalAgreement.getDueDate());
            case Chainsaw -> calculateChainsawDays(firstChargeDate, rentalAgreement.getDueDate());
            case Jackhammer -> calculateJackhammerDays(firstChargeDate, rentalAgreement.getDueDate());
        });
        rentalAgreement.setPreDiscountCharge(rentalAgreement.getDailyCharge().multiply(BigDecimal.valueOf(rentalAgreement.getChargeDays())));
        rentalAgreement.setFinalCharge(rentalAgreement.getPreDiscountCharge().multiply(BigDecimal.valueOf(100-rentalAgreement.getDiscountPercent()).movePointLeft(2)));
        rentalAgreement.setDiscountAmount(rentalAgreement.getPreDiscountCharge().subtract(rentalAgreement.getFinalCharge()));
        return rentalAgreement;
    }

    public int calculateLadderDays(LocalDate firstChargeDate, LocalDate dueDate){
        List<LocalDate> holidays = calculateHolidayDatesForDateRange(firstChargeDate, dueDate);
        return calculatePaidDaysMinusCertainDates(firstChargeDate, dueDate, holidays);
    }

    public int calculateChainsawDays(LocalDate firstChargeDate, LocalDate dueDate){
        return calculatePaidDaysNoWeekends(firstChargeDate, dueDate);
    }
    public int calculateJackhammerDays(LocalDate firstChargeDate, LocalDate dueDate){
        int paidDaysNoWeekends = calculatePaidDaysNoWeekends(firstChargeDate, dueDate);
        List<LocalDate> holidays = calculateHolidayDatesForDateRange(firstChargeDate, dueDate);
        int paidDaysNoHolidays = calculatePaidDaysMinusCertainDates(firstChargeDate, dueDate, holidays);
        int rentalDays = (int) firstChargeDate.datesUntil(dueDate.plusDays(1)).count();
        int totalDaysMissing = (rentalDays - paidDaysNoWeekends) + (rentalDays - paidDaysNoHolidays);
        return rentalDays - totalDaysMissing;
    }

    public int calculatePaidDaysNoWeekends(LocalDate firstChargeDate, LocalDate dueDate){
        // this could also most likely be done through math more quickly but with a lot more checks/edge case checking and be less readable
        final Set<DayOfWeek> weekdays = Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        //adding 1 day because datesUntil is exclusive instead of inclusive
        return  (int) firstChargeDate.datesUntil(dueDate.plusDays(1)).filter(totalDays -> weekdays.contains(totalDays.getDayOfWeek())).count();
    }

    public int calculatePaidDaysMinusCertainDates(LocalDate firstChargeDate, LocalDate dueDate, List<LocalDate> ignoredDates){
        //adding 1 day because datesUntil is exclusive instead of inclusive
        if(ignoredDates == null){
            return (int) firstChargeDate.datesUntil(dueDate.plusDays(1)).count();
        }else {
            return (int) firstChargeDate.datesUntil(dueDate.plusDays(1)).filter(totalDays -> !ignoredDates.contains(totalDays)).count();
        }
    }

    public List<LocalDate> calculateHolidayDatesForDateRange(LocalDate firstChargeDate, LocalDate dueDate){
        //adding 1 day because datesUntil is exclusive instead of inclusive
        List<LocalDate> totalDays = firstChargeDate.datesUntil(dueDate.plusDays(1)).toList();
        List<LocalDate> holidaysInDate = new ArrayList<>();
        //4th of July
        holidaysInDate.addAll(totalDays.stream()
                .filter(currentDate -> currentDate.getMonth() == Month.JULY &&
                        (currentDate.getDayOfMonth() == 4 || (currentDate.getDayOfMonth() == 3 && currentDate.getDayOfWeek() == DayOfWeek.FRIDAY) || (currentDate.getDayOfMonth() == 5 && currentDate.getDayOfWeek() == DayOfWeek.MONDAY)))
                .map(fourthOfJuly -> fourthOfJuly.with(new MoveToClosestWeekdayAdjustor()))
                .distinct()
                .toList());

        //Labor Day
        holidaysInDate.addAll(totalDays.stream()
                .filter(currentDate -> currentDate.getMonth() == Month.SEPTEMBER && (currentDate.getDayOfYear() == currentDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)).getDayOfYear()))
                .toList());

        return holidaysInDate;
    }
}
