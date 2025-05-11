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
 *  org.objectweb.asm.tree.LocalVariableNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.TypeInsnNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class EntityRendererTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "net.minecraft.client.renderer.EntityRenderer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode method : node.methods) {
            if (!method.name.equals(dev ? "updateLightmap" : "func_78472_g") && (!method.name.equals("h") || !method.desc.equals("(F)V"))) continue;
            LabelNode startLabel = new LabelNode();
            LabelNode endLabel = new LabelNode();
            LocalVariableNode lightMapEvent = new LocalVariableNode("lightMapEvent", "Lnet/minecraftforge/event/Event;", null, startLabel, endLabel, method.localVariables.size());
            method.localVariables.add(lightMapEvent);
            MethodInsnNode target = null;
            for (AbstractInsnNode abstractInsnNode : method.instructions.toArray()) {
                if (abstractInsnNode.getOpcode() != 182) continue;
                MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                if (!methodInsnNode.owner.equals("net/minecraft/client/entity/EntityClientPlayerMP") || !methodInsnNode.name.equals(dev ? "isPotionActive" : "func_70644_a") || !methodInsnNode.desc.equals("(Lnet/minecraft/potion/Potion;)Z")) continue;
                boolean b = !NationsGUITransformer.optifine || target != null;
                target = methodInsnNode;
                if (b) break;
            }
            JumpInsnNode jumpInsnNode = (JumpInsnNode)target.getNext();
            InsnList patch = new InsnList();
            patch.add((AbstractInsnNode)startLabel);
            patch.add((AbstractInsnNode)new TypeInsnNode(187, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent"));
            patch.add((AbstractInsnNode)new InsnNode(89));
            patch.add((AbstractInsnNode)new VarInsnNode(23, 11));
            patch.add((AbstractInsnNode)new VarInsnNode(23, 12));
            patch.add((AbstractInsnNode)new VarInsnNode(23, 13));
            patch.add((AbstractInsnNode)new InsnNode(11));
            patch.add((AbstractInsnNode)new MethodInsnNode(183, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent", "<init>", "(FFFF)V"));
            patch.add((AbstractInsnNode)new VarInsnNode(58, lightMapEvent.index));
            patch.add((AbstractInsnNode)new FieldInsnNode(178, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS", "Lnet/minecraftforge/event/EventBus;"));
            patch.add((AbstractInsnNode)new VarInsnNode(25, lightMapEvent.index));
            patch.add((AbstractInsnNode)new MethodInsnNode(182, "net/minecraftforge/event/EventBus", "post", "(Lnet/minecraftforge/event/Event;)Z"));
            patch.add((AbstractInsnNode)new InsnNode(87));
            patch.add((AbstractInsnNode)new VarInsnNode(25, lightMapEvent.index));
            patch.add((AbstractInsnNode)new FieldInsnNode(180, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent", "r", "F"));
            patch.add((AbstractInsnNode)new VarInsnNode(56, 11));
            patch.add((AbstractInsnNode)new VarInsnNode(25, lightMapEvent.index));
            patch.add((AbstractInsnNode)new FieldInsnNode(180, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent", "g", "F"));
            patch.add((AbstractInsnNode)new VarInsnNode(56, 12));
            patch.add((AbstractInsnNode)new VarInsnNode(25, lightMapEvent.index));
            patch.add((AbstractInsnNode)new FieldInsnNode(180, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent", "b", "F"));
            patch.add((AbstractInsnNode)new VarInsnNode(56, 13));
            patch.add((AbstractInsnNode)endLabel);
            method.instructions.insert((AbstractInsnNode)jumpInsnNode.label, patch);
            return;
        }
    }
}

