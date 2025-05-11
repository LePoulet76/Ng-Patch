/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 */
package net.ilexiconn.nationsgui.forge.client.cache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;

public class CacheManager {
    private final File cacheDir;
    public final HashMap<String, File> cacheFiles = new HashMap();

    public CacheManager() {
        this.cacheDir = new File(".", "/nationsgui/cache/");
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdir();
        }
    }

    public File downloadAndStockInCache(String strUrl, String name) throws IOException {
        File file = new File(this.cacheDir, name);
        URL url = new URL(strUrl);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0 NationsCacher/1.0");
        conn.connect();
        FileUtils.copyInputStreamToFile((InputStream)conn.getInputStream(), (File)file);
        this.cacheFiles.put(name, file);
        return file;
    }

    public File getCachedFileFromName(String name) throws Exception {
        if (this.isCacheExist(name)) {
            return this.cacheFiles.get(name);
        }
        return null;
    }

    public boolean isCacheExist(String name) {
        return this.cacheFiles.containsKey(name);
    }

    public File getCacheFile() {
        return this.cacheDir;
    }

    public void removeFromCache(String name) {
        if (this.cacheFiles.containsKey(name)) {
            this.cacheFiles.remove(name);
        }
    }
}

