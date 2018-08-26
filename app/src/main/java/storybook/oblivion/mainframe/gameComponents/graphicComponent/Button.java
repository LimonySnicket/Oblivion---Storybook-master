package storybook.oblivion.mainframe.gameComponents.graphicComponent;

import androidEngine.androidGraphics.graphicComponents.GraphicAnimation;
import androidEngine.androidGraphics.graphicComponents.GraphicObject;
import androidEngine.androidGraphics.graphicComponents.ObjPosition;

/**
 * Created by SN317602 on 12/6/2017.
 */

public class Button extends GraphicObject {
    public Button(ObjPosition position, GraphicAnimation animation) {
        super(position, 0, animation, 0, 0, 0);
    }

    public void clicked() {

    }
}
