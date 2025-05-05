package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ItemStackTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.item.ItemStack";
   }

   public void transform(ClassNode node, boolean dev) {
      if(NationsGUITransformer.isServer) {
         Iterator var3 = node.methods.iterator();

         while(var3.hasNext()) {
            MethodNode method = (MethodNode)var3.next();
            if(method.name.equals(dev?"tryPlaceItemIntoWorld":"func_77943_a")) {
               AbstractInsnNode[] var5 = method.instructions.toArray();
               int var6 = var5.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  AbstractInsnNode abstractInsnNode = var5[var7];
                  if(abstractInsnNode.getOpcode() == 181) {
                     FieldInsnNode insnNode = (FieldInsnNode)abstractInsnNode;
                     if(insnNode.name.equals("captureTreeGeneration")) {
                        method.instructions.remove(insnNode.getPrevious());
                        method.instructions.insertBefore(insnNode, new InsnNode(3));
                        return;
                     }
                  }
               }
            }
         }

      }
   }
}
