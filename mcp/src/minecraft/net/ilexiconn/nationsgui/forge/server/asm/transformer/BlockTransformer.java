/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class BlockTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.block.Block";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            InsnList insnList = methodNode.instructions;
            if (!methodNode.name.equals("isBlockNormalCube") && !methodNode.name.equals("func_72809_s")) continue;
            AbstractInsnNode targetNode = null;
            for (AbstractInsnNode instruction : methodNode.instructions.toArray()) {
                if (instruction.getOpcode() != 25 || ((VarInsnNode)instruction).var != 0) continue;
                targetNode = instruction;
                break;
            }
            if (targetNode == null) continue;
            insnList.insertBefore(targetNode, (AbstractInsnNode)new VarInsnNode(25, 0));
            LabelNode newLabel = new LabelNode();
            insnList.insertBefore(targetNode, (AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "isBlocBypassCheckNormalCube", "(Lnet/minecraft/block/Block;)Z"));
            insnList.insertBefore(targetNode, (AbstractInsnNode)new JumpInsnNode(153, newLabel));
            insnList.insertBefore(targetNode, (AbstractInsnNode)new InsnNode(4));
            insnList.insertBefore(targetNode, (AbstractInsnNode)new InsnNode(172));
            insnList.insertBefore(targetNode, (AbstractInsnNode)newLabel);
        }
    }
}

