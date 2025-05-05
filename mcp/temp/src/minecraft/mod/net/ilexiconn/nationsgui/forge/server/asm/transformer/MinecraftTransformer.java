package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import java.util.ListIterator;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class MinecraftTransformer implements Transformer {

   public static MinecraftTransformer INSTANCE;


   public MinecraftTransformer() {
      INSTANCE = this;
   }

   public String getTarget() {
      return "net.minecraft.client.Minecraft";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator var3 = node.methods.iterator();

      while(var3.hasNext()) {
         MethodNode methodNode = (MethodNode)var3.next();
         InsnList var14;
         if(!methodNode.name.equals("startGame") && !methodNode.name.equals("func_71384_a")) {
            if(!methodNode.name.equals("loadScreen") && !methodNode.name.equals("func_71357_I")) {
               AbstractInsnNode var16;
               if(!methodNode.name.equals("runTick") && !methodNode.name.equals("func_71407_l")) {
                  if(!methodNode.name.equals("refreshResources") && !methodNode.name.equals("func_110436_a")) {
                     if(methodNode.name.equals("screenshotListener") || methodNode.name.equals("func_71365_K")) {
                        var14 = methodNode.instructions;
                        this.patchKey(var14, 60, "KEY_SCREENSHOT", dev);
                     }
                  } else {
                     var14 = methodNode.instructions;
                     var16 = var14.getFirst();

                     while((var16 = var16.getNext()).getOpcode() != 177) {
                        ;
                     }

                     var14.insertBefore(var16, new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "onResourceReload", "()V"));
                  }
               } else {
                  var14 = methodNode.instructions;
                  var16 = null;
                  AbstractInsnNode[] var18 = var14.toArray();
                  int var22 = var18.length;

                  for(int var21 = 0; var21 < var22; ++var21) {
                     AbstractInsnNode var23 = var18[var21];
                     if(var23 instanceof FieldInsnNode) {
                        FieldInsnNode var24 = (FieldInsnNode)var23;
                        if(var24.getOpcode() == 181 && var24.owner.equals("net/minecraft/entity/player/InventoryPlayer") && var24.name.equals(dev?"currentItem":"field_70461_c")) {
                           var16 = var23;
                        }
                     } else if(var23 instanceof TypeInsnNode && var23.getOpcode() == 193) {
                        TypeInsnNode var25 = (TypeInsnNode)var23;
                        if(var25.desc.equals("net/minecraft/client/gui/GuiSleepMP")) {
                           var25.desc = "net/ilexiconn/nationsgui/forge/client/gui/SleepGUI";
                        }
                     }
                  }

                  AbstractInsnNode var20;
                  do {
                     var20 = var16.getPrevious();
                     var14.remove(var16);
                     var16 = var20;
                  } while(var20.getOpcode() != 25);

                  var14.remove(var20);
                  this.patchKey(var14, 59, "KEY_HIDE_GUI", dev);
                  this.patchKey(var14, 61, "KEY_DEBUG", dev);
                  this.patchKey(var14, 63, "KEY_THIRD_PERSON", dev);
                  this.patchKey(var14, 66, "KEY_SMOOTH_CAMERA", dev);
                  this.patchKey(var14, 87, "KEY_FULLSCREEN", dev);
               }
            } else {
               var14 = methodNode.instructions;
               var14.clear();
               var14.add(new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks;"));
               var14.add(new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIClientHooks", "drawLoadingScreen", "()V"));
               var14.add(new InsnNode(177));
            }
         } else {
            AbstractInsnNode[] insnList = methodNode.instructions.toArray();
            int current = insnList.length;

            AbstractInsnNode target;
            for(int previous = 0; previous < current; ++previous) {
               target = insnList[previous];
               if(target instanceof LdcInsnNode && ((LdcInsnNode)target).cst.equals("Minecraft 1.6.4")) {
                  ((LdcInsnNode)target).cst = "NationsGlory";
               }
            }

            var14 = new InsnList();
            ListIterator var15 = methodNode.instructions.iterator();
            boolean var17 = false;

            while(var15.hasNext()) {
               target = (AbstractInsnNode)var15.next();
               if(target instanceof MethodInsnNode && ((MethodInsnNode)target).owner.equalsIgnoreCase("net/minecraft/client/Minecraft") && ((MethodInsnNode)target).name.equalsIgnoreCase(dev?"loadScreen":"func_71357_I") && ((MethodInsnNode)target).desc.equalsIgnoreCase("()V")) {
                  var17 = true;
               }

               var14.add(target);
               if(var17) {
                  var14.add(new VarInsnNode(25, 0));
                  var14.add(new MethodInsnNode(183, "net/minecraft/client/Minecraft", dev?"loadScreen":"func_71357_I", "()V"));
               }
            }

            methodNode.instructions = var14;
            MethodInsnNode var19 = null;
            AbstractInsnNode[] var9 = methodNode.instructions.toArray();
            int insnNode = var9.length;

            for(int typeInsnNode = 0; typeInsnNode < insnNode; ++typeInsnNode) {
               AbstractInsnNode abstractInsnNode = var9[typeInsnNode];
               if(abstractInsnNode instanceof MethodInsnNode) {
                  MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                  if(methodInsnNode.name.equals("setResizable")) {
                     var19 = methodInsnNode;
                     break;
                  }
               }
            }

            methodNode.instructions.remove(var19.getPrevious());
            methodNode.instructions.insertBefore(var19, new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/ClientHooks", "defineDisplayResizable", "()V"));
            methodNode.instructions.remove(var19);
         }
      }

   }

   public void patchKey(InsnList insnList, int targetCode, String keyName, boolean dev) {
      AbstractInsnNode current = insnList.getFirst();

      while((current = current.getNext()) != null) {
         if(current.getOpcode() == 16) {
            IntInsnNode varInsnNode = (IntInsnNode)current;
            if(varInsnNode.operand == targetCode) {
               InsnList patch = new InsnList();
               patch.add(new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/client/ClientKeyHandler", keyName, "Lnet/minecraft/client/settings/KeyBinding;"));
               patch.add(new FieldInsnNode(180, "net/minecraft/client/settings/KeyBinding", dev?"keyCode":"field_74512_d", "I"));
               insnList.insertBefore(current, patch);
               AbstractInsnNode node = current.getPrevious();
               insnList.remove(current);
               current = node;
            }
         }
      }

   }
}
