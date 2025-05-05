package net.ilexiconn.nationsgui.forge.server.command;

import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ChatMessageComponent;

public class CookCommand extends CommandBase {

   public String func_71517_b() {
      return "cook";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return "/cook";
   }

   public void func_71515_b(ICommandSender icommandsender, String[] astring) {
      if(icommandsender instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)icommandsender;
         ItemStack[] playerInventory = player.field_71071_by.field_70462_a;
         Map recipes = FurnaceRecipes.func_77602_a().func_77599_b();
         int amountChanged = 0;

         for(int i = 0; i < playerInventory.length; ++i) {
            if(playerInventory[i] != null) {
               int id = playerInventory[i].field_77993_c;
               if(recipes.containsKey(Integer.valueOf(id)) && ((ItemStack)recipes.get(Integer.valueOf(id))).field_77993_c != 263) {
                  playerInventory[i] = (ItemStack)recipes.get(Integer.valueOf(id));
                  ++amountChanged;
               }
            }
         }

         if(amountChanged != 0) {
            player.field_71071_by.field_70459_e = true;
            player.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7aVous venez de faire cuire les objets de votre inventaire.")));
         } else {
            player.func_70006_a(ChatMessageComponent.func_111066_d(Translation.get("\u00a7cVotre inventaire ne contient que des objets ne pouvant pas \u00eatre cuits.")));
         }
      }

   }

   public int compareTo(Object o) {
      return 0;
   }

   public List func_71516_a(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      return null;
   }

   public boolean func_82358_a(String[] par1ArrayOfStr, int par2) {
      return par2 == 0;
   }
}
