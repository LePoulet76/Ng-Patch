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

public class ServerConfigurationManagerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.server.management.ServerConfigurationManager";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            InsnList insnList = methodNode.instructions;
            if (!methodNode.name.equals("getCurrentPlayerCount") && !methodNode.name.equals("func_71233_x") && !methodNode.name.equals("func_72394_k")) continue;
            AbstractInsnNode targetNode = null;
            for (AbstractInsnNode instruction : methodNode.instructions.toArray()) {
                if (instruction.getOpcode() != 172) continue;
                targetNode = instruction;
            }
            if (targetNode == null) continue;
            insnList.insertBefore(targetNode, (AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks;"));
            insnList.insertBefore(targetNode, (AbstractInsnNode)new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getPlayersInWaiting", "()I"));
            insnList.insertBefore(targetNode, (AbstractInsnNode)new InsnNode(96));
        }
    }
}

