package net.ilexiconn.nationsgui.forge.client.voices.device;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Mixer.Info;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;

public class DeviceHandler
{
    private List devices = new ArrayList();

    public List loadDevices()
    {
        this.devices.clear();
        Info[] mixers = AudioSystem.getMixerInfo();
        Info[] arr$ = mixers;
        int len$ = mixers.length;

        for (int i$ = 0; i$ < len$; ++i$)
        {
            Info info = arr$[i$];
            Mixer mixer = AudioSystem.getMixer(info);

            try
            {
                javax.sound.sampled.DataLine.Info e = new javax.sound.sampled.DataLine.Info(TargetDataLine.class, SoundManager.getUniversalAudioFormat());
                TargetDataLine tdl = (TargetDataLine)mixer.getLine(e);

                if (info != null)
                {
                    this.devices.add(new Device(tdl, info));
                }
            }
            catch (LineUnavailableException var9)
            {
                ;
            }
            catch (IllegalArgumentException var10)
            {
                ;
            }
        }

        return this.devices;
    }

    public Device getDeviceByName(String deviceName)
    {
        for (int i = 0; i < this.devices.size(); ++i)
        {
            Device device = (Device)this.devices.get(i);

            if (device.getName().equals(deviceName))
            {
                return device;
            }
        }

        return null;
    }

    public Device getDefaultDevice()
    {
        javax.sound.sampled.DataLine.Info info = new javax.sound.sampled.DataLine.Info(TargetDataLine.class, SoundManager.getUniversalAudioFormat());

        if (!AudioSystem.isLineSupported(info))
        {
            return null;
        }
        else
        {
            TargetDataLine line;

            try
            {
                line = (TargetDataLine)AudioSystem.getLine(info);
            }
            catch (Exception var4)
            {
                return null;
            }

            return line != null ? this.getDeviceByLine(line) : null;
        }
    }

    public boolean isEmpty()
    {
        return this.devices.isEmpty();
    }

    private Device getDeviceByLine(TargetDataLine line)
    {
        for (int i = 0; i < this.devices.size(); ++i)
        {
            Device device = (Device)this.devices.get(i);

            if (device.getLine().getLineInfo().equals(line.getLineInfo()))
            {
                return device;
            }
        }

        return null;
    }

    public List getDevices()
    {
        return this.devices;
    }
}
