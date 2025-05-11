/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.LineNumberNode
 *  org.objectweb.asm.tree.LocalVariableNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PlayerManagerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.server.management.PlayerManager";
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            void var11_19;
            if (methodNode.name.equals(dev ? "filterChunkLoadQueue" : "func_72691_b") || methodNode.name.equals(dev ? "updateMountedMovingPlayer" : "func_72685_d")) {
                FieldInsnNode target = null;
                for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                    if (!(abstractInsnNode instanceof FieldInsnNode)) continue;
                    FieldInsnNode fieldInsnNode = (FieldInsnNode)abstractInsnNode;
                    if (!fieldInsnNode.name.equals(dev ? "playerViewRadius" : "field_72698_e")) continue;
                    target = fieldInsnNode;
                    break;
                }
                InsnList patch = new InsnList();
                patch.add((AbstractInsnNode)new VarInsnNode(25, 1));
                patch.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getPlayerRenderRadius", "(Lnet/minecraft/entity/player/EntityPlayerMP;)I"));
                methodNode.instructions.insert((AbstractInsnNode)target, patch);
                methodNode.instructions.remove(target.getPrevious());
                methodNode.instructions.remove((AbstractInsnNode)target);
                continue;
            }
            if (!methodNode.name.equals(dev ? "addPlayer" : "func_72683_a") && !methodNode.name.equals(dev ? "removePlayer" : "func_72695_c")) continue;
            LocalVariableNode firstVariable = null;
            for (LocalVariableNode variableNode : methodNode.localVariables) {
                if (variableNode.index != 1) continue;
                firstVariable = variableNode;
                break;
            }
            LocalVariableNode localVariableNode = new LocalVariableNode("renderDistance", "I", null, firstVariable.start, firstVariable.end, methodNode.localVariables.size());
            methodNode.localVariables.add(localVariableNode);
            ArrayList<FieldInsnNode> targets = new ArrayList<FieldInsnNode>();
            LineNumberNode lineNumberNode = null;
            AbstractInsnNode[] abstractInsnNodeArray = methodNode.instructions.toArray();
            int n = abstractInsnNodeArray.length;
            boolean bl = false;
            while (var11_19 < n) {
                AbstractInsnNode insnNode3 = abstractInsnNodeArray[var11_19];
                if (insnNode3 instanceof FieldInsnNode) {
                    FieldInsnNode fieldInsnNode = (FieldInsnNode)insnNode3;
                    if (fieldInsnNode.name.equals(dev ? "playerViewRadius" : "field_72698_e")) {
                        targets.add(fieldInsnNode);
                    }
                } else if (lineNumberNode == null && insnNode3 instanceof LineNumberNode) {
                    lineNumberNode = (LineNumberNode)insnNode3;
                }
                ++var11_19;
            }
            InsnList insnList = new InsnList();
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 1));
            insnList.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getPlayerRenderRadius", "(Lnet/minecraft/entity/player/EntityPlayerMP;)I"));
            insnList.add((AbstractInsnNode)new VarInsnNode(54, localVariableNode.index));
            methodNode.instructions.insert((AbstractInsnNode)lineNumberNode, insnList);
            for (AbstractInsnNode abstractInsnNode : targets) {
                VarInsnNode varInsnNode = (VarInsnNode)abstractInsnNode.getPrevious();
                varInsnNode.var = localVariableNode.index;
                varInsnNode.setOpcode(21);
                methodNode.instructions.remove(abstractInsnNode);
            }
        }
    }
}

