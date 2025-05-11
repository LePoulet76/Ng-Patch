/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PlayerControllerMPTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.multiplayer.PlayerControllerMP";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals(dev ? "getBlockReachDistance" : "func_78757_d")) continue;
            methodNode.instructions.clear();
            methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
            methodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "getBlockReachDistance", "(Lnet/minecraft/client/multiplayer/PlayerControllerMP;)F"));
            methodNode.instructions.add((AbstractInsnNode)new InsnNode(174));
        }
    }
}

