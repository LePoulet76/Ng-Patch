package net.ilexiconn.nationsgui.forge.client.voices;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.voices.device.Device;
import net.ilexiconn.nationsgui.forge.client.voices.device.DeviceHandler;
import net.ilexiconn.nationsgui.forge.server.voices.MathUtility;
import net.ilexiconn.nationsgui.forge.server.voices.networking.voiceservers.EnumVoiceNetworkType;

@SideOnly(Side.CLIENT)
public class Settings
{
    private final DeviceHandler deviceHandler = new DeviceHandler();
    private boolean debugMode;
    private Device inputDevice;
    private float worldVolume = 1.0F;
    private float uiOpacity = 1.0F;
    private int speakMode = 0;
    private int encodingMode = 0;
    public int minQuality = 0;
    public int maxQuality = 10;
    private float encodingQuality = 1.0F;
    private boolean perceptualEnchantment = true;
    private boolean setupNeeded;
    public int maxSoundDistance = 63;
    private List<String> mutedPlayers = new ArrayList();
    private boolean isVoiceEnable = true;
    Configuration configuration;

    public Settings(File file)
    {
        this.configuration = new Configuration(this, file);
    }

    public void init()
    {
        this.deviceHandler.loadDevices();
        this.configuration.init(this.deviceHandler);
    }

    public Device getInputDevice()
    {
        if (this.inputDevice == null)
        {
            this.inputDevice = this.deviceHandler.getDefaultDevice();
        }

        return this.inputDevice;
    }

    public void setInputDevice(Device loadedDevice)
    {
        this.inputDevice = loadedDevice;
    }

    public float getWorldVolume()
    {
        return this.worldVolume;
    }

    public void setWorldVolume(float worldVolume)
    {
        this.worldVolume = worldVolume;
    }

    public float getUIOpacity()
    {
        return this.uiOpacity;
    }

    public void setUIOpacity(float chatIconOpacity)
    {
        this.uiOpacity = chatIconOpacity;
    }

    public int getSpeakMode()
    {
        return this.speakMode;
    }

    public void setSpeakMode(int speakMode)
    {
        this.speakMode = speakMode;
    }

    public void setDebugMode(boolean debugMode)
    {
        this.debugMode = debugMode;
    }

    public boolean getDebugMode()
    {
        return this.debugMode;
    }

    public DeviceHandler getDeviceHandler()
    {
        return this.deviceHandler;
    }

    public Configuration getConfiguration()
    {
        return this.configuration;
    }

    public float getEncodingQuality()
    {
        return MathUtility.clamp(this.encodingQuality, 0.0F, 1.0F);
    }

    public void setEncodingQuality(float encodingQuality)
    {
        this.encodingQuality = encodingQuality;
    }

    public int getEncodingMode()
    {
        return (int)MathUtility.clamp((float)this.encodingMode, 0.0F, 2.0F);
    }

    public void setEncodingMode(int encodingMode)
    {
        this.encodingMode = encodingMode;
    }

    public String getEncodingModeString()
    {
        String s = "Narrowband";

        switch (this.encodingMode)
        {
            case 0:
                s = "Narrowband";
                break;

            case 1:
                s = "Wideband";
                break;

            case 2:
                s = "Ultrawideband";
        }

        return s;
    }

    public boolean isSetupNeeded()
    {
        return this.setupNeeded;
    }

    public void setSetupNeeded(boolean setupNeeded)
    {
        this.setupNeeded = setupNeeded;
    }

    public int getUDPPort()
    {
        return 54777;
    }

    public int getTCPPort()
    {
        return 54555;
    }

    public EnumVoiceNetworkType getVoiceServerType()
    {
        return EnumVoiceNetworkType.MINECRAFT;
    }

    public float getTimeToAuthenticate()
    {
        return 350.0F;
    }

    public boolean isPerceptualEnchantmentAllowed()
    {
        return this.perceptualEnchantment;
    }

    public void setPerceptualEnchantment(boolean perceptualEnchantment)
    {
        this.perceptualEnchantment = perceptualEnchantment;
    }

    public void setSoundDistance(int soundDist)
    {
        this.maxSoundDistance = soundDist;
    }

    public int getSoundDistance()
    {
        return this.maxSoundDistance;
    }

    public void resetQuality()
    {
        this.minQuality = 0;
        this.maxQuality = 10;
    }

    public void setBandwidthLimit(int x, int x1)
    {
        this.minQuality = x;
        this.maxQuality = x1;
    }

    public void setMutedPlayersString(String property)
    {
        this.mutedPlayers = new ArrayList();
        String[] mutedPlayers = property.split(" ");
        String[] var3 = mutedPlayers;
        int var4 = mutedPlayers.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            String name = var3[var5];
            this.addMutedPlayer(name);
        }
    }

    public boolean removeMutedPlayer(String name)
    {
        return this.mutedPlayers.remove(name);
    }

    public void addMutedPlayer(String name)
    {
        if (!this.isPlayerMuted(name))
        {
            this.mutedPlayers.add(name);
        }
    }

    public boolean isPlayerMuted(String name)
    {
        return this.mutedPlayers.contains(name);
    }

    public String getMutedPlayersString()
    {
        StringBuffer sb = new StringBuffer();

        if (this.mutedPlayers != null && !this.mutedPlayers.isEmpty())
        {
            Iterator var2 = this.mutedPlayers.iterator();

            while (var2.hasNext())
            {
                String s = (String)var2.next();
                sb.append(s + " ");
            }
        }

        return sb.toString();
    }

    public boolean isVoiceEnable()
    {
        return this.isVoiceEnable;
    }

    public void setVoiceEnable(boolean isVoiceEnable)
    {
        this.isVoiceEnable = isVoiceEnable;
    }
}
