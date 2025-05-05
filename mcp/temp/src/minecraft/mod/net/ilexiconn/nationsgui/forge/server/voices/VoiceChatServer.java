package net.ilexiconn.nationsgui.forge.server.voices;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLStateEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Logger;
import net.ilexiconn.nationsgui.forge.server.voices.ServerSettings;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.ilexiconn.nationsgui.forge.server.voices.commands.VoiceGlobalMuteCommand;
import net.ilexiconn.nationsgui.forge.server.voices.commands.VoicePlayerMuteCommand;
import net.ilexiconn.nationsgui.forge.server.voices.networking.CommonPlayerTracker;
import net.ilexiconn.nationsgui.forge.server.voices.networking.ServerNetwork;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.ConnectionHandler;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.MinecraftVoiceServer;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.VoiceServer;
import net.minecraft.crash.CallableMinecraftVersion;
import net.minecraft.crash.CrashReport;
import net.minecraft.server.MinecraftServer;

public class VoiceChatServer {

   public static final int VOICESERVER_UDP = 5447;
   private static final String VERSION = "0.4.0";
   private static final String MC_VERSION = (new CallableMinecraftVersion((CrashReport)null)).func_71493_a();
   private VoiceServer voiceServer;
   private Thread voiceServerThread;
   private ServerNetwork serverNetwork;
   private ServerSettings settings;
   private File configurationDirectory;


   public static synchronized Logger getLogger() {
      Logger l = Logger.getLogger("Voice Chat Mod");
      l.setParent(FMLLog.getLogger());
      return l;
   }

   public static String getMinecraftVersion() {
      return MC_VERSION;
   }

   public void initServer(FMLServerStartingEvent event) {
      GameRegistry.registerPlayerTracker(new CommonPlayerTracker(this));
      NetworkRegistry.instance().registerConnectionHandler(new ConnectionHandler(this));
      this.voiceServerThread = this.startVoiceServer();
      getLogger().info("Voice Chat Mod for Forge Server has started.");
      this.settings = new ServerSettings(this);
      if(MinecraftServer.func_71276_C().func_71262_S()) {
         try {
            Field file = MinecraftServer.class.getField("anvilFile");
            file.setAccessible(true);
            File e = (File)file.get(MinecraftServer.func_71276_C());
         } catch (Exception var5) {
            ;
         }

         this.configurationDirectory = new File("config", "vc");
         if(!this.configurationDirectory.exists()) {
            this.configurationDirectory.mkdir();
         }

         File file1 = new File(this.configurationDirectory, "server-config.properties");
         if(!file1.exists()) {
            try {
               file1.createNewFile();
            } catch (IOException var4) {
               var4.printStackTrace();
            }
         }

         this.settings.init(file1);
      }

      event.registerServerCommand(new VoiceGlobalMuteCommand());
      event.registerServerCommand(new VoicePlayerMuteCommand());
   }

   private Thread startVoiceServer() {
      this.serverNetwork = new ServerNetwork(this);
      this.serverNetwork.init();
      this.voiceServer = new MinecraftVoiceServer(this, EnumVoiceNetworkType.MINECRAFT);
      Thread thread = new Thread(this.voiceServer, "Voice Server");
      thread.start();
      VoiceChat.getLogger().info("Started [" + this.voiceServer.getType().name + "] Server.");
      return thread;
   }

   public synchronized VoiceServer getVoiceServer() {
      return this.voiceServer;
   }

   public synchronized ServerNetwork getServerNetwork() {
      return this.serverNetwork;
   }

   public void initClient(VoiceChat voiceChat, FMLStateEvent event) {}

   public void preInitServer(FMLServerAboutToStartEvent event) {}

   public String getVersion() {
      return "0.4.0";
   }

   public void preInitClient(FMLPreInitializationEvent event) {}

   public ServerSettings getServerSettings() {
      return this.settings;
   }

}
