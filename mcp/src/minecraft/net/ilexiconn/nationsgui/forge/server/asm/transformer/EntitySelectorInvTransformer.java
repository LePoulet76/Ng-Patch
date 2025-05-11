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

public class EntitySelectorInvTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.command.IEntitySelector";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode mn : node.methods) {
            if (!mn.name.equals("<clinit>")) continue;
            for (int i = 0; i < mn.instructions.size(); ++i) {
                TypeInsnNode insn;
                AbstractInsnNode in = mn.instructions.get(i);
                if (in instanceof TypeInsnNode) {
                    insn = (TypeInsnNode)in;
                    if (!insn.desc.equals("net/minecraft/command/EntitySelectorInventory")) continue;
                    insn.desc = "net/ilexiconn/nationsgui/forge/server/asm/transformer/EntitySelectorInvWithoutVehicles";
                    continue;
                }
                if (!(in instanceof MethodInsnNode)) continue;
                insn = (MethodInsnNode)in;
                if (!insn.owner.equals("net/minecraft/command/EntitySelectorInventory")) continue;
                insn.owner = "net/ilexiconn/nationsgui/forge/server/asm/transformer/EntitySelectorInvWithoutVehicles";
            }
        }
    }
}

