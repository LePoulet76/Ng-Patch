/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;

public enum PermissionCache {
    INSTANCE;

    private Map<String, Boolean> permissionMap = new HashMap<String, Boolean>();
    private Map<String, IPermissionCallback> callbackMap = new HashMap<String, IPermissionCallback>();
    private List<String> queue = new ArrayList<String>();

    public void addPermission(String permission, Boolean has) {
        this.permissionMap.put(permission, has);
        this.queue.remove(permission);
        if (this.callbackMap.containsKey(permission)) {
            this.callbackMap.get(permission).call(permission, has);
            this.callbackMap.remove(permission);
        }
    }

    public Boolean checkPermission(PermissionType type, String ... extra) {
        return this.checkPermission(type, (IPermissionCallback)null, extra);
    }

    public Boolean checkPermission(PermissionType type, IPermissionCallback callback, String ... extra) {
        for (int i = 0; i < extra.length; ++i) {
            extra[i] = extra[i].toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        }
        String permission = type.getPermission(extra);
        if (this.permissionMap.containsKey(permission)) {
            boolean has = this.permissionMap.get(permission);
            if (callback != null) {
                callback.call(permission, has);
            }
            return has;
        }
        if (!this.queue.contains(permission)) {
            if (callback != null) {
                this.callbackMap.put(permission, callback);
            }
            this.queue.add(permission);
            PacketCallbacks.PERMISSION.send(permission);
        }
        return null;
    }

    public void clearCache() {
        this.queue.clear();
        this.permissionMap.clear();
        this.callbackMap.clear();
    }
}

