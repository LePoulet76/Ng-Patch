/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class GameSettingsTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.settings.GameSettings";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("<init>")) continue;
            LdcInsnNode target = null;
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (!(insnNode instanceof LdcInsnNode)) continue;
                LdcInsnNode ldcInsnNode = (LdcInsnNode)insnNode;
                if (!ldcInsnNode.cst.equals("en_US")) continue;
                target = ldcInsnNode;
                break;
            }
            methodNode.instructions.insertBefore(target, (AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/GameSettingsTransformer", "getDefaultLanguage", "()Ljava/lang/String;"));
            methodNode.instructions.remove(target);
        }
    }

    public static String getDefaultLanguage() {
        String lang = System.getProperty("java.lang");
        if (lang == null) {
            lang = "en_US";
        }
        return lang;
    }
}

