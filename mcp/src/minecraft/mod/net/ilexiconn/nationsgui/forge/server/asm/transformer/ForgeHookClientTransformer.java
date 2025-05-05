package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ForgeHookClientTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraftforge.client.ForgeHooksClient";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();

            if (methodNode.name.equals("renderInventoryItem"))
            {
                InsnList var10 = new InsnList();
                var10.add(new VarInsnNode(25, 2));
                var10.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "hookItemStackRender", "(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"));
                var10.add(new VarInsnNode(58, 2));
                methodNode.instructions.insert(var10);
            }
            else if (methodNode.name.equals("getArmorTexture"))
            {
                AbstractInsnNode target = null;
                AbstractInsnNode[] list = methodNode.instructions.toArray();
                int var7 = list.length;

                for (int var8 = 0; var8 < var7; ++var8)
                {
                    AbstractInsnNode abstractInsnNode = list[var8];

                    if (abstractInsnNode.getOpcode() == 176)
                    {
                        target = abstractInsnNode;
                        break;
                    }
                }

                InsnList var11 = new InsnList();
                var11.add(new VarInsnNode(25, 0));
                var11.add(new VarInsnNode(25, 1));
                var11.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/itemskin/ArmorSimpleSkin", "getTexture", "(Ljava/lang/String;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/lang/String;"));
                methodNode.instructions.insertBefore(target, var11);
            }
        }
    }
}
