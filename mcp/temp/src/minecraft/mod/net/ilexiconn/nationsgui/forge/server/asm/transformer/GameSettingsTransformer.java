package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class GameSettingsTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.client.settings.GameSettings";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         if(methodNode.name.equals("<init>")) {
            LdcInsnNode target = null;
            AbstractInsnNode[] var6 = methodNode.instructions.toArray();
            int var7 = var6.length;
            int var8 = 0;

            while(true) {
               if(var8 < var7) {
                  label34: {
                     AbstractInsnNode insnNode = var6[var8];
                     if(insnNode instanceof LdcInsnNode) {
                        LdcInsnNode ldcInsnNode = (LdcInsnNode)insnNode;
                        if(ldcInsnNode.cst.equals("en_US")) {
                           target = ldcInsnNode;
                           break label34;
                        }
                     }

                     ++var8;
                     continue;
                  }
               }

               methodNode.instructions.insertBefore(target, new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/GameSettingsTransformer", "getDefaultLanguage", "()Ljava/lang/String;"));
               methodNode.instructions.remove(target);
               break;
            }
         }
      }

   }

   public static String getDefaultLanguage() {
      String lang = System.getProperty("java.lang");
      if(lang == null) {
         lang = "en_US";
      }

      return lang;
   }
}
