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

/**
 * Intances of this class "remember" their size and location via the
 * preferences system.
 *
 * @author Paul landes
 */
public class ConfigPrefFrame extends PrefFrame {
    protected PrefSupport prefSupport;

    private boolean init;

    /**
     * Construct with a prefernces support.
     *
     * @param prefSupport used for this frame instance
     * @param prefix a string used as part of the Java preferences namespace to
     * identify this component
     */
    public ConfigPrefFrame(PrefSupport prefSupport, String prefix) {
	super(prefix);
	this.prefSupport = prefSupport;
    }

    /**
     * Construct with a prefernces support.
     *
     * @param packageOwner class name is used for the preferences Java
     * preferences namespace
     * @param prefix a string used as part of the Java preferences namespace to
     * identify this component
     */
    public ConfigPrefFrame(Class packageOwner, String prefix) {
	super(prefix);
	prefSupport = new PrefSupport
	    (packageOwner != null ? packageOwner : getClass());
    }

    /**
     * Construct with a prefernces support.
     *
     * @param prefix a string used as part of the Java preferences namespace to
     * identify this component
     */
    public ConfigPrefFrame(String prefix) {
	this((Class)null, prefix);
    }

    /** Called by the framework to configure the component (for subclass
     * overriding). */
    protected void configure() {}

    /**
     * Called by the framework to configure for the first time (no preferences).
     * The default behavior is to invoke {@link #pack}.
     */
    protected void nascentConfigure() {
	pack();
    }

    public PrefSupport getPrefSupport() {
	return prefSupport;
    }

    /**
     * Called by the framework to initialize this component.
     * @see #configure
     * @see #nascentConfigure
     */
    public void init() {
	if (!init) {
	    setPrefSupport(prefSupport);
	    initPrefSupport();

	    configure();

	    prefSupport.setEnabled(true);

	    if (!prefSupport.hasPreferences()) {
		nascentConfigure();
	    } else {
		prefSupport.unpersist();
	    }

	    init = true;
	}
    }
}
