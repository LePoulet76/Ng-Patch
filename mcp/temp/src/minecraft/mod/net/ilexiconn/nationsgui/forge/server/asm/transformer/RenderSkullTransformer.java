package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class RenderSkullTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      MethodNode methodNode;
      do {
         if(!var3.hasNext()) {
            return;
         }

         methodNode = (MethodNode)var3.next();
      } while(!methodNode.name.equals("func_82393_a"));

      InsnList insnList = methodNode.instructions;
      insnList.clear();
      insnList.add(new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/client/render/block/SkullBlockRenderer", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/client/render/block/SkullBlockRenderer;"));
      insnList.add(new VarInsnNode(23, 1));
      insnList.add(new VarInsnNode(23, 2));
      insnList.add(new VarInsnNode(23, 3));
      insnList.add(new VarInsnNode(21, 4));
      insnList.add(new VarInsnNode(23, 5));
      insnList.add(new VarInsnNode(21, 6));
      insnList.add(new VarInsnNode(25, 7));
      insnList.add(new InsnNode(4));
      insnList.add(new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/client/render/block/SkullBlockRenderer", "renderSkull", "(FFFIFILjava/lang/String;Z)V"));
      insnList.add(new InsnNode(177));
   }
}
