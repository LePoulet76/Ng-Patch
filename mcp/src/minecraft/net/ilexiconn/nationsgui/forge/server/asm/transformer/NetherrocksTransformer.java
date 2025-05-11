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

import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class NetherrocksTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "Netherrocks.core.conf.IDs";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            methodNode.tryCatchBlocks.clear();
            if (!methodNode.name.equals("doConfig")) continue;
            MethodInsnNode beginTarget = null;
            ArrayList<AbstractInsnNode> idInsList = new ArrayList<AbstractInsnNode>();
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (insnNode instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode = (MethodInsnNode)insnNode;
                    if (beginTarget != null || !methodInsnNode.name.equals("load") || !methodInsnNode.owner.equals("net/minecraftforge/common/Configuration")) continue;
                    beginTarget = methodInsnNode;
                    continue;
                }
                if (insnNode.getOpcode() != 17) continue;
                idInsList.add(insnNode);
            }
            while (beginTarget.getPrevious() != null) {
                methodNode.instructions.remove(beginTarget.getPrevious());
            }
            methodNode.instructions.remove(beginTarget);
            AbstractInsnNode lastIdIns = null;
            Iterator iterator = idInsList.iterator();
            while (iterator.hasNext()) {
                int i;
                AbstractInsnNode idIns;
                lastIdIns = idIns = (AbstractInsnNode)iterator.next();
                for (i = 0; i < 3; ++i) {
                    methodNode.instructions.remove(idIns.getPrevious());
                }
                for (i = 0; i < 2; ++i) {
                    methodNode.instructions.remove(idIns.getNext());
                }
            }
            lastIdIns = lastIdIns.getNext();
            while (lastIdIns.getNext() != null && lastIdIns.getNext().getOpcode() != 177) {
                methodNode.instructions.remove(lastIdIns.getNext());
            }
        }
    }
}

