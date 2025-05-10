package net.ilexiconn.nationsgui.forge.server.block;

import fr.zeamateis.nationsglory.client.renders.RenderTransparentBlock;
import fr.zeamateis.nationsglory.common.tileEntity.TileEntityTransparent;
import net.ilexiconn.nationsgui.forge.server.block.ImageHologramBlock$1;
import net.ilexiconn.nationsgui.forge.server.block.entity.ImageHologramBlockEntity;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionCache;
import net.ilexiconn.nationsgui.forge.server.permission.PermissionType;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ImageHologramBlock extends BlockContainer
{
    public ImageHologramBlock()
    {
        super(649, Material.iron);
        this.setUnlocalizedName("imagehologram");
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
        if (world.isRemote && player.isSneaking())
        {
            PermissionCache.INSTANCE.checkPermission(PermissionType.URL_ADMIN, new ImageHologramBlock$1(this, player, world, x, y, z), new String[0]);
        }

        return true;
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
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world)
    {
        return new ImageHologramBlockEntity();
    }
}
