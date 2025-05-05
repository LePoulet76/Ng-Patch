package net.ilexiconn.nationsgui.forge.client.gui.shop;

import net.ilexiconn.nationsgui.forge.client.gui.shop.Category;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;

class Category$1 implements IPermissionCallback {

   // $FF: synthetic field
   final Category this$0;


   Category$1(Category this$0) {
      this.this$0 = this$0;
   }

   public void call(String permission, boolean has) {
      Category.access$002(this.this$0, Boolean.valueOf(has));
   }
}
