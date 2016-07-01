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

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.prefs.Preferences;
import javax.swing.JSplitPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Provides preferences support to store/recall divider position in a {@link
 * JSplitPane}.
 *
 * @author Paul Landes
 */
public class DividerPrefSupport {
    private static final Log log = LogFactory.getLog(DividerPrefSupport.class);

    private String name;
    private JSplitPane  sp;
    private PrefSupport prefs;
    private int initVal;

    public DividerPrefSupport(String name, JSplitPane sp, PrefSupport prefs,
                              int initVal) {
        this.sp = sp;
        this.name = name + ".divider";
        this.prefs = prefs;
        this.initVal = initVal;
        init();
    }

    private void init() {
        sp.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();

                if ("dividerLocation".equals(name)) {
                    prefs.persist();
                }
            }
        });
        prefs.addListener(new PrefsListener() {
            public void persist(Preferences prefs) {
                int loc = sp.getDividerLocation();
                if (log.isDebugEnabled()) {
                    log.debug("persisting divider at location: " + loc);
                }
                prefs.putInt(name, loc);
            }

            public void unpersist(Preferences prefs) {
                int loc = prefs.getInt(name, initVal);
                if (log.isDebugEnabled()) {
                    log.debug("persisting divider at location: " + loc);
                }
                sp.setDividerLocation(loc);
            }
        });
    }
}
