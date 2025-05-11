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

public class MinecraftForgeClientTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraftforge.client.MinecraftForgeClient";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("getItemRenderer")) continue;
            AbstractInsnNode target = null;
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (insnNode.getOpcode() != 58) continue;
                target = insnNode;
                break;
            }
            InsnList insnList = new InsnList();
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insnList.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/itemskin/ItemSkinModel", "hookRenderer", "(Lnet/minecraftforge/client/IItemRenderer;Lnet/minecraft/item/ItemStack;)Lnet/minecraftforge/client/IItemRenderer;"));
            methodNode.instructions.insertBefore(target, insnList);
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (insnNode.getOpcode() != 176) continue;
                target = insnNode;
                break;
            }
            for (int i = 0; i < 4; ++i) {
                methodNode.instructions.remove(target.getPrevious());
            }
            methodNode.instructions.insertBefore(target, (AbstractInsnNode)new VarInsnNode(25, 2));
            return;
        }
    }
}

