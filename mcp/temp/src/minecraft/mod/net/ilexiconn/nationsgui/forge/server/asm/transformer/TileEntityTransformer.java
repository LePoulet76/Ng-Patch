package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

public class TileEntityTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.tileentity.TileEntity";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         boolean that = false;
         Iterator var6 = methodNode.localVariables.iterator();

         while(var6.hasNext()) {
            LocalVariableNode variableNode = (LocalVariableNode)var6.next();
            if(variableNode.name.equals("this")) {
               if(that) {
                  variableNode.name = "that";
               } else {
                  that = true;
               }
            }
         }

         if(methodNode.name.equals("func_85027_a")) {
            methodNode.instructions.clear();
            methodNode.instructions.add(new InsnNode(177));
            break;
         }
      }

   }
}
