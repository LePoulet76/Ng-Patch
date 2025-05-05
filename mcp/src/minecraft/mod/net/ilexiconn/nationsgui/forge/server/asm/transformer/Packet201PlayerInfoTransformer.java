package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class Packet201PlayerInfoTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.network.packet.Packet201PlayerInfo";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();

            if (methodNode.name.equals(dev ? "readPacketData" : "readPacketData"))
            {
                AbstractInsnNode[] var5 = methodNode.instructions.toArray();
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; ++var7)
                {
                    AbstractInsnNode abstractInsnNode = var5[var7];

                    if (abstractInsnNode instanceof MethodInsnNode)
                    {
                        MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;

                        if (methodInsnNode.name.equals(dev ? "readString" : "readString"))
                        {
                            IntInsnNode varInsnNode = (IntInsnNode)methodInsnNode.getPrevious();
                            varInsnNode.operand = 100;
                            return;
                        }
                    }
                }
            }
        }
    }
}
