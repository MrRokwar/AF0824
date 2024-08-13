package test;

import Exceptions.InvalidRentalAgreementException;
import Models.RentalAgreement;
import Models.Tool;
import Services.RentalStore;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalStoreTest {

    @Test
    public void setupRentalAgreementTest(){
        RentalAgreement resultAgreement;

        try {
            resultAgreement = RentalStore.setupRentalAgreement("LADW", 1, 0, LocalDate.of(2015,9,3));
            assertEquals("LADW", resultAgreement.getToolCode());
            assertEquals(Tool.ToolType.Ladder, resultAgreement.getToolType());
            assertEquals("Werner", resultAgreement.getToolBrand());
            assertEquals(BigDecimal.valueOf(1.99), resultAgreement.getDailyCharge());
            assertEquals(1, resultAgreement.getRentalDays());
            assertEquals(0, resultAgreement.getDiscountPercent());
            assertEquals(LocalDate.of(2015, 9, 3),resultAgreement.getCheckoutDate());
        } catch (InvalidRentalAgreementException e){
            fail(e);
        }

    }

    @Test
    public void setupRentalAgreementHighDiscountTest(){
        try {
            RentalStore.setupRentalAgreement("LADW", 5, 101, LocalDate.of(2015,9,3));
            fail();
        } catch (InvalidRentalAgreementException e){
            assertEquals("Discount percentage 101% outside of range 0-100", e.getMessage());
        }
    }

    @Test
    public void setupRentalAgreementLowDiscountTest(){
        try {
            RentalStore.setupRentalAgreement("LADW", 5, -1, LocalDate.of(2015,9,3));
            fail();
        } catch (InvalidRentalAgreementException e){
            assertEquals("Discount percentage -1% outside of range 0-100", e.getMessage());
        }
    }

    @Test void setupRentalAgreementLowRentalDaysTest(){
        try {
            RentalStore.setupRentalAgreement("LADW", 0, 0, LocalDate.of(2015,9,3));
            fail();
        } catch (InvalidRentalAgreementException e){
            assertEquals("Number of rental days needs to be 1 or greater", e.getMessage());
        }
    }

    @Test
    public void setupRentalAgreementZeroChargeDaysTest(){
        RentalAgreement resultAgreement;

        //try 0 actually charged days
        try {
            resultAgreement = RentalStore.setupRentalAgreement("CHNS", 2, 0, LocalDate.of(2024,8,3));
            assertEquals("CHNS", resultAgreement.getToolCode());
            assertEquals(Tool.ToolType.Chainsaw, resultAgreement.getToolType());
            assertEquals("Stihl", resultAgreement.getToolBrand());
            assertEquals(BigDecimal.valueOf(1.49), resultAgreement.getDailyCharge());
            assertEquals(2, resultAgreement.getRentalDays());
            assertEquals(0, resultAgreement.getDiscountPercent());
            assertEquals(LocalDate.of(2024, 8, 3),resultAgreement.getCheckoutDate());
            assertEquals(LocalDate.of(2024, 8, 4),resultAgreement.getDueDate());
        } catch (InvalidRentalAgreementException e){
            fail(e);
        }
    }

    @Test
    public void setupRentalAgreementWeekend4thOfJulyTest(){
        RentalAgreement resultAgreement;

        //Try charge day that is only the celebrated day of July 4th (in this case only the 3rd)
        try{
            resultAgreement = RentalStore.setupRentalAgreement("LADW", 1, 10, LocalDate.of(2020,7,3));
            assertEquals(0,resultAgreement.getChargeDays());
        } catch (InvalidRentalAgreementException e){
            fail(e);
        }

        //Try charge day that is only the celebrated day of July 4th (in this case only the 5th)
        try{
            resultAgreement = RentalStore.setupRentalAgreement("LADW", 1, 10, LocalDate.of(2021,7,5));
            assertEquals(0,resultAgreement.getChargeDays());
        } catch (InvalidRentalAgreementException e){
            fail(e);
        }
    }

    @Test
    public void test1(){
        RentalAgreement resultAgreement = null;
        try {
            resultAgreement = RentalStore.setupRentalAgreement("JAKR", 5, 101, LocalDate.of(2015,9,3));
        } catch (InvalidRentalAgreementException e) {
            assertEquals("Discount percentage 101% outside of range 0-100", e.getMessage());
        }
        assertNull(resultAgreement);
    }

    @Test
    public void test2(){
        RentalAgreement resultAgreement = null;
        try {
            resultAgreement = RentalStore.setupRentalAgreement("LADW", 3, 10, LocalDate.of(2020,7,2));
        } catch (InvalidRentalAgreementException e) {
            fail(e);
        }
        String expectedAgreement = "toolCode: 'LADW', toolType: 'Ladder', toolBrand: 'Werner', rentalDays: 3, checkoutDate: 07/02/20, dueDate: 07/04/20, dailyCharge: $1.99, chargeDays: 2, preDiscountCharge: $3.98, discountPercent: 10%, discountAmount: $0.40, finalCharge: $3.58";
        assertEquals(expectedAgreement, resultAgreement.formattedOutput());
    }

    @Test
    public void test3(){
        RentalAgreement resultAgreement = null;
        try {
            resultAgreement = RentalStore.setupRentalAgreement("CHNS", 5, 25, LocalDate.of(2015,7,2));
        } catch (InvalidRentalAgreementException e) {
            fail(e);
        }
        String expectedAgreement = "toolCode: 'CHNS', toolType: 'Chainsaw', toolBrand: 'Stihl', rentalDays: 5, checkoutDate: 07/02/15, dueDate: 07/06/15, dailyCharge: $1.49, chargeDays: 3, preDiscountCharge: $4.47, discountPercent: 25%, discountAmount: $1.12, finalCharge: $3.35";
        assertEquals(expectedAgreement, resultAgreement.formattedOutput());
    }

    @Test
    public void test4(){
        RentalAgreement resultAgreement = null;
        try {
            resultAgreement = RentalStore.setupRentalAgreement("JAKD", 6, 0, LocalDate.of(2015,9,3));
        } catch (InvalidRentalAgreementException e) {
            fail(e);
        }
        String expectedAgreement = "toolCode: 'JAKD', toolType: 'Jackhammer', toolBrand: 'DeWalt', rentalDays: 6, checkoutDate: 09/03/15, dueDate: 09/08/15, dailyCharge: $2.99, chargeDays: 3, preDiscountCharge: $8.97, discountPercent: 0%, discountAmount: $0.00, finalCharge: $8.97";
        assertEquals(expectedAgreement, resultAgreement.formattedOutput());
    }

    @Test
    public void test5(){
        RentalAgreement resultAgreement = null;
        try {
            resultAgreement = RentalStore.setupRentalAgreement("JAKR", 9, 0, LocalDate.of(2015,7,2));
        } catch (InvalidRentalAgreementException e) {
            fail(e);
        }
        String expectedAgreement = "toolCode: 'JAKR', toolType: 'Jackhammer', toolBrand: 'Ridgid', rentalDays: 9, checkoutDate: 07/02/15, dueDate: 07/10/15, dailyCharge: $2.99, chargeDays: 6, preDiscountCharge: $17.94, discountPercent: 0%, discountAmount: $0.00, finalCharge: $17.94";
        assertEquals(expectedAgreement, resultAgreement.formattedOutput());
    }

    @Test
    public void test6(){
        RentalAgreement resultAgreement = null;
        try {
            resultAgreement = RentalStore.setupRentalAgreement("JAKR", 4, 50, LocalDate.of(2020,7,2));
        } catch (InvalidRentalAgreementException e) {
            fail(e);
        }
        String expectedAgreement = "toolCode: 'JAKR', toolType: 'Jackhammer', toolBrand: 'Ridgid', rentalDays: 4, checkoutDate: 07/02/20, dueDate: 07/05/20, dailyCharge: $2.99, chargeDays: 1, preDiscountCharge: $2.99, discountPercent: 50%, discountAmount: $1.50, finalCharge: $1.50";
        assertEquals(expectedAgreement, resultAgreement.formattedOutput());
    }
}
