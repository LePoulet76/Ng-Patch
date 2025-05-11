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

public class BlockSpawnerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.block.BlockMobSpawner";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode method : node.methods) {
            if (!method.name.equals(dev ? "idPicked" : "func_71922_a")) continue;
            InsnList insnList = method.instructions;
            insnList.clear();
            insnList.add((AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/NationsGUI", "MOB_SPAWNER", "Lnet/minecraft/item/Item;"));
            insnList.add((AbstractInsnNode)new FieldInsnNode(180, "net/minecraft/item/Item", dev ? "itemID" : "field_77779_bT", "I"));
            insnList.add((AbstractInsnNode)new InsnNode(172));
            break;
        }
        MethodNode getDamageValue = new MethodNode(1, dev ? "getDamageValue" : "func_71873_h", "(Lnet/minecraft/world/World;III)I", null, null);
        InsnList insnList = getDamageValue.instructions;
        insnList.add((AbstractInsnNode)new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks;"));
        insnList.add((AbstractInsnNode)new VarInsnNode(25, 1));
        insnList.add((AbstractInsnNode)new VarInsnNode(21, 2));
        insnList.add((AbstractInsnNode)new VarInsnNode(21, 3));
        insnList.add((AbstractInsnNode)new VarInsnNode(21, 4));
        insnList.add((AbstractInsnNode)new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getSpawnerMeta", "(Lnet/minecraft/world/World;III)I"));
        insnList.add((AbstractInsnNode)new InsnNode(172));
        node.methods.add(getDamageValue);
    }
}

