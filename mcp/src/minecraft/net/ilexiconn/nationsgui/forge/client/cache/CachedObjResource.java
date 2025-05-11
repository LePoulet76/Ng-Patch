/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.model.IModelCustom
 *  net.minecraftforge.client.model.ModelFormatException
 *  net.minecraftforge.client.model.obj.ObjModelLoader
 */
package net.ilexiconn.nationsgui.forge.client.cache;

import java.net.MalformedURLException;
import net.ilexiconn.nationsgui.forge.client.cache.CachedResource;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;
import net.minecraftforge.client.model.obj.ObjModelLoader;

public class CachedObjResource
extends CachedResource {
    public CachedObjResource(String url) {
        super(url);
    }

    public IModelCustom getModel() {
        try {
            return new ObjModelLoader().loadInstance(this.file.getName(), this.file.toURL());
        }
        catch (MalformedURLException | ModelFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}

