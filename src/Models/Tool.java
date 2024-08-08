package Models;

import java.math.BigDecimal;

public abstract class Tool {
    public enum ToolType{
        Chainsaw,
        Jackhammer,
        Ladder
    }
    protected String code;
    protected String brand;
    protected ToolType toolType;
    protected BigDecimal costPerDay;

    public Tool(String code, String brand) {
        this.code = code;
        this.brand = brand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public BigDecimal getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(BigDecimal costPerDay) {
        this.costPerDay = costPerDay;
    }
}
