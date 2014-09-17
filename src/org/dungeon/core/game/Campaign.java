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
package org.dungeon.core.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dungeon.core.achievements.Achievement;
import org.dungeon.core.counters.CounterMap;
import org.dungeon.core.creatures.Creature;
import org.dungeon.core.creatures.CreatureID;
import org.dungeon.core.creatures.CreaturePreset;
import org.dungeon.core.creatures.Hero;
import org.dungeon.io.IO;
import org.dungeon.utils.Constants;
import org.dungeon.utils.Utils;

//
// Why is this calss marked final?
//
// A quote from Effective Java (2nd)
//
// There are a few more restrictions that a class must obey to allow inheritance.
//
// Constructors must not invoke overridable methods, directly or indirectly. If you violate this rule, program failure will result. The
// superclass constructor runs before the subclass constructor, so the overriding method in the subclass will be invoked before the subclass
// constructor has run. If the overriding method depends on any initialization performed by the subclass constructor, the method will not
// behave as expected.
//
// Bernardo Sulzbach (mafagafogigante) [16/09/2014]: although I can avoid the usage of setters in the constructor, I decided to follow the 
// item 17 in the above-mentioned book "Design and document for inheritance, or else prohibit it.";
//
public final class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Achievement> campaignAchievements;
    private final CounterMap<CreatureID> campaignBattleCounter;
    private final World campaignWorld;
    private final Hero campaignHero;
    private Point heroPosition;

    private boolean saved;

    private int totalAchievementsCount;
    private int unlockedAchievementsCount;

    public Campaign() {
        campaignBattleCounter = new CounterMap<CreatureID>();

        campaignAchievements = createDemoAchievements();

        // Set the number of achievements the campaign has.
        setTotalAchievementsCount(campaignAchievements.size());

        campaignHero = new Hero("Seth");
        heroPosition = new Point(0, 0);
        campaignHero.setWeapon(new Weapon("Stick", 6, 20));

        campaignWorld = createDemoWorld();
    }

    private List<Achievement> createDemoAchievements() {
        List<Achievement> achievements = new ArrayList<Achievement>();

        CounterMap suicideSolutionRequirements = new CounterMap<CreatureID>();
        CounterMap baneRequirements = new CounterMap<CreatureID>();
        CounterMap catRequirements = new CounterMap<CreatureID>();
        CounterMap evilBastardRequirements = new CounterMap<CreatureID>();
        CounterMap stayDeadRequirements = new CounterMap<CreatureID>();

        // Suicide Solution requires one battle against the Hero himself.
        suicideSolutionRequirements.incrementCounter(CreatureID.HERO);
        // Bane requires six battles against bats.
        baneRequirements.incrementCounter(CreatureID.BAT, 6);
        // Cat requires four battles against rats.
        catRequirements.incrementCounter(CreatureID.RAT, 4);
        // Evil Bastard requires one battle against a rabbit.
        evilBastardRequirements.incrementCounter(CreatureID.RABBIT);
        // Stay Dead requires two battles against a zombie.
        stayDeadRequirements.incrementCounter(CreatureID.ZOMBIE, 2);

        achievements.add(new Achievement("Suicide Solution", "Attempt to kill yourself.", suicideSolutionRequirements));
        achievements.add(new Achievement("Bane", "Kill 6 bats.", baneRequirements));
        achievements.add(new Achievement("Cat", "Kill 4 rats.", catRequirements));
        achievements.add(new Achievement("Evil Bastard", "Kill an innocent rabbit.", evilBastardRequirements));
        achievements.add(new Achievement("Stay Dead", "Kill 2 zombies.", stayDeadRequirements));

        return achievements;
    }

    private World createDemoWorld() {
        World world = new World();
        Point startingPoint = new Point(0, 0);
        world.addLocation(new Location("Forest"), startingPoint);
        // The hero
        world.addCreature(campaignHero, startingPoint);
        // Beasts
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.BAT, 1, 2), startingPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.RABBIT, 1, 4), startingPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.RAT, 1, 2), startingPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.SPIDER, 1, 1), startingPoint);

        //Items
        world.addItem(new Weapon("Spear", 10, 5), startingPoint);

        Point upPoint = new Point(0, 1);
        world.addLocation(new Location("Clearing"), upPoint);

        //Beasts
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.BAT, 1, 2), upPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.RABBIT, 1, 3), upPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.RAT, 1, 3), upPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.SPIDER, 1, 1), upPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.WOLF, 1, 1), upPoint);

        // Items
        world.addItem(new Weapon("Dagger", 15, 20), upPoint);

        Point rightPoint = new Point(1, 1);
        world.addLocation(new Location("Road to The Fort"), rightPoint);

        //Beasts
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.RAT, 1, 4), rightPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.RABBIT, 1, 2), rightPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.BEAR, 1, 2), rightPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.WOLF, 1, 2), rightPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.ZOMBIE, 1, 3), rightPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.RABBIT, 1, 4), rightPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.SPIDER, 1, 1), rightPoint);

        // Items
        world.addItem(new Weapon("Mace", 18, 15), rightPoint);

        Point leftPoint = new Point(-1, 1);
        world.addLocation(new Location("Cave"), leftPoint);

        //Beasts
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.RAT, 1, 3), leftPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.BAT, 1, 5), leftPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.ZOMBIE, 1, 2), leftPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.BEAR, 1, 3), leftPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.WOLF, 1, 2), leftPoint);
        world.addCreatureArray(Creature.createCreatureArray(CreaturePreset.SPIDER, 1, 1), leftPoint);

        //Items
        world.addItem(new Weapon("Longsword", 25, 17), leftPoint);

        return world;
    }

    public CounterMap getBattleCounter() {
        return campaignBattleCounter;
    }

    public World getWorld() {
        return campaignWorld;
    }

    public Hero getHero() {
        return campaignHero;
    }

    public Point getHeroPoint() {
        return heroPosition;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public int getTotalAchievementsCount() {
        return totalAchievementsCount;
    }

    public void setTotalAchievementsCount(int totalAchievementsCount) {
        this.totalAchievementsCount = totalAchievementsCount;
    }

    public int getUnlockedAchievementsCount() {
        return unlockedAchievementsCount;
    }

    private void incrementUnlockedAchievementsCount() {
        this.unlockedAchievementsCount++;
    }

    /**
     * Prints all unlocked achievements.
     */
    public void printUnlockedAchievements() {
        StringBuilder builder = new StringBuilder();
        builder.append("Progress: ").append(getUnlockedAchievementsCount()).append('/').append(getTotalAchievementsCount());
        for (Achievement a : campaignAchievements) {
            if (a.isUnlocked()) {
                builder.append("\n").append(a.toOneLineString());
            }
        }
        IO.writeString(builder.toString());
    }

    /**
     * Refreshes the campaign. Should be called after the player plays a turn.
     */
    public void refresh() {
        refreshAchievements();
    }

    private void refreshAchievements() {
        for (Achievement a : campaignAchievements) {
            if (a.update(campaignBattleCounter)) {
                incrementUnlockedAchievementsCount();
            }
        }
    }

    public void parseHeroWalk(String[] inputWords) {
        if (inputWords.length == 1) {
            Direction walkDirection = Utils.selectFromList(Arrays.asList(Direction.values()));
            if (walkDirection != null) {
                heroWalk(walkDirection);
            }
            return;
        } else {
            String secondWord = inputWords[1];
            for (Direction dir : Direction.values()) {
                if (dir.toString().equalsIgnoreCase(secondWord)) {
                    heroWalk(dir);
                    return;
                }
            }
        }
        IO.writeString(Constants.INVALID_INPUT);
    }

    public void heroWalk(Direction dir) {
        Point destination = new Point(heroPosition, dir);
        if (getWorld().hasLocation(destination)) {
            getWorld().moveCreature(campaignHero, heroPosition, destination);
            heroPosition = destination;
            campaignHero.setLocation(getWorld().getLocation(destination));
        } else {
            IO.writeString(Constants.WALK_BLOCKED);
        }
    }

}
