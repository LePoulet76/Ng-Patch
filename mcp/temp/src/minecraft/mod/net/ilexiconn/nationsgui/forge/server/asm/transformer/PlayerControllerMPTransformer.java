package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PlayerControllerMPTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.client.multiplayer.PlayerControllerMP";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         if(methodNode.name.equals(dev?"getBlockReachDistance":"func_78757_d")) {
            methodNode.instructions.clear();
            methodNode.instructions.add(new VarInsnNode(25, 0));
            methodNode.instructions.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "getBlockReachDistance", "(Lnet/minecraft/client/multiplayer/PlayerControllerMP;)F"));
            methodNode.instructions.add(new InsnNode(174));
         }
      }

   }
}
