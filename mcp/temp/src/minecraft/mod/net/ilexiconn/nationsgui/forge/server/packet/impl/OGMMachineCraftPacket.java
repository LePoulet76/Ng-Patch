package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import fr.nationsglory.client.gui.OGMMachineGUI;
import fr.nationsglory.ngcontent.NGContent;
import fr.nationsglory.server.block.entity.GCOGMMachineBlockEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.OGMMachineCraftPacket$1;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class OGMMachineCraftPacket implements IPacket, IServerPacket, IClientPacket {

   public static Map<String, String> indicesText = new OGMMachineCraftPacket$1();
   private String ogmName;
   private int tileX;
   private int tileY;
   private int tileZ;
   private String error = "";
   private float percentSucces = 0.0F;


   public OGMMachineCraftPacket(String ogmName, int tileX, int tileY, int tileZ) {
      this.ogmName = ogmName;
      this.tileX = tileX;
      this.tileY = tileY;
      this.tileZ = tileZ;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.ogmName = data.readUTF();
      this.tileX = data.readInt();
      this.tileY = data.readInt();
      this.tileZ = data.readInt();
      this.error = data.readUTF();
      this.percentSucces = data.readFloat();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.ogmName);
      data.writeInt(this.tileX);
      data.writeInt(this.tileY);
      data.writeInt(this.tileZ);
      data.writeUTF(this.error);
      data.writeFloat(this.percentSucces);
   }

   public void handleServerPacket(EntityPlayer player) {
      List recipeIngredients = NationsGUI.getOGMRecipe(this.ogmName);
      if(recipeIngredients != null) {
         TileEntity tileEntity = player.func_130014_f_().func_72796_p(this.tileX, this.tileY, this.tileZ);
         if(tileEntity instanceof GCOGMMachineBlockEntity) {
            GCOGMMachineBlockEntity ogmMachineEntity = (GCOGMMachineBlockEntity)tileEntity;
            if(ogmMachineEntity.energyStored >= 50.0F) {
               ogmMachineEntity.energyStored -= 50.0F;
               Collections.shuffle(recipeIngredients);
               int amountValidItems = 0;
               int totalItemsRequired = 0;
               Iterator i = recipeIngredients.iterator();

               label61:
               while(i.hasNext()) {
                  String ingredientData = (String)i.next();
                  totalItemsRequired += Integer.parseInt(ingredientData.split("#")[2]);
                  int farmerLevel = NationsGUI.getPlayerSkillLevel(player.field_71092_bJ, "farmer");
                  int goodQte = Integer.parseInt(ingredientData.split("#")[2]);
                  Random rand = new Random();
                  int qteMin = Math.max(0, goodQte - rand.nextInt(farmerLevel >= 3?2:4));
                  int qteMax = Math.min(64, goodQte + rand.nextInt(farmerLevel >= 3?2:4));

                  for(int i1 = 0; i1 <= 8; ++i1) {
                     if(ogmMachineEntity.itemStacks[i1].field_77993_c == Integer.parseInt(ingredientData.split("#")[0]) && ogmMachineEntity.itemStacks[i1].func_77960_j() == Integer.parseInt(ingredientData.split("#")[1])) {
                        if(ogmMachineEntity.itemStacks[i1].field_77994_a != goodQte) {
                           this.error = "quantity#" + qteMin + "#" + qteMax + "###" + (String)indicesText.get(ingredientData.split("#")[0] + "#" + ingredientData.split("#")[1]);
                        } else {
                           amountValidItems += Integer.parseInt(ingredientData.split("#")[2]);
                        }
                        continue label61;
                     }
                  }

                  this.error = "quantity#" + qteMin + "#" + qteMax + "###" + (String)indicesText.get(ingredientData.split("#")[0] + "#" + ingredientData.split("#")[1]);
               }

               for(int var15 = 0; var15 <= 8; ++var15) {
                  ogmMachineEntity.itemStacks[var15] = null;
               }

               if(this.error.isEmpty()) {
                  this.error = "success_" + this.ogmName;
               }

               if(this.error.contains("success")) {
                  this.percentSucces = 1.0F;
                  PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(this), (Player)player);
                  ((GCOGMMachineBlockEntity)tileEntity).itemStacks[9] = new ItemStack(NGContent.getCerealIdFromName(this.ogmName).intValue() + 1, 64, 0);
                  NationsGUI.countOGMREcipe(this.ogmName, true, 100);
                  NationsGUI.addPlayerSkill(player.field_71092_bJ, "farmer", 15);
               } else {
                  this.percentSucces = (float)amountValidItems / ((float)totalItemsRequired * 1.0F);
                  if(amountValidItems > 0) {
                     this.percentSucces = Math.max(0.01F, this.percentSucces);
                  }

                  this.error = this.error + "###" + (int)(this.percentSucces * 100.0F);
                  NationsGUI.countOGMREcipe(this.ogmName, false, (int)(this.percentSucces * 100.0F));
                  PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(this), (Player)player);
               }
            }
         }
      }

   }

   public void handleClientPacket(EntityPlayer player) {
      if(!this.error.isEmpty()) {
         OGMMachineGUI.errorToDisplay = this.error;
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

   public static boolean removeItemWithAmount(InventoryPlayer inventory, ItemStack stack) {
      for(int i = 0; i < inventory.func_70302_i_(); ++i) {
         ItemStack stack1 = inventory.func_70301_a(i);
         if(stack1 != null && stack1.func_77973_b() == stack.func_77973_b()) {
            if(stack.field_77994_a - stack1.field_77994_a < 0) {
               stack1.field_77994_a -= stack.field_77994_a;
               return true;
            }

            stack.field_77994_a -= stack1.field_77994_a;
            inventory.field_70462_a[i] = null;
            if(stack.field_77994_a == 0) {
               return true;
            }
         }
      }

      return false;
   }

}
