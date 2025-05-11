/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.voices.device;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import net.ilexiconn.nationsgui.forge.client.voices.device.Device;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;

public class DeviceHandler {
    private List devices = new ArrayList();

    public List loadDevices() {
        Mixer.Info[] mixers;
        this.devices.clear();
        Mixer.Info[] arr$ = mixers = AudioSystem.getMixerInfo();
        int len$ = mixers.length;
        for (int i$ = 0; i$ < len$; ++i$) {
            Mixer.Info info = arr$[i$];
            Mixer mixer = AudioSystem.getMixer(info);
            try {
                DataLine.Info e = new DataLine.Info(TargetDataLine.class, SoundManager.getUniversalAudioFormat());
                TargetDataLine tdl = (TargetDataLine)mixer.getLine(e);
                if (info == null) continue;
                this.devices.add(new Device(tdl, info));
                continue;
            }
            catch (LineUnavailableException lineUnavailableException) {
                continue;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        return this.devices;
    }

    public Device getDeviceByName(String deviceName) {
        for (int i = 0; i < this.devices.size(); ++i) {
            Device device = (Device)this.devices.get(i);
            if (!device.getName().equals(deviceName)) continue;
            return device;
        }
        return null;
    }

    public Device getDefaultDevice() {
        TargetDataLine line;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, SoundManager.getUniversalAudioFormat());
        if (!AudioSystem.isLineSupported(info)) {
            return null;
        }
        try {
            line = (TargetDataLine)AudioSystem.getLine(info);
        }
        catch (Exception var4) {
            return null;
        }
        return line != null ? this.getDeviceByLine(line) : null;
    }

    public boolean isEmpty() {
        return this.devices.isEmpty();
    }

    private Device getDeviceByLine(TargetDataLine line) {
        for (int i = 0; i < this.devices.size(); ++i) {
            Device device = (Device)this.devices.get(i);
            if (!device.getLine().getLineInfo().equals(line.getLineInfo())) continue;
            return device;
        }
        return null;
    }

    public List getDevices() {
        return this.devices;
    }
}

