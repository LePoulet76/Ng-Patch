package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ThreadLoginVerifierTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.network.ThreadLoginVerifier";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         if(methodNode.name.equals("run") || methodNode.name.equals("auth")) {
            AbstractInsnNode[] var5 = methodNode.instructions.toArray();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               AbstractInsnNode insnNode = var5[var7];
               if(insnNode instanceof LdcInsnNode) {
                  LdcInsnNode ldcInsnNode = (LdcInsnNode)insnNode;
                  if(ldcInsnNode.cst.equals("http://session.minecraft.net/game/checkserver.jsp?user=")) {
                     ldcInsnNode.cst = "https://authserver.nationsglory.fr/checksession?username=";
                  }
               }
            }

            return;
         }
      }

   }
}
