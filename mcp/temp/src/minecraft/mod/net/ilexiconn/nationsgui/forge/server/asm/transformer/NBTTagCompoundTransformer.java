package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import net.minecraft.nbt.NBTTagCompound;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class NBTTagCompoundTransformer implements Transformer {

   private static Field tagMap = null;


   public String getTarget() {
      return "net.minecraft.nbt.NBTTagCompound";
   }

   public void transform(ClassNode node, boolean dev) {
      if(NationsGUITransformer.isServer) {
         Iterator var3 = node.methods.iterator();

         while(var3.hasNext()) {
            MethodNode methodNode = (MethodNode)var3.next();
            if(methodNode.name.equals("equals")) {
               LineNumberNode start = null;
               AbstractInsnNode[] insnList = methodNode.instructions.toArray();
               int var7 = insnList.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  AbstractInsnNode insnNode = insnList[var8];
                  if(insnNode instanceof LineNumberNode) {
                     LineNumberNode lineNumberNode = (LineNumberNode)insnNode;
                     if(lineNumberNode.line == 440) {
                        start = lineNumberNode;
                     }
                  }
               }

               while(start.getNext().getOpcode() != 172) {
                  methodNode.instructions.remove(start.getNext());
               }

               InsnList var11 = new InsnList();
               var11.add(new VarInsnNode(25, 0));
               var11.add(new VarInsnNode(25, 1));
               var11.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/NBTTagCompoundTransformer", "nbtEquals", "(Lnet/minecraft/nbt/NBTTagCompound;Ljava/lang/Object;)Z"));
               methodNode.instructions.insert(start, var11);
               break;
            }
         }

      }
   }

   public static boolean nbtEquals(NBTTagCompound tag1, Object tag2) {
      try {
         if(tagMap == null) {
            tagMap = NBTTagCompound.class.getDeclaredField("field_74784_a");
            tagMap.setAccessible(true);
         }

         if(tag1.func_74781_a("rottenTimer") == null && ((NBTTagCompound)tag2).func_74781_a("rottenTimer") == null) {
            return ((Map)tagMap.get(tag1)).equals((Map)tagMap.get(tag2));
         } else {
            NBTTagCompound e = (NBTTagCompound)tag1.func_74737_b();
            NBTTagCompound nbtTagCompound2 = (NBTTagCompound)((NBTTagCompound)tag2).func_74737_b();
            e.func_82580_o("rottenTimer");
            nbtTagCompound2.func_82580_o("rottenTimer");
            Map tagMap1 = (Map)tagMap.get(e);
            Map tagMap2 = (Map)tagMap.get(nbtTagCompound2);
            return tagMap1.entrySet().equals(tagMap2.entrySet());
         }
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

}
