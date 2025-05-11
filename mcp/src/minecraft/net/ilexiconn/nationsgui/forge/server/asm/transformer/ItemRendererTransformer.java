/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
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
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ItemRendererTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.renderer.ItemRenderer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            InsnList insnList;
            if (methodNode.name.equals("renderItem") && methodNode.desc.equals("(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;ILnet/minecraftforge/client/IItemRenderer$ItemRenderType;)V")) {
                insnList = new InsnList();
                insnList.add((AbstractInsnNode)new VarInsnNode(25, 2));
                insnList.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "hookItemStackRender", "(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"));
                insnList.add((AbstractInsnNode)new VarInsnNode(58, 2));
                methodNode.instructions.insert(insnList);
                continue;
            }
            if (!methodNode.name.equals(dev ? "renderInsideOfBlock" : "func_78446_a")) continue;
            insnList = new InsnList();
            LabelNode labelNode = new LabelNode();
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 2));
            insnList.add((AbstractInsnNode)new JumpInsnNode(199, labelNode));
            insnList.add((AbstractInsnNode)new InsnNode(177));
            insnList.add((AbstractInsnNode)labelNode);
            methodNode.instructions.insert(insnList);
        }
    }
}

