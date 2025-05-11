/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  fr.zeamateis.nationsglory.client.renders.RenderTransparentBlock
 *  fr.zeamateis.nationsglory.common.tileEntity.TileEntityTransparent
 *  net.minecraft.block.BlockContainer
 *  net.minecraft.block.material.Material
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.World
 */
package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.common.network.PacketDispatcher;
import fr.zeamateis.nationsglory.client.renders.RenderTransparentBlock;
import fr.zeamateis.nationsglory.common.tileEntity.TileEntityTransparent;
import java.awt.Desktop;
import java.net.URI;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.url.URLGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.URLBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.URLSavePacket;
import net.ilexiconn.nationsgui.forge.server.permission.IPermissionCallback;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class URLBlock
extends BlockContainer {
    public URLBlock() {
        super(648, Material.field_76243_f);
        this.func_71864_b("url");
        this.func_71849_a(CreativeTabs.field_78030_b);
        this.func_71875_q();
        this.func_71848_c(2.0f);
        this.func_71894_b(2.0f);
        this.func_111022_d("wool_colored_orange");
        this.func_71868_h(0);
    }

    public boolean func_71903_a(final World world, final int x, final int y, final int z, final EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
        URLBlockEntity blockEntity;
        if (world.field_72995_K) {
            if (!player.func_70093_af()) {
                Desktop desktop;
                URLBlockEntity blockEntity2 = (URLBlockEntity)world.func_72796_p(x, y, z);
                if (blockEntity2 != null && !blockEntity2.url.isEmpty() && blockEntity2.url.startsWith("http") && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.BROWSE)) {
                    try {
                        String cmd = blockEntity2.url;
                        cmd = cmd.replaceAll("<player>", player.field_71092_bJ);
                        cmd = cmd.replaceAll("<server>", ClientProxy.currentServerName.toLowerCase());
                        desktop.browse(new URI(cmd));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                PermissionCache.INSTANCE.checkPermission(PermissionType.URL_ADMIN, new IPermissionCallback(){

                    @Override
                    public void call(String permission, boolean has) {
                        if (has) {
                            player.openGui((Object)NationsGUI.INSTANCE, 889, world, x, y, z);
                        }
                    }
                }, new String[0]);
            }
        } else if (!player.func_70093_af() && (blockEntity = (URLBlockEntity)world.func_72796_p(x, y, z)) != null && !blockEntity.url.isEmpty() && (blockEntity.url.startsWith("<") || blockEntity.url.startsWith("/") || blockEntity.url.startsWith("*"))) {
            NationsGUI.executePlayerCmd(player.field_71092_bJ, blockEntity.url);
        }
        return true;
    }

    public void func_71860_a(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        URLBlockEntity blockEntity;
        if (par1World.field_72995_K && (blockEntity = (URLBlockEntity)par1World.func_72796_p(par2, par3, par4)) != null && !URLGUI.savedClientURL.isEmpty() && System.currentTimeMillis() - URLGUI.savedClientTime < 60000L) {
            blockEntity.url = URLGUI.savedClientURL;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new URLSavePacket(blockEntity)));
        }
        super.func_71860_a(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
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

    public TileEntity func_72274_a(World world) {
        return new URLBlockEntity();
    }
}

