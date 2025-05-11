/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.IntInsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class Packet201PlayerInfoTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.network.packet.Packet201PlayerInfo";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals(dev ? "readPacketData" : "func_73267_a")) continue;
            for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                if (!(abstractInsnNode instanceof MethodInsnNode)) continue;
                MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                if (!methodInsnNode.name.equals(dev ? "readString" : "func_73282_a")) continue;
                IntInsnNode varInsnNode = (IntInsnNode)methodInsnNode.getPrevious();
                varInsnNode.operand = 100;
                return;
            }
        }
    }
}

