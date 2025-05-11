/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class EntityPlayerSPTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.entity.EntityPlayerSP";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("displayGUIWorkbench") && !methodNode.name.equals("func_71058_b")) continue;
            for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                if (abstractInsnNode instanceof MethodInsnNode) {
                    MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                    if (!methodInsnNode.owner.equals("net/minecraft/client/gui/inventory/GuiCrafting")) continue;
                    methodInsnNode.owner = "net/ilexiconn/nationsgui/forge/client/gui/CustomCraftingGUI";
                    continue;
                }
                if (!(abstractInsnNode instanceof TypeInsnNode)) continue;
                TypeInsnNode typeInsnNode = (TypeInsnNode)abstractInsnNode;
                if (!typeInsnNode.desc.equals("net/minecraft/client/gui/inventory/GuiCrafting")) continue;
                typeInsnNode.desc = "net/ilexiconn/nationsgui/forge/client/gui/CustomCraftingGUI";
            }
        }
    }
}

