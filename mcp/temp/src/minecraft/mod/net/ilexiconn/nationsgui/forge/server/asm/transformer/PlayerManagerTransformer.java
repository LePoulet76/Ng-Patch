package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PlayerManagerTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.server.management.PlayerManager";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         if(!methodNode.name.equals(dev?"filterChunkLoadQueue":"func_72691_b") && !methodNode.name.equals(dev?"updateMountedMovingPlayer":"func_72685_d")) {
            if(methodNode.name.equals(dev?"addPlayer":"func_72683_a") || methodNode.name.equals(dev?"removePlayer":"func_72695_c")) {
               LocalVariableNode var14 = null;
               Iterator var16 = methodNode.localVariables.iterator();

               while(var16.hasNext()) {
                  LocalVariableNode var18 = (LocalVariableNode)var16.next();
                  if(var18.index == 1) {
                     var14 = var18;
                     break;
                  }
               }

               LocalVariableNode var17 = new LocalVariableNode("renderDistance", "I", (String)null, var14.start, var14.end, methodNode.localVariables.size());
               methodNode.localVariables.add(var17);
               ArrayList var19 = new ArrayList();
               LineNumberNode var20 = null;
               AbstractInsnNode[] var21 = methodNode.instructions.toArray();
               int var23 = var21.length;

               for(int target = 0; target < var23; ++target) {
                  AbstractInsnNode varInsnNode = var21[target];
                  if(varInsnNode instanceof FieldInsnNode) {
                     FieldInsnNode fieldInsnNode1 = (FieldInsnNode)varInsnNode;
                     if(fieldInsnNode1.name.equals(dev?"playerViewRadius":"field_72698_e")) {
                        var19.add(fieldInsnNode1);
                     }
                  } else if(var20 == null && varInsnNode instanceof LineNumberNode) {
                     var20 = (LineNumberNode)varInsnNode;
                  }
               }

               InsnList var22 = new InsnList();
               var22.add(new VarInsnNode(25, 1));
               var22.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getPlayerRenderRadius", "(Lnet/minecraft/entity/player/EntityPlayerMP;)I"));
               var22.add(new VarInsnNode(54, var17.index));
               methodNode.instructions.insert(var20, var22);
               Iterator var24 = var19.iterator();

               while(var24.hasNext()) {
                  AbstractInsnNode var25 = (AbstractInsnNode)var24.next();
                  VarInsnNode var26 = (VarInsnNode)var25.getPrevious();
                  var26.var = var17.index;
                  var26.setOpcode(21);
                  methodNode.instructions.remove(var25);
               }
            }
         } else {
            FieldInsnNode firstVariable = null;
            AbstractInsnNode[] localVariableNode = methodNode.instructions.toArray();
            int targets = localVariableNode.length;

            for(int lineNumberNode = 0; lineNumberNode < targets; ++lineNumberNode) {
               AbstractInsnNode patch = localVariableNode[lineNumberNode];
               if(patch instanceof FieldInsnNode) {
                  FieldInsnNode fieldInsnNode = (FieldInsnNode)patch;
                  if(fieldInsnNode.name.equals(dev?"playerViewRadius":"field_72698_e")) {
                     firstVariable = fieldInsnNode;
                     break;
                  }
               }
            }

            InsnList var15 = new InsnList();
            var15.add(new VarInsnNode(25, 1));
            var15.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getPlayerRenderRadius", "(Lnet/minecraft/entity/player/EntityPlayerMP;)I"));
            methodNode.instructions.insert(firstVariable, var15);
            methodNode.instructions.remove(firstVariable.getPrevious());
            methodNode.instructions.remove(firstVariable);
         }
      }

   }
}
