/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class FurnitureTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "com.mrcrayfish.furniture.network.PacketManager";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode m : node.methods) {
            if (!m.name.equalsIgnoreCase("handleEnvelopePacket")) continue;
            m.instructions.insertBefore(m.instructions.getFirst(), (AbstractInsnNode)new InsnNode(177));
        }
    }
}

