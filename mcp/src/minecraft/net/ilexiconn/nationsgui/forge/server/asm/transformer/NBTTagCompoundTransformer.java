/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.LineNumberNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.lang.reflect.Field;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.ilexiconn.nationsgui.forge.server.asm.transformer.Transformer;
import net.minecraft.nbt.NBTTagCompound;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class NBTTagCompoundTransformer
implements Transformer {
    private static Field tagMap = null;

    @Override
    public String getTarget() {
        return "net.minecraft.nbt.NBTTagCompound";
    }

    @Override
    public void transform(ClassNode node, boolean dev) {
        if (!NationsGUITransformer.isServer) {
            return;
        }
        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("equals")) continue;
            LineNumberNode start = null;
            for (AbstractInsnNode insnNode : methodNode.instructions.toArray()) {
                if (!(insnNode instanceof LineNumberNode)) continue;
                LineNumberNode lineNumberNode = (LineNumberNode)insnNode;
                if (lineNumberNode.line != 440) continue;
                start = lineNumberNode;
            }
            while (start.getNext().getOpcode() != 172) {
                methodNode.instructions.remove(start.getNext());
            }
            InsnList insnList = new InsnList();
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insnList.add((AbstractInsnNode)new VarInsnNode(25, 1));
            insnList.add((AbstractInsnNode)new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/NBTTagCompoundTransformer", "nbtEquals", "(Lnet/minecraft/nbt/NBTTagCompound;Ljava/lang/Object;)Z"));
            methodNode.instructions.insert((AbstractInsnNode)start, insnList);
            break;
        }
    }

    public static boolean nbtEquals(NBTTagCompound tag1, Object tag2) {
        try {
            if (tagMap == null) {
                tagMap = NBTTagCompound.class.getDeclaredField("field_74784_a");
                tagMap.setAccessible(true);
            }
            if (tag1.func_74781_a("rottenTimer") != null || ((NBTTagCompound)tag2).func_74781_a("rottenTimer") != null) {
                NBTTagCompound nbtTagCompound = (NBTTagCompound)tag1.func_74737_b();
                NBTTagCompound nbtTagCompound2 = (NBTTagCompound)((NBTTagCompound)tag2).func_74737_b();
                nbtTagCompound.func_82580_o("rottenTimer");
                nbtTagCompound2.func_82580_o("rottenTimer");
                Map tagMap1 = (Map)tagMap.get(nbtTagCompound);
                Map tagMap2 = (Map)tagMap.get(nbtTagCompound2);
                return tagMap1.entrySet().equals(tagMap2.entrySet());
            }
            return ((Map)tagMap.get(tag1)).equals((Map)tagMap.get(tag2));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

