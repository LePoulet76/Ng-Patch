/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer.loading;

import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ResourceManagerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net/minecraft/client/resources/SimpleReloadableResourceManager";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        if (NationsGUITransformer.isServer) {
            return;
        }
        for (MethodNode m : node.methods) {
            if (!m.name.equalsIgnoreCase("func_110544_b") || !m.desc.equalsIgnoreCase("()V")) continue;
            InsnList list = new InsnList();
            list.add((AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks;"));
            list.add((AbstractInsnNode)new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawLoadingScreen", "()V"));
            for (AbstractInsnNode i : m.instructions.toArray()) {
                if (!(i instanceof MethodInsnNode)) continue;
                MethodInsnNode insn = (MethodInsnNode)i;
                if (!insn.owner.equalsIgnoreCase("net/minecraft/client/resources/ResourceManagerReloadListener") || !insn.name.equalsIgnoreCase("func_110549_a") || !insn.desc.equalsIgnoreCase("(Lnet/minecraft/client/resources/ResourceManager;)V")) continue;
                m.instructions.insertBefore(insn.getPrevious().getPrevious(), list);
            }
        }
    }
}

