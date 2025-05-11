/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagByte
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagFloat
 */
package net.ilexiconn.nationsgui.forge.server.config;

import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;

public class NBTConfig {
    public static final File FILE = new File(".", "nationsgui.dat");
    public static final NBTConfig CONFIG = NBTConfig.get();
    private NBTTagCompound compound;

    public static NBTConfig get() {
        NBTConfig config = new NBTConfig();
        try {
            config.compound = CompressedStreamTools.func_74797_a((File)FILE);
        }
        catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
        if (config.compound == null) {
            config.compound = new NBTTagCompound();
        }
        config.setIfNull("RadioVolume", (NBTBase)new NBTTagFloat("RadioVolume", 100.0f));
        config.setIfNull("RadioVolume", (NBTBase)new NBTTagFloat("BrowserVolume", 100.0f));
        config.setIfNull("DatedScreenshot", (NBTBase)new NBTTagByte("DatedScreenshot", 0));
        config.setIfNull("Buddies", (NBTBase)new NBTTagCompound("Buddies"));
        config.setIfNull("Aliases", (NBTBase)new NBTTagCompound("Aliases"));
        config.setIfNull("Badges", (NBTBase)new NBTTagCompound("Badges"));
        config.setIfNull("FactionChest", (NBTBase)new NBTTagCompound("FactionChest"));
        return config;
    }

    private void setIfNull(String key, NBTBase value) {
        if (!this.compound.func_74764_b(key)) {
            this.compound.func_74782_a(key, value);
        }
    }

    public NBTTagCompound getCompound() {
        return this.compound;
    }

    public void save() {
        try {
            if (!FILE.exists()) {
                FILE.createNewFile();
            }
            CompressedStreamTools.func_74795_b((NBTTagCompound)this.compound, (File)FILE);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

