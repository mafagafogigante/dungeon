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

/**
 * Enumerated types of the parts of the day.
 * <p/>
 * Created by Bernardo Sulzbach on 24/09/2014.
 */
public enum PartOfDay {

    AFTERNOON("Afternoon", 0.8),
    DAWN("Dawn", 0.5),
    DUSK("Dusk", 0.4),
    EVENING("Evening", 0.3),
    MIDNIGHT("Midnight", 0.1),
    MORNING("Morning", 0.7),
    NIGHT("Night", 0.2),
    NOON("Noon", 1.0);

    private final String stringRepresentation;

    private double luminosity;

    PartOfDay(String stringRepresentation, double luminosity) {
        this.stringRepresentation = stringRepresentation;
        this.luminosity = luminosity;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    public double getLuminosity() { return luminosity; }

}
