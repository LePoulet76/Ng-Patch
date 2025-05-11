/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PotionTransformer
implements Transformer {
    private static final double HARMING_MULTIPLIER = 0.4;

    @Override
    public String getTarget() {
        return "net.minecraft.potion.Potion";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode mn : node.methods) {
            if (!mn.name.equals("applyInstantEffect")) continue;
            for (int i = 0; i < mn.instructions.size(); ++i) {
                AbstractInsnNode insn = mn.instructions.get(i);
                if (!(insn instanceof VarInsnNode) || ((VarInsnNode)insn).var != 7) continue;
                mn.instructions.insertBefore(insn, (AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/PotionTransformer", "getNewHarmingDamage", "(I)I"));
                return;
            }
        }
    }

    public static int getNewHarmingDamage(int i) {
        return (int)((double)i * 0.4);
    }
}

