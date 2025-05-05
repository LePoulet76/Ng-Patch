package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class WorldClientTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.client.multiplayer.WorldClient";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         if(methodNode.name.equals(dev?"doPreChunk":"func_73025_a")) {
            methodNode.instructions.clear();
            methodNode.instructions.add(new VarInsnNode(25, 0));
            methodNode.instructions.add(new VarInsnNode(21, 1));
            methodNode.instructions.add(new VarInsnNode(21, 2));
            methodNode.instructions.add(new VarInsnNode(21, 3));
            methodNode.instructions.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "doPreChunk", "(Lnet/minecraft/client/multiplayer/WorldClient;IIZ)V"));
            methodNode.instructions.add(new InsnNode(177));
         }
      }

   }
}
