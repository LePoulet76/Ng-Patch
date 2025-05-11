/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ItemStackTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.item.ItemStack";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        if (!NationsGUITransformer.isServer) {
            return;
        }
        for (MethodNode method : node.methods) {
            if (!method.name.equals(dev ? "tryPlaceItemIntoWorld" : "func_77943_a")) continue;
            for (AbstractInsnNode abstractInsnNode : method.instructions.toArray()) {
                if (abstractInsnNode.getOpcode() != 181) continue;
                FieldInsnNode insnNode = (FieldInsnNode)abstractInsnNode;
                if (!insnNode.name.equals("captureTreeGeneration")) continue;
                method.instructions.remove(insnNode.getPrevious());
                method.instructions.insertBefore((AbstractInsnNode)insnNode, (AbstractInsnNode)new InsnNode(3));
                return;
            }
        }
    }
}

