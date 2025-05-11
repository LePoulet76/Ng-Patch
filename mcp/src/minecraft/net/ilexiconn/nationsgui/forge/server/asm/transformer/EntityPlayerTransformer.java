/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class EntityPlayerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.entity.player.EntityPlayer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (methodNode.name.equals("<init>")) {
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (insnNode.getOpcode() == 187) {
                        TypeInsnNode insnNode1 = (TypeInsnNode)insnNode;
                        if (!insnNode1.desc.equals("net/minecraft/inventory/ContainerPlayer")) continue;
                        insnNode1.desc = "net/ilexiconn/nationsgui/forge/server/container/PlayerContainer";
                        continue;
                    }
                    if (insnNode.getOpcode() != 183) continue;
                    MethodInsnNode methodInsnNode = (MethodInsnNode)insnNode;
                    if (!methodInsnNode.owner.equals("net/minecraft/inventory/ContainerPlayer")) continue;
                    methodInsnNode.owner = "net/ilexiconn/nationsgui/forge/server/container/PlayerContainer";
                }
                continue;
            }
            if (!methodNode.name.equals(dev ? "getItemIcon" : "func_70620_b")) continue;
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (insnNode.getOpcode() != 176) continue;
                InsnList patch = new InsnList();
                patch.add((AbstractInsnNode)new VarInsnNode(25, 0));
                patch.add((AbstractInsnNode)new VarInsnNode(25, 1));
                patch.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/itemskin/ItemSkinSimple", "getCustomIcon", "(Lnet/minecraft/util/Icon;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/util/Icon;"));
                methodNode.instructions.insertBefore(insnNode, patch);
            }
        }
    }
}

