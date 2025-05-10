package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public class PlayerRenderDeobfTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.client.renderer.entity.RenderPlayer";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.fields.iterator();

        while (var3.hasNext())
        {
            FieldNode fieldNode = (FieldNode)var3.next();

            if (fieldNode.access == 2)
            {
                fieldNode.access = 1;
            }
        }
    }
}
