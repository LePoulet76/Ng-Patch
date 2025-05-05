package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class EntitySelectorInvTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.command.IEntitySelector";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode mn = (MethodNode)var3.next();
         if(mn.name.equals("<clinit>")) {
            for(int i = 0; i < mn.instructions.size(); ++i) {
               AbstractInsnNode in = mn.instructions.get(i);
               if(in instanceof TypeInsnNode) {
                  TypeInsnNode insn = (TypeInsnNode)in;
                  if(insn.desc.equals("net/minecraft/command/EntitySelectorInventory")) {
                     insn.desc = "net/ilexiconn/nationsgui/forge/server/asm/transformer/EntitySelectorInvWithoutVehicles";
                  }
               } else if(in instanceof MethodInsnNode) {
                  MethodInsnNode var8 = (MethodInsnNode)in;
                  if(var8.owner.equals("net/minecraft/command/EntitySelectorInventory")) {
                     var8.owner = "net/ilexiconn/nationsgui/forge/server/asm/transformer/EntitySelectorInvWithoutVehicles";
                  }
               }
            }
         }
      }

   }
}
