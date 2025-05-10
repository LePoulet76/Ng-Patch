package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class FMLNetworkHandlerTransformer implements Transformer
{
    public String getTarget()
    {
        return "cpw.mods.fml.common.network.FMLNetworkHandler";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode method = (MethodNode)var3.next();

            if (method.name.equals("handlePacket250Packet"))
            {
                InsnList patch = new InsnList();
                patch.add(new VarInsnNode(25, 0));
                patch.add(new VarInsnNode(25, 1));
                patch.add(new VarInsnNode(25, 2));
                patch.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "networkDebug", "(Lnet/minecraft/network/packet/Packet250CustomPayload;Lnet/minecraft/network/INetworkManager;Lnet/minecraft/network/packet/NetHandler;)V"));
                method.instructions.insert(patch);
                break;
            }
        }
    }
}
