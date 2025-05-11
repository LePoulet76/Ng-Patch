/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  cpw.mods.fml.common.ObfuscationReflectionHelper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.Resource
 *  net.minecraft.client.resources.data.MetadataSection
 *  net.minecraft.client.resources.data.MetadataSerializer
 *  org.apache.commons.io.IOUtils
 */
package net.ilexiconn.nationsgui.forge.client.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.data.MetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import org.apache.commons.io.IOUtils;

public class LocalResource
implements Resource {
    private final InputStream mainFileSteam;
    private InputStream mcmetaInputStream = null;
    private final MetadataSerializer srMetadataSerializer;
    private boolean mcmetaJsonChecked;
    private JsonObject mcmetaJson;

    public LocalResource(File file) throws IOException {
        this.mainFileSteam = Files.newInputStream(file.toPath(), new OpenOption[0]);
        File metaFile = new File(file.getAbsoluteFile() + ".mcmeta");
        if (metaFile.exists()) {
            this.mcmetaInputStream = Files.newInputStream(metaFile.toPath(), new OpenOption[0]);
        }
        this.srMetadataSerializer = (MetadataSerializer)ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, (Object)Minecraft.func_71410_x(), (String[])new String[]{"metadataSerializer_", "field_110452_an", "ap"});
    }

    public InputStream func_110527_b() {
        return this.mainFileSteam;
    }

    public boolean func_110528_c() {
        return this.mcmetaInputStream != null;
    }

    public MetadataSection func_110526_a(String s) {
        if (!this.func_110528_c()) {
            return null;
        }
        if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
            this.mcmetaJsonChecked = true;
            BufferedReader bufferedreader = null;
            try {
                bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
                this.mcmetaJson = new JsonParser().parse((Reader)bufferedreader).getAsJsonObject();
            }
            catch (Throwable throwable) {
                IOUtils.closeQuietly(bufferedreader);
                throw throwable;
            }
            IOUtils.closeQuietly((Reader)bufferedreader);
        }
        return this.srMetadataSerializer.func_110503_a(s, this.mcmetaJson);
    }
}

