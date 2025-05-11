/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public class PlayerRenderDeobfTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.renderer.entity.RenderPlayer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (FieldNode fieldNode : node.fields) {
            if (fieldNode.access != 2) continue;
            fieldNode.access = 1;
        }
    }
}

