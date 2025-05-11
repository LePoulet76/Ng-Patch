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

public class RenderTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.renderer.entity.Render";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode method : node.methods) {
            if (!method.name.equals(dev ? "bindEntityTexture" : "func_110777_b")) continue;
            MethodInsnNode lastNode = null;
            for (AbstractInsnNode insnNode : method.instructions.toArray()) {
                if (!(insnNode instanceof MethodInsnNode)) continue;
                lastNode = (MethodInsnNode)insnNode;
            }
            InsnList patch = new InsnList();
            patch.add((AbstractInsnNode)new VarInsnNode(25, 1));
            patch.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/itemskin/EntitySkin", "hookEntityTexture", "(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/ResourceLocation;"));
            method.instructions.insertBefore((AbstractInsnNode)lastNode, patch);
            return;
        }
    }
}

