package androidEngine.androidGraphics.graphicComponents;

/**
 * Created by andrew on 11/20/17.
 */

public class ObjectStatus {
    //
    //   Used by other Objects to Track Status
    //   Object Status will be checked each Update;
    //
    public static final String IS_ALIVE = "IS ALIVE";

    protected final String value;
    public ObjectStatus(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }
}
