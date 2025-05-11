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

public class WorldClientTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.multiplayer.WorldClient";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals(dev ? "doPreChunk" : "func_73025_a")) continue;
            methodNode.instructions.clear();
            methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
            methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(21, 1));
            methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(21, 2));
            methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(21, 3));
            methodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "doPreChunk", "(Lnet/minecraft/client/multiplayer/WorldClient;IIZ)V"));
            methodNode.instructions.add((AbstractInsnNode)new InsnNode(177));
        }
    }
}

