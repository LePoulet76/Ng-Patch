package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class KeyRegistryTransformer implements Transformer {

   public String getTarget() {
      return "cpw.mods.fml.client.registry.KeyBindingRegistry";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      MethodNode methodNode;
      do {
         if(!var3.hasNext()) {
            return;
         }

         methodNode = (MethodNode)var3.next();
      } while(!methodNode.name.equals("uploadKeyBindingsToGame"));

      InsnList insnList = methodNode.instructions;
      AbstractInsnNode[] var6 = insnList.toArray();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         AbstractInsnNode insnNode = var6[var8];
         if(insnNode instanceof VarInsnNode && insnNode.getNext() instanceof VarInsnNode && insnNode.getNext().getNext() instanceof MethodInsnNode && ((MethodInsnNode)insnNode.getNext().getNext()).name.equals("size")) {
            insnList.insertBefore(insnNode, new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks;"));
            insnList.insertBefore(insnNode, new VarInsnNode(25, 0));
            insnList.insertBefore(insnNode, new FieldInsnNode(180, "cpw/mods/fml/client/registry/KeyBindingRegistry", "keyHandlers", "Ljava/util/Set;"));
            insnList.insertBefore(insnNode, new VarInsnNode(25, 2));
            insnList.insertBefore(insnNode, new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "applyControlBlacklist", "(Ljava/util/Set;Ljava/util/List;)V"));
            break;
         }
      }

   }
}
