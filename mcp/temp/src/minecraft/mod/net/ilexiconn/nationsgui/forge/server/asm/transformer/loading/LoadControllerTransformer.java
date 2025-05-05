package net.ilexiconn.nationsgui.forge.server.asm.transformer.loading;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class LoadControllerTransformer implements Transformer {

   public String getTarget() {
      return "cpw.mods.fml.common.LoadController";
   }

   public void transform(ClassNode node, boolean dev) {
      if(!NationsGUITransformer.isServer) {
         Iterator var3 = node.methods.iterator();

         while(var3.hasNext()) {
            MethodNode m = (MethodNode)var3.next();
            if(m.name.equalsIgnoreCase("sendEventToModContainer") && m.desc.equalsIgnoreCase("(Lcpw/mods/fml/common/event/FMLEvent;Lcpw/mods/fml/common/ModContainer;)V")) {
               InsnList list = new InsnList();
               list.add(new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks;"));
               list.add(new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawLoadingScreen", "()V"));
               AbstractInsnNode[] var6 = m.instructions.toArray();
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  AbstractInsnNode i = var6[var8];
                  if(i instanceof FieldInsnNode) {
                     FieldInsnNode insn = (FieldInsnNode)i;
                     if(insn.owner.equalsIgnoreCase("cpw/mods/fml/common/LoadController") && insn.name.equalsIgnoreCase("eventChannels") && insn.desc.equalsIgnoreCase("Lcom/google/common/collect/ImmutableMap;")) {
                        m.instructions.insertBefore(insn.getPrevious(), list);
                     }
                  }
               }
            }
         }

      }
   }
}
