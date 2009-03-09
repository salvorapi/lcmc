/*
 * This file is part of DRBD Management Console by LINBIT HA-Solutions GmbH
 * written by Rasto Levrinc.
 *
 * Copyright (C) 2009, LINBIT HA-Solutions GmbH.
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


package drbd.data.resources;

/**
 * This class holds data of a service.
 *
 * @author Rasto Levrinc
 * @version $Id$
 *
 */
public class Service extends Resource {
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;
    /** Id is heartbeatId whithout name of the service. */
    private String id = null;
    /** Heartbeat id of this service. */
    private String heartbeatId = null;
    /** Whether the service is newly allocated. */
    private boolean newService = false;
    /** Whether the service is removed. */
    private boolean removed = false;
    /** Whether the service is being removed. */
    private boolean removing = false;
    /** Whether the service is modified. */
    private boolean modified = false;
    /** Whether the service is being modified. */
    private boolean modifying = false;
    /** Heartbeat class:  heartbeat, ocf, lsb. */
    private String heartbeatClass = null;
    /** Heartbeat id prefix for resource. */
    private static final String RES_ID_PREFIX = "res_";
    /** Heartbeat id prefix for group. */
    private static final String GRP_ID_PREFIX = "grp_";

    /**
     * Prepares a new <code>Service</code> object.
     *
     * @param name
     *          name of this service.
     */
    public Service(final String name) {
        super(name);
    }

    /**
     * Returns id that is used in the heartbeat for this service.
     */
    public final String getHeartbeatId() {
        return heartbeatId;
    }

    /**
     * Returns id of the service. This is usually heartbeat id without service
     * name and underscore part.
     */
    public final String getId() {
        return id;
    }

    /**
     * Sets heartbeat id and gui id without the service name part.
     */
    public final void setHeartbeatId(final String heartbeatId) {
        this.heartbeatId = heartbeatId;
        if ("Group".equals(getName())) {
            if (heartbeatId.equals(GRP_ID_PREFIX)) {
                id = "";
            } else if (heartbeatId.startsWith(GRP_ID_PREFIX)) {
                id = heartbeatId.substring(GRP_ID_PREFIX.length() + 1);
            } else {
                id = heartbeatId;
            }
        } else {
            if (heartbeatId.startsWith(RES_ID_PREFIX + getName() + "_")) {
                id = heartbeatId.substring((RES_ID_PREFIX + getName()).length()
                                           + 1);
            } else {
                id = heartbeatId;
            }
        }
        setValue("id", id);
    }

    /**
     * Sets the id and heartbeat id.
     */
    public final void setId(final String id) {
        if (this.id != null) {
            /* set id only once */
            return;
        }
        this.id = id;
        if ("Group".equals(getName())) {
            if (id.startsWith(GRP_ID_PREFIX)) {
                heartbeatId = id;
            } else {
                heartbeatId = GRP_ID_PREFIX + id;
            }
        } else {
            if (id.startsWith(RES_ID_PREFIX + getName() + "_")) {
                heartbeatId = id;
            } else {
                heartbeatId = RES_ID_PREFIX + getName() + "_" + id;
            }
        }
        setValue("id", id);
    }

    /**
     * Sets whether the service is newly allocated.
     */
    public final void setNew(final boolean newService) {
        this.newService = newService;
    }

    /**
     * Returns whether the service is newly allocated.
     */
    public final boolean isNew() {
        return newService;
    }

    /**
     * Sets whether the service was removed.
     */
    public final void setRemoved(final boolean removed) {
        this.removed = removed;
        if (removed) {
            removing = true;
        }
    }

    /**
     * Returns whether the service was removed.
     */
    public final boolean isRemoved() {
        return removed || removing;
    }

    /**
     * Sets that the service was done being removed.
     */
    public final void doneRemoving() {
        this.removing = removing;
    }

    /**
     * Sets whether the service was modified.
     */
    public final void setModified(final boolean modified) {
        this.modified = modified;
        if (modified) {
            modifying = true;
        }
    }

    /**
     * Sets that the service is done being modified.
     */
    public final void doneModifying() {
        modifying = false;
    }

    /**
     * Makes the service available, after the status as seen in the gui was
     * confirmed from the heartbeat.
     */
    public final void setAvailable() {
        newService      = false;
        modified = false;
        removed  = false;
    }

    /**
     * Returns whether the service is available. It is not available if it was
     * just created, it was just removed or modified.
     */
    public final boolean isAvailable() {
        return !newService && !modified && !removed && !modifying && !removing;
    }

    /**
     * Sets heartbeat resource class heartbeat (old style), ocf, lsb (from
     * init.d).
     */
    public final void setHeartbeatClass(final String heartbeatClass) {
        this.heartbeatClass = heartbeatClass;
    }

    /**
     * Returns the heartbeat class of this service.
     */
    public final String getHeartbeatClass() {
        return heartbeatClass;
    }
}
