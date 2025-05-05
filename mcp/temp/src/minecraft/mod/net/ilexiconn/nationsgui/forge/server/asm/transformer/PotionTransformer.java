package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class PotionTransformer implements Transformer {

   private static final double HARMING_MULTIPLIER = 0.4D;


   public String getTarget() {
      return "net.minecraft.potion.Potion";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode mn = (MethodNode)var3.next();
         if(mn.name.equals("applyInstantEffect")) {
            for(int i = 0; i < mn.instructions.size(); ++i) {
               AbstractInsnNode insn = mn.instructions.get(i);
               if(insn instanceof VarInsnNode && ((VarInsnNode)insn).var == 7) {
                  mn.instructions.insertBefore(insn, new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/PotionTransformer", "getNewHarmingDamage", "(I)I"));
                  return;
               }
            }
         }
      }

   }

   public static int getNewHarmingDamage(int i) {
      return (int)((double)i * 0.4D);
   }
}
