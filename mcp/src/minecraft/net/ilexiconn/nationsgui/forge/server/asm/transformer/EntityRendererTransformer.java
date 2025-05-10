package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
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

public class EntityRendererTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.client.renderer.EntityRenderer";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();
        MethodNode method;

        do
        {
            if (!var3.hasNext())
            {
                return;
            }

            method = (MethodNode)var3.next();
        }
        while (!method.name.equals(dev ? "updateLightmap" : "updateLightmap") && (!method.name.equals("h") || !method.desc.equals("(F)V")));

        LabelNode startLabel = new LabelNode();
        LabelNode endLabel = new LabelNode();
        LocalVariableNode lightMapEvent = new LocalVariableNode("lightMapEvent", "Lnet/minecraftforge/event/Event;", (String)null, startLabel, endLabel, method.localVariables.size());
        method.localVariables.add(lightMapEvent);
        MethodInsnNode target = null;
        AbstractInsnNode[] jumpInsnNode = method.instructions.toArray();
        int patch = jumpInsnNode.length;

        for (int var11 = 0; var11 < patch; ++var11)
        {
            AbstractInsnNode abstractInsnNode = jumpInsnNode[var11];

            if (abstractInsnNode.getOpcode() == 182)
            {
                MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;

                if (methodInsnNode.owner.equals("net/minecraft/client/entity/EntityClientPlayerMP") && methodInsnNode.name.equals(dev ? "isPotionActive" : "isPotionActive") && methodInsnNode.desc.equals("(Lnet/minecraft/potion/Potion;)Z"))
                {
                    boolean b = !NationsGUITransformer.optifine || target != null;
                    target = methodInsnNode;

                    if (b)
                    {
                        break;
                    }
                }
            }
        }

        JumpInsnNode var15 = (JumpInsnNode)target.getNext();
        InsnList var16 = new InsnList();
        var16.add(startLabel);
        var16.add(new TypeInsnNode(187, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent"));
        var16.add(new InsnNode(89));
        var16.add(new VarInsnNode(23, 11));
        var16.add(new VarInsnNode(23, 12));
        var16.add(new VarInsnNode(23, 13));
        var16.add(new InsnNode(11));
        var16.add(new MethodInsnNode(183, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent", "<init>", "(FFFF)V"));
        var16.add(new VarInsnNode(58, lightMapEvent.index));
        var16.add(new FieldInsnNode(178, "net/minecraftforge/common/MinecraftForge", "EVENT_BUS", "Lnet/minecraftforge/event/EventBus;"));
        var16.add(new VarInsnNode(25, lightMapEvent.index));
        var16.add(new MethodInsnNode(182, "net/minecraftforge/event/EventBus", "post", "(Lnet/minecraftforge/event/Event;)Z"));
        var16.add(new InsnNode(87));
        var16.add(new VarInsnNode(25, lightMapEvent.index));
        var16.add(new FieldInsnNode(180, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent", "r", "F"));
        var16.add(new VarInsnNode(56, 11));
        var16.add(new VarInsnNode(25, lightMapEvent.index));
        var16.add(new FieldInsnNode(180, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent", "g", "F"));
        var16.add(new VarInsnNode(56, 12));
        var16.add(new VarInsnNode(25, lightMapEvent.index));
        var16.add(new FieldInsnNode(180, "net/ilexiconn/nationsgui/forge/client/events/UpdateLightMapEvent", "b", "F"));
        var16.add(new VarInsnNode(56, 13));
        var16.add(endLabel);
        method.instructions.insert(var15.label, var16);
    }
}
