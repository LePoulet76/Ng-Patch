/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.IFMLLoadingPlugin$MCVersion
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FrameNode
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package fr.nationsglory.itemmanager.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

@IFMLLoadingPlugin.MCVersion(value="1.6.4")
public class GameRegistryTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "cpw.mods.fml.common.registry.GameRegistry";
    }

    @Override
    public void transform(ClassNode classNode, boolean dev) {
        for (MethodNode methodNode : classNode.methods) {
            if ((!methodNode.name.equals("registerBlock") || !methodNode.desc.equals("(Lnet/minecraft/block/Block;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)V")) && (!methodNode.name.equals("registerItem") || !methodNode.desc.equals("(Lnet/minecraft/item/Item;Ljava/lang/String;Ljava/lang/String;)V"))) continue;
            AbstractInsnNode first = methodNode.instructions.getFirst();
            methodNode.instructions.insertBefore(first, (AbstractInsnNode)new VarInsnNode(25, 0));
            methodNode.instructions.insertBefore(first, (AbstractInsnNode)new VarInsnNode(25, methodNode.name.equals("registerBlock") ? 3 : 2));
            methodNode.instructions.insertBefore(first, (AbstractInsnNode)new MethodInsnNode(184, "fr/nationsglory/itemmanager/RegisterHook", "blockIsBlacklisted", "(Ljava/lang/Object;Ljava/lang/String;)Z"));
            methodNode.instructions.insertBefore(first, (AbstractInsnNode)new InsnNode(4));
            LabelNode l1 = new LabelNode();
            methodNode.instructions.insertBefore(first, (AbstractInsnNode)new JumpInsnNode(160, l1));
            methodNode.instructions.insertBefore(first, (AbstractInsnNode)new InsnNode(177));
            methodNode.instructions.insertBefore(first, (AbstractInsnNode)new FrameNode(3, 0, null, 0, null));
            methodNode.instructions.insertBefore(first, (AbstractInsnNode)l1);
        }
    }
}

