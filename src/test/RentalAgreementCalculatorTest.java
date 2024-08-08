package test;

import Exceptions.InvalidRentalAgreementException;
import Models.RentalAgreement;
import Services.RentalAgreementCalculator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RentalAgreementCalculatorTest {

    RentalAgreementCalculator rentalAgreementCalculator = new RentalAgreementCalculator();

    @Test
    public void calculateRentalAgreementTest(){
        RentalAgreement rentalAgreementInput = new RentalAgreement();
        try {
            RentalAgreement rentalAgreementResult = rentalAgreementCalculator.calculateRentalAgreement(rentalAgreementInput);
        } catch (InvalidRentalAgreementException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void calculateLadderDaysTest(){
        //No Holidays, Thursday-Sunday
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        int ladderDays = rentalAgreementCalculator.calculateLadderDays(startDate, endDate);
        assertEquals(4, ladderDays);

        //4th of july week
        startDate = LocalDate.of(2024, 7, 1);
        endDate = LocalDate.of(2024, 7, 6);
        ladderDays = rentalAgreementCalculator.calculateLadderDays(startDate, endDate);
        assertEquals(5, ladderDays);

        //Labor day week
        startDate = LocalDate.of(2024, 9, 1);
        endDate = LocalDate.of(2024, 9, 7);
        ladderDays = rentalAgreementCalculator.calculateLadderDays(startDate, endDate);
        assertEquals(6, ladderDays);

        //4th of july and labor day
        startDate = LocalDate.of(2024, 7, 1);
        endDate = LocalDate.of(2024, 9, 7);
        ladderDays = rentalAgreementCalculator.calculateLadderDays(startDate, endDate);
        assertEquals(67, ladderDays);
    }

    @Test
    public void calculateChainsawDaysTest(){
        //Thursday - Sunday
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        int chainsawDays = rentalAgreementCalculator.calculateChainsawDays(startDate, endDate);
        assertEquals(2, chainsawDays);

        //Whole month
        startDate = LocalDate.of(2024, 8, 1);
        endDate = LocalDate.of(2024, 8, 31);
        chainsawDays = rentalAgreementCalculator.calculateChainsawDays(startDate, endDate);
        assertEquals(22, chainsawDays);

        //2 months
        startDate = LocalDate.of(2024, 8, 1);
        endDate = LocalDate.of(2024, 9, 30);
        chainsawDays = rentalAgreementCalculator.calculateChainsawDays(startDate, endDate);
        assertEquals(43, chainsawDays);
    }

    @Test
    public void calculateJackhammerDays(){
        //No Holidays, Thursday-Sunday
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        int jackhammerDays = rentalAgreementCalculator.calculateJackhammerDays(startDate, endDate);
        assertEquals(2, jackhammerDays);

        //4th of july week
        startDate = LocalDate.of(2024, 7, 1);
        endDate = LocalDate.of(2024, 7, 6);
        jackhammerDays = rentalAgreementCalculator.calculateJackhammerDays(startDate, endDate);
        assertEquals(4, jackhammerDays);

        //Labor day week
        startDate = LocalDate.of(2024, 9, 1);
        endDate = LocalDate.of(2024, 9, 7);
        jackhammerDays = rentalAgreementCalculator.calculateJackhammerDays(startDate, endDate);
        assertEquals(4, jackhammerDays);

        //4th of july and labor day
        startDate = LocalDate.of(2024, 7, 1);
        endDate = LocalDate.of(2024, 9, 7);
        jackhammerDays = rentalAgreementCalculator.calculateJackhammerDays(startDate, endDate);
        assertEquals(48, jackhammerDays);

    }

    @Test
    public void calculatePaidDaysNoWeekendsTest(){
        //Thursday - Sunday
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        int noWeekendsDays = rentalAgreementCalculator.calculatePaidDaysNoWeekends(startDate, endDate);
        assertEquals(2, noWeekendsDays);

        //Whole month
        startDate = LocalDate.of(2024, 8, 1);
        endDate = LocalDate.of(2024, 8, 31);
        noWeekendsDays = rentalAgreementCalculator.calculatePaidDaysNoWeekends(startDate, endDate);
        assertEquals(22, noWeekendsDays);

        //2 months
        startDate = LocalDate.of(2024, 8, 1);
        endDate = LocalDate.of(2024, 9, 30);
        noWeekendsDays = rentalAgreementCalculator.calculatePaidDaysNoWeekends(startDate, endDate);
        assertEquals(43, noWeekendsDays);
    }

    @Test
    public void calculatePaidDaysMinusCertainDates(){

    }

    @Test
    public void calculateHolidayDatesForDateRangeTest(){
        //No Holidays, Thursday-Sunday
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        List<LocalDate> holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(0, holidayDatesForDateRange.size());

        //4th of july week
        startDate = LocalDate.of(2024, 7, 1);
        endDate = LocalDate.of(2024, 7, 6);
        holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(1, holidayDatesForDateRange.size());

        //Labor day week
        startDate = LocalDate.of(2024, 9, 1);
        endDate = LocalDate.of(2024, 9, 7);
        holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(1, holidayDatesForDateRange.size());

        //4th of july and labor day
        startDate = LocalDate.of(2024, 7, 1);
        endDate = LocalDate.of(2024, 9, 7);
        holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(2, holidayDatesForDateRange.size());
    }
}
