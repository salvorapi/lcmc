/*
 * This file is part of DRBD Management Console by LINBIT HA-Solutions GmbH
 * written by Rasto Levrinc.
 *
 * Copyright (C) 2009-2010, LINBIT HA-Solutions GmbH.
 * Copyright (C) 2009-2010, Rasto Levrinc
 *
 * DRBD Management Console is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * DRBD Management Console is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with drbd; see the file COPYING.  If not, write to
 * the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package drbd.gui.resources;

import drbd.gui.Browser;
import drbd.data.resources.Network;
import drbd.utilities.Tools;

import javax.swing.ImageIcon;

/**
 * This class holds info data for a network.
 */
public class NetworkInfo extends Info {
    /** Network icon. */
    private static final ImageIcon NETWORK_ICON =
        Tools.createImageIcon(
                Tools.getDefault("ClusterBrowser.NetworkIcon"));
    /**
     * Prepares a new <code>NetworkInfo</code> object.
     */
    public NetworkInfo(final String name,
                       final Network network,
                       final Browser browser) {
        super(name, browser);
        setResource(network);
    }

    /**
     * Returns network info.
     */
    public final String getInfo() {
        final String ret = "Network: " + getNetwork().getName()
                           + "\n    IPs: " + getNetwork().getIps()
                           + "\nNetmask: " + getNetwork().getNetMask()
                           + "\n";
        return ret;
    }

    /**
     * Returns network resource object.
     */
    public final Network getNetwork() {
        return (Network) getResource();
    }

    /**
     * Returns menu icon for network.
     */
    public final ImageIcon getMenuIcon(final boolean testOnly) {
        return NETWORK_ICON;
    }
}
