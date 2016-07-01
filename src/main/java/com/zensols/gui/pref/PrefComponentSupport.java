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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.prefs.Preferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class takes care of the footwork to synchronize {@link Component}
 * data with the Java preferences system proxied through a {@link
 * PrefSupport}.  This data includes the size and lcoation of a window.
 *
 * @author Paul Landes
 */
public class PrefComponentSupport {
    private static final Log log = LogFactory.getLog(PrefComponentSupport.class);

    private Component delegate;
    private PrefSupport prefs;

    private String locxProp;
    private String locyProp;
    private String sizeWidthProp;
    private String sizeHeightProp;

    /**
     * Create with the component to sync size/location.
     *
     * @param delegate the component with which to proxy prefs
     * @param prefix a string used as part of the Java preferences namespace to
     * identify this component
     */
    public PrefComponentSupport(Component delegate, String prefix) {
        this.delegate = delegate;
        locxProp = prefix + ".loc.x";
        locyProp = prefix + ".loc.y";
        sizeWidthProp = prefix + ".size.width";
        sizeHeightProp = prefix + ".size.height";
    }

    public PrefSupport getPrefSupport() {
        return prefs;
    }

    public void setPrefSupport(PrefSupport prefs) {
        this.prefs = prefs;
    }

    /**
     * Initialize a nascent preferences state for the component.
     */
    public void initPrefSupport() {
        Point loc = new Point(100, 100);
        Dimension size = new Dimension(700, 900);
        initPrefSupport(loc, size);
    }


    /**
     * Initialize a nascent preferences state for the component.
     * @param initLocation the initial location of the component
     * @param initSize the initial size of the component
     */
    public void initPrefSupport(final Point initLocation,
				final Dimension initSize) {
        if (log.isDebugEnabled()) {
            log.debug("configuring prefs");
        }
        prefs.addListener(new PrefsListener() {
            public void persist(Preferences prefs) {
                Dimension size = delegate.getSize();
                Point location = delegate.getLocation();

                prefs.putInt(locxProp, location.x);
                prefs.putInt(locyProp, location.y);
                prefs.putInt(sizeWidthProp, size.width);
                prefs.putInt(sizeHeightProp, size.height);
            }

            public void unpersist(Preferences prefs) {
                Point location = new Point(
		    prefs.getInt(locxProp, initLocation.x),
		    prefs.getInt(locyProp, initLocation.y));
                Dimension size = new Dimension(
                    prefs.getInt(sizeWidthProp, initSize.width),
                    prefs.getInt(sizeHeightProp, initSize.height));

                delegate.setLocation(location);
                delegate.setSize(size);
            }});

        delegate.addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                prefs.persist();
            }
            public void componentResized(ComponentEvent e) {
                prefs.persist();
            }});
    }
}
