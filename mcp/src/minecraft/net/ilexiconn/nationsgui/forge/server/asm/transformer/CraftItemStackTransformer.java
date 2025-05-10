package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class CraftItemStackTransformer implements Transformer
{
    public String getTarget()
    {
        return "org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode methodNode = (MethodNode)var3.next();

            if (methodNode.name.equals("hasItemMeta") && methodNode.desc.equals("()Z"))
            {
                AbstractInsnNode[] var5 = methodNode.instructions.toArray();
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; ++var7)
                {
                    AbstractInsnNode abstractInsnNode = var5[var7];

                    if (abstractInsnNode instanceof MethodInsnNode && abstractInsnNode.getOpcode() == 184)
                    {
                        MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
                        methodInsnNode.owner = "net/ilexiconn/nationsgui/forge/server/asm/transformer/CraftItemStackTransformer";
                    }
                }

                return;
            }
        }
    }

    public static boolean hasItemMeta(ItemStack item)
    {
        if (item == null)
        {
            return false;
        }
        else if (item.getItem() instanceof ItemFood && item.stackTagCompound != null)
        {
            NBTTagCompound nbtTagCompound = (NBTTagCompound)item.stackTagCompound.copy();
            nbtTagCompound.removeTag("rottenTimer");
            return !nbtTagCompound.hasNoTags();
        }
        else
        {
            return item.stackTagCompound != null && !item.stackTagCompound.hasNoTags();
        }
    }
}
