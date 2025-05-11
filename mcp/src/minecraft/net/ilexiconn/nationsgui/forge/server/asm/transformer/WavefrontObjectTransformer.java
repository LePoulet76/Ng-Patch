/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.FieldNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class WavefrontObjectTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraftforge.client.model.obj.WavefrontObject";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (FieldNode fieldNode : node.fields) {
            if (!fieldNode.desc.equals("Ljava/util/regex/Matcher;")) continue;
            fieldNode.access = 2;
        }
        List<String> methodsList = Arrays.asList("isValidVertexLine", "isValidVertexNormalLine", "isValidTextureCoordinateLine", "isValidFace_V_VT_VN_Line", "isValidFace_V_VT_Line", "isValidFace_V_VN_Line", "isValidFace_V_Line", "isValidGroupObjectLine");
        for (MethodNode methodNode : node.methods) {
            MethodInsnNode methodInsnNode;
            if (methodsList.contains(methodNode.name)) {
                methodNode.access = 2;
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (!(insnNode instanceof VarInsnNode)) continue;
                    VarInsnNode varInsnNode = (VarInsnNode)insnNode;
                    if (varInsnNode.var != 0) continue;
                    varInsnNode.var = 1;
                }
                block7: for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (!(insnNode instanceof FieldInsnNode)) continue;
                    FieldInsnNode fieldInsnNode = (FieldInsnNode)insnNode;
                    if (!fieldInsnNode.desc.equals("Ljava/util/regex/Matcher;")) continue;
                    switch (fieldInsnNode.getOpcode()) {
                        case 178: {
                            fieldInsnNode.setOpcode(180);
                            methodNode.instructions.insertBefore((AbstractInsnNode)fieldInsnNode, (AbstractInsnNode)new VarInsnNode(25, 0));
                            continue block7;
                        }
                        case 179: {
                            fieldInsnNode.setOpcode(181);
                        }
                    }
                }
            } else {
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (!(insnNode instanceof MethodInsnNode)) continue;
                    MethodInsnNode methodInsnNode2 = (MethodInsnNode)insnNode;
                    if (!methodsList.contains(methodInsnNode2.name) && !methodInsnNode2.name.equals("isValidFaceLine")) continue;
                    methodInsnNode2.setOpcode(182);
                }
            }
            HashMap<String, String> patch1Map = new HashMap<String, String>();
            patch1Map.put("isValidTextureCoordinateLine", "textureCoordinateMatcher");
            patch1Map.put("isValidFace_V_VT_VN_Line", "face_V_VT_VN_Matcher");
            patch1Map.put("isValidFace_V_VT_Line", "face_V_VT_Matcher");
            patch1Map.put("isValidFace_V_Line", "face_V_Matcher");
            patch1Map.put("isValidGroupObjectLine", "groupObjectMatcher");
            patch1Map.put("isValidVertexLine", "vertexMatcher");
            patch1Map.put("isValidVertexNormalLine", "vertexNormalMatcher");
            patch1Map.put("isValidFace_V_VN_Line", "face_V_VN_Matcher");
            if (patch1Map.containsKey(methodNode.name)) {
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    FieldInsnNode fieldInsnNode;
                    if (!(insnNode instanceof FieldInsnNode) || (fieldInsnNode = (FieldInsnNode)insnNode).getOpcode() != 181 || !fieldInsnNode.name.equals(patch1Map.get(methodNode.name))) continue;
                    AbstractInsnNode current = insnNode;
                    while (current.getOpcode() != 178) {
                        current = current.getPrevious();
                    }
                    methodNode.instructions.insertBefore(current, (AbstractInsnNode)new VarInsnNode(25, 0));
                }
            }
            HashMap<String, String> patch2Map = new HashMap<String, String>();
            patch2Map.put("parseVertex", "isValidVertexLine");
            patch2Map.put("parseVertexNormal", "isValidVertexNormalLine");
            patch2Map.put("parseTextureCoordinate", "isValidTextureCoordinateLine");
            patch2Map.put("parseGroupObject", "isValidGroupObjectLine");
            if (patch2Map.containsKey(methodNode.name)) {
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (!(insnNode instanceof MethodInsnNode)) continue;
                    MethodInsnNode methodInsnNode3 = (MethodInsnNode)insnNode;
                    if (!methodInsnNode3.name.equals(patch2Map.get(methodNode.name))) continue;
                    methodNode.instructions.insertBefore(methodInsnNode3.getPrevious().getPrevious(), (AbstractInsnNode)new VarInsnNode(25, 0));
                }
            }
            if (methodNode.name.equals("parseFace")) {
                List<String> patch3List = Arrays.asList("isValidFaceLine", "isValidFace_V_VT_VN_Line", "isValidFace_V_VT_Line", "isValidFace_V_VN_Line", "isValidFace_V_Line");
                for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                    if (!(insnNode instanceof MethodInsnNode)) continue;
                    methodInsnNode = (MethodInsnNode)insnNode;
                    if (!patch3List.contains(methodInsnNode.name)) continue;
                    methodNode.instructions.insertBefore(methodInsnNode.getPrevious().getPrevious(), (AbstractInsnNode)new VarInsnNode(25, 0));
                }
            }
            if (!methodNode.name.equals("isValidFaceLine")) continue;
            methodNode.access = 2;
            List<String> patch4List = Arrays.asList("isValidFace_V_VT_VN_Line", "isValidFace_V_VT_Line", "isValidFace_V_VN_Line", "isValidFace_V_Line");
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (!(insnNode instanceof MethodInsnNode)) continue;
                methodInsnNode = (MethodInsnNode)insnNode;
                if (!patch4List.contains(methodInsnNode.name)) continue;
                methodNode.instructions.insertBefore((AbstractInsnNode)methodInsnNode, (AbstractInsnNode)new VarInsnNode(25, 1));
            }
        }
    }
}

