package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ContainerTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.inventory.Container";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode mn = (MethodNode)var3.next();

            if (mn.name.equals(dev ? "detectAndSendChanges" : "detectAndSendChanges") && mn.desc.equals("()V"))
            {
                InsnList var14 = new InsnList();
                var14.add(new VarInsnNode(25, 0));
                var14.add(new FieldInsnNode(180, "net/minecraft/inventory/Container", dev ? "inventorySlots" : "inventorySlots", "Ljava/util/List;"));
                var14.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/ContainerTransformer", "updateItemIfFood", "(Ljava/util/List;)V"));
                mn.instructions.insertBefore(mn.instructions.getFirst(), var14);
            }
            else if (!mn.name.equals(dev ? "mergeItemStack" : "mergeItemStack"))
            {
                AbstractInsnNode[] var13;
                int var15;
                int var17;
                AbstractInsnNode var19;
                MethodInsnNode var20;

                if (mn.name.equals("func_94527_a"))
                {
                    var13 = mn.instructions.toArray();
                    var15 = var13.length;

                    for (var17 = 0; var17 < var15; ++var17)
                    {
                        var19 = var13[var17];

                        if (var19 instanceof MethodInsnNode)
                        {
                            var20 = (MethodInsnNode)var19;

                            if (var20.name.equals(dev ? "areItemStackTagsEqual" : "areItemStackTagsEqual"))
                            {
                                var20.name = "areItemStackTagsEqual";
                                var20.owner = "net/ilexiconn/nationsgui/forge/server/asm/transformer/ContainerTransformer";
                                break;
                            }
                        }
                    }
                }
                else if (mn.name.equals(dev ? "slotClick" : "slotClick"))
                {
                    var13 = mn.instructions.toArray();
                    var15 = var13.length;

                    for (var17 = 0; var17 < var15; ++var17)
                    {
                        var19 = var13[var17];

                        if (var19 instanceof MethodInsnNode)
                        {
                            var20 = (MethodInsnNode)var19;

                            if (var20.name.equals(dev ? "areItemStackTagsEqual" : "areItemStackTagsEqual"))
                            {
                                var20.name = "areItemStackTagsEqual";
                                var20.owner = "net/ilexiconn/nationsgui/forge/server/asm/transformer/ContainerTransformer";
                                break;
                            }
                        }
                    }
                }
            }
            else
            {
                AbstractInsnNode target = null;
                LabelNode jumpLabel = null;
                AbstractInsnNode[] patch = mn.instructions.toArray();
                int insnNode = patch.length;

                for (int methodInsnNode = 0; methodInsnNode < insnNode; ++methodInsnNode)
                {
                    AbstractInsnNode insnNode1 = patch[methodInsnNode];

                    if (insnNode1 instanceof MethodInsnNode)
                    {
                        MethodInsnNode methodInsnNode1 = (MethodInsnNode)insnNode1;

                        if (methodInsnNode1.getOpcode() == 182 && methodInsnNode1.name.equals(dev ? "getStack" : "getStack"))
                        {
                            target = methodInsnNode1.getNext();
                            AbstractInsnNode current;

                            for (current = target; !(current instanceof JumpInsnNode); current = current.getNext())
                            {
                                ;
                            }

                            jumpLabel = ((JumpInsnNode)current).label;
                            break;
                        }
                    }
                }

                InsnList var16 = new InsnList();
                var16.add(new VarInsnNode(25, 1));
                var16.add(new VarInsnNode(25, 8));
                var16.add(new MethodInsnNode(184, "net/ilexiconn/nationsgui/forge/server/asm/transformer/ContainerTransformer", "canMergeItemFood", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z"));
                LabelNode var18 = new LabelNode();
                var16.add(new JumpInsnNode(154, var18));
                var16.add(new JumpInsnNode(167, (LabelNode)jumpLabel));
                var16.add(var18);
                mn.instructions.insert(target, var16);
            }
        }
    }

    public static boolean areItemStackTagsEqual(ItemStack item, ItemStack item2)
    {
        if (item != null && item2 != null && item.getItem() instanceof ItemFood && item2.getItem() instanceof ItemFood)
        {
            if (item.hasTagCompound() && item2.hasTagCompound())
            {
                NBTTagCompound nbt1 = item.getTagCompound();
                NBTTagCompound nbt2 = item2.getTagCompound();
                return !nbt1.hasKey("rottenTimer") && !nbt2.hasKey("rottenTimer") || nbt1.hasKey("rottenTimer") && nbt2.hasKey("rottenTimer") && nbt1.getLong("rottenTimer") == nbt2.getLong("rottenTimer");
            }
            else
            {
                return false;
            }
        }
        else
        {
            return ItemStack.areItemStackTagsEqual(item, item2);
        }
    }

    public static boolean canMergeItemFood(ItemStack item, ItemStack item2)
    {
        if (item != null && item2 != null && item.getItem() instanceof ItemFood && item2.getItem() instanceof ItemFood)
        {
            if (item.hasTagCompound() && item2.hasTagCompound())
            {
                NBTTagCompound nbt1 = item.getTagCompound();
                NBTTagCompound nbt2 = item2.getTagCompound();
                return !nbt1.hasKey("rottenTimer") && !nbt2.hasKey("rottenTimer") || nbt1.hasKey("rottenTimer") && nbt2.hasKey("rottenTimer") && nbt1.getLong("rottenTimer") == nbt2.getLong("rottenTimer");
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    public static void updateItemIfFood(List listSlot)
    {
        Iterator var1 = listSlot.iterator();

        while (var1.hasNext())
        {
            Slot slot = (Slot)var1.next();

            if (slot != null)
            {
                ItemStack stack = slot.getStack();

                if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemFood)
                {
                    stack.getItem().onUpdate(stack, (World)null, (Entity)null, 0, false);
                }
            }
        }
    }
}
