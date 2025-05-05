package fr.nationsglory.itemmanager.asm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

@MCVersion("1.6.4")
public class GameRegistryTransformer implements Transformer {

   public String getTarget() {
      return "cpw.mods.fml.common.registry.GameRegistry";
   }

   public void transform(ClassNode classNode, boolean dev) {
      Iterator var3 = classNode.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         if(methodNode.name.equals("registerBlock") && methodNode.desc.equals("(Lnet/minecraft/block/Block;Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)V") || methodNode.name.equals("registerItem") && methodNode.desc.equals("(Lnet/minecraft/item/Item;Ljava/lang/String;Ljava/lang/String;)V")) {
            AbstractInsnNode first = methodNode.instructions.getFirst();
            methodNode.instructions.insertBefore(first, new VarInsnNode(25, 0));
            methodNode.instructions.insertBefore(first, new VarInsnNode(25, methodNode.name.equals("registerBlock")?3:2));
            methodNode.instructions.insertBefore(first, new MethodInsnNode(184, "fr/nationsglory/itemmanager/RegisterHook", "blockIsBlacklisted", "(Ljava/lang/Object;Ljava/lang/String;)Z"));
            methodNode.instructions.insertBefore(first, new InsnNode(4));
            LabelNode l1 = new LabelNode();
            methodNode.instructions.insertBefore(first, new JumpInsnNode(160, l1));
            methodNode.instructions.insertBefore(first, new InsnNode(177));
            methodNode.instructions.insertBefore(first, new FrameNode(3, 0, (Object[])null, 0, (Object[])null));
            methodNode.instructions.insertBefore(first, l1);
         }
      }

   }
}
