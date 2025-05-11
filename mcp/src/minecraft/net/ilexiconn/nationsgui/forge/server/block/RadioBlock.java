/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.particle.EntityFX
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 */
package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.particle.RadioParticle;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class RadioBlock
extends BlockContainer {
    public RadioBlock() {
        super(646, Material.field_76243_f);
        this.func_71864_b("radio");
        this.func_71849_a(CreativeTabs.field_78030_b);
        this.func_71848_c(2.0f);
        this.func_71894_b(2.0f);
        this.func_111022_d("wool_colored_orange");
        this.func_71905_a(0.2f, 0.0f, 0.2f, 0.8f, 0.6f, 0.8f);
    }

    public void func_71860_a(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        world.func_72921_c(x, y, z, MathHelper.func_76128_c((double)((double)(entity.field_70177_z * 16.0f / 360.0f) + 0.5)) & 0xF, 2);
    }

    public boolean func_71903_a(final World world, final int x, final int y, final int z, final EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
        if (player.func_70093_af()) {
            return false;
        }
        if (world.field_72995_K) {
            RadioBlockEntity blockEntity = (RadioBlockEntity)world.func_72796_p(x, y, z);
            if (blockEntity.canOpen) {
                player.openGui((Object)NationsGUI.INSTANCE, 888, world, x, y, z);
            } else {
                PermissionCache.INSTANCE.checkPermission(PermissionType.RADIO_MODERATION, new IPermissionCallback(){

                    @Override
                    public void call(String permission, boolean has) {
                        if (has) {
                            player.openGui((Object)NationsGUI.INSTANCE, 888, world, x, y, z);
                        }
                    }
                }, new String[0]);
            }
        }
        return true;
    }

    public int func_71857_b() {
        return -1;
    }

    public boolean func_71926_d() {
        return false;
    }

    public boolean func_71886_c() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void func_71862_a(World world, int x, int y, int z, Random random) {
        RadioBlockEntity blockEntity = (RadioBlockEntity)world.func_72796_p(x, y, z);
        if (blockEntity != null && (float)blockEntity.volume > 0.0f && blockEntity.streamer != null && blockEntity.streamer.isPlaying() && (!blockEntity.needsRedstone || world.func_94572_D(x, y, z) > 0)) {
            Minecraft.func_71410_x().field_71452_i.func_78873_a((EntityFX)new RadioParticle(world, (float)x + ((float)random.nextInt(75) + 1.0f) / 100.0f, (float)y + ((float)random.nextInt(100) + 1.0f) / 100.0f, (float)z + ((float)random.nextInt(100) + 1.0f) / 100.0f, (float)blockEntity.volume / 100.0f, random));
        }
    }

    public TileEntity func_72274_a(World world) {
        return new RadioBlockEntity();
    }
}

