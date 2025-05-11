/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ForgeHookClientTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraftforge.client.ForgeHooksClient";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (methodNode.name.equals("renderInventoryItem")) {
                InsnList insnList = new InsnList();
                insnList.add((AbstractInsnNode)new VarInsnNode(25, 2));
                insnList.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "hookItemStackRender", "(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"));
                insnList.add((AbstractInsnNode)new VarInsnNode(58, 2));
                methodNode.instructions.insert(insnList);
                continue;
            }
            if (!methodNode.name.equals("getArmorTexture")) continue;
            AbstractInsnNode target = null;
            for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray()) {
                if (abstractInsnNode.getOpcode() != 176) continue;
                target = abstractInsnNode;
                break;
            }
            InsnList list = new InsnList();
            list.add((AbstractInsnNode)new VarInsnNode(25, 0));
            list.add((AbstractInsnNode)new VarInsnNode(25, 1));
            list.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/itemskin/ArmorSimpleSkin", "getTexture", "(Ljava/lang/String;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/lang/String;"));
            methodNode.instructions.insertBefore(target, list);
        }
    }
}

