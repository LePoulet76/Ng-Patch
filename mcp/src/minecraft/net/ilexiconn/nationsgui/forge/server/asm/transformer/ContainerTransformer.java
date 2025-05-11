/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.List;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ContainerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.inventory.Container";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        block0: for (MethodNode mn : node.methods) {
            if (mn.name.equals(dev ? "detectAndSendChanges" : "func_75142_b") && mn.desc.equals("()V")) {
                InsnList list = new InsnList();
                list.add((AbstractInsnNode)new VarInsnNode(25, 0));
                list.add((AbstractInsnNode)new FieldInsnNode(180, "net/minecraft/inventory/Container", dev ? "inventorySlots" : "field_75151_b", "Ljava/util/List;"));
                list.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/ContainerTransformer", "updateItemIfFood", "(Ljava/util/List;)V"));
                mn.instructions.insertBefore(mn.instructions.getFirst(), list);
                continue;
            }
            if (mn.name.equals(dev ? "mergeItemStack" : "func_75135_a")) {
                AbstractInsnNode target = null;
                LabelNode jumpLabel = null;
                for (AbstractInsnNode insnNode : mn.instructions.toArray()) {
                    MethodInsnNode methodInsnNode;
                    if (!(insnNode instanceof MethodInsnNode) || (methodInsnNode = (MethodInsnNode)insnNode).getOpcode() != 182 || !methodInsnNode.name.equals(dev ? "getStack" : "func_75211_c")) continue;
                    AbstractInsnNode current = target = methodInsnNode.getNext();
                    while (!(current instanceof JumpInsnNode)) {
                        current = current.getNext();
                    }
                    jumpLabel = ((JumpInsnNode)current).label;
                    break;
                }
                InsnList patch = new InsnList();
                patch.add((AbstractInsnNode)new VarInsnNode(25, 1));
                patch.add((AbstractInsnNode)new VarInsnNode(25, 8));
                patch.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/ContainerTransformer", "canMergeItemFood", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"));
                LabelNode labelContinue = new LabelNode();
                patch.add((AbstractInsnNode)new JumpInsnNode(154, labelContinue));
                patch.add((AbstractInsnNode)new JumpInsnNode(167, jumpLabel));
                patch.add((AbstractInsnNode)labelContinue);
                mn.instructions.insert(target, patch);
                continue;
            }
            if (mn.name.equals("func_94527_a")) {
                for (AbstractInsnNode insnNode : mn.instructions.toArray()) {
                    if (!(insnNode instanceof MethodInsnNode)) continue;
                    MethodInsnNode methodInsnNode = (MethodInsnNode)insnNode;
                    if (!methodInsnNode.name.equals(dev ? "areItemStackTagsEqual" : "func_77970_a")) continue;
                    methodInsnNode.name = "areItemStackTagsEqual";
                    methodInsnNode.owner = "net/ilexiconn/nationsgui/forge/server/asm/transformer/ContainerTransformer";
                    continue block0;
                }
                continue;
            }
            if (!mn.name.equals(dev ? "slotClick" : "func_75144_a")) continue;
            for (AbstractInsnNode insnNode : mn.instructions.toArray()) {
                if (!(insnNode instanceof MethodInsnNode)) continue;
                MethodInsnNode methodInsnNode = (MethodInsnNode)insnNode;
                if (!methodInsnNode.name.equals(dev ? "areItemStackTagsEqual" : "func_77970_a")) continue;
                methodInsnNode.name = "areItemStackTagsEqual";
                methodInsnNode.owner = "net/ilexiconn/nationsgui/forge/server/asm/transformer/ContainerTransformer";
                continue block0;
            }
        }
    }

    public static boolean areItemStackTagsEqual(ItemStack item, ItemStack item2) {
        if (item != null && item2 != null && item.func_77973_b() instanceof ItemFood && item2.func_77973_b() instanceof ItemFood) {
            if (item.func_77942_o() && item2.func_77942_o()) {
                NBTTagCompound nbt1 = item.func_77978_p();
                NBTTagCompound nbt2 = item2.func_77978_p();
                return !nbt1.func_74764_b("rottenTimer") && !nbt2.func_74764_b("rottenTimer") || nbt1.func_74764_b("rottenTimer") && nbt2.func_74764_b("rottenTimer") && nbt1.func_74763_f("rottenTimer") == nbt2.func_74763_f("rottenTimer");
            }
            return false;
        }
        return ItemStack.func_77970_a((ItemStack)item, (ItemStack)item2);
    }

    public static boolean canMergeItemFood(ItemStack item, ItemStack item2) {
        if (item != null && item2 != null && item.func_77973_b() instanceof ItemFood && item2.func_77973_b() instanceof ItemFood) {
            if (item.func_77942_o() && item2.func_77942_o()) {
                NBTTagCompound nbt1 = item.func_77978_p();
                NBTTagCompound nbt2 = item2.func_77978_p();
                return !nbt1.func_74764_b("rottenTimer") && !nbt2.func_74764_b("rottenTimer") || nbt1.func_74764_b("rottenTimer") && nbt2.func_74764_b("rottenTimer") && nbt1.func_74763_f("rottenTimer") == nbt2.func_74763_f("rottenTimer");
            }
            return false;
        }
        return true;
    }

    public static void updateItemIfFood(List listSlot) {
        for (Slot slot : listSlot) {
            ItemStack stack;
            if (slot == null || (stack = slot.func_75211_c()) == null || stack.func_77973_b() == null || !(stack.func_77973_b() instanceof ItemFood)) continue;
            stack.func_77973_b().func_77663_a(stack, null, null, 0, false);
        }
    }
}

