package net.ilexiconn.nationsgui.forge.server.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.block.entity.RadioBlockEntity;
import net.ilexiconn.nationsgui.forge.server.block.entity.SpeakerBlockEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class SpeakerItem extends ItemBlock
{
    public SpeakerItem(int id)
    {
        super(id);
        this.setMaxStackSize(1);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        NBTTagCompound compound;

        if (world.getBlockId(x, y, z) == NationsGUI.RADIO.blockID && player.isSneaking())
        {
            if (!stack.hasTagCompound())
            {
                stack.setTagCompound(new NBTTagCompound());
            }

            compound = stack.getTagCompound();
            compound.setInteger("RadioX", x);
            compound.setInteger("RadioY", y);
            compound.setInteger("RadioZ", z);
            return true;
        }
        else if (side != 0 && side != 1)
        {
            if (!world.getBlockMaterial(x, y, z).isSolid())
            {
                return false;
            }
            else
            {
                if (side == 2)
                {
                    --z;
                }
                else if (side == 3)
                {
                    ++z;
                }
                else if (side == 4)
                {
                    --x;
                }
                else if (side == 5)
                {
                    ++x;
                }

                if (!stack.hasTagCompound())
                {
                    if (world.isRemote)
                    {
                        player.addChatMessage("[NationsGUI] " + StatCollector.translateToLocal("nationsgui.radio.select_radio"));
                    }

                    return false;
                }
                else if (!player.canPlayerEdit(x, y, z, side, stack))
                {
                    return false;
                }
                else if (!NationsGUI.SPEAKER.canPlaceBlockAt(world, x, y, z))
                {
                    return false;
                }
                else
                {
                    compound = stack.getTagCompound();
                    int radioX = compound.getInteger("RadioX");
                    int radioY = compound.getInteger("RadioY");
                    int radioZ = compound.getInteger("RadioZ");
                    int blockID = world.getBlockId(radioX, radioY, radioZ);

                    if (blockID == NationsGUI.RADIO.blockID)
                    {
                        world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), NationsGUI.SPEAKER.stepSound.getPlaceSound(), (NationsGUI.SPEAKER.stepSound.getVolume() + 1.0F) / 2.0F, NationsGUI.SPEAKER.stepSound.getPitch() * 0.8F);
                        world.setBlock(x, y, z, NationsGUI.SPEAKER.blockID, side, 2);
                        --stack.stackSize;
                        RadioBlockEntity blockEntity = (RadioBlockEntity)world.getBlockTileEntity(radioX, radioY, radioZ);
                        blockEntity.speakers.add(new ChunkCoordinates(x, y, z));
                        SpeakerBlockEntity speaker = (SpeakerBlockEntity)world.getBlockTileEntity(x, y, z);
                        speaker.radioX = radioX;
                        speaker.radioY = radioY;
                        speaker.radioZ = radioZ;
                        return true;
                    }
                    else
                    {
                        if (world.isRemote)
                        {
                            player.addChatMessage("[NationsGUI] " + StatCollector.translateToLocal("nationsgui.radio.invalid_location"));
                        }

                        return false;
                    }
                }
            }
        }
        else
        {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
    {
        if (stack.hasTagCompound())
        {
            NBTTagCompound compound = stack.getTagCompound();
            int x = compound.getInteger("RadioX");
            int y = compound.getInteger("RadioY");
            int z = compound.getInteger("RadioZ");
            int blockID = player.worldObj.getBlockId(x, y, z);
            String text = StatCollector.translateToLocal("tile.radio.name") + ": " + x + ", " + y + ", " + z;

            if (blockID != NationsGUI.RADIO.blockID)
            {
                text = text + EnumChatFormatting.RED + " (" + StatCollector.translateToLocal("nationsgui.radio.invalid") + ")";
            }

            list.add(text);
        }
    }
}
