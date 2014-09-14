/* 
 * Copyright (C) 2014 Bernardo Sulzbach
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Campaign implements Serializable {

    private final ArrayList<Achievement> campaignAchievements;
    private final BattleCounter campaignBattleCounter;
    private final World campaignWorld;
    private final Hero campaignHero;

    private boolean saved;

    public Campaign() {
        campaignBattleCounter = new BattleCounter();
        campaignAchievements = createDemoAchievements();
        campaignHero = new Hero("Seth");
        campaignHero.setWeapon(new Weapon("Stick", 6, 20));
        campaignWorld = createDemoWorld();
    }

    private ArrayList<Achievement> createDemoAchievements() {
        ArrayList<Achievement> achievements = new ArrayList<>();

        BattleCounter suicideSolutionRequirements = new BattleCounter();
        suicideSolutionRequirements.setCounter(CreatureID.HERO, 1);
        achievements.add(new Achievement("Suicide Solution", "Attempt to kill yourself.", suicideSolutionRequirements));

        return achievements;
    }

    private World createDemoWorld() {
        World world = new World(new Location("Training Grounds"), campaignHero);

        world.addCreature(new Creature(CreatureID.BAT, 1), 0);
        world.addCreature(new Creature(CreatureID.BEAR, 1), 0);
        world.addCreature(new Creature(CreatureID.RABBIT, 1), 0);
        world.addCreature(new Creature(CreatureID.RAT, 1), 0);
        world.addCreature(new Creature(CreatureID.SPIDER, 1), 0);
        world.addCreature(new Creature(CreatureID.WOLF, 1), 0);
        world.addCreature(new Creature(CreatureID.ZOMBIE, 1), 0);

        Weapon longSword = new Weapon("Longsword", 18, 15);
        longSword.setDestructible(true);

        world.addItem(longSword, 0);

        return world;
    }

    public BattleCounter getBattleCounter() {
        return campaignBattleCounter;
    }

    public World getWorld() {
        return campaignWorld;
    }

    public Hero getHero() {
        return campaignHero;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * Refreshes the campaign. Should be called after the player plays a turn.
     */
    public void refresh() {
        for (Achievement a : campaignAchievements) {
            a.update(campaignBattleCounter);
        }
    }

}
