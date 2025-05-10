package net.ilexiconn.nationsgui.forge.server.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;

public enum PermissionCache
{
    INSTANCE;
    private Map<String, Boolean> permissionMap = new HashMap();
    private Map<String, IPermissionCallback> callbackMap = new HashMap();
    private List<String> queue = new ArrayList();

    public void addPermission(String permission, Boolean has)
    {
        this.permissionMap.put(permission, has);
        this.queue.remove(permission);

        if (this.callbackMap.containsKey(permission))
        {
            ((IPermissionCallback)this.callbackMap.get(permission)).call(permission, has.booleanValue());
            this.callbackMap.remove(permission);
        }
    }

    public Boolean checkPermission(PermissionType type, String ... extra)
    {
        return this.checkPermission(type, (IPermissionCallback)null, extra);
    }

    public Boolean checkPermission(PermissionType type, IPermissionCallback callback, String ... extra)
    {
        for (int permission = 0; permission < extra.length; ++permission)
        {
            extra[permission] = extra[permission].toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        }

        String var6 = type.getPermission(extra);

        if (this.permissionMap.containsKey(var6))
        {
            boolean has = ((Boolean)this.permissionMap.get(var6)).booleanValue();

            if (callback != null)
            {
                callback.call(var6, has);
            }

            return Boolean.valueOf(has);
        }
        else
        {
            if (!this.queue.contains(var6))
            {
                if (callback != null)
                {
                    this.callbackMap.put(var6, callback);
                }

                this.queue.add(var6);
                PacketCallbacks.PERMISSION.send(new String[] {var6});
            }

            return null;
        }
    }

    public void clearCache()
    {
        this.queue.clear();
        this.permissionMap.clear();
        this.callbackMap.clear();
    }
}
