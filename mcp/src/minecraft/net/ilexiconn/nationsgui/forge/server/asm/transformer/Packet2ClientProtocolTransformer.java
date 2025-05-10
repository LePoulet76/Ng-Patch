package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class Packet2ClientProtocolTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.network.packet.Packet2ClientProtocol";
    }

    public void transform(ClassNode classNode, boolean dev)
    {
        String methodName = NationsGUITransformer.inDevelopment ? "processPacket" : "processPacket";
        Iterator var4 = classNode.methods.iterator();

        while (var4.hasNext())
        {
            MethodNode methodNode = (MethodNode)var4.next();

            if (methodNode.name.equals(methodName) && methodNode.desc.equals("(Lnet/minecraft/network/packet/NetHandler;)V"))
            {
                InsnList insnList = methodNode.instructions;
                insnList.clear();
                insnList.add(new VarInsnNode(25, 0));
                insnList.add(new VarInsnNode(25, 1));
                insnList.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NetworkHook", "onProtocolPacket", "(Lnet/minecraft/network/packet/Packet2ClientProtocol;Lnet/minecraft/network/packet/NetHandler;)V"));
                insnList.add(new InsnNode(177));
            }
        }
    }
}
