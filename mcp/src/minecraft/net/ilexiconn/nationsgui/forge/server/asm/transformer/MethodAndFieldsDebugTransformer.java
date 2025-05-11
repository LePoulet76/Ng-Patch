/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.ClassNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.ClassNode;

public class MethodAndFieldsDebugTransformer
implements Transformer {
    public MethodAndFieldsDebugTransformer() {
        System.out.println("MethodAndFieldsDebugTransformer created");
    }

    @Override
    public String getTarget() {
        return "net.minecraft.network.ServerListenThread";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
    }
}

