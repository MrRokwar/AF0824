package Models;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RentalAgreement {
    private String toolCode;
    private Tool.ToolType toolType;
    private String toolBrand;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate  dueDate;
    private BigDecimal dailyCharge;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private int discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public Tool.ToolType getToolType() {
        return toolType;
    }

    public void setToolType(Tool.ToolType toolType) {
        this.toolType = toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public LocalDate  getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate  checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate  getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate  dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(BigDecimal preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(BigDecimal finalCharge) {
        this.finalCharge = finalCharge;
    }

    @Override
    public String toString() {
        return "RentalAgreement{" +
                "toolCode:'" + toolCode + '\'' +
                ", toolType:'" + toolType + '\'' +
                ", toolBrand:'" + toolBrand + '\'' +
                ", rentalDays" + rentalDays +
                ", checkoutDate:" + checkoutDate +
                ", dueDate:" + dueDate +
                ", dailyCharge:" + dailyCharge +
                ", chargeDays:" + chargeDays +
                ", preDiscountCharge:" + preDiscountCharge +
                ", discountPercent:" + discountPercent +
                ", dicountAmount:" + discountAmount +
                ", finalCharge:" + finalCharge +
                '}';
    }

    public String formattedOutput() {
        //Ideally these locales would not be hard coded to the US and could adjust per country
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return "toolCode: '" + toolCode + '\'' +
                ", toolType: '" + toolType + '\'' +
                ", toolBrand: '" + toolBrand + '\'' +
                ", rentalDays: " + rentalDays +
                ", checkoutDate: " + checkoutDate.format(dateFormatter) +
                ", dueDate: " + dueDate.format(dateFormatter) +
                ", dailyCharge: " + NumberFormat.getCurrencyInstance(Locale.US).format(dailyCharge) +
                ", chargeDays: " + chargeDays +
                ", preDiscountCharge: " + NumberFormat.getCurrencyInstance(Locale.US).format(preDiscountCharge) +
                ", discountPercent: " + discountPercent + "%"+
                ", discountAmount: " + NumberFormat.getCurrencyInstance(Locale.US).format(discountAmount) +
                ", finalCharge: " + NumberFormat.getCurrencyInstance(Locale.US).format(finalCharge);
    }
}
