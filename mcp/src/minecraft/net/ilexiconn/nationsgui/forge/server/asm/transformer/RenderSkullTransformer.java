/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
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
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class RenderSkullTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("func_82393_a")) continue;
            InsnList insnList = methodNode.instructions;
            insnList.clear();
            insnList.add((AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/client/render/block/SkullBlockRenderer", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/client/render/block/SkullBlockRenderer;"));
            insnList.add((AbstractInsnNode)new VarInsnNode(23, 1));
            insnList.add((AbstractInsnNode)new VarInsnNode(23, 2));
            insnList.add((AbstractInsnNode)new VarInsnNode(23, 3));
            insnList.add((AbstractInsnNode)new VarInsnNode(21, 4));
            insnList.add((AbstractInsnNode)new VarInsnNode(23, 5));
            insnList.add((AbstractInsnNode)new VarInsnNode(21, 6));
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 7));
            insnList.add((AbstractInsnNode)new InsnNode(4));
            insnList.add((AbstractInsnNode)new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/client/render/block/SkullBlockRenderer", "renderSkull", "(FFFIFILjava/lang/String;Z)V"));
            insnList.add((AbstractInsnNode)new InsnNode(177));
            return;
        }
    }
}

