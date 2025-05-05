package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;

public class EntityMinecartTNTTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.entity.item.EntityMinecartTNT";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         InsnList insnList = methodNode.instructions;
         if(methodNode.name.equals("killMinecart") || methodNode.name.equals("func_94095_a")) {
            AbstractInsnNode targetNode = null;
            AbstractInsnNode[] mn = methodNode.instructions.toArray();
            int var8 = mn.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               AbstractInsnNode instruction = mn[var9];
               if(instruction.getOpcode() == 178 && (((FieldInsnNode)instruction).name.equals("tnt") || ((FieldInsnNode)instruction).name.equals("field_72091_am") || ((FieldInsnNode)instruction).name.equals("field_76262_s"))) {
                  targetNode = instruction;
               }
            }

            if(targetNode != null) {
               MethodNode var11 = new MethodNode();
               var11.visitFieldInsn(178, "net/minecraft/block/Block", dev?"rail":"field_72056_aG", "Lnet/minecraft/block/Block;");
               insnList.insertBefore(targetNode, var11.instructions);
               insnList.remove(targetNode);
            }
         }
      }

   }
}
