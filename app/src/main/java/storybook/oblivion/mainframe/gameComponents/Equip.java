package storybook.oblivion.mainframe.gameComponents;

/**
 * Created by andrew on 9/24/17.
 */

public class Equip extends Item {
    public int attack;
    public int defense;
    public int health;
    public Equip(int att, int def, int hp) {
        attack = att;
        defense = def;
        health = hp;
    }


}
