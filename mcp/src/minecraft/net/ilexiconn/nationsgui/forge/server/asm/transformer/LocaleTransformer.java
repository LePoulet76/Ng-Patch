/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class LocaleTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.resources.Locale";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            InsnList insnList;
            if (methodNode.name.equals("isUnicode") || methodNode.name.equals("func_135025_a")) {
                insnList = methodNode.instructions;
                insnList.clear();
                insnList.add((AbstractInsnNode)new InsnNode(3));
                insnList.add((AbstractInsnNode)new InsnNode(172));
                continue;
            }
            if (!methodNode.name.equals("checkUnicode") && !methodNode.name.equals("func_135024_b")) continue;
            insnList = methodNode.instructions;
            insnList.clear();
            insnList.add((AbstractInsnNode)new InsnNode(177));
        }
    }
}

