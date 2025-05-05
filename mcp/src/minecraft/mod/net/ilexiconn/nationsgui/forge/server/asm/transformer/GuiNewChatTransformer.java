package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class GuiNewChatTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.client.gui.GuiNewChat";
    }

    public void transform(ClassNode node, boolean dev)
    {
        boolean patchedDrawChat = false;
        boolean patchedPrintMessage = false;
        Iterator var5 = node.methods.iterator();

        while (var5.hasNext())
        {
            MethodNode method = (MethodNode)var5.next();

            if (method.name.equals("drawChat") || method.name.equals("drawChat"))
            {
                MethodNode mn = new MethodNode();
                mn.visitMethodInsn(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIChatHooks", "filterChatLineString", "(Ljava/lang/String;)Ljava/lang/String;");
                AbstractInsnNode insertAfter = null;
                AbstractInsnNode toRemove;

                for (int insertBefore = 0; insertBefore < method.instructions.size(); ++insertBefore)
                {
                    toRemove = method.instructions.get(insertBefore);

                    if (toRemove instanceof MethodInsnNode && toRemove.getOpcode() == 182 && (((MethodInsnNode)toRemove).name.equals("getChatLineString") || ((MethodInsnNode)toRemove).name.equals("getChatLineString")))
                    {
                        insertAfter = toRemove;
                        break;
                    }
                }

                if (insertAfter != null)
                {
                    method.instructions.insert(insertAfter, mn.instructions);
                    mn = new MethodNode();
                    mn.visitVarInsn(25, 10);
                    mn.visitVarInsn(25, 17);
                    mn.visitVarInsn(21, 15);
                    mn.visitVarInsn(21, 16);
                    mn.visitVarInsn(21, 14);
                    mn.visitMethodInsn(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIChatHooks", "drawChatMessagePost", "(Lnet/minecraft/client/gui/ChatLine;Ljava/lang/String;III)V");
                    AbstractInsnNode var13 = null;
                    toRemove = null;

                    for (int i = 0; i < method.instructions.size(); ++i)
                    {
                        AbstractInsnNode nod = method.instructions.get(i);

                        if (nod instanceof MethodInsnNode && nod.getOpcode() == 182 && (((MethodInsnNode)nod).name.equals("drawStringWithShadow") || ((MethodInsnNode)nod).name.equals("drawStringWithShadow")))
                        {
                            var13 = nod;
                            break;
                        }
                    }

                    if (var13 != null)
                    {
                        method.instructions.insert(var13, mn.instructions);

                        if (patchedPrintMessage)
                        {
                            break;
                        }
                    }
                }
            }
        }
    }
}
