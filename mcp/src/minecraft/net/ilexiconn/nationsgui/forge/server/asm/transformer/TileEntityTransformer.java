/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.LocalVariableNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public class TileEntityTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.tileentity.TileEntity";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            boolean that = false;
            for (LocalVariableNode variableNode : methodNode.localVariables) {
                if (!variableNode.name.equals("this")) continue;
                if (that) {
                    variableNode.name = "that";
                    continue;
                }
                that = true;
            }
            if (!methodNode.name.equals("func_85027_a")) continue;
            methodNode.instructions.clear();
            methodNode.instructions.add((AbstractInsnNode)new InsnNode(177));
            break;
        }
    }
}

