package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ServerListenThreadTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.server.ServerListenThread";
   }

   public void transform(ClassNode node, boolean dev) {
      System.out.println("Transforming class: " + node.name);
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode method = (MethodNode)var3.next();
         if(method.name.equals("<init>")) {
            System.out.println("Found constructor in ServerListenThread.");
            this.injectServerSocketReplacement(method);
         }
      }

   }

   private void injectServerSocketReplacement(MethodNode method) {
      InsnList instructions = method.instructions;
      AbstractInsnNode targetPutFieldNode = null;

      for(AbstractInsnNode newInstructions = instructions.getFirst(); newInstructions != null; newInstructions = newInstructions.getNext()) {
         if(newInstructions.getOpcode() == 181 && newInstructions instanceof FieldInsnNode && ((FieldInsnNode)newInstructions).name.equals("field_71774_e")) {
            targetPutFieldNode = newInstructions;
            break;
         }
      }

      if(targetPutFieldNode != null) {
         System.out.println("Found PUTFIELD for myServerSocket. Injecting replacement...");
         InsnList newInstructions1 = new InsnList();
         newInstructions1.add(new VarInsnNode(25, 0));
         newInstructions1.add(new FieldInsnNode(180, "net/minecraft/server/ServerListenThread", "field_71774_e", "Ljava/net/ServerSocket;"));
         LabelNode skipClose = new LabelNode();
         newInstructions1.add(new JumpInsnNode(198, skipClose));
         newInstructions1.add(new VarInsnNode(25, 0));
         newInstructions1.add(new FieldInsnNode(180, "net/minecraft/server/ServerListenThread", "field_71774_e", "Ljava/net/ServerSocket;"));
         newInstructions1.add(new MethodInsnNode(182, "java/net/ServerSocket", "close", "()V"));
         newInstructions1.add(skipClose);
         newInstructions1.add(new VarInsnNode(25, 0));
         newInstructions1.add(new TypeInsnNode(187, "net/ilexiconn/nationsgui/forge/server/asm/FilteredServerSocket"));
         newInstructions1.add(new InsnNode(89));
         newInstructions1.add(new VarInsnNode(21, 3));
         newInstructions1.add(new MethodInsnNode(183, "net/ilexiconn/nationsgui/forge/server/asm/FilteredServerSocket", "<init>", "(I)V"));
         newInstructions1.add(new FieldInsnNode(181, "net/minecraft/server/ServerListenThread", "field_71774_e", "Ljava/net/ServerSocket;"));
         instructions.insert(targetPutFieldNode, newInstructions1);
      } else {
         System.out.println("myServerSocket PUTFIELD not found!");
      }

   }
}
