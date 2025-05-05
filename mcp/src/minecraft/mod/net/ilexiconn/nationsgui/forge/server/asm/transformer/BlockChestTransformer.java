package net.ilexiconn.nationsgui.forge.server.asm.transformer;

import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.tileentity.TileEntityChest;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class BlockChestTransformer implements Transformer
{
    public String getTarget()
    {
        return "net.minecraft.tileentity.TileEntityChest";
    }

    public void transform(ClassNode node, boolean dev)
    {
        Iterator var3 = node.methods.iterator();

        while (var3.hasNext())
        {
            MethodNode mn = (MethodNode)var3.next();

            if (mn.name.equalsIgnoreCase("func_94044_a") && mn.desc.equalsIgnoreCase("(III)Z"))
            {
                InsnList list = new InsnList();
                list.add(new VarInsnNode(25, 0));
                list.add(new VarInsnNode(21, 1));
                list.add(new VarInsnNode(21, 2));
                list.add(new VarInsnNode(21, 3));
                list.add(new MethodInsnNode(184, BlockChestTransformer.class.getName().replace('.', '/'), "checkCast", "(Lnet/minecraft/tileentity/TileEntityChest;III)Z"));
                list.add(new InsnNode(172));
                mn.instructions.insertBefore(mn.instructions.getFirst(), list);
            }
        }
    }

    public static boolean checkCast(TileEntityChest tile, int x, int y, int z)
    {
        Block block = Block.blocksList[tile.worldObj.getBlockId(x, y, z)];
        return block != null && block.blockID != 3590 && tile.worldObj.getBlockTileEntity(x, y, z) instanceof TileEntityChest && block != null && block instanceof BlockChest ? ((BlockChest)block).chestType == tile.getChestType() : false;
    }
}
