package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class GuiContainerTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.client.gui.inventory.GuiContainer";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         if(!methodNode.name.equals("checkHotbarKeys") && !methodNode.name.equals("func_146983_a")) {
            if(!methodNode.name.equals("drawItemStackTooltip") && !methodNode.name.equals("func_74184_a")) {
               if((methodNode.name.equals("drawScreen") || methodNode.name.equals("func_73863_a")) && !NationsGUITransformer.inDevelopment) {
                  AbstractInsnNode[] var5 = methodNode.instructions.toArray();
                  int var6 = var5.length;

                  for(int var7 = 0; var7 < var6; ++var7) {
                     AbstractInsnNode insnNode = var5[var7];

                     try {
                        AbstractInsnNode future = insnNode.getNext().getNext().getNext().getNext();
                        if(future instanceof MethodInsnNode) {
                           MethodInsnNode mInsnNode = (MethodInsnNode)future;
                           if(mInsnNode.name.equals("renderToolTips")) {
                              methodNode.instructions.insertBefore(insnNode, new VarInsnNode(25, 9));
                              methodNode.instructions.insertBefore(insnNode, new MethodInsnNode(182, "net/minecraft/entity/player/InventoryPlayer", dev?"getItemStack":"func_70445_o", "()Lnet/minecraft/item/ItemStack;"));
                              LabelNode l67 = new LabelNode();
                              LabelNode l56 = new LabelNode();
                              methodNode.instructions.insertBefore(insnNode, new JumpInsnNode(199, l67));
                              methodNode.instructions.insertBefore(insnNode, new VarInsnNode(25, 0));
                              methodNode.instructions.insertBefore(insnNode, new FieldInsnNode(180, "net/minecraft/client/gui/inventory/GuiContainer", dev?"theSlot":"field_82320_o", "Lnet/minecraft/inventory/Slot;"));
                              methodNode.instructions.insertBefore(insnNode, new JumpInsnNode(198, l67));
                              methodNode.instructions.insertBefore(insnNode, new VarInsnNode(25, 0));
                              methodNode.instructions.insertBefore(insnNode, new FieldInsnNode(180, "net/minecraft/client/gui/inventory/GuiContainer", dev?"theSlot":"field_82320_o", "Lnet/minecraft/inventory/Slot;"));
                              methodNode.instructions.insertBefore(insnNode, new MethodInsnNode(182, "net/minecraft/inventory/Slot", dev?"getHasStack":"func_75216_d", "()Z"));
                              methodNode.instructions.insertBefore(insnNode, new JumpInsnNode(153, l67));
                              methodNode.instructions.insertBefore(insnNode, new VarInsnNode(25, 0));
                              methodNode.instructions.insertBefore(insnNode, new FieldInsnNode(180, "net/minecraft/client/gui/inventory/GuiContainer", dev?"theSlot":"field_82320_o", "Lnet/minecraft/inventory/Slot;"));
                              methodNode.instructions.insertBefore(insnNode, new MethodInsnNode(182, "net/minecraft/inventory/Slot", dev?"getStack":"func_75211_c", "()Lnet/minecraft/item/ItemStack;"));
                              methodNode.instructions.insertBefore(insnNode, new VarInsnNode(58, 11));
                              methodNode.instructions.insertBefore(insnNode, new VarInsnNode(25, 0));
                              methodNode.instructions.insertBefore(insnNode, new VarInsnNode(25, 11));
                              methodNode.instructions.insertBefore(insnNode, new VarInsnNode(21, 1));
                              methodNode.instructions.insertBefore(insnNode, new VarInsnNode(21, 2));
                              methodNode.instructions.insertBefore(insnNode, new MethodInsnNode(182, "net/minecraft/client/gui/inventory/GuiContainer", dev?"drawItemStackTooltip":"func_74184_a", "(Lnet/minecraft/item/ItemStack;II)V"));
                              methodNode.instructions.insertBefore(insnNode, new JumpInsnNode(167, l56));
                              methodNode.instructions.insertBefore(insnNode, l67);
                              methodNode.instructions.insert(future, l56);
                              break;
                           }
                        }
                     } catch (NullPointerException var13) {
                        ;
                     }
                  }
               }
            } else {
               methodNode.instructions.clear();
               methodNode.instructions.add(new VarInsnNode(25, 0));
               methodNode.instructions.add(new VarInsnNode(25, 1));
               methodNode.instructions.add(new VarInsnNode(21, 2));
               methodNode.instructions.add(new VarInsnNode(21, 3));
               methodNode.instructions.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawItemStackTooltip", "(Lnet/minecraft/item/ItemStack;II)V"));
               methodNode.instructions.add(new InsnNode(177));
            }
         } else {
            methodNode.instructions.clear();
            methodNode.instructions.add(new InsnNode(3));
            methodNode.instructions.add(new InsnNode(172));
         }
      }

   }
}
