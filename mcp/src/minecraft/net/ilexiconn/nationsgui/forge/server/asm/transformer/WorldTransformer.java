package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class WorldTransformer implements Transformer
{
    int i = 0;
    int j = 0;

    public String getTarget()
    {
        return "net.minecraft.world.World";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();

            if ((methodNode.name.equalsIgnoreCase("h") || methodNode.name.equalsIgnoreCase("updateEntities") || methodNode.name.equalsIgnoreCase("updateEntities")) && methodNode.desc.equalsIgnoreCase("()V"))
            {
                AbstractInsnNode[] var5 = methodNode.instructions.toArray();
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; ++var7)
                {
                    AbstractInsnNode insn = var5[var7];

                    if (insn instanceof MethodInsnNode)
                    {
                        MethodInsnNode ins = (MethodInsnNode)insn;

                        if (ins.name.equalsIgnoreCase("removeAll") && ins.desc.equalsIgnoreCase("(Ljava/util/Collection;)Z") && ins.owner.equalsIgnoreCase("java/util/List"))
                        {
                            methodNode.instructions.set(insn, new MethodInsnNode(184, WorldTransformer.class.getName().replace('.', '/'), "removeAllHashSet", "(Ljava/util/List;Ljava/util/List;)Z"));
                        }
                    }
                }
            }
        }
    }

    public static boolean removeAllHashSet(List l, List remove)
    {
        return l.removeAll(new HashSet(remove));
    }
}
