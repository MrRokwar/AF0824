package test;

import Exceptions.InvalidRentalAgreementException;
import Models.RentalAgreement;
import Models.Tool;
import Services.RentalAgreementCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RentalAgreementCalculatorTest {

    RentalAgreementCalculator rentalAgreementCalculator = new RentalAgreementCalculator();

    @Test
    public void calculateRentalAgreementTest(){
        RentalAgreement rentalAgreementInput = new RentalAgreement();
        rentalAgreementInput.setToolCode("ABCD");
        rentalAgreementInput.setToolType(Tool.ToolType.Ladder);
        rentalAgreementInput.setToolBrand("FakeTool");
        rentalAgreementInput.setDailyCharge(BigDecimal.valueOf(1.00));
        rentalAgreementInput.setRentalDays(10);
        rentalAgreementInput.setDiscountPercent(10);
        rentalAgreementInput.setCheckoutDate(LocalDate.of(2024, 1, 1));
        try {
            RentalAgreement resultAgreement = rentalAgreementCalculator.calculateRentalAgreement(rentalAgreementInput);
            assertEquals("ABCD", resultAgreement.getToolCode());
            assertEquals(Tool.ToolType.Ladder, resultAgreement.getToolType());
            assertEquals("FakeTool", resultAgreement.getToolBrand());
            assertEquals(BigDecimal.valueOf(1.00), resultAgreement.getDailyCharge().setScale(1, RoundingMode.CEILING));
            assertEquals(10, resultAgreement.getRentalDays());
            assertEquals(10, resultAgreement.getDiscountPercent());
            assertEquals(LocalDate.of(2024, 1, 1),resultAgreement.getCheckoutDate());
            assertEquals(LocalDate.of(2024, 1, 10),resultAgreement.getDueDate());
            assertEquals(BigDecimal.valueOf(10.00), resultAgreement.getPreDiscountCharge().setScale(1, RoundingMode.CEILING));
            assertEquals(BigDecimal.valueOf(1.00), resultAgreement.getDiscountAmount().setScale(1, RoundingMode.CEILING));
            assertEquals(BigDecimal.valueOf(9.00), resultAgreement.getFinalCharge().setScale(1, RoundingMode.CEILING));

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

        //Saturday/Sunday
        startDate = LocalDate.of(2024, 8, 3);
        endDate = LocalDate.of(2024, 8, 4);
        chainsawDays = rentalAgreementCalculator.calculateChainsawDays(startDate, endDate);
        assertEquals(0, chainsawDays);

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
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        ArrayList<LocalDate> ignoredDates = new ArrayList<>(Arrays.asList(LocalDate.of(2024, 8, 5), LocalDate.of(2024, 8, 6)));
        assertEquals(4, rentalAgreementCalculator.calculatePaidDaysMinusCertainDates(startDate, endDate, ignoredDates));

        endDate = LocalDate.of(2024, 8, 5);
        assertEquals(4, rentalAgreementCalculator.calculatePaidDaysMinusCertainDates(startDate, endDate, ignoredDates));

        endDate = LocalDate.of(2024, 8, 7);
        assertEquals(5, rentalAgreementCalculator.calculatePaidDaysMinusCertainDates(startDate, endDate, ignoredDates));

        ignoredDates = null;
        assertEquals(7, rentalAgreementCalculator.calculatePaidDaysMinusCertainDates(startDate, endDate, ignoredDates));

    }

    @Test
    public void calculateHolidayDatesForDateRangeTest(){
        //No Holidays, Thursday-Sunday
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 4);
        List<LocalDate> holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(0, holidayDatesForDateRange.size());
    }

    @Test
    public void calculateHolidayDatesForDateRange4thOfJuly(){
        //4th of july week
        LocalDate startDate = LocalDate.of(2024, 7, 1);
        LocalDate endDate = LocalDate.of(2024, 7, 6);
        List<LocalDate> holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(1, holidayDatesForDateRange.size());
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2024,7,4)));
    }

    @Test
    public void calculateHolidayDatesForDateRangeLaborDay(){
        //Labor day week
        LocalDate startDate = LocalDate.of(2024, 9, 1);
        LocalDate endDate = LocalDate.of(2024, 9, 7);
        List<LocalDate> holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(1, holidayDatesForDateRange.size());
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2024,9,2)));
    }

    @Test
    public void calculateHolidayDatesForDAteRangeLaborDayAnd4thOfJuly(){
        //4th of july and labor day
        LocalDate startDate = LocalDate.of(2024, 7, 1);
        LocalDate endDate = LocalDate.of(2024, 9, 7);
        List<LocalDate> holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(2, holidayDatesForDateRange.size());
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2024,7,4)));
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2024,9,2)));
    }

    @Test
    public void calculateHolidayDatesForDateRange4thOfJulyWeekend(){
        //4th of July on Sunday
        LocalDate startDate = LocalDate.of(2021, 7, 1);
        LocalDate endDate = LocalDate.of(2021, 7, 5);
        List<LocalDate> holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(1, holidayDatesForDateRange.size());
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2021,7,5)));

        //4th of July on Saturday
        startDate = LocalDate.of(2020, 7, 1);
        endDate = LocalDate.of(2020, 7, 5);
        holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(1, holidayDatesForDateRange.size());
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2020,7,3)));
    }

    @Test
    public void calculateHolidayDAtesForDateRangeMultiYear(){
        //Multi Year
        LocalDate startDate = LocalDate.of(2024, 7, 1);
        LocalDate endDate = LocalDate.of(2025, 9, 7);
        List<LocalDate> holidayDatesForDateRange = rentalAgreementCalculator.calculateHolidayDatesForDateRange(startDate, endDate);
        assertEquals(4, holidayDatesForDateRange.size());
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2024,7,4)));
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2024,9,2)));
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2025,7,4)));
        assertTrue(holidayDatesForDateRange.contains(LocalDate.of(2025,9,1)));
    }
}
