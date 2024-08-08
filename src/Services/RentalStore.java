package Services;

import Exceptions.InvalidRentalAgreementException;
import Models.RentalAgreement;
import Models.Tool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalStore {
    public static void main(String[] args){
        rentalAgreementFromUserInput("JAKR", 5, 101, "9/3/15");

        rentalAgreementFromUserInput("LADW", 3, 10, "7/2/20");

        rentalAgreementFromUserInput("CHNS", 5, 25, "7/2/15");

        rentalAgreementFromUserInput("JAKD", 6, 0, "9/3/15");

        rentalAgreementFromUserInput("JAKR", 9, 0, "7/2/15");

        rentalAgreementFromUserInput("JAKR", 4, 50, "7/2/20");
    }


    //Simulates what the call would be from user input and outputs rental agreement at the end
    public static void rentalAgreementFromUserInput(String toolCode, int rentalDayCount, int discountPercent, String checkoutDate){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yy");
        LocalDate parsedCheckoutDate = LocalDate.parse(checkoutDate, dateFormatter);

        //These system outs would normally be parsed into something else
        try{
            RentalAgreement completedAgreement = setupRentalAgreement(toolCode, rentalDayCount, discountPercent, parsedCheckoutDate);
            System.out.println(completedAgreement.formattedOutput());
        }catch (InvalidRentalAgreementException invalidRentalAgreementException){
            System.out.println(invalidRentalAgreementException.getMessage());
        }catch (IllegalArgumentException illegalArgumentException){
            System.out.println("Invalid Tool Code");
        }
    }

    public static RentalAgreement setupRentalAgreement(String toolCode, int rentalDayCount, int discountPercent, LocalDate checkoutDate) throws InvalidRentalAgreementException {
        RentalAgreement rentalAgreement = new RentalAgreement();
        Tool selectedTool = ToolFactory.getTool(toolCode);

        rentalAgreement.setToolCode(selectedTool.getCode());
        rentalAgreement.setToolType(selectedTool.getToolType());
        rentalAgreement.setToolBrand(selectedTool.getBrand());
        rentalAgreement.setDailyCharge(selectedTool.getCostPerDay());
        rentalAgreement.setRentalDays(rentalDayCount);
        rentalAgreement.setDiscountPercent(discountPercent);
        rentalAgreement.setCheckoutDate(checkoutDate);

        RentalAgreementCalculator rentalAgreementCalculator = new RentalAgreementCalculator();

        return rentalAgreementCalculator.calculateRentalAgreement(rentalAgreement);
    }
}
