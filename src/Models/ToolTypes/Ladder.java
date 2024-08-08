package Models.ToolTypes;

import Models.Tool;

import java.math.BigDecimal;

public class Ladder extends Tool {
    public Ladder(String code, String brand) {
        super(code, brand);
        super.toolType = ToolType.Ladder;
        super.costPerDay = BigDecimal.valueOf(1.99);
    }
}
