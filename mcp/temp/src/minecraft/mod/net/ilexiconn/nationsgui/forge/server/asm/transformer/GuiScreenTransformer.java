package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.MinecraftTransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class GuiScreenTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.client.gui.GuiScreen";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         if(!methodNode.name.equals("drawScreen") && !methodNode.name.equals("func_73863_a")) {
            if(methodNode.name.equals(dev?"handleKeyboardInput":"func_73860_n")) {
               MinecraftTransformer.INSTANCE.patchKey(methodNode.instructions, 87, "KEY_FULLSCREEN", dev);
            }
         } else {
            InsnList insnList = methodNode.instructions;
            insnList.insertBefore(insnList.getFirst(), new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "onDrawScreen", "()V"));
            insnList.insertBefore(insnList.getFirst(), new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks;"));
         }
      }

   }
}
