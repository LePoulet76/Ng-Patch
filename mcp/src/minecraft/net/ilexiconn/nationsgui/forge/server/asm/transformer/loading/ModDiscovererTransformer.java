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

public class ModDiscovererTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "cpw.mods.fml.common.discovery.ModDiscoverer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        if (NationsGUITransformer.isServer) {
            return;
        }
        for (MethodNode m : node.methods) {
            if (!m.name.equalsIgnoreCase("identifyMods") || !m.desc.equalsIgnoreCase("()Ljava/util/List;")) continue;
            InsnList list = new InsnList();
            list.add((AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks;"));
            list.add((AbstractInsnNode)new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawLoadingScreen", "()V"));
            for (AbstractInsnNode i : m.instructions.toArray()) {
                if (!(i instanceof FieldInsnNode)) continue;
                FieldInsnNode insn = (FieldInsnNode)i;
                if (!insn.owner.equalsIgnoreCase("cpw/mods/fml/common/discovery/ModDiscoverer") || !insn.name.equalsIgnoreCase("dataTable") || !insn.desc.equalsIgnoreCase("Lcpw/mods/fml/common/discovery/ASMDataTable;")) continue;
                m.instructions.insertBefore(insn.getPrevious().getPrevious(), list);
            }
        }
    }
}

