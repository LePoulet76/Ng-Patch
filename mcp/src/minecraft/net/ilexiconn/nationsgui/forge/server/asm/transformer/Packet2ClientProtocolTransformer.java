/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class Packet2ClientProtocolTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.network.packet.Packet2ClientProtocol";
    }

    @Override
    public void transform(ClassNode classNode, boolean dev) {
        String methodName = NationsGUITransformer.inDevelopment ? "processPacket" : "func_73279_a";
        for (MethodNode methodNode : classNode.methods) {
            if (!methodNode.name.equals(methodName) || !methodNode.desc.equals("(Lnet/minecraft/network/packet/NetHandler;)V")) continue;
            InsnList insnList = methodNode.instructions;
            insnList.clear();
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 1));
            insnList.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NetworkHook", "onProtocolPacket", "(Lnet/minecraft/network/packet/Packet2ClientProtocol;Lnet/minecraft/network/packet/NetHandler;)V"));
            insnList.add((AbstractInsnNode)new InsnNode(177));
        }
    }
}

