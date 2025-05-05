package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class EntityPlayerTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraft.entity.player.EntityPlayer";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         AbstractInsnNode[] var5;
         int var6;
         int var7;
         AbstractInsnNode insnNode;
         if(methodNode.name.equals("<init>")) {
            var5 = methodNode.instructions.toArray();
            var6 = var5.length;

            for(var7 = 0; var7 < var6; ++var7) {
               insnNode = var5[var7];
               if(insnNode.getOpcode() == 187) {
                  TypeInsnNode var10 = (TypeInsnNode)insnNode;
                  if(var10.desc.equals("net/minecraft/inventory/ContainerPlayer")) {
                     var10.desc = "net/ilexiconn/nationsgui/forge/server/container/PlayerContainer";
                  }
               } else if(insnNode.getOpcode() == 183) {
                  MethodInsnNode var11 = (MethodInsnNode)insnNode;
                  if(var11.owner.equals("net/minecraft/inventory/ContainerPlayer")) {
                     var11.owner = "net/ilexiconn/nationsgui/forge/server/container/PlayerContainer";
                  }
               }
            }
         } else if(methodNode.name.equals(dev?"getItemIcon":"func_70620_b")) {
            var5 = methodNode.instructions.toArray();
            var6 = var5.length;

            for(var7 = 0; var7 < var6; ++var7) {
               insnNode = var5[var7];
               if(insnNode.getOpcode() == 176) {
                  InsnList patch = new InsnList();
                  patch.add(new VarInsnNode(25, 0));
                  patch.add(new VarInsnNode(25, 1));
                  patch.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/itemskin/ItemSkinSimple", "getCustomIcon", "(Lnet/minecraft/util/Icon;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/util/Icon;"));
                  methodNode.instructions.insertBefore(insnNode, patch);
               }
            }
         }
      }

   }
}
