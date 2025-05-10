package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class BlockSpawnerTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.block.BlockMobSpawner";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator getDamageValue = node.methods.iterator();

        while (getDamageValue.hasNext())
        {
            MethodNode insnList = (MethodNode)getDamageValue.next();

            if (insnList.name.equals(dev ? "idPicked" : "idPicked"))
            {
                InsnList insnList1 = insnList.instructions;
                insnList1.clear();
                insnList1.add(new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/NationsGUI", "MOB_SPAWNER", "Lnet/minecraft/item/Item;"));
                insnList1.add(new FieldInsnNode(180, "net/minecraft/item/Item", dev ? "itemID" : "itemID", "I"));
                insnList1.add(new InsnNode(172));
                break;
            }
        }

        MethodNode getDamageValue1 = new MethodNode(1, dev ? "getDamageValue" : "getDamageValue", "(Lnet/minecraft/world/World;III)I", (String)null, (String[])null);
        InsnList insnList2 = getDamageValue1.instructions;
        insnList2.add(new FieldInsnNode(178, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "INSTANCE", "Lnet/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks;"));
        insnList2.add(new VarInsnNode(25, 1));
        insnList2.add(new VarInsnNode(21, 2));
        insnList2.add(new VarInsnNode(21, 3));
        insnList2.add(new VarInsnNode(21, 4));
        insnList2.add(new MethodInsnNode(182, "net/ilexiconn/nationsgui/forge/server/asm/NationsGUIHooks", "getSpawnerMeta", "(Lnet/minecraft/world/World;III)I"));
        insnList2.add(new InsnNode(172));
        node.methods.add(getDamageValue1);
    }
}
