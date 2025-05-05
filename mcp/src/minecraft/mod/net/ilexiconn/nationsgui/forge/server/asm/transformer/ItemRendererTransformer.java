package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ItemRendererTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.client.renderer.ItemRenderer";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();
            InsnList insnList;

            if (methodNode.name.equals("renderItem") && methodNode.desc.equals("(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;ILnet/minecraftforge/client/IItemRenderer$ItemRenderType;)V"))
            {
                insnList = new InsnList();
                insnList.add(new VarInsnNode(25, 2));
                insnList.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "hookItemStackRender", "(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"));
                insnList.add(new VarInsnNode(58, 2));
                methodNode.instructions.insert(insnList);
            }
            else if (methodNode.name.equals(dev ? "renderInsideOfBlock" : "renderInsideOfBlock"))
            {
                insnList = new InsnList();
                LabelNode labelNode = new LabelNode();
                insnList.add(new VarInsnNode(25, 2));
                insnList.add(new JumpInsnNode(199, labelNode));
                insnList.add(new InsnNode(177));
                insnList.add(labelNode);
                methodNode.instructions.insert(insnList);
            }
        }
    }
}
