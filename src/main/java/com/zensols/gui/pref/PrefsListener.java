/*
This file is part of the Zenos Solutions GUI Preferences Library (ZSGPL).

ZSGPL is free software: you can redistribute it and/or modify it under the
terms of the GNU Lesser General Public License as published by the Free
Software Foundation, either version 3 of the License, or (at your option) any
later version.

ZSGPL is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with
ZSGPL.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.zensols.gui.pref;

import java.util.prefs.Preferences;

/**
 * Implementations are receivers of preferences events initiated by {@link
 * PrefSupport}.
 *
 * @author Paul Landes
 */
public interface PrefsListener {
    /**
     * We're persisting preferences.
     * @param prefs recipient for the preference updates
     */
    public void persist(Preferences prefs);

    /**
     * We're unpersisting preferences.
     * @param prefs contains the previously persisted preference updates
     */
    public void unpersist(Preferences prefs);
}
