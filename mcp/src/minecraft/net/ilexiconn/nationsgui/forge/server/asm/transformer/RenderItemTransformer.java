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

public class RenderItemTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.renderer.entity.RenderItem";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("renderItemIntoGUI") || !methodNode.desc.equals("(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IIZ)V")) continue;
            InsnList insnList = new InsnList();
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 3));
            insnList.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "hookItemStackRender", "(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"));
            insnList.add((AbstractInsnNode)new VarInsnNode(58, 3));
            methodNode.instructions.insert(insnList);
            break;
        }
    }
}

