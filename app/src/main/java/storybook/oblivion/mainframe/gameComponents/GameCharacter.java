package storybook.oblivion.mainframe.gameComponents;

import storybook.oblivion.mainframe.gameComponents.graphicComponent.CharacterObject;


/**
 * Created by andrew on 9/23/17.
 */

public class GameCharacter {
    public String name;
    public CharacterObject graphicObject;
    public Item[] inventory;
    public Equip[] equips;
    public int[] stats;

    public GameCharacter() {

    }
}
