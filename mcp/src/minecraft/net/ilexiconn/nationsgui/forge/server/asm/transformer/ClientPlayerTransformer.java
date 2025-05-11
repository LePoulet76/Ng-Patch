/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public class ClientPlayerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.entity.AbstractClientPlayer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        block0: for (MethodNode methodNode : node.methods) {
            InsnList insnList;
            if (methodNode.name.equals("getDownloadImageSkin") || methodNode.name.equals("func_110304_a")) {
                insnList = methodNode.instructions;
                for (AbstractInsnNode insnNode : insnList.toArray()) {
                    if (!(insnNode instanceof TypeInsnNode)) continue;
                    ((TypeInsnNode)insnNode).desc = "net/ilexiconn/nationsgui/forge/client/util/SkinImageBuffer";
                    ((MethodInsnNode)insnNode.getNext().getNext()).owner = "net/ilexiconn/nationsgui/forge/client/util/SkinImageBuffer";
                    continue block0;
                }
                continue;
            }
            if (methodNode.name.equals("getDownloadImageCape") || methodNode.name.equals("func_110307_b")) {
                insnList = methodNode.instructions;
                boolean flag = false;
                AbstractInsnNode[] abstractInsnNodeArray = insnList.toArray();
                int n = abstractInsnNodeArray.length;
                for (int insnNode = 0; insnNode < n; ++insnNode) {
                    AbstractInsnNode insnNode2 = abstractInsnNodeArray[insnNode];
                    if (!(insnNode2 instanceof InsnNode)) continue;
                    if (flag) {
                        insnList.insertBefore(insnNode2, (AbstractInsnNode)new TypeInsnNode(187, "net/ilexiconn/nationsgui/forge/client/util/CapeImageBuffer"));
                        insnList.insertBefore(insnNode2, (AbstractInsnNode)new InsnNode(89));
                        insnList.insertBefore(insnNode2, (AbstractInsnNode)new MethodInsnNode(183, "net/ilexiconn/nationsgui/forge/client/util/CapeImageBuffer", "<init>", "()V"));
                        insnList.remove(insnNode2);
                        continue block0;
                    }
                    flag = true;
                }
                continue;
            }
            if (!methodNode.name.equals("<clinit>")) continue;
            insnList = methodNode.instructions;
            for (AbstractInsnNode insnNode : insnList.toArray()) {
                if (insnNode instanceof MethodInsnNode) {
                    ((MethodInsnNode)insnNode).desc = "(Ljava/lang/String;Ljava/lang/String;)V";
                    continue;
                }
                if (!(insnNode instanceof LdcInsnNode)) continue;
                insnList.insertBefore(insnNode, (AbstractInsnNode)new LdcInsnNode((Object)"nationsgui"));
                ((LdcInsnNode)insnNode).cst = "textures/entity/steve.png";
            }
        }
    }
}

