/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 */
package net.ilexiconn.nationsgui.forge.client.voices;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import net.ilexiconn.nationsgui.forge.client.voices.Settings;
import net.ilexiconn.nationsgui.forge.client.voices.device.DeviceHandler;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;

@SideOnly(value=Side.CLIENT)
public class Configuration {
    private Properties properties = new Properties();
    private final File file;
    private Settings settings;

    public Configuration(Settings settings, File file) {
        this.settings = settings;
        this.file = file;
    }

    private boolean load(DeviceHandler handler) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(this.file);
            this.properties.load(is);
            if (handler.getDefaultDevice() != null) {
                this.settings.setInputDevice(handler.getDeviceByName(this.properties.getProperty("mic_name", handler.getDefaultDevice().getName())));
            }
            this.settings.setWorldVolume(Float.parseFloat(this.properties.getProperty("world_volume")));
            this.settings.setUIOpacity(Float.parseFloat(this.properties.getProperty("ui_opa")));
            this.settings.setSpeakMode(Integer.parseInt(this.properties.getProperty("speak_mode")));
            this.settings.setDebugMode(Boolean.parseBoolean(this.properties.getProperty("debug_enabled")));
            this.settings.setEncodingQuality(Float.parseFloat(this.properties.getProperty("enc_quality")));
            this.settings.setEncodingMode(Integer.parseInt(this.properties.getProperty("enc_mode")));
            this.settings.setPerceptualEnchantment(Boolean.parseBoolean(this.properties.getProperty("enc_ench")));
            this.settings.setMutedPlayersString(this.properties.getProperty("muted_players"));
            this.settings.setVoiceEnable(Boolean.parseBoolean(this.properties.getProperty("is_voice_enable") != null ? this.properties.getProperty("is_voice_enable") : "true"));
            return true;
        }
        catch (Exception var4) {
            return false;
        }
    }

    public boolean save() {
        try {
            this.properties.setProperty("save-version", VoiceChat.getProxyInstance().getVersion());
            if (this.settings.getInputDevice().getName() != null) {
                this.properties.setProperty("mic_name", this.settings.getInputDevice().getName());
            }
            this.properties.setProperty("world_volume", "" + this.settings.getWorldVolume());
            this.properties.setProperty("speak_mode", "" + this.settings.getSpeakMode());
            this.properties.setProperty("ui_opa", "" + this.settings.getUIOpacity());
            this.properties.setProperty("debug_enabled", "" + this.settings.getDebugMode());
            this.properties.setProperty("enc_quality", "" + this.settings.getEncodingQuality());
            this.properties.setProperty("enc_mode", "" + this.settings.getEncodingMode());
            this.properties.setProperty("enc_ench", "" + this.settings.isPerceptualEnchantmentAllowed());
            this.properties.setProperty("muted_players", this.settings.getMutedPlayersString());
            this.properties.setProperty("is_voice_enable", "" + this.settings.isVoiceEnable());
            FileOutputStream e = new FileOutputStream(this.file);
            this.properties.store(e, "Properties for Voice Chat Mod for Forge, VERSION: " + VoiceChat.getProxyInstance().getVersion());
            return true;
        }
        catch (Exception var3) {
            return false;
        }
    }

    public void init(DeviceHandler deviceHandler) {
        if (!this.load(deviceHandler)) {
            VoiceChat.getLogger().info("No Configuration file found, will create one with default settings.");
            this.settings.setSetupNeeded(true);
            if (this.save()) {
                VoiceChat.getLogger().info("Created Configuration file with default settings.");
            }
        }
    }
}

