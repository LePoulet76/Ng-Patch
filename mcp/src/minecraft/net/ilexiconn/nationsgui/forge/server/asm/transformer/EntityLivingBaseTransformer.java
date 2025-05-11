/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class EntityLivingBaseTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.entity.EntityLivingBase";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            InsnList insnList = methodNode.instructions;
            if (methodNode.name.equals("onNewPotionEffect") || methodNode.name.equals("func_70670_a")) {
                for (AbstractInsnNode insnNode : insnList.toArray()) {
                    if (insnNode.getOpcode() != 177) continue;
                    insnList.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                    insnList.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 1));
                    insnList.insertBefore(insnNode, (AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "onNewPotionEffect", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/potion/PotionEffect;)V"));
                }
                continue;
            }
            if (methodNode.name.equals("onChangedPotionEffect") || methodNode.name.equals("func_70695_b")) {
                for (AbstractInsnNode insnNode : insnList.toArray()) {
                    if (insnNode.getOpcode() != 177) continue;
                    insnList.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                    insnList.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 1));
                    insnList.insertBefore(insnNode, (AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "onChangedPotionEffect", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/potion/PotionEffect;)V"));
                }
                continue;
            }
            if (methodNode.name.equals("onFinishedPotionEffect") || methodNode.name.equals("func_70688_c")) {
                for (AbstractInsnNode insnNode : insnList.toArray()) {
                    if (insnNode.getOpcode() != 177) continue;
                    insnList.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                    insnList.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 1));
                    insnList.insertBefore(insnNode, (AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "onFinishedPotionEffect", "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/potion/PotionEffect;)V"));
                }
                continue;
            }
            if (methodNode.name.equals("setArrowCountInEntity") || methodNode.name.equals("func_85034_r")) {
                insnList.clear();
                insnList.add((AbstractInsnNode)new InsnNode(177));
                continue;
            }
            if (!methodNode.name.equals("getArrowCountInEntity") && !methodNode.name.equals("func_85035_bI")) continue;
            insnList.clear();
            insnList.add((AbstractInsnNode)new InsnNode(3));
            insnList.add((AbstractInsnNode)new InsnNode(172));
        }
    }
}

