package net.ilexiconn.nationsgui.forge.client.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.data.MetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import org.apache.commons.io.IOUtils;

public class LocalResource implements Resource
{
    private final InputStream mainFileSteam;
    private InputStream mcmetaInputStream = null;
    private final MetadataSerializer srMetadataSerializer;
    private boolean mcmetaJsonChecked;
    private JsonObject mcmetaJson;

    public LocalResource(File file) throws IOException
    {
        this.mainFileSteam = Files.newInputStream(file.toPath(), new OpenOption[0]);
        File metaFile = new File(file.getAbsoluteFile() + ".mcmeta");

        if (metaFile.exists())
        {
            this.mcmetaInputStream = Files.newInputStream(metaFile.toPath(), new OpenOption[0]);
        }

        this.srMetadataSerializer = (MetadataSerializer)ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), new String[] {"metadataSerializer_", "metadataSerializer_", "ap"});
    }

    public InputStream getInputStream()
    {
        return this.mainFileSteam;
    }

    public boolean hasMetadata()
    {
        return this.mcmetaInputStream != null;
    }

    public MetadataSection getMetadata(String s)
    {
        if (!this.hasMetadata())
        {
            return null;
        }
        else
        {
            if (this.mcmetaJson == null && !this.mcmetaJsonChecked)
            {
                this.mcmetaJsonChecked = true;
                BufferedReader bufferedreader = null;

                try
                {
                    bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
                    this.mcmetaJson = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
                }
                finally
                {
                    IOUtils.closeQuietly(bufferedreader);
                }
            }

            return this.srMetadataSerializer.parseMetadataSection(s, this.mcmetaJson);
        }
    }
}
