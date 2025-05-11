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

public class TileEntityRendererTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.renderer.tileentity.TileEntityRenderer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals(dev ? "renderTileEntityAt" : "func_76949_a")) continue;
            LabelNode firstLabel = null;
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (!(insnNode instanceof LabelNode)) continue;
                firstLabel = (LabelNode)insnNode;
                break;
            }
            InsnList patch = new InsnList();
            patch.add((AbstractInsnNode)new VarInsnNode(25, 1));
            patch.add((AbstractInsnNode)new VarInsnNode(24, 2));
            patch.add((AbstractInsnNode)new VarInsnNode(24, 4));
            patch.add((AbstractInsnNode)new VarInsnNode(24, 6));
            patch.add((AbstractInsnNode)new VarInsnNode(23, 8));
            patch.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "skipTileRender", "(Lnet/minecraft/tileentity/TileEntity;DDDF)Z"));
            patch.add((AbstractInsnNode)new JumpInsnNode(153, firstLabel));
            patch.add((AbstractInsnNode)new InsnNode(177));
            methodNode.instructions.insert(patch);
        }
    }
}

