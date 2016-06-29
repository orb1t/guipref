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

public class PrefFrame extends javax.swing.JFrame {
    private static final Log log = LogFactory.getLog(PrefFrame.class);

    protected PrefComponentSupport prefCompSupport;

    public PrefFrame(String prefix) {
        prefCompSupport = new PrefComponentSupport(this, prefix);
    }

    public PrefFrame(PrefComponentSupport prefCompSupport) {
        this.prefCompSupport = prefCompSupport;
    }

    public PrefSupport getPrefSupport() {
        return prefCompSupport.getPrefSupport();
    }

    public void setPrefSupport(PrefSupport prefs) {
        prefCompSupport.setPrefSupport(prefs);
    }

    protected void initPrefSupport() {
        prefCompSupport.initPrefSupport();       
    }
}
