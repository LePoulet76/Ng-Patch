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

public class AbstractClientPlayerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.entity.AbstractClientPlayer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            LdcInsnNode ldcInsnNode;
            if (methodNode.name.equals("getSkinUrl") || methodNode.name.equals("func_110300_d")) {
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (!(insnNode instanceof LdcInsnNode)) continue;
                    ldcInsnNode = (LdcInsnNode)insnNode;
                    ldcInsnNode.cst = "https://skins.nationsglory.fr/%s";
                }
                continue;
            }
            if (!methodNode.name.equals("getCapeUrl") && !methodNode.name.equals("func_110308_e")) continue;
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (!(insnNode instanceof LdcInsnNode)) continue;
                ldcInsnNode = (LdcInsnNode)insnNode;
                ldcInsnNode.cst = "http://skins.minecraft.net/MinecraftCloaks/";
            }
        }
    }
}

