package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ServerConfigurationManagerTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.server.management.ServerConfigurationManager";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();
            InsnList insnList = methodNode.instructions;

            if (methodNode.name.equals("getCurrentPlayerCount") || methodNode.name.equals("getCurrentPlayerCount") || methodNode.name.equals("getCurrentPlayerCount"))
            {
                AbstractInsnNode targetNode = null;
                AbstractInsnNode[] var7 = methodNode.instructions.toArray();
                int var8 = var7.length;

                for (int var9 = 0; var9 < var8; ++var9)
                {
                    AbstractInsnNode instruction = var7[var9];

                    if (instruction.getOpcode() == 172)
                    {
                        targetNode = instruction;
                    }
                }

                if (targetNode != null)
                {
                    insnList.insertBefore(targetNode, new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks;"));
                    insnList.insertBefore(targetNode, new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getPlayersInWaiting", "()I"));
                    insnList.insertBefore(targetNode, new InsnNode(96));
                }
            }
        }
    }
}
