/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class SkullTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.block.BlockSkull";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        MethodNode getPickBlock = new MethodNode(1, "getPickBlock", "(Lnet/minecraft/util/MovingObjectPosition;Lnet/minecraft/world/World;III)Lnet/minecraft/item/ItemStack;", null, null);
        InsnList insnList = getPickBlock.instructions;
        insnList.add((AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks;"));
        insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
        insnList.add((AbstractInsnNode)new VarInsnNode(25, 2));
        insnList.add((AbstractInsnNode)new VarInsnNode(21, 3));
        insnList.add((AbstractInsnNode)new VarInsnNode(21, 4));
        insnList.add((AbstractInsnNode)new VarInsnNode(21, 5));
        insnList.add((AbstractInsnNode)new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getPickBlock", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;III)Lnet/minecraft/item/ItemStack;"));
        insnList.add((AbstractInsnNode)new InsnNode(176));
        node.methods.add(getPickBlock);
    }
}

