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

import java.util.LinkedList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrefSupport {
    private static final Log log = LogFactory.getLog(PrefSupport.class);

    private static final String INIT_KEY = "prefs.init";
    private List<PrefsListener> listeners;
    private Preferences prefs;
    private boolean unpersisted;
    private boolean unpersisting;
    private boolean enabled;

    public PrefSupport(Class packageNode) {
        if (log.isDebugEnabled()) log.debug("initializing pref support with pkg node from: " + packageNode);
        prefs = Preferences.userNodeForPackage(packageNode);
        listeners = new java.util.LinkedList();
        enabled = false;
    }

    public void addListener(PrefsListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public boolean hasPreferences() {
        return prefs.getBoolean(INIT_KEY, false);
    }

    public boolean unpersisted() {
        return unpersisted;
    }

    public Preferences getPreferences() {
	return prefs;
    }

    public void unpersist() {
        final Runnable r = new Runnable() {
            public void run() {
                synchronized (listeners) {
                    unpersisting = true;

                    try {
                        for (PrefsListener l : listeners) {
                            l.unpersist(prefs);
                        }
                    }
                    finally {
                        unpersisting = false;
                    }
                }
            }
        };

        if (enabled) {
            if (log.isTraceEnabled()) {
                log.trace("_un_persisting...");
            }
            if (SwingUtilities.isEventDispatchThread()) {
                r.run();
            } else {
                try {
                    SwingUtilities.invokeAndWait(r);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        unpersisted = true;
    }

    public void persist() {
        final Runnable r = new Runnable() {
            public void run() {
                synchronized (listeners) {
                    if (!unpersisting) {
                        for (PrefsListener l : listeners) {
                            l.persist(prefs);
                        }
                    }

                    prefs.putBoolean(INIT_KEY, true);

                    try {
                        prefs.sync();
                    }
                    catch (java.util.prefs.BackingStoreException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        if (enabled) {
            if (log.isTraceEnabled()) {
                log.trace("persisting...");
            }
            if (SwingUtilities.isEventDispatchThread()) {
                r.run();
            }
            else {
                try {
                    SwingUtilities.invokeAndWait(r);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean getEanbled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

