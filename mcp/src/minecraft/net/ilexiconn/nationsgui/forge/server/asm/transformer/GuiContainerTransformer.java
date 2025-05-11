/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class GuiContainerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.gui.inventory.GuiContainer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        block2: for (MethodNode methodNode : node.methods) {
            if (methodNode.name.equals("checkHotbarKeys") || methodNode.name.equals("func_146983_a")) {
                methodNode.instructions.clear();
                methodNode.instructions.add((AbstractInsnNode)new InsnNode(3));
                methodNode.instructions.add((AbstractInsnNode)new InsnNode(172));
                continue;
            }
            if (methodNode.name.equals("drawItemStackTooltip") || methodNode.name.equals("func_74184_a")) {
                methodNode.instructions.clear();
                methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
                methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(25, 1));
                methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(21, 2));
                methodNode.instructions.add((AbstractInsnNode)new VarInsnNode(21, 3));
                methodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawItemStackTooltip", "(Lnet/minecraft/item/ItemStack;II)V"));
                methodNode.instructions.add((AbstractInsnNode)new InsnNode(177));
                continue;
            }
            if (!methodNode.name.equals("drawScreen") && !methodNode.name.equals("func_73863_a") || NationsGUITransformer.inDevelopment) continue;
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                try {
                    AbstractInsnNode future = insnNode.getNext().getNext().getNext().getNext();
                    if (!(future instanceof MethodInsnNode)) continue;
                    MethodInsnNode mInsnNode = (MethodInsnNode)future;
                    if (!mInsnNode.name.equals("renderToolTips")) continue;
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 9));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new MethodInsnNode(182, "net/minecraft/entity/player/InventoryPlayer", dev ? "getItemStack" : "func_70445_o", "()Lnet/minecraft/item/ItemStack;"));
                    LabelNode l67 = new LabelNode();
                    LabelNode l56 = new LabelNode();
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new JumpInsnNode(199, l67));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new FieldInsnNode(180, "net/minecraft/client/gui/inventory/GuiContainer", dev ? "theSlot" : "field_82320_o", "Lnet/minecraft/inventory/Slot;"));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new JumpInsnNode(198, l67));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new FieldInsnNode(180, "net/minecraft/client/gui/inventory/GuiContainer", dev ? "theSlot" : "field_82320_o", "Lnet/minecraft/inventory/Slot;"));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new MethodInsnNode(182, "net/minecraft/inventory/Slot", dev ? "getHasStack" : "func_75216_d", "()Z"));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new JumpInsnNode(153, l67));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new FieldInsnNode(180, "net/minecraft/client/gui/inventory/GuiContainer", dev ? "theSlot" : "field_82320_o", "Lnet/minecraft/inventory/Slot;"));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new MethodInsnNode(182, "net/minecraft/inventory/Slot", dev ? "getStack" : "func_75211_c", "()Lnet/minecraft/item/ItemStack;"));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(58, 11));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(25, 11));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(21, 1));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new VarInsnNode(21, 2));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new MethodInsnNode(182, "net/minecraft/client/gui/inventory/GuiContainer", dev ? "drawItemStackTooltip" : "func_74184_a", "(Lnet/minecraft/item/ItemStack;II)V"));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)new JumpInsnNode(167, l56));
                    methodNode.instructions.insertBefore(insnNode, (AbstractInsnNode)l67);
                    methodNode.instructions.insert(future, (AbstractInsnNode)l56);
                    continue block2;
                }
                catch (NullPointerException nullPointerException) {
                    // empty catch block
                }
            }
        }
    }
}

