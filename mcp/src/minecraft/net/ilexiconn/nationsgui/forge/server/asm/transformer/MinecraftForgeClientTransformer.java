package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class MinecraftForgeClientTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraftforge.client.MinecraftForgeClient";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();
        MethodNode methodNode;

        do
        {
            if (!var3.hasNext())
            {
                return;
            }

            methodNode = (MethodNode)var3.next();
        }
        while (!methodNode.name.equals("getItemRenderer"));

        AbstractInsnNode target = null;
        AbstractInsnNode[] insnList = methodNode.instructions.toArray();
        int i = insnList.length;
        int var8;

        for (var8 = 0; var8 < i; ++var8)
        {
            AbstractInsnNode insnNode = insnList[var8];

            if (insnNode.getOpcode() == 58)
            {
                target = insnNode;
                break;
            }
        }

        InsnList var11 = new InsnList();
        var11.add(new VarInsnNode(25, 0));
        var11.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/itemskin/ItemSkinModel", "hookRenderer", "(Lnet/minecraftforge/client/IItemRenderer;Lnet/minecraft/item/ItemStack;)Lnet/minecraftforge/client/IItemRenderer;"));
        methodNode.instructions.insertBefore(target, var11);
        AbstractInsnNode[] var12 = methodNode.instructions.toArray();
        var8 = var12.length;

        for (int var13 = 0; var13 < var8; ++var13)
        {
            AbstractInsnNode insnNode1 = var12[var13];

            if (insnNode1.getOpcode() == 176)
            {
                target = insnNode1;
                break;
            }
        }

        for (i = 0; i < 4; ++i)
        {
            methodNode.instructions.remove(target.getPrevious());
        }

        methodNode.instructions.insertBefore(target, new VarInsnNode(25, 2));
    }
}
