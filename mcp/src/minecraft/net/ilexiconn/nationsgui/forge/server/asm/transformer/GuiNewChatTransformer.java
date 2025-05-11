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

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class GuiNewChatTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.gui.GuiNewChat";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        boolean patchedDrawChat = false;
        boolean patchedPrintMessage = false;
        for (MethodNode method : node.methods) {
            if (!method.name.equals("drawChat") && !method.name.equals("func_73762_a")) continue;
            MethodNode mn = new MethodNode();
            mn.visitMethodInsn(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIChatHooks", "filterChatLineString", "(Ljava/lang/String;)Ljava/lang/String;");
            AbstractInsnNode insertAfter = null;
            for (int i = 0; i < method.instructions.size(); ++i) {
                AbstractInsnNode nod = method.instructions.get(i);
                if (!(nod instanceof MethodInsnNode) || nod.getOpcode() != 182 || !((MethodInsnNode)nod).name.equals("getChatLineString") && !((MethodInsnNode)nod).name.equals("func_74538_a")) continue;
                insertAfter = nod;
                break;
            }
            if (insertAfter == null) continue;
            method.instructions.insert(insertAfter, mn.instructions);
            mn = new MethodNode();
            mn.visitVarInsn(25, 10);
            mn.visitVarInsn(25, 17);
            mn.visitVarInsn(21, 15);
            mn.visitVarInsn(21, 16);
            mn.visitVarInsn(21, 14);
            mn.visitMethodInsn(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIChatHooks", "drawChatMessagePost", "(Lnet/minecraft/client/gui/ChatLine;Ljava/lang/String;III)V");
            AbstractInsnNode insertBefore = null;
            Object toRemove = null;
            for (int i = 0; i < method.instructions.size(); ++i) {
                AbstractInsnNode nod = method.instructions.get(i);
                if (!(nod instanceof MethodInsnNode) || nod.getOpcode() != 182 || !((MethodInsnNode)nod).name.equals("drawStringWithShadow") && !((MethodInsnNode)nod).name.equals("func_78261_a")) continue;
                insertBefore = nod;
                break;
            }
            if (insertBefore == null) continue;
            method.instructions.insert(insertBefore, mn.instructions);
            if (!patchedPrintMessage) continue;
            break;
        }
    }
}

