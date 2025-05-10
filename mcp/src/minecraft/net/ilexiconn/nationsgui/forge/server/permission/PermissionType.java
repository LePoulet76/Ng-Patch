package net.ilexiconn.nationsgui.forge.server.permission;

import net.ilexiconn.nationsgui.forge.server.permission.PermissionType$1;

public enum PermissionType
{
    CATEGORY,
    MAIN_BADGE,
    SHOP,
    PREMIUM,
    ASSISTANCE_MODERATION,
    RADIO_MODERATION,
    URL_ADMIN,
    GECKO_DIALOG_ADMIN;

    public abstract String getPermission(String ... var1);

    PermissionType(PermissionType$1 x2)
    {
        this(x0, x1);
    }
}
