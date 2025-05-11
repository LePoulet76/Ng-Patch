/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.HashSet;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class WorldTransformer
implements Transformer {
    int i = 0;
    int j = 0;

    @Override
    public String getTarget() {
        return "net.minecraft.world.World";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equalsIgnoreCase("h") && !methodNode.name.equalsIgnoreCase("updateEntities") && !methodNode.name.equalsIgnoreCase("func_72939_s") || !methodNode.desc.equalsIgnoreCase("()V")) continue;
            for (AbstractInsnNode insn : methodNode.instructions.toArray()) {
                if (!(insn instanceof MethodInsnNode)) continue;
                MethodInsnNode ins = (MethodInsnNode)insn;
                if (!ins.name.equalsIgnoreCase("removeAll") || !ins.desc.equalsIgnoreCase("(Ljava/util/Collection;)Z") || !ins.owner.equalsIgnoreCase("java/util/List")) continue;
                methodNode.instructions.set(insn, (AbstractInsnNode)new MethodInsnNode(184, WorldTransformer.class.getName().replace('.', '/'), "removeAllHashSet", "(Ljava/util/List;Ljava/util/List;)Z"));
            }
        }
    }

    public static boolean removeAllHashSet(List l, List remove) {
        return l.removeAll(new HashSet(remove));
    }
}

