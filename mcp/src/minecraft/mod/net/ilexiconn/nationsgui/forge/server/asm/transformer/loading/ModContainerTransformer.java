package net.ilexiconn.nationsgui.forge.server.asm.transformer.loading;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ModContainerTransformer implements Transformer
{
    public String getTarget()
    {
        return "cpw.mods.fml.common.FMLModContainer";
    }

    public void transform(ClassNode node, boolean dev)
    {
        if (!NationsGUITransformer.isServer)
        {
            InsnList list = new InsnList();
            list.add(new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks;"));
            list.add(new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawLoadingScreen", "()V"));
            Iterator var4 = node.methods.iterator();

            while (var4.hasNext())
            {
                MethodNode m = (MethodNode)var4.next();
                AbstractInsnNode[] var6;
                int var7;
                int var8;
                AbstractInsnNode i;

                if (m.name.equalsIgnoreCase("constructMod") && m.desc.equalsIgnoreCase("(Lcpw/mods/fml/common/event/FMLConstructionEvent;)V"))
                {
                    var6 = m.instructions.toArray();
                    var7 = var6.length;

                    for (var8 = 0; var8 < var7; ++var8)
                    {
                        i = var6[var8];

                        if (i instanceof MethodInsnNode)
                        {
                            MethodInsnNode var11 = (MethodInsnNode)i;

                            if (var11.name.equalsIgnoreCase("processFieldAnnotations"))
                            {
                                m.instructions.insert(var11, list);
                            }
                        }
                    }
                }
                else if (m.name.equalsIgnoreCase("handleModStateEvent") && m.desc.equalsIgnoreCase("(Lcpw/mods/fml/common/event/FMLEvent;)V"))
                {
                    var6 = m.instructions.toArray();
                    var7 = var6.length;

                    for (var8 = 0; var8 < var7; ++var8)
                    {
                        i = var6[var8];

                        if (i instanceof FieldInsnNode)
                        {
                            FieldInsnNode insn = (FieldInsnNode)i;

                            if (insn.owner.equalsIgnoreCase("cpw/mods/fml/common/FMLModContainer") && insn.name.equalsIgnoreCase("eventMethods") && insn.desc.equalsIgnoreCase("Lcom/google/common/collect/ListMultimap;"))
                            {
                                m.instructions.insertBefore(insn.getPrevious(), list);
                            }
                        }
                    }
                }
            }
        }
    }
}
