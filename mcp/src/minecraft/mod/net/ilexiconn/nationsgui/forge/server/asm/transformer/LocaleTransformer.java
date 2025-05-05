package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class LocaleTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.client.resources.Locale";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();
            InsnList insnList;

            if (!methodNode.name.equals("isUnicode") && !methodNode.name.equals("isUnicode"))
            {
                if (methodNode.name.equals("checkUnicode") || methodNode.name.equals("checkUnicode"))
                {
                    insnList = methodNode.instructions;
                    insnList.clear();
                    insnList.add(new InsnNode(177));
                }
            }
            else
            {
                insnList = methodNode.instructions;
                insnList.clear();
                insnList.add(new InsnNode(3));
                insnList.add(new InsnNode(172));
            }
        }
    }
}
