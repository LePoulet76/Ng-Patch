/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class RenderGlobalTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.renderer.RenderGlobal";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode method : node.methods) {
            if (!method.name.equals(dev ? "loadRenderers" : "func_72712_a")) continue;
            ArrayList<FieldInsnNode> targets = new ArrayList<FieldInsnNode>();
            for (AbstractInsnNode insnNode : method.instructions.toArray()) {
                if (!(insnNode instanceof FieldInsnNode)) continue;
                FieldInsnNode fieldInsnNode = (FieldInsnNode)insnNode;
                if (NationsGUITransformer.optifine) {
                    if (fieldInsnNode.name.equals("ofRenderDistanceFine")) {
                        targets.add(fieldInsnNode);
                    }
                    if (targets.size() < 2) continue;
                    break;
                }
                if (fieldInsnNode.getOpcode() != 180 || !fieldInsnNode.name.equals(dev ? "renderDistance" : "field_72739_F") || !fieldInsnNode.owner.equals("net/minecraft/client/renderer/RenderGlobal")) continue;
                targets.add(fieldInsnNode);
                break;
            }
            for (AbstractInsnNode abstractInsnNode : targets) {
                method.instructions.insert(abstractInsnNode, (AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "hookRenderDistance", "(I)I"));
            }
        }
    }
}

