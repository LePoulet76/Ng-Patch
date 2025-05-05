package net.ilexiconn.nationsgui.forge.server.permission;


enum PermissionType$1 {

   PermissionType$1(String var1, int var2) {}

   public String getPermission(String ... extra) {
      return String.format("nationsgui.category.%s", (Object[])extra);
   }
}
