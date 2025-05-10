package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class RenderItemTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.client.renderer.entity.RenderItem";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();

            if (methodNode.name.equals("renderItemIntoGUI") && methodNode.desc.equals("(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IIZ)V"))
            {
                InsnList insnList = new InsnList();
                insnList.add(new VarInsnNode(25, 3));
                insnList.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "hookItemStackRender", "(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"));
                insnList.add(new VarInsnNode(58, 3));
                methodNode.instructions.insert(insnList);
                break;
            }
        }
    }
}
