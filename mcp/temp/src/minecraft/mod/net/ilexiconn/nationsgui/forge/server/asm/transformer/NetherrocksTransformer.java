package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class NetherrocksTransformer implements Transformer {

   public String getTarget() {
      return "Netherrocks.core.conf.IDs";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         methodNode.tryCatchBlocks.clear();
         if(methodNode.name.equals("doConfig")) {
            MethodInsnNode beginTarget = null;
            ArrayList idInsList = new ArrayList();
            AbstractInsnNode[] lastIdIns = methodNode.instructions.toArray();
            int var8 = lastIdIns.length;

            for(int idIns = 0; idIns < var8; ++idIns) {
               AbstractInsnNode i = lastIdIns[idIns];
               if(i instanceof MethodInsnNode) {
                  MethodInsnNode methodInsnNode = (MethodInsnNode)i;
                  if(beginTarget == null && methodInsnNode.name.equals("load") && methodInsnNode.owner.equals("net/minecraftforge/common/Configuration")) {
                     beginTarget = methodInsnNode;
                  }
               } else if(i.getOpcode() == 17) {
                  idInsList.add(i);
               }
            }

            while(beginTarget.getPrevious() != null) {
               methodNode.instructions.remove(beginTarget.getPrevious());
            }

            methodNode.instructions.remove(beginTarget);
            AbstractInsnNode var12 = null;
            Iterator var13 = idInsList.iterator();

            while(var13.hasNext()) {
               AbstractInsnNode var14 = (AbstractInsnNode)var13.next();
               var12 = var14;

               int var15;
               for(var15 = 0; var15 < 3; ++var15) {
                  methodNode.instructions.remove(var14.getPrevious());
               }

               for(var15 = 0; var15 < 2; ++var15) {
                  methodNode.instructions.remove(var14.getNext());
               }
            }

            var12 = var12.getNext();

            while(var12.getNext() != null && var12.getNext().getOpcode() != 177) {
               methodNode.instructions.remove(var12.getNext());
            }
         }
      }

   }
}
