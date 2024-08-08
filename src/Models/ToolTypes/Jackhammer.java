package Models.ToolTypes;
import Models.Tool;

import java.math.BigDecimal;

public class Jackhammer extends Tool{

    public Jackhammer(String code, String brand) {
        super(code, brand);
        super.toolType = ToolType.Jackhammer;
        super.costPerDay = BigDecimal.valueOf(2.99);
    }
}
