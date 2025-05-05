package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class RenderGlobalTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.client.renderer.RenderGlobal";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      label57:
      while(var3.hasNext()) {
         MethodNode method = (MethodNode)var3.next();
         if(method.name.equals(dev?"loadRenderers":"func_72712_a")) {
            ArrayList targets = new ArrayList();
            AbstractInsnNode[] var6 = method.instructions.toArray();
            int target = var6.length;
            int var8 = 0;

            while(true) {
               if(var8 < target) {
                  label50: {
                     AbstractInsnNode insnNode = var6[var8];
                     if(insnNode instanceof FieldInsnNode) {
                        FieldInsnNode fieldInsnNode = (FieldInsnNode)insnNode;
                        if(NationsGUITransformer.optifine) {
                           if(fieldInsnNode.name.equals("ofRenderDistanceFine")) {
                              targets.add(fieldInsnNode);
                           }

                           if(targets.size() >= 2) {
                              break label50;
                           }
                        } else if(fieldInsnNode.getOpcode() == 180 && fieldInsnNode.name.equals(dev?"renderDistance":"field_72739_F") && fieldInsnNode.owner.equals("net/minecraft/client/renderer/RenderGlobal")) {
                           targets.add(fieldInsnNode);
                           break label50;
                        }
                     }

                     ++var8;
                     continue;
                  }
               }

               Iterator var11 = targets.iterator();

               while(true) {
                  if(!var11.hasNext()) {
                     continue label57;
                  }

                  AbstractInsnNode var12 = (AbstractInsnNode)var11.next();
                  method.instructions.insert(var12, new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "hookRenderDistance", "(I)I"));
               }
            }
         }
      }

   }
}
