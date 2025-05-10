package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class SkullTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.block.BlockSkull";
    }

    public void transform(ClassNode node, boolean dev)
    {
        MethodNode getPickBlock = new MethodNode(1, "getPickBlock", "(Lnet/minecraft/util/MovingObjectPosition;Lnet/minecraft/world/World;III)Lnet/minecraft/item/ItemStack;", (String)null, (String[])null);
        InsnList insnList = getPickBlock.instructions;
        insnList.add(new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks;"));
        insnList.add(new VarInsnNode(25, 0));
        insnList.add(new VarInsnNode(25, 2));
        insnList.add(new VarInsnNode(21, 3));
        insnList.add(new VarInsnNode(21, 4));
        insnList.add(new VarInsnNode(21, 5));
        insnList.add(new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getPickBlock", "(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;III)Lnet/minecraft/item/ItemStack;"));
        insnList.add(new InsnNode(176));
        node.methods.add(getPickBlock);
    }
}
