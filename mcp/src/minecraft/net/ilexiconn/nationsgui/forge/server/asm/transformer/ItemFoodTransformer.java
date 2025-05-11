/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.ngcontent.server.item.ItemCustomBread
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.world.World
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
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
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ItemFoodTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.item.ItemFood";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        MethodNode addInformationMethod = new MethodNode(1, dev ? "addInformation" : "func_77624_a", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Ljava/util/List;Z)V", "", new String[0]);
        addInformationMethod.maxStack = 5;
        InsnList list1 = new InsnList();
        list1.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list1.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list1.add((AbstractInsnNode)new VarInsnNode(25, 2));
        list1.add((AbstractInsnNode)new VarInsnNode(25, 3));
        list1.add((AbstractInsnNode)new VarInsnNode(21, 4));
        list1.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/ItemFoodTransformer", "addInformation", "(Lnet/minecraft/item/ItemFood;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Ljava/util/List;Z)V"));
        list1.add((AbstractInsnNode)new InsnNode(177));
        addInformationMethod.instructions.add(list1);
        MethodNode onUpdateMethod = new MethodNode(1, dev ? "onUpdate" : "func_77663_a", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;IZ)V", "", new String[0]);
        onUpdateMethod.maxStack = 6;
        InsnList list2 = new InsnList();
        list2.add((AbstractInsnNode)new VarInsnNode(25, 0));
        list2.add((AbstractInsnNode)new VarInsnNode(25, 1));
        list2.add((AbstractInsnNode)new VarInsnNode(25, 2));
        list2.add((AbstractInsnNode)new VarInsnNode(25, 3));
        list2.add((AbstractInsnNode)new VarInsnNode(21, 4));
        list2.add((AbstractInsnNode)new VarInsnNode(21, 5));
        list2.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/ItemFoodTransformer", "updateFoodStack", "(Lnet/minecraft/item/ItemFood;Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;IZ)V"));
        list2.add((AbstractInsnNode)new InsnNode(177));
        onUpdateMethod.instructions.add(list2);
        node.methods.add(addInformationMethod);
        node.methods.add(onUpdateMethod);
    }

    public static void addInformation(ItemFood item, ItemStack stack, EntityPlayer player, List list, boolean advancedItemTooltips) {
        if (stack != null && stack.func_77942_o() && stack.field_77990_d.func_74764_b("rottenTimer")) {
            SimpleDateFormat format = new SimpleDateFormat(I18n.func_135053_a((String)"rotten.date.format"));
            list.add("");
            list.add(EnumChatFormatting.GRAY + I18n.func_135053_a((String)"rotten.date"));
            list.add(EnumChatFormatting.WHITE + format.format(new Date(stack.field_77990_d.func_74763_f("rottenTimer"))));
        }
    }

    public static void updateFoodStack(ItemFood item, ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
        if (world != null && (world.field_72995_K && ClientProxy.serverType.equals("ng") || !world.field_72995_K && NationsGUI.getServerType().equals("ng"))) {
            if (stack.func_77942_o()) {
                long timer;
                if (stack.field_77990_d.func_74764_b("rottenTimer") && (timer = stack.field_77990_d.func_74763_f("rottenTimer")) < System.currentTimeMillis()) {
                    stack.field_77993_c = NationsGUI.ROTTEN_FOOD.field_77779_bT;
                    stack.func_77982_d(new NBTTagCompound());
                }
            } else {
                NBTTagCompound comp = new NBTTagCompound();
                int expiryDays = 10;
                if (entity instanceof EntityPlayer) {
                    int farmerLevel = 0;
                    farmerLevel = entity.field_70170_p.field_72995_K ? (ClientData.skillsLevel.containsKey("farmer") ? ClientData.skillsLevel.get("farmer") : 0) : NationsGUI.getPlayerSkillLevel(((EntityPlayer)entity).field_71092_bJ, "farmer");
                    if (farmerLevel == 1) {
                        expiryDays = 12;
                    } else if (farmerLevel == 2) {
                        expiryDays = 14;
                    } else if (farmerLevel == 3) {
                        expiryDays = 16;
                    } else if (farmerLevel == 4) {
                        expiryDays = 18;
                    } else if (farmerLevel == 5) {
                        expiryDays = 20;
                    }
                }
                if (item instanceof ItemCustomBread) {
                    expiryDays += 3;
                }
                comp.func_74772_a("rottenTimer", ItemFoodTransformer.timeToXDaysLater(expiryDays));
                stack.func_77982_d(comp);
            }
        }
    }

    private static long timeToXDaysLater(int days) {
        long currentDayTimeStamp = System.currentTimeMillis() / 86400000L * 86400000L;
        return currentDayTimeStamp += 86400000L * (long)days;
    }
}

