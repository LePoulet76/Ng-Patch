package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.ClassNode;

public class MethodAndFieldsDebugTransformer implements Transformer {

   public MethodAndFieldsDebugTransformer() {
      System.out.println("MethodAndFieldsDebugTransformer created");
   }

   public String getTarget() {
      return "net.minecraft.network.ServerListenThread";
   }

   public void transform(ClassNode node, boolean dev) {}
}
