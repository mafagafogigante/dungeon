package org.dungeon.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * CommandHistory class that is used to keep track of all the commands issued by the player.
 *
 * Created by Bernardo Sulzbach on 23/09/2014.
 */
public class CommandHistory implements Serializable {

    private List<String> userCommands;

    public CommandHistory() {
        this.userCommands = new ArrayList<String>();
    }

    public void addCommand(String command) {
        userCommands.add(command);
    }

    public int getCommandCount() {
        return userCommands.size();
    }
}