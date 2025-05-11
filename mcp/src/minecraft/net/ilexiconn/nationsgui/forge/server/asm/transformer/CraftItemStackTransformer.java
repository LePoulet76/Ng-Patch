/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class CraftItemStackTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("hasItemMeta") || !methodNode.desc.equals("()Z")) continue;
            for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                if (!(abstractInsnNode instanceof MethodInsnNode) || abstractInsnNode.getOpcode() != 184) continue;
                MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                methodInsnNode.owner = "net/ilexiconn/nationsgui/forge/server/asm/transformer/CraftItemStackTransformer";
            }
        }
    }

    public static boolean hasItemMeta(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (item.func_77973_b() instanceof ItemFood && item.field_77990_d != null) {
            NBTTagCompound nbtTagCompound = (NBTTagCompound)item.field_77990_d.func_74737_b();
            nbtTagCompound.func_82580_o("rottenTimer");
            return !nbtTagCompound.func_82582_d();
        }
        return item.field_77990_d != null && !item.field_77990_d.func_82582_d();
    }
}

