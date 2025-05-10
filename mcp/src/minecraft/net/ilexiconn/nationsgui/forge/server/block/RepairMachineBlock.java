package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.RepairMachineBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class RepairMachineBlock extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    public Icon textureFront;
    @SideOnly(Side.CLIENT)
    public Icon textureBack;
    @SideOnly(Side.CLIENT)
    public Icon textureTop;
    @SideOnly(Side.CLIENT)
    public Icon textureBottom;
    @SideOnly(Side.CLIENT)
    public Icon textureSide;
    public Random random = new Random();

    public RepairMachineBlock()
    {
        super(NationsGUI.CONFIG.repairMachineID, Material.wood);
        this.setUnlocalizedName("repair_machine");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0F);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            entityPlayer.openGui(NationsGUI.INSTANCE, this.blockID, world, x, y, z);
        }

        return true;
    }

    /**
     * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
     * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
     * metadata
     */
    public void breakBlock(World world, int x, int y, int z, int blockID, int metadata)
    {
        RepairMachineBlockEntity blockEntity = (RepairMachineBlockEntity)world.getBlockTileEntity(x, y, z);

        if (blockEntity != null)
        {
            for (int j1 = 0; j1 < blockEntity.getSizeInventory(); ++j1)
            {
                ItemStack itemstack = blockEntity.getStackInSlot(j1);

                if (itemstack != null)
                {
                    float offsetX = this.random.nextFloat() * 0.8F + 0.1F;
                    float offsetY = this.random.nextFloat() * 0.8F + 0.1F;
                    float offsetZ = this.random.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0)
                    {
                        int stackSize = this.random.nextInt(21) + 10;

                        if (stackSize > itemstack.stackSize)
                        {
                            stackSize = itemstack.stackSize;
                        }

                        itemstack.stackSize -= stackSize;
                        EntityItem entityItem = new EntityItem(world, (double)((float)x + offsetX), (double)((float)y + offsetY), (double)((float)z + offsetZ), new ItemStack(itemstack.itemID, stackSize, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound())
                        {
                            entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }

                        float radius = 0.05F;
                        entityItem.motionX = (double)((float)this.random.nextGaussian() * radius);
                        entityItem.motionY = (double)((float)this.random.nextGaussian() * radius + 0.2F);
                        entityItem.motionZ = (double)((float)this.random.nextGaussian() * radius);
                        world.spawnEntityInWorld(entityItem);
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, blockID, metadata);
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        if (!world.isRemote)
        {
            int back = world.getBlockId(x, y, z - 1);
            int front = world.getBlockId(x, y, z + 1);
            int left = world.getBlockId(x - 1, y, z);
            int right = world.getBlockId(x + 1, y, z);
            byte metadata = 3;

            if (Block.opaqueCubeLookup[back] && !Block.opaqueCubeLookup[front])
            {
                metadata = 3;
            }

            if (Block.opaqueCubeLookup[front] && !Block.opaqueCubeLookup[back])
            {
                metadata = 2;
            }

            if (Block.opaqueCubeLookup[left] && !Block.opaqueCubeLookup[right])
            {
                metadata = 5;
            }

            if (Block.opaqueCubeLookup[right] && !Block.opaqueCubeLookup[left])
            {
                metadata = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
        }
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack)
    {
        int metadata = MathHelper.floor_double((double)(entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (metadata == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        else if (metadata == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        else if (metadata == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        else if (metadata == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        if (itemStack.hasDisplayName())
        {
            ((RepairMachineBlockEntity)world.getBlockTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister iconRegister)
    {
        this.textureFront = iconRegister.registerIcon("nationsgui:repair_machine_front");
        this.textureBack = iconRegister.registerIcon("nationsgui:repair_machine_back");
        this.textureTop = iconRegister.registerIcon("nationsgui:repair_machine_top");
        this.textureBottom = iconRegister.registerIcon("nationsgui:repair_machine_bottom");
        this.textureSide = iconRegister.registerIcon("nationsgui:repair_machine_side");
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int side, int meta)
    {
        return side == 0 ? this.textureBottom : (side == 1 ? this.textureTop : (side % 2 == 0 && side + 1 == meta ? this.textureBack : (side % 2 == 1 && side - 1 == meta ? this.textureBack : (side != meta ? this.textureSide : this.textureFront))));
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world)
    {
        return new RepairMachineBlockEntity();
    }
}
