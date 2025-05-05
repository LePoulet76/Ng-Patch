package net.ilexiconn.nationsgui.forge.server.permission;


enum PermissionType$2 {

   PermissionType$2(String var1, int var2) {}

   public String getPermission(String ... extra) {
      return String.format("nationsgui.mainbadge.%s", (Object[])extra);
   }
}
