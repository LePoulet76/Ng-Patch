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

public class ResourceManagerTransformer implements Transformer
{
    public String getTarget()
    {
        return "net/minecraft/client/resources/SimpleReloadableResourceManager";
    }

    public void transform(ClassNode node, boolean dev)
    {
        if (!NationsGUITransformer.isServer)
        {
            Iterator var3 = node.methods.iterator();

            while (var3.hasNext())
            {
                MethodNode m = (MethodNode)var3.next();

                if (m.name.equalsIgnoreCase("notifyReloadListeners") && m.desc.equalsIgnoreCase("()V"))
                {
                    InsnList list = new InsnList();
                    list.add(new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks;"));
                    list.add(new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawLoadingScreen", "()V"));
                    AbstractInsnNode[] var6 = m.instructions.toArray();
                    int var7 = var6.length;

                    for (int var8 = 0; var8 < var7; ++var8)
                    {
                        AbstractInsnNode i = var6[var8];

                        if (i instanceof MethodInsnNode)
                        {
                            MethodInsnNode insn = (MethodInsnNode)i;

                            if (insn.owner.equalsIgnoreCase("net/minecraft/client/resources/ResourceManagerReloadListener") && insn.name.equalsIgnoreCase("onResourceManagerReload") && insn.desc.equalsIgnoreCase("(Lnet/minecraft/client/resources/ResourceManager;)V"))
                            {
                                m.instructions.insertBefore(insn.getPrevious().getPrevious(), list);
                            }
                        }
                    }
                }
            }
        }
    }
}
