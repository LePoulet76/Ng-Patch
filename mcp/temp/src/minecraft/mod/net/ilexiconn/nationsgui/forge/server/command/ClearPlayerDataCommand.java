package net.ilexiconn.nationsgui.forge.server.command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.storage.SaveHandler;

public class ClearPlayerDataCommand extends CommandBase {

   public String func_71517_b() {
      return "clearplayerdata";
   }

   public String func_71518_a(ICommandSender iCommandSender) {
      return "[potions] [player]";
   }

   public void func_71515_b(ICommandSender iCommandSender, String[] strings) {
      if(strings.length < 2) {
         throw new CommandException("wrongFormat", new Object[0]);
      } else {
         SaveHandler saveHandler = (SaveHandler)MinecraftServer.func_71276_C().func_130014_f_().func_72860_G();
         NBTTagCompound nbtTagCompound = saveHandler.func_75764_a(strings[1]);
         if(nbtTagCompound == null) {
            iCommandSender.func_70006_a(ChatMessageComponent.func_111066_d("Player not found"));
         } else {
            if(strings[0].equals("potions")) {
               nbtTagCompound.func_74782_a("ActiveEffects", new NBTTagList());
            }

            this.writePlayerData(new File(saveHandler.func_75765_b(), "players"), nbtTagCompound, strings[1]);
         }
      }
   }

   public int compareTo(Object o) {
      return 0;
   }

   public boolean func_82358_a(String[] strings, int i) {
      return i == 1;
   }

   private void writePlayerData(File playersDirectory, NBTTagCompound nbttagcompound, String playerName) {
      try {
         File exception = new File(playersDirectory, playerName + ".dat.tmp");
         File file2 = new File(playersDirectory, playerName + ".dat");
         CompressedStreamTools.func_74799_a(nbttagcompound, Files.newOutputStream(exception.toPath(), new OpenOption[0]));
         if(file2.exists()) {
            file2.delete();
         }

         exception.renameTo(file2);
      } catch (Exception var6) {
         var6.printStackTrace();
         MinecraftServer.func_71276_C().func_98033_al().func_98236_b("Failed to save player data for " + playerName);
      }

   }
}
