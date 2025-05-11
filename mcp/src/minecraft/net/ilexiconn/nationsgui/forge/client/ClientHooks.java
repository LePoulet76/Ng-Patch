/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.ngcontent.server.block.FurnitureBlock
 *  fr.nationsglory.ngcontent.server.block.FurnitureFrigoBlock
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.EnumOS
 *  net.minecraft.util.Util
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.Event
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client;

import fr.nationsglory.ngcontent.server.block.FurnitureBlock;
import fr.nationsglory.ngcontent.server.block.FurnitureFrigoBlock;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.events.RenderDistanceEvent;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.block.HatBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumOS;
import net.minecraft.util.Util;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class ClientHooks {
    private static final List<? extends Class> blacklistClasses = Arrays.asList(FurnitureFrigoBlock.class, FurnitureBlock.class);
    private static final List<Integer> blacklistIDs = Arrays.asList(new Integer[0]);

    public static boolean skipTileRender(TileEntity t, double x, double y, double z, float partialTick) {
        try {
            Block block = t.func_70311_o();
            if (!ClientProxy.clientConfig.renderFurnitures && (blacklistClasses.contains(block.getClass()) || blacklistIDs.contains(block.field_71990_ca))) {
                block.func_71902_a((IBlockAccess)t.field_70331_k, t.field_70329_l, t.field_70330_m, t.field_70327_n);
                AxisAlignedBB box = block.func_71911_a_(t.field_70331_k, t.field_70329_l, t.field_70330_m, t.field_70327_n).func_72325_c((double)(-t.field_70329_l), (double)(-t.field_70330_m), (double)(-t.field_70327_n));
                GL11.glDepthMask((boolean)false);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2884);
                GL11.glEnable((int)3042);
                GL11.glEnable((int)2896);
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                GL11.glPushMatrix();
                Tessellator tessellator = Tessellator.field_78398_a;
                tessellator.func_78382_b();
                tessellator.func_78369_a(1.0f, 0.0f, 0.0f, 0.5f);
                double d3 = box.field_72340_a;
                double d4 = box.field_72339_c;
                double d5 = box.field_72336_d;
                double d6 = box.field_72339_c;
                double d7 = box.field_72340_a;
                double d8 = box.field_72334_f;
                double d9 = box.field_72336_d;
                double d10 = box.field_72334_f;
                double d11 = box.field_72337_e;
                double d12 = box.field_72338_b;
                if (!t.func_70314_l().func_72804_r(t.field_70329_l, t.field_70330_m, t.field_70327_n - 1)) {
                    tessellator.func_78377_a(x + d3, y + d11, z + d4);
                    tessellator.func_78377_a(x + d3, y + d12, z + d4);
                    tessellator.func_78377_a(x + d5, y + d12, z + d6);
                    tessellator.func_78377_a(x + d5, y + d11, z + d6);
                }
                if (!t.func_70314_l().func_72804_r(t.field_70329_l, t.field_70330_m, t.field_70327_n + 1)) {
                    tessellator.func_78377_a(x + d9, y + d11, z + d10);
                    tessellator.func_78377_a(x + d9, y + d12, z + d10);
                    tessellator.func_78377_a(x + d7, y + d12, z + d8);
                    tessellator.func_78377_a(x + d7, y + d11, z + d8);
                }
                if (!t.func_70314_l().func_72804_r(t.field_70329_l + 1, t.field_70330_m, t.field_70327_n)) {
                    tessellator.func_78377_a(x + d5, y + d11, z + d6);
                    tessellator.func_78377_a(x + d5, y + d12, z + d6);
                    tessellator.func_78377_a(x + d9, y + d12, z + d10);
                    tessellator.func_78377_a(x + d9, y + d11, z + d10);
                }
                if (!t.func_70314_l().func_72804_r(t.field_70329_l - 1, t.field_70330_m, t.field_70327_n)) {
                    tessellator.func_78377_a(x + d7, y + d11, z + d8);
                    tessellator.func_78377_a(x + d7, y + d12, z + d8);
                    tessellator.func_78377_a(x + d3, y + d12, z + d4);
                    tessellator.func_78377_a(x + d3, y + d11, z + d4);
                }
                if (!t.func_70314_l().func_72804_r(t.field_70329_l, t.field_70330_m - 1, t.field_70327_n)) {
                    tessellator.func_78377_a(x + box.field_72340_a, y + box.field_72338_b, z + box.field_72339_c);
                    tessellator.func_78377_a(x + box.field_72340_a, y + box.field_72338_b, z + box.field_72334_f);
                    tessellator.func_78377_a(x + box.field_72336_d, y + box.field_72338_b, z + box.field_72334_f);
                    tessellator.func_78377_a(x + box.field_72336_d, y + box.field_72338_b, z + box.field_72339_c);
                }
                if (!t.func_70314_l().func_72804_r(t.field_70329_l, t.field_70330_m + 1, t.field_70327_n)) {
                    tessellator.func_78377_a(x + box.field_72340_a, y + box.field_72337_e, z + box.field_72339_c);
                    tessellator.func_78377_a(x + box.field_72340_a, y + box.field_72337_e, z + box.field_72334_f);
                    tessellator.func_78377_a(x + box.field_72336_d, y + box.field_72337_e, z + box.field_72334_f);
                    tessellator.func_78377_a(x + box.field_72336_d, y + box.field_72337_e, z + box.field_72339_c);
                }
                tessellator.func_78381_a();
                GL11.glPopMatrix();
                GL11.glEnable((int)3553);
                GL11.glEnable((int)2884);
                GL11.glDisable((int)3042);
                GL11.glDepthMask((boolean)true);
                return true;
            }
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
        return false;
    }

    public static ItemStack hookItemStackRender(ItemStack itemStack) {
        ItemBlock itemBlock;
        Block block;
        Item item;
        if (itemStack != null && (item = itemStack.func_77973_b()) instanceof ItemBlock && ((block = Block.field_71973_m[(itemBlock = (ItemBlock)item).func_77883_f()]) instanceof FurnitureBlock && !ClientProxy.clientConfig.renderFurnitures || block instanceof HatBlock && !ClientProxy.clientConfig.render3DSkins)) {
            return new ItemStack(1542, 1, 3);
        }
        return itemStack;
    }

    public static float getBlockReachDistance(PlayerControllerMP controller) {
        if (controller.func_78758_h()) {
            return Math.max(ClientProxy.blockReach, 5.0f);
        }
        return ClientProxy.blockReach;
    }

    public static void setEntityViewRenderer(EntityPlayer target) {
        Minecraft.func_71410_x().field_71451_h = target;
    }

    public static EntityPlayer getNearPlayerFromName(String targetUsername) {
        for (Entity ent : Minecraft.func_71410_x().field_71441_e.field_72996_f) {
            if (!(ent instanceof EntityPlayer)) continue;
            EntityPlayer player = (EntityPlayer)ent;
            if (!player.field_71092_bJ.equalsIgnoreCase(targetUsername)) continue;
            return player;
        }
        return null;
    }

    public static int hookRenderDistance(int renderDistance) {
        RenderDistanceEvent event = new RenderDistanceEvent(renderDistance, (EntityPlayer)Minecraft.func_71410_x().field_71439_g, (World)Minecraft.func_71410_x().field_71441_e);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (NationsGUITransformer.optifine) {
            return (64 << 3 - event.renderDistance) / 2;
        }
        return event.renderDistance;
    }

    public static void defineDisplayResizable() {
        if (Util.func_110647_a() == EnumOS.MACOS) {
            boolean resize = false;
            try {
                Process exec = Runtime.getRuntime().exec(new String[]{"/usr/bin/uname", "-m"});
                InputStream inputStream = exec.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String s = reader.readLine();
                inputStream.close();
                resize = !s.equals("arm64");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Display.setResizable((boolean)resize);
        } else {
            Display.setResizable((boolean)true);
        }
    }
}

