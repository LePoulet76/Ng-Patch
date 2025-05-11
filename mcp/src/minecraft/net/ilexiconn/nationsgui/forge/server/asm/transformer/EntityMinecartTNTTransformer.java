/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

public class EntityMinecartTNTTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.entity.item.EntityMinecartTNT";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            InsnList insnList = methodNode.instructions;
            if (!methodNode.name.equals("killMinecart") && !methodNode.name.equals("func_94095_a")) continue;
            AbstractInsnNode targetNode = null;
            for (AbstractInsnNode instruction : methodNode.instructions.toArray()) {
                if (instruction.getOpcode() != 178 || !((FieldInsnNode)instruction).name.equals("tnt") && !((FieldInsnNode)instruction).name.equals("field_72091_am") && !((FieldInsnNode)instruction).name.equals("field_76262_s")) continue;
                targetNode = instruction;
            }
            if (targetNode == null) continue;
            MethodNode mn = new MethodNode();
            mn.visitFieldInsn(178, "net/minecraft/block/Block", dev ? "rail" : "field_72056_aG", "Lnet/minecraft/block/Block;");
            insnList.insertBefore(targetNode, mn.instructions);
            insnList.remove(targetNode);
        }
    }
}

