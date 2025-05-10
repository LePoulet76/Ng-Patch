package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.common.network.PacketDispatcher;
import fr.zeamateis.nationsglory.client.renders.RenderTransparentBlock;
import fr.zeamateis.nationsglory.common.tileEntity.TileEntityTransparent;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.url.URLGUI;
import net.ilexiconn.nationsgui.forge.server.block.URLBlock$1;
import net.ilexiconn.nationsgui.forge.server.block.entity.URLBlockEntity;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.URLSavePacket;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class URLBlock extends BlockContainer
{
    public URLBlock()
    {
        super(648, Material.iron);
        this.setUnlocalizedName("url");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setBlockUnbreakable();
        this.setHardness(2.0F);
        this.setResistance(2.0F);
        this.setTextureName("wool_colored_orange");
        this.setLightOpacity(0);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
    {
        URLBlockEntity blockEntity;

        if (world.isRemote)
        {
            if (!player.isSneaking())
            {
                blockEntity = (URLBlockEntity)world.getBlockTileEntity(x, y, z);

                if (blockEntity != null && !blockEntity.url.isEmpty() && blockEntity.url.startsWith("http"))
                {
                    Desktop desktop = Desktop.getDesktop();

                    if (desktop.isSupported(Action.BROWSE))
                    {
                        try
                        {
                            String e = blockEntity.url;
                            e = e.replaceAll("<player>", player.username);
                            e = e.replaceAll("<server>", ClientProxy.currentServerName.toLowerCase());
                            desktop.browse(new URI(e));
                        }
                        catch (Exception var13)
                        {
                            var13.printStackTrace();
                        }
                    }
                }
            }
            else
            {
                PermissionCache.INSTANCE.checkPermission(PermissionType.URL_ADMIN, new URLBlock$1(this, player, world, x, y, z), new String[0]);
            }
        }
        else if (!player.isSneaking())
        {
            blockEntity = (URLBlockEntity)world.getBlockTileEntity(x, y, z);

            if (blockEntity != null && !blockEntity.url.isEmpty() && (blockEntity.url.startsWith("<") || blockEntity.url.startsWith("/") || blockEntity.url.startsWith("*")))
            {
                NationsGUI.executePlayerCmd(player.username, blockEntity.url);
            }
        }

        return true;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        if (par1World.isRemote)
        {
            URLBlockEntity blockEntity = (URLBlockEntity)par1World.getBlockTileEntity(par2, par3, par4);

            if (blockEntity != null && !URLGUI.savedClientURL.isEmpty() && System.currentTimeMillis() - URLGUI.savedClientTime.longValue() < 60000L)
            {
                blockEntity.url = URLGUI.savedClientURL;
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new URLSavePacket(blockEntity)));
            }
        }

        super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return TileEntityTransparent.rendering ? super.getRenderType() : RenderTransparentBlock.renderID;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world)
    {
        return new URLBlockEntity();
    }
}
