package net.ilexiconn.nationsgui.forge.server.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.particle.RadioParticle;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.SpeakerBlockEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class SpeakerBlock extends BlockContainer
{
    public SpeakerBlock()
    {
        super(647, Material.cloth);
        this.setUnlocalizedName("speaker");
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0F);
        this.setResistance(2.0F);
        this.setTextureName("wool_colored_black");
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

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        switch (world.getBlockMetadata(x, y, z) & 7)
        {
            case 2:
                this.setBlockBounds(0.12F, 0.12F, 0.65F, 0.88F, 0.88F, 1.0F);
                break;

            case 3:
                this.setBlockBounds(0.12F, 0.12F, 0.0F, 0.88F, 0.88F, 0.35F);
                break;

            case 4:
                this.setBlockBounds(0.65F, 0.12F, 0.12F, 1.0F, 0.88F, 0.88F);
                break;

            case 5:
                this.setBlockBounds(0.0F, 0.12F, 0.12F, 0.35F, 0.88F, 0.88F);
        }
    }

    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
    {
        SpeakerBlockEntity blockEntity = (SpeakerBlockEntity)world.getBlockTileEntity(x, y, z);
        ArrayList list = new ArrayList();
        ItemStack stack = new ItemStack(NationsGUI.SPEAKER);
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("RadioX", blockEntity.radioX);
        compound.setInteger("RadioY", blockEntity.radioY);
        compound.setInteger("RadioZ", blockEntity.radioZ);
        stack.setTagCompound(compound);
        list.add(stack);
        return list;
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        if (Keyboard.isKeyDown(29))
        {
            SpeakerBlockEntity blockEntity = (SpeakerBlockEntity)world.getBlockTileEntity(x, y, z);
            ItemStack stack = new ItemStack(NationsGUI.SPEAKER);
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("RadioX", blockEntity.radioX);
            compound.setInteger("RadioY", blockEntity.radioY);
            compound.setInteger("RadioZ", blockEntity.radioZ);
            stack.setTagCompound(compound);
            return stack;
        }
        else
        {
            return super.getPickBlock(target, world, x, y, z);
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        SpeakerBlockEntity speaker = (SpeakerBlockEntity)world.getBlockTileEntity(x, y, z);
        RadioBlockEntity blockEntity = (RadioBlockEntity)world.getBlockTileEntity(speaker.radioX, speaker.radioY, speaker.radioZ);

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
        return new SpeakerBlockEntity();
    }
}
