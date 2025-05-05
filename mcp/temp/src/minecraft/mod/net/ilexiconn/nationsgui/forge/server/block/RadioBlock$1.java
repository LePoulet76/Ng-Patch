package net.ilexiconn.nationsgui.forge.server.block;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.block.RadioBlock;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

class RadioBlock$1 implements IPermissionCallback {

   // $FF: synthetic field
   final EntityPlayer val$player;
   // $FF: synthetic field
   final World val$world;
   // $FF: synthetic field
   final int val$x;
   // $FF: synthetic field
   final int val$y;
   // $FF: synthetic field
   final int val$z;
   // $FF: synthetic field
   final RadioBlock this$0;


   RadioBlock$1(RadioBlock this$0, EntityPlayer var2, World var3, int var4, int var5, int var6) {
      this.this$0 = this$0;
      this.val$player = var2;
      this.val$world = var3;
      this.val$x = var4;
      this.val$y = var5;
      this.val$z = var6;
   }

   public void call(String permission, boolean has) {
      if(has) {
         this.val$player.openGui(NationsGUI.INSTANCE, 888, this.val$world, this.val$x, this.val$y, this.val$z);
      }

   }
}
