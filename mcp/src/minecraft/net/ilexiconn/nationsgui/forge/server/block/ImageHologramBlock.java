/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.zeamateis.nationsglory.client.renders.RenderTransparentBlock
 *  fr.zeamateis.nationsglory.common.tileEntity.TileEntityTransparent
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.material.Material
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.world.World
 */
package net.ilexiconn.nationsgui.forge.server.block;

import fr.zeamateis.nationsglory.client.renders.RenderTransparentBlock;
import fr.zeamateis.nationsglory.common.tileEntity.TileEntityTransparent;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.ImageHologramBlockEntity;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ImageHologramBlock
extends BlockContainer {
    public ImageHologramBlock() {
        super(649, Material.field_76243_f);
        this.func_71864_b("imagehologram");
        this.func_71849_a(CreativeTabs.field_78030_b);
        this.func_71875_q();
        this.func_71848_c(2.0f);
        this.func_71894_b(2.0f);
        this.func_111022_d("wool_colored_orange");
        this.func_71868_h(0);
    }

    public boolean func_71903_a(final World world, final int x, final int y, final int z, final EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
        if (world.field_72995_K && player.func_70093_af()) {
            PermissionCache.INSTANCE.checkPermission(PermissionType.URL_ADMIN, new IPermissionCallback(){

                @Override
                public void call(String permission, boolean has) {
                    if (has) {
                        player.openGui((Object)NationsGUI.INSTANCE, 890, world, x, y, z);
                    }
                }
            }, new String[0]);
        }
        return true;
    }

    public boolean func_71886_c() {
        return false;
    }

    public boolean func_71926_d() {
        return false;
    }

    public int func_71857_b() {
        return TileEntityTransparent.rendering ? super.func_71857_b() : RenderTransparentBlock.renderID;
    }

    public AxisAlignedBB func_71872_e(World world, int x, int y, int z) {
        return null;
    }

    public TileEntity func_72274_a(World world) {
        return new ImageHologramBlockEntity();
    }
}

