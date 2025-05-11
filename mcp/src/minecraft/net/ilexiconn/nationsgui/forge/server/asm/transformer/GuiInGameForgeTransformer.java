/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class GuiInGameForgeTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraftforge.client.GuiIngameForge";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            InsnList insnList = methodNode.instructions;
            if (!methodNode.name.equals("renderHUDText")) continue;
            AbstractInsnNode targetNode = null;
            AbstractInsnNode versionNode = null;
            for (AbstractInsnNode instruction : methodNode.instructions.toArray()) {
                if (instruction.getOpcode() == 18) {
                    if (!((LdcInsnNode)instruction).cst.equals("Minecraft ")) continue;
                    targetNode = instruction;
                    continue;
                }
                if (instruction.getOpcode() != 178 || !((FieldInsnNode)instruction).name.equals("MC_VERSION")) continue;
                versionNode = instruction;
            }
            if (targetNode != null) {
                insnList.insertBefore(targetNode.getNext(), (AbstractInsnNode)new LdcInsnNode((Object)"NationsGlory"));
                insnList.remove(targetNode);
            }
            if (versionNode == null) continue;
            insnList.remove(versionNode.getNext());
            insnList.remove(versionNode);
        }
    }
}

