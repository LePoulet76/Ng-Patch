package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class BlockTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.block.Block";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();
            InsnList insnList = methodNode.instructions;

            if (methodNode.name.equals("isBlockNormalCube") || methodNode.name.equals("isBlockNormalCube"))
            {
                AbstractInsnNode targetNode = null;
                AbstractInsnNode[] newLabel = methodNode.instructions.toArray();
                int var8 = newLabel.length;

                for (int var9 = 0; var9 < var8; ++var9)
                {
                    AbstractInsnNode instruction = newLabel[var9];

                    if (instruction.getOpcode() == 25 && ((VarInsnNode)instruction).var == 0)
                    {
                        targetNode = instruction;
                        break;
                    }
                }

                if (targetNode != null)
                {
                    insnList.insertBefore(targetNode, new VarInsnNode(25, 0));
                    LabelNode var11 = new LabelNode();
                    insnList.insertBefore(targetNode, new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "isBlocBypassCheckNormalCube", "(Lnet/minecraft/block/Block;)Z"));
                    insnList.insertBefore(targetNode, new JumpInsnNode(153, var11));
                    insnList.insertBefore(targetNode, new InsnNode(4));
                    insnList.insertBefore(targetNode, new InsnNode(172));
                    insnList.insertBefore(targetNode, var11);
                }
            }
        }
    }
}
