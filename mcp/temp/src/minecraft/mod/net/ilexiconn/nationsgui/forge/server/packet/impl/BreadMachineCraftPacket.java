package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.collect.UnmodifiableIterator;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.common.recipes.BreadRecipes;
import fr.nationsglory.common.recipes.BreadRecipes.BreadRecipe;
import fr.nationsglory.ngcontent.server.item.ItemCustomBread;
import fr.nationsglory.server.block.entity.GCBreadMachineBlockEntity;
import java.util.Arrays;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.ServerUtils;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.util.Translation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class BreadMachineCraftPacket implements IPacket, IServerPacket {

   private String breadName;
   private int tileX;
   private int tileY;
   private int tileZ;


   public BreadMachineCraftPacket(String breadName, int tileX, int tileY, int tileZ) {
      this.breadName = breadName;
      this.tileX = tileX;
      this.tileY = tileY;
      this.tileZ = tileZ;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.breadName = data.readUTF();
      this.tileX = data.readInt();
      this.tileY = data.readInt();
      this.tileZ = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.breadName);
      data.writeInt(this.tileX);
      data.writeInt(this.tileY);
      data.writeInt(this.tileZ);
   }

   public void handleServerPacket(EntityPlayer player) {
      if(BreadRecipes.RECIPES.containsKey(this.breadName) && player.field_71071_by.func_70447_i() != -1) {
         TileEntity tileEntity = player.func_130014_f_().func_72796_p(this.tileX, this.tileY, this.tileZ);
         if(tileEntity instanceof GCBreadMachineBlockEntity && ((GCBreadMachineBlockEntity)tileEntity).energyStored >= 10.0F) {
            int farmerLevel = NationsGUI.getPlayerSkillLevel(player.field_71092_bJ, "farmer");
            if(this.breadName.equals("ginger") && farmerLevel < 4 || this.breadName.equals("tahde") && farmerLevel < 5 || this.breadName.equals("martian") && farmerLevel < 5 || this.breadName.equals("frozen") && farmerLevel < 4 || this.breadName.equals("gas") && farmerLevel < 4) {
               player.func_71035_c(Translation.get("\u00a7cVous n\'avez pas le niveau requis dans la comp\u00e9tence Agriculteur pour fabriquer ce pain."));
               return;
            }

            int researchLevelResource = ServerUtils.getCountryResearchLevel(player.field_71092_bJ, "resource");
            if(this.breadName.equals("frozen") && researchLevelResource < 12 || this.breadName.equals("gas") && researchLevelResource < 12) {
               player.func_71035_c(Translation.get("\u00a7cVotre pays n\'a pas le niveau de recherche requis pour fabriquer ce pain."));
               return;
            }

            if(this.breadName.equals("tahde") && researchLevelResource < 10 || this.breadName.equals("martian") && researchLevelResource < 10) {
               player.func_71035_c(Translation.get("\u00a7cVotre pays n\'a pas le niveau de recherche requis pour fabriquer ce pain."));
               return;
            }

            if(this.breadName.equals("energy") && researchLevelResource < 13 || this.breadName.equals("golden") && researchLevelResource < 13) {
               player.func_71035_c(Translation.get("\u00a7cVotre pays n\'a pas le niveau de recherche requis pour fabriquer ce pain."));
               return;
            }

            BreadRecipe recipe = BreadRecipes.getRecipe(this.breadName);
            UnmodifiableIterator itemStack = recipe.getMaterials().iterator();

            ItemStack stack;
            while(itemStack.hasNext()) {
               stack = (ItemStack)itemStack.next();
               if(!this.hasItemStack(player.field_71071_by, stack)) {
                  return;
               }
            }

            itemStack = recipe.getMaterials().iterator();

            while(itemStack.hasNext()) {
               stack = (ItemStack)itemStack.next();
               if(!removeItemWithAmount(player.field_71071_by, stack.func_77946_l())) {
                  return;
               }
            }

            ItemStack itemStack1 = ItemCustomBread.getBreadItemFromName(this.breadName);
            itemStack1.field_77994_a = 4;
            if(farmerLevel == 2) {
               itemStack1.field_77994_a = 5;
            } else if(farmerLevel >= 3) {
               itemStack1.field_77994_a = 6;
            }

            if(itemStack1 != null) {
               player.field_71071_by.func_70441_a(itemStack1);
               ((GCBreadMachineBlockEntity)tileEntity).lastBreadCraft = Long.valueOf(System.currentTimeMillis());
               ((GCBreadMachineBlockEntity)tileEntity).energyStored -= 10.0F;
               NationsGUI.addPlayerSkill(player.field_71092_bJ, "farmer", 20);
            }
         }
      }

   }

   public boolean hasItemStack(InventoryPlayer inventory, ItemStack stack) {
      int count = 0;
      Iterator var4 = Arrays.asList(inventory.field_70462_a).iterator();

      while(var4.hasNext()) {
         ItemStack stack1 = (ItemStack)var4.next();
         if(stack1 != null && stack1.func_77973_b() == stack.func_77973_b()) {
            count += stack1.field_77994_a;
         }
      }

      return stack.field_77994_a <= count;
   }

   public static boolean removeItemWithAmount(InventoryPlayer inventory, ItemStack stackToRemove) {
      for(int i = 0; i < inventory.func_70302_i_(); ++i) {
         ItemStack stackToCheck = inventory.func_70301_a(i);
         if(stackToCheck != null && stackToRemove.field_77993_c == stackToCheck.field_77993_c && stackToRemove.func_77960_j() == stackToCheck.func_77960_j() && stackToCheck.field_77994_a > 0) {
            int oldStackSize = stackToCheck.field_77994_a;
            inventory.func_70298_a(i, Math.min(stackToCheck.field_77994_a, stackToRemove.field_77994_a));
            stackToRemove.field_77994_a -= Math.min(oldStackSize, stackToRemove.field_77994_a);
            if(stackToRemove.field_77994_a == 0) {
               return true;
            }
         }
      }

      return false;
   }
}
