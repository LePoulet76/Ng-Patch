/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class FMLNetworkHandlerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "cpw.mods.fml.common.network.FMLNetworkHandler";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode method : node.methods) {
            if (!method.name.equals("handlePacket250Packet")) continue;
            InsnList patch = new InsnList();
            patch.add((AbstractInsnNode)new VarInsnNode(25, 0));
            patch.add((AbstractInsnNode)new VarInsnNode(25, 1));
            patch.add((AbstractInsnNode)new VarInsnNode(25, 2));
            patch.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "networkDebug", "(Lnet/minecraft/network/packet/Packet250CustomPayload;Lnet/minecraft/network/INetworkManager;Lnet/minecraft/network/packet/NetHandler;)V"));
            method.instructions.insert(patch);
            break;
        }
    }
}

