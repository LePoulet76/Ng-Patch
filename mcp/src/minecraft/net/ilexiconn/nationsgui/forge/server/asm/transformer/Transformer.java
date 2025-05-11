/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.Opcodes
 *  org.objectweb.asm.tree.ClassNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public interface Transformer
extends Opcodes {
    public String getTarget();

    public void transform(ClassNode var1, boolean var2);
}

