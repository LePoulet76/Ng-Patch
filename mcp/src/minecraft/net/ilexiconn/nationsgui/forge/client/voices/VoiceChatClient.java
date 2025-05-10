package net.ilexiconn.nationsgui.forge.client.voices;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLStateEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import net.ilexiconn.nationsgui.forge.client.voices.debug.Statistics;
import net.ilexiconn.nationsgui.forge.client.voices.keybindings.KeyManager;
import net.ilexiconn.nationsgui.forge.client.voices.networking.ClientNetwork;
import net.ilexiconn.nationsgui.forge.client.voices.networking.game.ClientPlayerTracker;
import net.ilexiconn.nationsgui.forge.client.voices.sound.PlayableStream;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChatServer;
import net.minecraft.client.Minecraft;

public class VoiceChatClient extends VoiceChatServer
{
    @SideOnly(Side.CLIENT)
    private File configurationDirectory;
    @SideOnly(Side.CLIENT)
    private Settings settings;
    @SideOnly(Side.CLIENT)
    public KeyManager keyManager;
    @SideOnly(Side.CLIENT)
    private static SoundManager soundManager;
    @SideOnly(Side.CLIENT)
    private ClientNetwork clientNetwork;
    @SideOnly(Side.CLIENT)
    private static Statistics stats;
    @SideOnly(Side.CLIENT)
    private boolean recorderActive;
    @SideOnly(Side.CLIENT)
    public static List<PlayableStream> activeStreams = new ArrayList();
    VoiceChat voiceChat;

    public void initClient(VoiceChat voiceChat, FMLStateEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        (new UpdatedSoundManager(this, mc.sndManager)).init();
        this.voiceChat = voiceChat;
        this.keyManager.init();

        if (this.settings.getDebugMode())
        {
            stats = new Statistics();
        }

        VoiceChat.getLogger().info("Started client-side on version (" + this.getVersion() + ")");
        this.clientNetwork = new ClientNetwork(this);
        GameRegistry.registerPlayerTracker(new ClientPlayerTracker(this));
    }

    public void preInitClient(FMLPreInitializationEvent event)
    {
        this.configurationDirectory = new File(event.getModConfigurationDirectory(), "gvc");

        if (!this.configurationDirectory.exists())
        {
            this.configurationDirectory.mkdir();
        }

        this.settings = new Settings(new File(this.configurationDirectory, "config.properties"));
        this.settings.init();
        this.keyManager = new KeyManager(this);
        soundManager = new SoundManager(Minecraft.getMinecraft(), this);
        soundManager.init();
    }

    public Settings getSettings()
    {
        return this.settings;
    }

    public static Statistics getStatistics()
    {
        return stats;
    }

    public static SoundManager getSoundManager()
    {
        return soundManager;
    }

    public ClientNetwork getClientNetwork()
    {
        return this.clientNetwork;
    }

    public static synchronized Logger getLogger()
    {
        Logger l = Logger.getLogger("Voice Chat Mod");
        l.setParent(FMLLog.getLogger());
        return l;
    }

    public boolean isRecorderActive()
    {
        return this.recorderActive;
    }

    public void setRecorderActive(boolean b)
    {
        if (this.clientNetwork.voiceClientExists())
        {
            this.recorderActive = b;
        }
    }

    public String getShortVersion()
    {
        return this.getVersion().replaceAll("\\.", "");
    }
}
