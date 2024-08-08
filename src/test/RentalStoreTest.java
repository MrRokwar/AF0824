package test;

import Exceptions.InvalidRentalAgreementException;
import Models.RentalAgreement;
import Services.RentalStore;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class RentalStoreTest {

//    @Test
//    public void setupRentalAgreementTest(){
//        try {
//            RentalAgreement resultAgreement = RentalStore.setupRentalAgreement("BLUR", 5, 101, LocalDate.of(2015,9,3));
//        } catch (InvalidRentalAgreementException e) {
//            fail(e);
//        }
//    }

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
        String expectedAgreement = "toolCode: 'LADW', toolType: 'Ladder', toolBrand: 'Werner', rentalDays: 3, checkoutDate: 07/02/20, dueDate: 07/05/20, dailyCharge: $1.99, chargeDays: 3, preDiscountCharge: $5.97, discountPercent: 10%, discountAmount: $0.60, finalCharge: $5.37";
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
        String expectedAgreement = "toolCode: 'CHNS', toolType: 'Chainsaw', toolBrand: 'Stihl', rentalDays: 5, checkoutDate: 07/02/15, dueDate: 07/07/15, dailyCharge: $1.49, chargeDays: 4, preDiscountCharge: $5.96, discountPercent: 25%, discountAmount: $1.49, finalCharge: $4.47";
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
        String expectedAgreement = "toolCode: 'JAKD', toolType: 'Jackhammer', toolBrand: 'DeWalt', rentalDays: 6, checkoutDate: 09/03/15, dueDate: 09/09/15, dailyCharge: $2.99, chargeDays: 4, preDiscountCharge: $11.96, discountPercent: 0%, discountAmount: $0.00, finalCharge: $11.96";
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
        String expectedAgreement = "toolCode: 'JAKR', toolType: 'Jackhammer', toolBrand: 'Ridgid', rentalDays: 9, checkoutDate: 07/02/15, dueDate: 07/11/15, dailyCharge: $2.99, chargeDays: 6, preDiscountCharge: $17.94, discountPercent: 0%, discountAmount: $0.00, finalCharge: $17.94";
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
        String expectedAgreement = "toolCode: 'JAKR', toolType: 'Jackhammer', toolBrand: 'Ridgid', rentalDays: 4, checkoutDate: 07/02/20, dueDate: 07/06/20, dailyCharge: $2.99, chargeDays: 2, preDiscountCharge: $5.98, discountPercent: 50%, discountAmount: $2.99, finalCharge: $2.99";
        assertEquals(expectedAgreement, resultAgreement.formattedOutput());
    }
}
