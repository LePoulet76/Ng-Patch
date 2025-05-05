package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class RenderPlayerTransformer implements Transformer
{
    public String getTarget()
    {
        return "micdoodle8.mods.galacticraft.core.client.render.entities.GCCoreRenderPlayer";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator renderFirstPersonArm = node.methods.iterator();

        while (renderFirstPersonArm.hasNext())
        {
            MethodNode insnList = (MethodNode)renderFirstPersonArm.next();

            if (insnList.name.equals("<init>"))
            {
                InsnList insnList1 = insnList.instructions;
                insnList1.clear();
                insnList1.add(new VarInsnNode(25, 0));
                insnList1.add(new MethodInsnNode(183, "net/minecraft/client/renderer/entity/RenderPlayer", "<init>", "()V"));
                insnList1.add(new VarInsnNode(25, 0));
                insnList1.add(new TypeInsnNode(187, "net/ilexiconn/nationsgui/forge/client/model/entity/PlayerModelGC"));
                insnList1.add(new InsnNode(89));
                insnList1.add(new InsnNode(11));
                insnList1.add(new MethodInsnNode(183, "net/ilexiconn/nationsgui/forge/client/model/entity/PlayerModelGC", "<init>", "(F)V"));
                insnList1.add(new FieldInsnNode(181, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "mainModel" : "mainModel", "Lnet/minecraft/client/model/ModelBase;"));
                insnList1.add(new VarInsnNode(25, 0));
                insnList1.add(new VarInsnNode(25, 0));
                insnList1.add(new FieldInsnNode(180, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "mainModel" : "mainModel", "Lnet/minecraft/client/model/ModelBase;"));
                insnList1.add(new TypeInsnNode(192, "net/ilexiconn/nationsgui/forge/client/model/entity/PlayerModelGC"));
                insnList1.add(new FieldInsnNode(181, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "modelBipedMain" : "modelBipedMain", "Lnet/minecraft/client/model/ModelBiped;"));
                insnList1.add(new VarInsnNode(25, 0));
                insnList1.add(new TypeInsnNode(187, "micdoodle8/mods/galacticraft/core/client/model/GCCoreModelPlayer"));
                insnList1.add(new InsnNode(89));
                insnList1.add(new InsnNode(12));
                insnList1.add(new MethodInsnNode(183, "micdoodle8/mods/galacticraft/core/client/model/GCCoreModelPlayer", "<init>", "(F)V"));
                insnList1.add(new FieldInsnNode(181, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "modelArmorChestplate" : "modelArmorChestplate", "Lnet/minecraft/client/model/ModelBiped;"));
                insnList1.add(new VarInsnNode(25, 0));
                insnList1.add(new TypeInsnNode(187, "micdoodle8/mods/galacticraft/core/client/model/GCCoreModelPlayer"));
                insnList1.add(new InsnNode(89));
                insnList1.add(new LdcInsnNode(new Float("0.5")));
                insnList1.add(new MethodInsnNode(183, "micdoodle8/mods/galacticraft/core/client/model/GCCoreModelPlayer", "<init>", "(F)V"));
                insnList1.add(new FieldInsnNode(181, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "modelArmor" : "modelArmor", "Lnet/minecraft/client/model/ModelBiped;"));
                insnList1.add(new InsnNode(177));
            }
        }

        MethodNode renderFirstPersonArm1 = new MethodNode(1, dev ? "renderFirstPersonArm" : "renderFirstPersonArm", "(Lnet/minecraft/entity/player/EntityPlayer;)V", (String)null, (String[])null);
        InsnList insnList2 = renderFirstPersonArm1.instructions;
        insnList2.add(new VarInsnNode(25, 0));
        insnList2.add(new VarInsnNode(25, 0));
        insnList2.add(new FieldInsnNode(180, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "modelBipedMain" : "modelBipedMain", "Lnet/minecraft/client/model/ModelBiped;"));
        insnList2.add(new VarInsnNode(25, 1));
        insnList2.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/model/entity/PlayerModelGC", "renderFirstPersonArm", "(Lnet/minecraft/client/renderer/entity/RenderPlayer;Lnet/minecraft/client/model/ModelBiped;Lnet/minecraft/entity/player/EntityPlayer;)V"));
        insnList2.add(new InsnNode(177));
        node.methods.add(renderFirstPersonArm1);
    }
}
