package net.ilexiconn.nationsgui.forge.server.block;

import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.TileEntityHatBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class HatBlock extends Block implements ITileEntityProvider
{
    public HatBlock()
    {
        super(3590, Material.cloth);
        this.setResistance(Block.stone.blockResistance);
        this.setUnlocalizedName("hatblock");
        this.setTextureName("wool_colored_white");
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack)
    {
        world.setBlockMetadataWithNotify(x, y, z, MathHelper.floor_double((double)(entity.rotationYaw * 16.0F / 360.0F) + 0.5D) & 15, 2);

        if (world.getBlockTileEntity(x, y, z) instanceof TileEntityHatBlock)
        {
            TileEntityHatBlock tileEntityHatBlock = (TileEntityHatBlock)world.getBlockTileEntity(x, y, z);

            if (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("HatID"))
            {
                tileEntityHatBlock.setHatID(itemStack.getTagCompound().getString("HatID"));
            }
        }
    }

    /**
     * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
     * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
     * metadata
     */
    public void breakBlock(World world, int x, int y, int z, int metadata, int fortune)
    {
        ItemStack is = new ItemStack(NationsGUI.HATBLOCK.blockID, 1, 0);

        if (world.getBlockTileEntity(x, y, z) instanceof TileEntityHatBlock)
        {
            TileEntityHatBlock te = (TileEntityHatBlock)world.getBlockTileEntity(x, y, z);
            NBTTagCompound comp = new NBTTagCompound();
            comp.setString("HatID", te.getHatID());
            is.stackTagCompound = comp;
            is.setItemName("\u00a76" + te.getHatID());
            world.spawnEntityInWorld(new EntityItem(world, (double)x, (double)y, (double)z, is));
            super.breakBlock(world, x, y, z, metadata, fortune);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.getBoundingBox((double)par2 + 0.2D, (double)par3 + 0.0D, (double)par4 + 0.2D, (double)par2 + 0.8D, (double)par3 + 0.6D, (double)par4 + 0.8D);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return AxisAlignedBB.getBoundingBox((double)par2 + 0.2D, (double)par3 + 0.0D, (double)par4 + 0.2D, (double)par2 + 0.8D, (double)par3 + 0.6D, (double)par4 + 0.8D);
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return -1;
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
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityHatBlock();
    }
}
