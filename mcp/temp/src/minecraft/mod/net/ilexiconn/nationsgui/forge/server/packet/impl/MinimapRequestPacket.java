package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MinimapPacket;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class MinimapRequestPacket implements IPacket, IServerPacket {

   private int posX;
   private int posZ;
   private int chunkWidth;
   private int chunkHeight;
   public static HashMap<String, Long> lastPlayerCall = new HashMap();


   public MinimapRequestPacket(int posX, int posZ, int chunkWidth, int chunkHeight) {
      this.posX = posX;
      this.posZ = posZ;
      this.chunkWidth = chunkWidth;
      this.chunkHeight = chunkHeight;
   }

   public MinimapRequestPacket() {}

   public void handleServerPacket(EntityPlayer player) {
      if(!lastPlayerCall.containsKey(player.field_71092_bJ) || System.currentTimeMillis() - ((Long)lastPlayerCall.get(player.field_71092_bJ)).longValue() > 15000L) {
         lastPlayerCall.put(player.field_71092_bJ, Long.valueOf(System.currentTimeMillis()));
         this.chunkHeight = Math.min(this.chunkHeight, 20);
         this.chunkWidth = Math.min(this.chunkWidth, 20);
         byte[] colors = new byte[this.chunkHeight * 16 * this.chunkWidth * 16];
         World world = player.field_70170_p;
         int chunkX = (this.posX >> 4) - this.chunkWidth / 2;
         int chunkZ = (this.posZ >> 4) - this.chunkHeight / 2;

         for(int z = 0; z < this.chunkHeight; ++z) {
            for(int x = 0; x < this.chunkWidth; ++x) {
               Chunk chunk = world.func_72964_e(x + chunkX, z + chunkZ);
               if(!chunk.func_76621_g()) {
                  for(int z1 = 0; z1 < 16; ++z1) {
                     for(int x1 = 0; x1 < 16; ++x1) {
                        boolean isTransparent = true;
                        int y = 256;

                        do {
                           int blockID = chunk.func_76610_a(x1, y, z1);
                           if(blockID != 0) {
                              MapColor mapcolor = Block.field_71973_m[blockID].field_72018_cp.field_76234_F;
                              colors[(z * 16 + z1) * this.chunkWidth * 16 + x * 16 + x1] = (byte)(mapcolor.field_76290_q * 4);
                              isTransparent = false;
                           }

                           --y;
                        } while(y > 1 && isTransparent);
                     }
                  }
               }
            }
         }

         PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new MinimapPacket(colors)), (Player)player);
      }

   }

   public void fromBytes(ByteArrayDataInput data) {
      this.posX = data.readInt();
      this.posZ = data.readInt();
      this.chunkWidth = data.readInt();
      this.chunkHeight = data.readInt();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeInt(this.posX);
      data.writeInt(this.posZ);
      data.writeInt(this.chunkWidth);
      data.writeInt(this.chunkHeight);
   }

}
