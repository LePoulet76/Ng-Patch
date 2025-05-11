/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ThreadLoginVerifierTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.network.ThreadLoginVerifier";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("run") && !methodNode.name.equals("auth")) continue;
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (!(insnNode instanceof LdcInsnNode)) continue;
                LdcInsnNode ldcInsnNode = (LdcInsnNode)insnNode;
                if (!ldcInsnNode.cst.equals("http://session.minecraft.net/game/checkserver.jsp?user=")) continue;
                ldcInsnNode.cst = "https://authserver.nationsglory.fr/checksession?username=";
            }
        }
    }
}

