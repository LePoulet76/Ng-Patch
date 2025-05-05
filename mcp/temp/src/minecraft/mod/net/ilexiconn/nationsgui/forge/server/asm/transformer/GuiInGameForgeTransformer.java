package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class GuiInGameForgeTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraftforge.client.GuiIngameForge";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         InsnList insnList = methodNode.instructions;
         if(methodNode.name.equals("renderHUDText")) {
            AbstractInsnNode targetNode = null;
            AbstractInsnNode versionNode = null;
            AbstractInsnNode[] var8 = methodNode.instructions.toArray();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               AbstractInsnNode instruction = var8[var10];
               if(instruction.getOpcode() == 18) {
                  if(((LdcInsnNode)instruction).cst.equals("Minecraft ")) {
                     targetNode = instruction;
                  }
               } else if(instruction.getOpcode() == 178 && ((FieldInsnNode)instruction).name.equals("MC_VERSION")) {
                  versionNode = instruction;
               }
            }

            if(targetNode != null) {
               insnList.insertBefore(targetNode.getNext(), new LdcInsnNode("NationsGlory"));
               insnList.remove(targetNode);
            }

            if(versionNode != null) {
               insnList.remove(versionNode.getNext());
               insnList.remove(versionNode);
            }
         }
      }

   }
}
