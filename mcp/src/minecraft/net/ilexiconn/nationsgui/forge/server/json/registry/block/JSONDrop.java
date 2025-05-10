package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.DropMessagePacket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class JSONDrop
{
    private static transient Random RANDOM = new Random();
    private int id;
    private int amount;
    private int metadata;
    private int weight;
    public Map<String, String> message = new HashMap();
    public String[] commands;

    public boolean canDrop()
    {
        System.out.println(this.weight + ", " + this.id + "x" + this.amount);
        return RANDOM.nextInt(100) < this.weight;
    }

    public void dropItem(EntityPlayer player, World world, int x, int y, int z, int blockID, int dropID)
    {
        if (this.id != 0)
        {
            EntityItem entity = new EntityItem(world, (double)((float)x + 0.5F), (double)((float)y + 1.0F), (double)((float)z + 0.5F), new ItemStack(this.id, this.amount, this.metadata));
            world.spawnEntityInWorld(entity);
        }

        if (this.commands != null && !world.isRemote)
        {
            String[] var12 = this.commands;
            int var9 = var12.length;

            for (int var10 = 0; var10 < var9; ++var10)
            {
                String command = var12[var10];
                command = command.replace("${username}", player.username);
                command = command.replace("${x}", x + "");
                command = command.replace("${y}", y + "");
                command = command.replace("${z}", z + "");
                FMLCommonHandler.instance().getMinecraftServerInstance().executeCommand(command);
            }
        }

        if (!this.message.isEmpty())
        {
            System.out.println(world.getBlockId(x, y, z));
            PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new DropMessagePacket(blockID, dropID)), (Player)player);
        }
    }
}
