package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class AbstractClientPlayerTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.client.entity.AbstractClientPlayer";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         AbstractInsnNode[] var5;
         int var6;
         int var7;
         AbstractInsnNode insnNode;
         LdcInsnNode ldcInsnNode;
         if(!methodNode.name.equals("getSkinUrl") && !methodNode.name.equals("func_110300_d")) {
            if(methodNode.name.equals("getCapeUrl") || methodNode.name.equals("func_110308_e")) {
               var5 = methodNode.instructions.toArray();
               var6 = var5.length;

               for(var7 = 0; var7 < var6; ++var7) {
                  insnNode = var5[var7];
                  if(insnNode instanceof LdcInsnNode) {
                     ldcInsnNode = (LdcInsnNode)insnNode;
                     ldcInsnNode.cst = "http://skins.minecraft.net/MinecraftCloaks/";
                  }
               }
            }
         } else {
            var5 = methodNode.instructions.toArray();
            var6 = var5.length;

            for(var7 = 0; var7 < var6; ++var7) {
               insnNode = var5[var7];
               if(insnNode instanceof LdcInsnNode) {
                  ldcInsnNode = (LdcInsnNode)insnNode;
                  ldcInsnNode.cst = "https://skins.nationsglory.fr/%s";
               }
            }
         }
      }

   }
}
