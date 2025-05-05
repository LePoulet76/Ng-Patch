package net.ilexiconn.nationsgui.forge.server.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBlockPortal extends Item {

   public Block doorBlock;


   public ItemBlockPortal(Block block) {
      super(4755);
      this.doorBlock = block;
      this.field_77777_bU = 1;
      this.func_77637_a(CreativeTabs.field_78030_b);
      this.func_77655_b("island_portal");
   }

   public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      par3List.add(I18n.func_135053_a("island.portal.tooltip.1"));
      par3List.add(I18n.func_135053_a("island.portal.tooltip.2"));
      par3List.add(I18n.func_135053_a("island.portal.tooltip.3"));
      par3List.add(I18n.func_135053_a("island.portal.tooltip.4"));
   }

   public boolean func_77648_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
      if(par7 != 1) {
         return false;
      } else {
         ++par5;
         Block block = this.doorBlock;
         if(par2EntityPlayer.func_82247_a(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.func_82247_a(par4, par5 + 1, par6, par7, par1ItemStack)) {
            if(!block.func_71930_b(par3World, par4, par5, par6)) {
               return false;
            } else {
               int i1 = MathHelper.func_76128_c((double)((par2EntityPlayer.field_70177_z + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
               ItemDoor.func_77869_a(par3World, par4, par5, par6, i1, block);
               --par1ItemStack.field_77994_a;
               return true;
            }
         } else {
            return false;
         }
      }
   }

   public void func_94581_a(IconRegister iconRegister) {
      this.field_77791_bV = iconRegister.func_94245_a("nationsgui:island_portal");
   }
}
