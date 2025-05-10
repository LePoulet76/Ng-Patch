package net.ilexiconn.nationsgui.forge.client;

import fr.nationsglory.ngcontent.server.block.FurnitureBlock;
import fr.nationsglory.ngcontent.server.block.FurnitureFrigoBlock;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class ClientHooks
{
    private static final List <? extends Class > blacklistClasses = Arrays.asList(new Class[] {FurnitureFrigoBlock.class, FurnitureBlock.class});
    private static final List<Integer> blacklistIDs = Arrays.asList(new Integer[0]);

    public static boolean skipTileRender(TileEntity t, double x, double y, double z, float partialTick)
    {
        try
        {
            Block block = t.getBlockType();

            if (!ClientProxy.clientConfig.renderFurnitures && (blacklistClasses.contains(block.getClass()) || blacklistIDs.contains(Integer.valueOf(block.blockID))))
            {
                block.setBlockBoundsBasedOnState(t.worldObj, t.xCoord, t.yCoord, t.zCoord);
                AxisAlignedBB box = block.getSelectedBoundingBoxFromPool(t.worldObj, t.xCoord, t.yCoord, t.zCoord).getOffsetBoundingBox((double)(-t.xCoord), (double)(-t.yCoord), (double)(-t.zCoord));
                GL11.glDepthMask(false);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
                GL11.glPushMatrix();
                Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(1.0F, 0.0F, 0.0F, 0.5F);
                double d3 = box.minX;
                double d4 = box.minZ;
                double d5 = box.maxX;
                double d6 = box.minZ;
                double d7 = box.minX;
                double d8 = box.maxZ;
                double d9 = box.maxX;
                double d10 = box.maxZ;
                double d11 = box.maxY;
                double d12 = box.minY;

                if (!t.getWorldObj().isBlockOpaqueCube(t.xCoord, t.yCoord, t.zCoord - 1))
                {
                    tessellator.addVertex(x + d3, y + d11, z + d4);
                    tessellator.addVertex(x + d3, y + d12, z + d4);
                    tessellator.addVertex(x + d5, y + d12, z + d6);
                    tessellator.addVertex(x + d5, y + d11, z + d6);
                }

                if (!t.getWorldObj().isBlockOpaqueCube(t.xCoord, t.yCoord, t.zCoord + 1))
                {
                    tessellator.addVertex(x + d9, y + d11, z + d10);
                    tessellator.addVertex(x + d9, y + d12, z + d10);
                    tessellator.addVertex(x + d7, y + d12, z + d8);
                    tessellator.addVertex(x + d7, y + d11, z + d8);
                }

                if (!t.getWorldObj().isBlockOpaqueCube(t.xCoord + 1, t.yCoord, t.zCoord))
                {
                    tessellator.addVertex(x + d5, y + d11, z + d6);
                    tessellator.addVertex(x + d5, y + d12, z + d6);
                    tessellator.addVertex(x + d9, y + d12, z + d10);
                    tessellator.addVertex(x + d9, y + d11, z + d10);
                }

                if (!t.getWorldObj().isBlockOpaqueCube(t.xCoord - 1, t.yCoord, t.zCoord))
                {
                    tessellator.addVertex(x + d7, y + d11, z + d8);
                    tessellator.addVertex(x + d7, y + d12, z + d8);
                    tessellator.addVertex(x + d3, y + d12, z + d4);
                    tessellator.addVertex(x + d3, y + d11, z + d4);
                }

                if (!t.getWorldObj().isBlockOpaqueCube(t.xCoord, t.yCoord - 1, t.zCoord))
                {
                    tessellator.addVertex(x + box.minX, y + box.minY, z + box.minZ);
                    tessellator.addVertex(x + box.minX, y + box.minY, z + box.maxZ);
                    tessellator.addVertex(x + box.maxX, y + box.minY, z + box.maxZ);
                    tessellator.addVertex(x + box.maxX, y + box.minY, z + box.minZ);
                }

                if (!t.getWorldObj().isBlockOpaqueCube(t.xCoord, t.yCoord + 1, t.zCoord))
                {
                    tessellator.addVertex(x + box.minX, y + box.maxY, z + box.minZ);
                    tessellator.addVertex(x + box.minX, y + box.maxY, z + box.maxZ);
                    tessellator.addVertex(x + box.maxX, y + box.maxY, z + box.maxZ);
                    tessellator.addVertex(x + box.maxX, y + box.maxY, z + box.minZ);
                }

                tessellator.draw();
                GL11.glPopMatrix();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                return true;
            }
        }
        catch (NullPointerException var31)
        {
            ;
        }

        return false;
    }

    public static ItemStack hookItemStackRender(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            Item item = itemStack.getItem();

            if (item instanceof ItemBlock)
            {
                ItemBlock itemBlock = (ItemBlock)item;
                Block block = Block.blocksList[itemBlock.getBlockID()];

                if (block instanceof FurnitureBlock && !ClientProxy.clientConfig.renderFurnitures || block instanceof HatBlock && !ClientProxy.clientConfig.render3DSkins)
                {
                    return new ItemStack(1542, 1, 3);
                }
            }
        }

        return itemStack;
    }

    public static float getBlockReachDistance(PlayerControllerMP controller)
    {
        return controller.isInCreativeMode() ? Math.max(ClientProxy.blockReach, 5.0F) : ClientProxy.blockReach;
    }

    public static void setEntityViewRenderer(EntityPlayer target)
    {
        Minecraft.getMinecraft().renderViewEntity = target;
    }

    public static EntityPlayer getNearPlayerFromName(String targetUsername)
    {
        Iterator var1 = Minecraft.getMinecraft().theWorld.loadedEntityList.iterator();

        while (var1.hasNext())
        {
            Entity ent = (Entity)var1.next();

            if (ent instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)ent;

                if (player.username.equalsIgnoreCase(targetUsername))
                {
                    return player;
                }
            }
        }

        return null;
    }

    public static int hookRenderDistance(int renderDistance)
    {
        RenderDistanceEvent event = new RenderDistanceEvent(renderDistance, Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld);
        MinecraftForge.EVENT_BUS.post(event);
        return NationsGUITransformer.optifine ? (64 << 3 - event.renderDistance) / 2 : event.renderDistance;
    }

    public static void defineDisplayResizable()
    {
        if (Util.getOSType() == EnumOS.MACOS)
        {
            boolean resize = false;

            try
            {
                Process e = Runtime.getRuntime().exec(new String[] {"/usr/bin/uname", "-m"});
                InputStream inputStream = e.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String s = reader.readLine();
                inputStream.close();
                resize = !s.equals("arm64");
            }
            catch (IOException var5)
            {
                var5.printStackTrace();
            }

            Display.setResizable(resize);
        }
        else
        {
            Display.setResizable(true);
        }
    }
}
