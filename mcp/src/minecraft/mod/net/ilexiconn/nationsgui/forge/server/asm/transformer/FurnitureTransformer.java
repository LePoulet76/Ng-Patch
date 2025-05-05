package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class FurnitureTransformer implements Transformer
{
    public String getTarget()
    {
        return "com.mrcrayfish.furniture.network.PacketManager";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode m = (MethodNode)var3.next();

            if (m.name.equalsIgnoreCase("handleEnvelopePacket"))
            {
                m.instructions.insertBefore(m.instructions.getFirst(), new InsnNode(177));
            }
        }
    }
}
