package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class ClientPlayerTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.client.entity.AbstractClientPlayer";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         InsnList insnList;
         AbstractInsnNode[] flag;
         int var7;
         int var8;
         AbstractInsnNode insnNode;
         if(!methodNode.name.equals("getDownloadImageSkin") && !methodNode.name.equals("func_110304_a")) {
            if(methodNode.name.equals("getDownloadImageCape") || methodNode.name.equals("func_110307_b")) {
               insnList = methodNode.instructions;
               boolean var11 = false;
               AbstractInsnNode[] var12 = insnList.toArray();
               var8 = var12.length;

               for(int var13 = 0; var13 < var8; ++var13) {
                  AbstractInsnNode insnNode1 = var12[var13];
                  if(insnNode1 instanceof InsnNode) {
                     if(var11) {
                        insnList.insertBefore(insnNode1, new TypeInsnNode(187, "net/ilexiconn/nationsgui/forge/client/util/CapeImageBuffer"));
                        insnList.insertBefore(insnNode1, new InsnNode(89));
                        insnList.insertBefore(insnNode1, new MethodInsnNode(183, "net/ilexiconn/nationsgui/forge/client/util/CapeImageBuffer", "<init>", "()V"));
                        insnList.remove(insnNode1);
                        break;
                     }

                     var11 = true;
                  }
               }
            } else if(methodNode.name.equals("<clinit>")) {
               insnList = methodNode.instructions;
               flag = insnList.toArray();
               var7 = flag.length;

               for(var8 = 0; var8 < var7; ++var8) {
                  insnNode = flag[var8];
                  if(insnNode instanceof MethodInsnNode) {
                     ((MethodInsnNode)insnNode).desc = "(Ljava/lang/String;Ljava/lang/String;)V";
                  } else if(insnNode instanceof LdcInsnNode) {
                     insnList.insertBefore(insnNode, new LdcInsnNode("nationsgui"));
                     ((LdcInsnNode)insnNode).cst = "textures/entity/steve.png";
                  }
               }
            }
         } else {
            insnList = methodNode.instructions;
            flag = insnList.toArray();
            var7 = flag.length;

            for(var8 = 0; var8 < var7; ++var8) {
               insnNode = flag[var8];
               if(insnNode instanceof TypeInsnNode) {
                  ((TypeInsnNode)insnNode).desc = "net/ilexiconn/nationsgui/forge/client/util/SkinImageBuffer";
                  ((MethodInsnNode)insnNode.getNext().getNext()).owner = "net/ilexiconn/nationsgui/forge/client/util/SkinImageBuffer";
                  break;
               }
            }
         }
      }

   }
}
