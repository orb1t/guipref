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

/**
 * <p>This class interfaces with the Java preferences system and registers
 * callback for the other framework components in this package.</p>
 * 
 * In order, you want to:
 * <ul>
 * <li>Construct this object and pass it around to your GUI components.</li>
 * <li>Each component call {@link #addListener(PrefsListener)}</li>
 * <li>Call {@link #unpersist} to populate from the Java preferences
 * system.</li>
 * <li>Call {@link #setEnabled(boolean)} with <tt>true</tt>.
 * </ul>
 *
 * @author Paul Landes
 */
public class PrefSupport {
    private static final Log log = LogFactory.getLog(PrefSupport.class);

    /** Used for init pref load state, which can be tricky. */
    private static final String INIT_KEY = "prefs.init";

    private List<PrefsListener> listeners;
    private Preferences prefs;
    private boolean unpersisted;
    private boolean unpersisting;
    private boolean enabled;

    /**
     * Construct with a class from your application.
     *
     * @param packageNode class name is used for the preferences Java
     * preferences namespace
     * @see Preferences#userNodeForPackage(Class)
     */
    public PrefSupport(Class packageNode) {
        if (log.isDebugEnabled()) {
	    log.debug("initializing pref support with pkg node from: " +
		      packageNode);
	}
        prefs = Preferences.userNodeForPackage(packageNode);
        listeners = new java.util.LinkedList();
        enabled = false;
    }

    /**
     * Register a call back listener called for preference (load) updates.
     * @param listener gets invoked with preference persists and unpersists
     */
    public void addListener(PrefsListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    /**
     * @return if we have preferences, which can be important for initial
     * preference load/setting.
     */
    public boolean hasPreferences() {
        return prefs.getBoolean(INIT_KEY, false);
    }

    /**
     * @return if we've unpersisted yet or not, which can be important for
     * initial preference load/settings.
     */
    public boolean unpersisted() {
        return unpersisted;
    }

    /** @return the Java system preferences. */
    public Preferences getPreferences() {
	return prefs;
    }

    /**
     * Unpersist, which invokes all the callback (observer/listeners).
     * Note this does nothing if this instance is not enabled.
     * @see #setEnabled(boolean)
     */
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

    /**
     * Persist the current state of this object to the Java preferences system.
     * Note this does nothing if the instance isn't enabled.
     * @see #setEnabled(boolean)
     */
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

    /**
     * @return the enabled state
     * @see #persist
     */
    public boolean getEanbled() {
        return enabled;
    }

    /**
     * Set the enabled state.
     * @param enabled <tt>true</tt> if the instance is enabled
     * @see #persist
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
