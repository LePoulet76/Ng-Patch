package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class EntityPlayerSPTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.client.entity.EntityPlayerSP";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();

            if (methodNode.name.equals("displayGUIWorkbench") || methodNode.name.equals("displayGUIWorkbench"))
            {
                AbstractInsnNode[] var5 = methodNode.instructions.toArray();
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; ++var7)
                {
                    AbstractInsnNode abstractInsnNode = var5[var7];

                    if (abstractInsnNode instanceof MethodInsnNode)
                    {
                        MethodInsnNode typeInsnNode = (MethodInsnNode)abstractInsnNode;

                        if (typeInsnNode.owner.equals("net/minecraft/client/gui/inventory/GuiCrafting"))
                        {
                            typeInsnNode.owner = "net/ilexiconn/nationsgui/forge/client/gui/CustomCraftingGUI";
                        }
                    }
                    else if (abstractInsnNode instanceof TypeInsnNode)
                    {
                        TypeInsnNode var10 = (TypeInsnNode)abstractInsnNode;

                        if (var10.desc.equals("net/minecraft/client/gui/inventory/GuiCrafting"))
                        {
                            var10.desc = "net/ilexiconn/nationsgui/forge/client/gui/CustomCraftingGUI";
                        }
                    }
                }
            }
        }
    }
}
