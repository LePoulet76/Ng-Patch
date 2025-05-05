package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.nio.IntBuffer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.UploadScreenPacket$Screenshoter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class UploadScreenPacket implements IPacket, IClientPacket {

   private static IntBuffer buffer;
   private static int[] array;
   private String allocatorId;


   public UploadScreenPacket(String allocatorId) {
      this.allocatorId = allocatorId;
   }

   public void fromBytes(ByteArrayDataInput data) {
      this.allocatorId = data.readUTF();
   }

   public void toBytes(ByteArrayDataOutput data) {
      data.writeUTF(this.allocatorId);
   }

   public void handleClientPacket(EntityPlayer player) {
      try {
         Minecraft exception = Minecraft.func_71410_x();
         int k = exception.field_71443_c * exception.field_71440_d;
         if(buffer == null || buffer.capacity() < k) {
            buffer = BufferUtils.createIntBuffer(k);
            array = new int[k];
         }

         GL11.glPixelStorei(3333, 1);
         GL11.glPixelStorei(3317, 1);
         buffer.clear();
         GL11.glReadPixels(0, 0, exception.field_71443_c, exception.field_71440_d, '\u80e1', '\u8367', buffer);
         buffer.get(array);
         Thread screenshot = new Thread(new UploadScreenPacket$Screenshoter(this.allocatorId, array));
         screenshot.setDaemon(true);
         screenshot.start();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }
}
