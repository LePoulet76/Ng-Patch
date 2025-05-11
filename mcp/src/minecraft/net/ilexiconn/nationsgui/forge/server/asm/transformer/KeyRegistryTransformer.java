/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
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
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class KeyRegistryTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "cpw.mods.fml.client.registry.KeyBindingRegistry";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("uploadKeyBindingsToGame")) continue;
            InsnList insnList = methodNode.instructions;
            for (AbstractInsnNode insnNode : insnList.toArray()) {
                if (!(insnNode instanceof VarInsnNode) || !(insnNode.getNext() instanceof VarInsnNode) || !(insnNode.getNext().getNext() instanceof MethodInsnNode) || !((MethodInsnNode)insnNode.getNext().getNext()).name.equals("size")) continue;
                insnList.insertBefore(insnNode, (AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks;"));
                insnList.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                insnList.insertBefore(insnNode, (AbstractInsnNode)new FieldInsnNode(180, "cpw/mods/fml/client/registry/KeyBindingRegistry", "keyHandlers", "Ljava/util/Set;"));
                insnList.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 2));
                insnList.insertBefore(insnNode, (AbstractInsnNode)new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "applyControlBlacklist", "(Ljava/util/Set;Ljava/util/List;)V"));
                break;
            }
            return;
        }
    }
}

