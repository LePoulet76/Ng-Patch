/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.JumpInsnNode
 *  org.objectweb.asm.tree.LabelNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ServerListenThreadTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.server.ServerListenThread";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        System.out.println("Transforming class: " + node.name);
        for (MethodNode method : node.methods) {
            if (!method.name.equals("<init>")) continue;
            System.out.println("Found constructor in ServerListenThread.");
            this.injectServerSocketReplacement(method);
        }
    }

    private void injectServerSocketReplacement(MethodNode method) {
        InsnList instructions = method.instructions;
        AbstractInsnNode targetPutFieldNode = null;
        for (AbstractInsnNode insn = instructions.getFirst(); insn != null; insn = insn.getNext()) {
            if (insn.getOpcode() != 181 || !(insn instanceof FieldInsnNode) || !((FieldInsnNode)insn).name.equals("field_71774_e")) continue;
            targetPutFieldNode = insn;
            break;
        }
        if (targetPutFieldNode != null) {
            System.out.println("Found PUTFIELD for myServerSocket. Injecting replacement...");
            InsnList newInstructions = new InsnList();
            newInstructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
            newInstructions.add((AbstractInsnNode)new FieldInsnNode(180, "net/minecraft/server/ServerListenThread", "field_71774_e", "Ljava/net/ServerSocket;"));
            LabelNode skipClose = new LabelNode();
            newInstructions.add((AbstractInsnNode)new JumpInsnNode(198, skipClose));
            newInstructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
            newInstructions.add((AbstractInsnNode)new FieldInsnNode(180, "net/minecraft/server/ServerListenThread", "field_71774_e", "Ljava/net/ServerSocket;"));
            newInstructions.add((AbstractInsnNode)new MethodInsnNode(182, "java/net/ServerSocket", "close", "()V"));
            newInstructions.add((AbstractInsnNode)skipClose);
            newInstructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
            newInstructions.add((AbstractInsnNode)new TypeInsnNode(187, "net/ilexiconn/nationsgui/forge/server/asm/FilteredServerSocket"));
            newInstructions.add((AbstractInsnNode)new InsnNode(89));
            newInstructions.add((AbstractInsnNode)new VarInsnNode(21, 3));
            newInstructions.add((AbstractInsnNode)new MethodInsnNode(183, "net/ilexiconn/nationsgui/forge/server/asm/FilteredServerSocket", "<init>", "(I)V"));
            newInstructions.add((AbstractInsnNode)new FieldInsnNode(181, "net/minecraft/server/ServerListenThread", "field_71774_e", "Ljava/net/ServerSocket;"));
            instructions.insert(targetPutFieldNode, newInstructions);
        } else {
            System.out.println("myServerSocket PUTFIELD not found!");
        }
    }
}

