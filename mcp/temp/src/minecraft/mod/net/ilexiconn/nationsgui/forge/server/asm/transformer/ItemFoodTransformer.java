package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import fr.nationsglory.ngcontent.server.item.ItemCustomBread;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ItemFoodTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.item.ItemFood";
   }

   public void transform(ClassNode node, boolean dev) {
      MethodNode addInformationMethod = new MethodNode(1, dev?"addInformation":"func_77624_a", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Ljava/util/List;Z)V", "", new String[0]);
      addInformationMethod.maxStack = 5;
      InsnList list1 = new InsnList();
      list1.add(new VarInsnNode(25, 0));
      list1.add(new VarInsnNode(25, 1));
      list1.add(new VarInsnNode(25, 2));
      list1.add(new VarInsnNode(25, 3));
      list1.add(new VarInsnNode(21, 4));
      list1.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/ItemFoodTransformer", "addInformation", "(Lnet/minecraft/item/ItemFood;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Ljava/util/List;Z)V"));
      list1.add(new InsnNode(177));
      addInformationMethod.instructions.add(list1);
      MethodNode onUpdateMethod = new MethodNode(1, dev?"onUpdate":"func_77663_a", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;IZ)V", "", new String[0]);
      onUpdateMethod.maxStack = 6;
      InsnList list2 = new InsnList();
      list2.add(new VarInsnNode(25, 0));
      list2.add(new VarInsnNode(25, 1));
      list2.add(new VarInsnNode(25, 2));
      list2.add(new VarInsnNode(25, 3));
      list2.add(new VarInsnNode(21, 4));
      list2.add(new VarInsnNode(21, 5));
      list2.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/ItemFoodTransformer", "updateFoodStack", "(Lnet/minecraft/item/ItemFood;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;IZ)V"));
      list2.add(new InsnNode(177));
      onUpdateMethod.instructions.add(list2);
      node.methods.add(addInformationMethod);
      node.methods.add(onUpdateMethod);
   }

   public static void addInformation(ItemFood item, ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
      if(stack != null && stack.func_77942_o() && stack.field_77990_d.func_74764_b("rottenTimer")) {
         SimpleDateFormat format = new SimpleDateFormat(I18n.func_135053_a("rotten.date.format"));
         list.add("");
         list.add(EnumChatFormatting.GRAY + I18n.func_135053_a("rotten.date"));
         list.add(EnumChatFormatting.WHITE + format.format(new Date(stack.field_77990_d.func_74763_f("rottenTimer"))));
      }

   }

   public static void updateFoodStack(ItemFood item, ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
      if(world != null && (world.field_72995_K && ClientProxy.serverType.equals("ng") || !world.field_72995_K && NationsGUI.getServerType().equals("ng"))) {
         if(stack.func_77942_o()) {
            if(stack.field_77990_d.func_74764_b("rottenTimer")) {
               long comp = stack.field_77990_d.func_74763_f("rottenTimer");
               if(comp < System.currentTimeMillis()) {
                  stack.field_77993_c = NationsGUI.ROTTEN_FOOD.field_77779_bT;
                  stack.func_77982_d(new NBTTagCompound());
               }
            }
         } else {
            NBTTagCompound comp1 = new NBTTagCompound();
            int expiryDays = 10;
            if(entity instanceof EntityPlayer) {
               boolean farmerLevel = false;
               int farmerLevel1;
               if(entity.field_70170_p.field_72995_K) {
                  farmerLevel1 = ClientData.skillsLevel.containsKey("farmer")?((Integer)ClientData.skillsLevel.get("farmer")).intValue():0;
               } else {
                  farmerLevel1 = NationsGUI.getPlayerSkillLevel(((EntityPlayer)entity).field_71092_bJ, "farmer");
               }

               if(farmerLevel1 == 1) {
                  expiryDays = 12;
               } else if(farmerLevel1 == 2) {
                  expiryDays = 14;
               } else if(farmerLevel1 == 3) {
                  expiryDays = 16;
               } else if(farmerLevel1 == 4) {
                  expiryDays = 18;
               } else if(farmerLevel1 == 5) {
                  expiryDays = 20;
               }
            }

            if(item instanceof ItemCustomBread) {
               expiryDays += 3;
            }

            comp1.func_74772_a("rottenTimer", timeToXDaysLater(expiryDays));
            stack.func_77982_d(comp1);
         }
      }

   }

   private static long timeToXDaysLater(int days) {
      long currentDayTimeStamp = System.currentTimeMillis() / 86400000L * 86400000L;
      currentDayTimeStamp += 86400000L * (long)days;
      return currentDayTimeStamp;
   }
}
