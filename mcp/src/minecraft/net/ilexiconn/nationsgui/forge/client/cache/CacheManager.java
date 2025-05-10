package net.ilexiconn.nationsgui.forge.client.cache;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;

public class CacheManager
{
    private final File cacheDir = new File(".", "/nationsgui/cache/");
    public final HashMap<String, File> cacheFiles = new HashMap();

    public CacheManager()
    {
        if (!this.cacheDir.exists())
        {
            this.cacheDir.mkdir();
        }
    }

    public File downloadAndStockInCache(String strUrl, String name) throws IOException
    {
        File file = new File(this.cacheDir, name);
        URL url = new URL(strUrl);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0 NationsCacher/1.0");
        conn.connect();
        FileUtils.copyInputStreamToFile(conn.getInputStream(), file);
        this.cacheFiles.put(name, file);
        return file;
    }

    public File getCachedFileFromName(String name) throws Exception
    {
        return this.isCacheExist(name) ? (File)this.cacheFiles.get(name) : null;
    }

    public boolean isCacheExist(String name)
    {
        return this.cacheFiles.containsKey(name);
    }

    public File getCacheFile()
    {
        return this.cacheDir;
    }

    public void removeFromCache(String name)
    {
        if (this.cacheFiles.containsKey(name))
        {
            this.cacheFiles.remove(name);
        }
    }
}
