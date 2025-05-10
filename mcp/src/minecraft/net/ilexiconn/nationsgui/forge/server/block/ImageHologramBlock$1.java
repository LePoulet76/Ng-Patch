package net.ilexiconn.nationsgui.forge.server.block;

import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

class ImageHologramBlock$1 implements IPermissionCallback
{
    final EntityPlayer val$player;

    final World val$world;

    final int val$x;

    final int val$y;

    final int val$z;

    final ImageHologramBlock this$0;

    ImageHologramBlock$1(ImageHologramBlock this$0, EntityPlayer var2, World var3, int var4, int var5, int var6)
    {
        this.this$0 = this$0;
        this.val$player = var2;
        this.val$world = var3;
        this.val$x = var4;
        this.val$y = var5;
        this.val$z = var6;
    }

    public void call(String permission, boolean has)
    {
        if (has)
        {
            this.val$player.openGui(NationsGUI.INSTANCE, 890, this.val$world, this.val$x, this.val$y, this.val$z);
        }
    }
}
