package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public interface Transformer extends Opcodes {

   String getTarget();

   void transform(ClassNode var1, boolean var2);
}
