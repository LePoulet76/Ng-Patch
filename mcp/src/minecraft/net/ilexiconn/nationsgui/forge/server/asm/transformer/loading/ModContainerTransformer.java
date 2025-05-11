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
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer.loading;

import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ModContainerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "cpw.mods.fml.common.FMLModContainer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        if (NationsGUITransformer.isServer) {
            return;
        }
        InsnList list = new InsnList();
        list.add((AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks;"));
        list.add((AbstractInsnNode)new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawLoadingScreen", "()V"));
        for (MethodNode m : node.methods) {
            MethodInsnNode insn;
            if (m.name.equalsIgnoreCase("constructMod") && m.desc.equalsIgnoreCase("(Lcpw/mods/fml/common/event/FMLConstructionEvent;)V")) {
                for (AbstractInsnNode i : m.instructions.toArray()) {
                    if (!(i instanceof MethodInsnNode)) continue;
                    insn = (MethodInsnNode)i;
                    if (!insn.name.equalsIgnoreCase("processFieldAnnotations")) continue;
                    m.instructions.insert((AbstractInsnNode)insn, list);
                }
                continue;
            }
            if (!m.name.equalsIgnoreCase("handleModStateEvent") || !m.desc.equalsIgnoreCase("(Lcpw/mods/fml/common/event/FMLEvent;)V")) continue;
            for (AbstractInsnNode i : m.instructions.toArray()) {
                if (!(i instanceof FieldInsnNode)) continue;
                insn = (FieldInsnNode)i;
                if (!insn.owner.equalsIgnoreCase("cpw/mods/fml/common/FMLModContainer") || !insn.name.equalsIgnoreCase("eventMethods") || !insn.desc.equalsIgnoreCase("Lcom/google/common/collect/ListMultimap;")) continue;
                m.instructions.insertBefore(insn.getPrevious(), list);
            }
        }
    }
}

