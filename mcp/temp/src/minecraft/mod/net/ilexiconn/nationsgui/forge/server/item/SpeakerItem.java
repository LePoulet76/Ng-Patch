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

public class SpeakerItem extends ItemBlock {

   public SpeakerItem(int id) {
      super(id);
      this.func_77625_d(1);
   }

   public boolean func_77648_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
      NBTTagCompound compound;
      if(world.func_72798_a(x, y, z) == NationsGUI.RADIO.field_71990_ca && player.func_70093_af()) {
         if(!stack.func_77942_o()) {
            stack.func_77982_d(new NBTTagCompound());
         }

         compound = stack.func_77978_p();
         compound.func_74768_a("RadioX", x);
         compound.func_74768_a("RadioY", y);
         compound.func_74768_a("RadioZ", z);
         return true;
      } else if(side != 0 && side != 1) {
         if(!world.func_72803_f(x, y, z).func_76220_a()) {
            return false;
         } else {
            if(side == 2) {
               --z;
            } else if(side == 3) {
               ++z;
            } else if(side == 4) {
               --x;
            } else if(side == 5) {
               ++x;
            }

            if(!stack.func_77942_o()) {
               if(world.field_72995_K) {
                  player.func_71035_c("[NationsGUI] " + StatCollector.func_74838_a("nationsgui.radio.select_radio"));
               }

               return false;
            } else if(!player.func_82247_a(x, y, z, side, stack)) {
               return false;
            } else if(!NationsGUI.SPEAKER.func_71930_b(world, x, y, z)) {
               return false;
            } else {
               compound = stack.func_77978_p();
               int radioX = compound.func_74762_e("RadioX");
               int radioY = compound.func_74762_e("RadioY");
               int radioZ = compound.func_74762_e("RadioZ");
               int blockID = world.func_72798_a(radioX, radioY, radioZ);
               if(blockID == NationsGUI.RADIO.field_71990_ca) {
                  world.func_72908_a((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), NationsGUI.SPEAKER.field_72020_cn.func_82593_b(), (NationsGUI.SPEAKER.field_72020_cn.func_72677_b() + 1.0F) / 2.0F, NationsGUI.SPEAKER.field_72020_cn.func_72678_c() * 0.8F);
                  world.func_72832_d(x, y, z, NationsGUI.SPEAKER.field_71990_ca, side, 2);
                  --stack.field_77994_a;
                  RadioBlockEntity blockEntity = (RadioBlockEntity)world.func_72796_p(radioX, radioY, radioZ);
                  blockEntity.speakers.add(new ChunkCoordinates(x, y, z));
                  SpeakerBlockEntity speaker = (SpeakerBlockEntity)world.func_72796_p(x, y, z);
                  speaker.radioX = radioX;
                  speaker.radioY = radioY;
                  speaker.radioZ = radioZ;
                  return true;
               } else {
                  if(world.field_72995_K) {
                     player.func_71035_c("[NationsGUI] " + StatCollector.func_74838_a("nationsgui.radio.invalid_location"));
                  }

                  return false;
               }
            }
         }
      } else {
         return false;
      }
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
      if(stack.func_77942_o()) {
         NBTTagCompound compound = stack.func_77978_p();
         int x = compound.func_74762_e("RadioX");
         int y = compound.func_74762_e("RadioY");
         int z = compound.func_74762_e("RadioZ");
         int blockID = player.field_70170_p.func_72798_a(x, y, z);
         String text = StatCollector.func_74838_a("tile.radio.name") + ": " + x + ", " + y + ", " + z;
         if(blockID != NationsGUI.RADIO.field_71990_ca) {
            text = text + EnumChatFormatting.RED + " (" + StatCollector.func_74838_a("nationsgui.radio.invalid") + ")";
         }

         list.add(text);
      }

   }
}
