/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ScreenShotHelperTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.util.ScreenShotHelper";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode mn : node.methods) {
            if (!mn.name.equalsIgnoreCase("func_74292_a") || !mn.desc.equalsIgnoreCase("(Ljava/io/File;Ljava/lang/String;II)Ljava/lang/String;")) continue;
            for (int i = 0; i < mn.instructions.size(); ++i) {
                AbstractInsnNode insn = mn.instructions.get(i);
                if (!(insn instanceof MethodInsnNode) || insn.getOpcode() != 184 || !((MethodInsnNode)insn).name.equalsIgnoreCase("write") || !((MethodInsnNode)insn).desc.equalsIgnoreCase("(Ljava/awt/image/RenderedImage;Ljava/lang/String;Ljava/io/File;)Z")) continue;
                MethodInsnNode method = (MethodInsnNode)insn;
                InsnList list = new InsnList();
                list.add((AbstractInsnNode)new VarInsnNode(25, 6));
                list.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "sendNotificationToUploadImage", "(Ljava/awt/image/BufferedImage;)V"));
                mn.instructions.insertBefore(method.getPrevious().getPrevious().getPrevious().getPrevious(), list);
                return;
            }
        }
    }
}

