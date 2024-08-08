package Models.ToolTypes;

import Models.Tool;

import java.math.BigDecimal;

public class Chainsaw extends Tool {

    public Chainsaw(String code, String brand) {
        super(code, brand);
        super.toolType = ToolType.Chainsaw;
        super.costPerDay = BigDecimal.valueOf(1.49);
    }

}
