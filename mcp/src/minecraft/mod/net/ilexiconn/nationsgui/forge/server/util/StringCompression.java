package net.ilexiconn.nationsgui.forge.server.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringCompression
{
    public static byte[] compress(String str) throws Exception
    {
        if (str != null && str.length() != 0)
        {
            ByteArrayOutputStream obj = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(obj);
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
            gzip.close();
            return obj.toByteArray();
        }
        else
        {
            return null;
        }
    }

    public static String decompress(byte[] str) throws Exception
    {
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
        String outStr;
        String line;

        for (outStr = ""; (line = bf.readLine()) != null; outStr = outStr + line)
        {
            ;
        }

        return outStr;
    }
}
