package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class WavefrontObjectTransformer implements Transformer {

   public String getTarget() {
      return "net.minecraftforge.client.model.obj.WavefrontObject";
   }

   public void transform(ClassNode node, boolean dev) {
      Iterator methodsList = node.fields.iterator();

      while(methodsList.hasNext()) {
         FieldNode fieldNode = (FieldNode)methodsList.next();
         if(fieldNode.desc.equals("Ljava/util/regex/Matcher;")) {
            fieldNode.access = 2;
         }
      }

      List var14 = Arrays.asList(new String[]{"isValidVertexLine", "isValidVertexNormalLine", "isValidTextureCoordinateLine", "isValidFace_V_VT_VN_Line", "isValidFace_V_VT_Line", "isValidFace_V_VN_Line", "isValidFace_V_Line", "isValidGroupObjectLine"});
      Iterator var15 = node.methods.iterator();

      while(var15.hasNext()) {
         MethodNode methodNode = (MethodNode)var15.next();
         AbstractInsnNode[] patch1Map;
         int patch2Map;
         int patch4List;
         AbstractInsnNode insnNode;
         if(var14.contains(methodNode.name)) {
            methodNode.access = 2;
            patch1Map = methodNode.instructions.toArray();
            patch2Map = patch1Map.length;

            for(patch4List = 0; patch4List < patch2Map; ++patch4List) {
               insnNode = patch1Map[patch4List];
               if(insnNode instanceof VarInsnNode) {
                  VarInsnNode insnNode1 = (VarInsnNode)insnNode;
                  if(insnNode1.var == 0) {
                     insnNode1.var = 1;
                  }
               }
            }

            patch1Map = methodNode.instructions.toArray();
            patch2Map = patch1Map.length;

            for(patch4List = 0; patch4List < patch2Map; ++patch4List) {
               insnNode = patch1Map[patch4List];
               if(insnNode instanceof FieldInsnNode) {
                  FieldInsnNode var21 = (FieldInsnNode)insnNode;
                  if(var21.desc.equals("Ljava/util/regex/Matcher;")) {
                     switch(var21.getOpcode()) {
                     case 178:
                        var21.setOpcode(180);
                        methodNode.instructions.insertBefore(var21, new VarInsnNode(25, 0));
                        break;
                     case 179:
                        var21.setOpcode(181);
                     }
                  }
               }
            }
         } else {
            patch1Map = methodNode.instructions.toArray();
            patch2Map = patch1Map.length;

            for(patch4List = 0; patch4List < patch2Map; ++patch4List) {
               insnNode = patch1Map[patch4List];
               if(insnNode instanceof MethodInsnNode) {
                  MethodInsnNode var22 = (MethodInsnNode)insnNode;
                  if(var14.contains(var22.name) || var22.name.equals("isValidFaceLine")) {
                     var22.setOpcode(182);
                  }
               }
            }
         }

         HashMap var16 = new HashMap();
         var16.put("isValidTextureCoordinateLine", "textureCoordinateMatcher");
         var16.put("isValidFace_V_VT_VN_Line", "face_V_VT_VN_Matcher");
         var16.put("isValidFace_V_VT_Line", "face_V_VT_Matcher");
         var16.put("isValidFace_V_Line", "face_V_Matcher");
         var16.put("isValidGroupObjectLine", "groupObjectMatcher");
         var16.put("isValidVertexLine", "vertexMatcher");
         var16.put("isValidVertexNormalLine", "vertexNormalMatcher");
         var16.put("isValidFace_V_VN_Line", "face_V_VN_Matcher");
         AbstractInsnNode insnNode3;
         int var19;
         if(var16.containsKey(methodNode.name)) {
            AbstractInsnNode[] var17 = methodNode.instructions.toArray();
            patch4List = var17.length;

            for(var19 = 0; var19 < patch4List; ++var19) {
               AbstractInsnNode var24 = var17[var19];
               if(var24 instanceof FieldInsnNode) {
                  FieldInsnNode insnNode2 = (FieldInsnNode)var24;
                  if(insnNode2.getOpcode() == 181 && insnNode2.name.equals(var16.get(methodNode.name))) {
                     for(insnNode3 = var24; insnNode3.getOpcode() != 178; insnNode3 = insnNode3.getPrevious()) {
                        ;
                     }

                     methodNode.instructions.insertBefore(insnNode3, new VarInsnNode(25, 0));
                  }
               }
            }
         }

         HashMap var18 = new HashMap();
         var18.put("parseVertex", "isValidVertexLine");
         var18.put("parseVertexNormal", "isValidVertexNormalLine");
         var18.put("parseTextureCoordinate", "isValidTextureCoordinateLine");
         var18.put("parseGroupObject", "isValidGroupObjectLine");
         int var26;
         if(var18.containsKey(methodNode.name)) {
            AbstractInsnNode[] var20 = methodNode.instructions.toArray();
            var19 = var20.length;

            for(var26 = 0; var26 < var19; ++var26) {
               AbstractInsnNode var27 = var20[var26];
               if(var27 instanceof MethodInsnNode) {
                  MethodInsnNode var29 = (MethodInsnNode)var27;
                  if(var29.name.equals(var18.get(methodNode.name))) {
                     methodNode.instructions.insertBefore(var29.getPrevious().getPrevious(), new VarInsnNode(25, 0));
                  }
               }
            }
         }

         MethodInsnNode methodInsnNode;
         List var23;
         AbstractInsnNode[] var25;
         int var28;
         if(methodNode.name.equals("parseFace")) {
            var23 = Arrays.asList(new String[]{"isValidFaceLine", "isValidFace_V_VT_VN_Line", "isValidFace_V_VT_Line", "isValidFace_V_VN_Line", "isValidFace_V_Line"});
            var25 = methodNode.instructions.toArray();
            var26 = var25.length;

            for(var28 = 0; var28 < var26; ++var28) {
               insnNode3 = var25[var28];
               if(insnNode3 instanceof MethodInsnNode) {
                  methodInsnNode = (MethodInsnNode)insnNode3;
                  if(var23.contains(methodInsnNode.name)) {
                     methodNode.instructions.insertBefore(methodInsnNode.getPrevious().getPrevious(), new VarInsnNode(25, 0));
                  }
               }
            }
         }

         if(methodNode.name.equals("isValidFaceLine")) {
            methodNode.access = 2;
            var23 = Arrays.asList(new String[]{"isValidFace_V_VT_VN_Line", "isValidFace_V_VT_Line", "isValidFace_V_VN_Line", "isValidFace_V_Line"});
            var25 = methodNode.instructions.toArray();
            var26 = var25.length;

            for(var28 = 0; var28 < var26; ++var28) {
               insnNode3 = var25[var28];
               if(insnNode3 instanceof MethodInsnNode) {
                  methodInsnNode = (MethodInsnNode)insnNode3;
                  if(var23.contains(methodInsnNode.name)) {
                     methodNode.instructions.insertBefore(methodInsnNode, new VarInsnNode(25, 1));
                  }
               }
            }
         }
      }

   }
}
