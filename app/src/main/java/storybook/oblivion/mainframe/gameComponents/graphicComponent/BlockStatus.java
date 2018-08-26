package storybook.oblivion.mainframe.gameComponents.graphicComponent;

import androidEngine.androidGraphics.graphicComponents.ObjectStatus;

/**
 * Created by andrew on 11/20/17.
 */

public class BlockStatus extends ObjectStatus {
    //
    //   VALUES
    //
    public static final String TOUCHING_FLOOR = "TOUCH FLOOR";
    public static final String TOUCHING_CEIL = "TOUCH CEILING";
    public static final String TOUCHING_LEFT_SIDE = "TOUCH LEFT";
    public static final String TOUCHING_RIGHT_SIDE = "TOUCH RIGHT";

    public BlockStatus(String v) {
        super(v);
    }

    @Override
    public String getValue() {
        return super.getValue();
    }
}
