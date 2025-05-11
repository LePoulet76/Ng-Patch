/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.texture.IconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package net.ilexiconn.nationsgui.forge.server.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBlockAntiBullet
extends Item {
    public ItemBlockAntiBullet() {
        super(4754);
        this.field_77777_bU = 64;
        this.func_77637_a(CreativeTabs.field_78030_b);
        this.func_77655_b("antibullet");
    }

    public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add("\u00a77Bloque les balles");
    }

    public void func_94581_a(IconRegister iconRegister) {
        this.field_77791_bV = iconRegister.func_94245_a("nationsgui:antibullet");
    }
}

