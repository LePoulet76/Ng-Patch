package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.particle.RadioParticle;
import net.ilexiconn.nationsgui.forge.server.block.RadioBlock$1;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class RadioBlock extends BlockContainer
{
    public RadioBlock()
    {
        super(646, Material.iron);
        this.setUnlocalizedName("radio");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0F);
        this.setResistance(2.0F);
        this.setTextureName("wool_colored_orange");
        this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.6F, 0.8F);
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
    {
        world.setBlockMetadataWithNotify(x, y, z, MathHelper.floor_double((double)(entity.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15, 2);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
    {
        if (player.isSneaking())
        {
            return false;
        }
        else
        {
            if (world.isRemote)
            {
                RadioBlockEntity blockEntity = (RadioBlockEntity)world.getBlockTileEntity(x, y, z);

                if (blockEntity.canOpen)
                {
                    player.openGui(NationsGUI.INSTANCE, 888, world, x, y, z);
                }
                else
                {
                    PermissionCache.INSTANCE.checkPermission(PermissionType.RADIO_MODERATION, new RadioBlock$1(this, player, world, x, y, z), new String[0]);
                }
            }

            return true;
        }
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return -1;
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
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        RadioBlockEntity blockEntity = (RadioBlockEntity)world.getBlockTileEntity(x, y, z);

        if (blockEntity != null && (float)blockEntity.volume > 0.0F && blockEntity.streamer != null && blockEntity.streamer.isPlaying() && (!blockEntity.needsRedstone || world.getStrongestIndirectPower(x, y, z) > 0))
        {
            Minecraft.getMinecraft().effectRenderer.addEffect(new RadioParticle(world, (double)((float)x + ((float)random.nextInt(75) + 1.0F) / 100.0F), (double)((float)y + ((float)random.nextInt(100) + 1.0F) / 100.0F), (double)((float)z + ((float)random.nextInt(100) + 1.0F) / 100.0F), (float)blockEntity.volume / 100.0F, random));
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world)
    {
        return new RadioBlockEntity();
    }
}
