package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class RenderTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.client.renderer.entity.Render";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();
        MethodNode method;

        do
        {
            if (!var3.hasNext())
            {
                return;
            }

            method = (MethodNode)var3.next();
        }
        while (!method.name.equals(dev ? "bindEntityTexture" : "bindEntityTexture"));

        MethodInsnNode lastNode = null;
        AbstractInsnNode[] patch = method.instructions.toArray();
        int var7 = patch.length;

        for (int var8 = 0; var8 < var7; ++var8)
        {
            AbstractInsnNode insnNode = patch[var8];

            if (insnNode instanceof MethodInsnNode)
            {
                lastNode = (MethodInsnNode)insnNode;
            }
        }

        InsnList var10 = new InsnList();
        var10.add(new VarInsnNode(25, 1));
        var10.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/itemskin/EntitySkin", "hookEntityTexture", "(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/ResourceLocation;"));
        method.instructions.insertBefore(lastNode, var10);
    }
}
