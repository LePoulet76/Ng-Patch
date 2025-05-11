/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.FMLCommonHandler
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.world.World
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.DropMessagePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;

public class JSONDrop {
    private static transient Random RANDOM = new Random();
    private int id;
    private int amount;
    private int metadata;
    private int weight;
    public Map<String, String> message = new HashMap<String, String>();
    public String[] commands;

    public boolean canDrop() {
        System.out.println(this.weight + ", " + this.id + "x" + this.amount);
        return RANDOM.nextInt(100) < this.weight;
    }

    public void dropItem(EntityPlayer player, World world, int x, int y, int z, int blockID, int dropID) {
        if (this.id != 0) {
            EntityItem entity = new EntityItem(world, (double)((float)x + 0.5f), (double)((float)y + 1.0f), (double)((float)z + 0.5f), new ItemStack(this.id, this.amount, this.metadata));
            world.func_72838_d((Entity)entity);
        }
        if (this.commands != null && !world.field_72995_K) {
            for (String command : this.commands) {
                command = command.replace("${username}", player.field_71092_bJ);
                command = command.replace("${x}", x + "");
                command = command.replace("${y}", y + "");
                command = command.replace("${z}", z + "");
                FMLCommonHandler.instance().getMinecraftServerInstance().func_71252_i(command);
            }
        }
        if (!this.message.isEmpty()) {
            System.out.println(world.func_72798_a(x, y, z));
            PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(new DropMessagePacket(blockID, dropID)), (Player)((Player)player));
        }
    }
}

