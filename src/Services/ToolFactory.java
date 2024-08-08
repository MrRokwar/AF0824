package Services;

import Models.Tool;
import Models.ToolTypes.Chainsaw;
import Models.ToolTypes.Jackhammer;
import Models.ToolTypes.Ladder;

public class ToolFactory {
    public enum ToolCodes{
        CHNS,
        LADW,
        JAKD,
        JAKR
    }
    public static Tool getTool(String code){
        ToolCodes selectedtoolCode = ToolCodes.valueOf(code);
        //ideally this would be controlled through a database that gave back data for an appropriate tool
        return switch (selectedtoolCode) {
            case CHNS -> new Chainsaw(selectedtoolCode.name(), "Stihl");
            case LADW -> new Ladder(selectedtoolCode.name(), "Werner");
            case JAKD -> new Jackhammer(selectedtoolCode.name(), "DeWalt");
            case JAKR -> new Jackhammer(selectedtoolCode.name(), "Ridgid");
        };
    }
}
