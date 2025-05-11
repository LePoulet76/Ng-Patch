/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.LdcInsnNode
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
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class RenderPlayerTransformer
implements Transformer {
    @Override
    public String getTarget() {
        return "micdoodle8.mods.galacticraft.core.client.render.entities.GCCoreRenderPlayer";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("<init>")) continue;
            InsnList insnList = methodNode.instructions;
            insnList.clear();
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insnList.add((AbstractInsnNode)new MethodInsnNode(183, "net/minecraft/client/renderer/entity/RenderPlayer", "<init>", "()V"));
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insnList.add((AbstractInsnNode)new TypeInsnNode(187, "net/ilexiconn/nationsgui/forge/client/model/entity/PlayerModelGC"));
            insnList.add((AbstractInsnNode)new InsnNode(89));
            insnList.add((AbstractInsnNode)new InsnNode(11));
            insnList.add((AbstractInsnNode)new MethodInsnNode(183, "net/ilexiconn/nationsgui/forge/client/model/entity/PlayerModelGC", "<init>", "(F)V"));
            insnList.add((AbstractInsnNode)new FieldInsnNode(181, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "mainModel" : "field_77045_g", "Lnet/minecraft/client/model/ModelBase;"));
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insnList.add((AbstractInsnNode)new FieldInsnNode(180, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "mainModel" : "field_77045_g", "Lnet/minecraft/client/model/ModelBase;"));
            insnList.add((AbstractInsnNode)new TypeInsnNode(192, "net/ilexiconn/nationsgui/forge/client/model/entity/PlayerModelGC"));
            insnList.add((AbstractInsnNode)new FieldInsnNode(181, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "modelBipedMain" : "field_77109_a", "Lnet/minecraft/client/model/ModelBiped;"));
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insnList.add((AbstractInsnNode)new TypeInsnNode(187, "micdoodle8/mods/galacticraft/core/client/model/GCCoreModelPlayer"));
            insnList.add((AbstractInsnNode)new InsnNode(89));
            insnList.add((AbstractInsnNode)new InsnNode(12));
            insnList.add((AbstractInsnNode)new MethodInsnNode(183, "micdoodle8/mods/galacticraft/core/client/model/GCCoreModelPlayer", "<init>", "(F)V"));
            insnList.add((AbstractInsnNode)new FieldInsnNode(181, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "modelArmorChestplate" : "field_77108_b", "Lnet/minecraft/client/model/ModelBiped;"));
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insnList.add((AbstractInsnNode)new TypeInsnNode(187, "micdoodle8/mods/galacticraft/core/client/model/GCCoreModelPlayer"));
            insnList.add((AbstractInsnNode)new InsnNode(89));
            insnList.add((AbstractInsnNode)new LdcInsnNode((Object)new Float("0.5")));
            insnList.add((AbstractInsnNode)new MethodInsnNode(183, "micdoodle8/mods/galacticraft/core/client/model/GCCoreModelPlayer", "<init>", "(F)V"));
            insnList.add((AbstractInsnNode)new FieldInsnNode(181, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "modelArmor" : "field_77111_i", "Lnet/minecraft/client/model/ModelBiped;"));
            insnList.add((AbstractInsnNode)new InsnNode(177));
        }
        MethodNode renderFirstPersonArm = new MethodNode(1, dev ? "renderFirstPersonArm" : "func_82441_a", "(Lnet/minecraft/entity/player/EntityPlayer;)V", null, null);
        InsnList insnList = renderFirstPersonArm.instructions;
        insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
        insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
        insnList.add((AbstractInsnNode)new FieldInsnNode(180, "micdoodle8/mods/galacticraft/core/client/render/entities/GCCoreRenderPlayer", dev ? "modelBipedMain" : "field_77109_a", "Lnet/minecraft/client/model/ModelBiped;"));
        insnList.add((AbstractInsnNode)new VarInsnNode(25, 1));
        insnList.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/client/model/entity/PlayerModelGC", "renderFirstPersonArm", "(Lnet/minecraft/client/renderer/entity/RenderPlayer;Lnet/minecraft/client/model/ModelBiped;Lnet/minecraft/entity/player/EntityPlayer;)V"));
        insnList.add((AbstractInsnNode)new InsnNode(177));
        node.methods.add(renderFirstPersonArm);
    }
}

