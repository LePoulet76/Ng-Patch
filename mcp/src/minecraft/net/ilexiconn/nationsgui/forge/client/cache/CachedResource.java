/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.cache;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;

public class CachedResource {
    private URL url;
    protected File file;

    public CachedResource(String url) {
        if (url == "" || url == null) {
            return;
        }
        try {
            this.url = new URL(url);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            this.file = ClientProxy.getCacheManager().downloadAndStockInCache(url, CachedResource.MD5(url.toString()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public URL getURL() {
        return this.url;
    }

    public File getFile() {
        return this.file;
    }

    public static String MD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString(array[i] & 0xFF | 0x100).substring(1, 3));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return "";
        }
    }

    public String getName() {
        return CachedResource.MD5(this.url.toString());
    }
}

