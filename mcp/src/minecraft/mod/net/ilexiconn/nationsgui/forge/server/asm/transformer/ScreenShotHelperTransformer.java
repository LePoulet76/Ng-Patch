package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ScreenShotHelperTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.util.ScreenShotHelper";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode mn = (MethodNode)var3.next();

            if (mn.name.equalsIgnoreCase("func_74292_a") && mn.desc.equalsIgnoreCase("(Ljava/io/File;Ljava/lang/String;II)Ljava/lang/String;"))
            {
                for (int i = 0; i < mn.instructions.size(); ++i)
                {
                    AbstractInsnNode insn = mn.instructions.get(i);

                    if (insn instanceof MethodInsnNode && insn.getOpcode() == 184 && ((MethodInsnNode)insn).name.equalsIgnoreCase("write") && ((MethodInsnNode)insn).desc.equalsIgnoreCase("(Ljava/awt/image/RenderedImage;Ljava/lang/String;Ljava/io/File;)Z"))
                    {
                        MethodInsnNode method = (MethodInsnNode)insn;
                        InsnList list = new InsnList();
                        list.add(new VarInsnNode(25, 6));
                        list.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "sendNotificationToUploadImage", "(Ljava/awt/image/BufferedImage;)V"));
                        mn.instructions.insertBefore(method.getPrevious().getPrevious().getPrevious().getPrevious(), list);
                        return;
                    }
                }
            }
        }
    }
}
