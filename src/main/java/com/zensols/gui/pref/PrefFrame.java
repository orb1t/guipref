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

import javax.swing.JFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A frame that takes care of all the preferences logic for you.  This is the
 * class you want to extend in most cases.
 *
 * @author Paul Landes
 */
public class PrefFrame extends javax.swing.JFrame {
    private static final Log log = LogFactory.getLog(PrefFrame.class);

    protected PrefComponentSupport prefCompSupport;

    /**
     * Create using this class for the preferences namespace (not
     * preferrable and better to use the other constructor).
     *
     * @param prefix a string used as part of the Java preferences namespace to
     * identify this frame
     */
    public PrefFrame(String prefix) {
        prefCompSupport = new PrefComponentSupport(this, prefix);
    }

    /**
     * Create with a preferences support you already have.
     *
     * @param prefCompSupport used for all preferences configuration for this
     * frame instance
     */
    public PrefFrame(PrefComponentSupport prefCompSupport) {
        this.prefCompSupport = prefCompSupport;
    }

    public PrefSupport getPrefSupport() {
        return prefCompSupport.getPrefSupport();
    }

    public void setPrefSupport(PrefSupport prefs) {
        prefCompSupport.setPrefSupport(prefs);
    }

    /**
     * Overridable method that inits the pref support.
     * @see PrefComponentSupport#initPrefSupport
     */
    protected void initPrefSupport() {
        prefCompSupport.initPrefSupport();       
    }
}
