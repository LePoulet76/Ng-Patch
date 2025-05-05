package net.ilexiconn.nationsgui.forge.server.permission;

import net.ilexiconn.nationsgui.forge.server.permission.PermissionType$1;

public enum PermissionType {

   CATEGORY("CATEGORY", 0),
   MAIN_BADGE("MAIN_BADGE", 1),
   SHOP("SHOP", 2),
   PREMIUM("PREMIUM", 3),
   ASSISTANCE_MODERATION("ASSISTANCE_MODERATION", 4),
   RADIO_MODERATION("RADIO_MODERATION", 5),
   URL_ADMIN("URL_ADMIN", 6),
   GECKO_DIALOG_ADMIN("GECKO_DIALOG_ADMIN", 7);
   // $FF: synthetic field
   private static final PermissionType[] $VALUES = new PermissionType[]{CATEGORY, MAIN_BADGE, SHOP, PREMIUM, ASSISTANCE_MODERATION, RADIO_MODERATION, URL_ADMIN, GECKO_DIALOG_ADMIN};


   private PermissionType(String var1, int var2) {}

   public abstract String getPermission(String ... var1);

   // $FF: synthetic method
   PermissionType(String x0, int x1, PermissionType$1 x2) {
      this(x0, x1);
   }

}
