/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.google.common.reflect.TypeToken
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.bouncycastle.util.encoders.Base64
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.common.hash.Hashing;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import org.bouncycastle.util.encoders.Base64;

@SideOnly(value=Side.CLIENT)
public class TextureProperty
implements JSONProperty<JSONBlock> {
    private Gson gson = new Gson();
    private File parent = new File(".", "nationsgui");

    @Override
    public boolean isApplicable(String name, JsonElement element, JSONBlock block) {
        return name.equals("texture");
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONBlock block) {
        Map map = (Map)this.gson.fromJson(element, new TypeToken<Map<String, String>>(){}.getType());
        for (Map.Entry entry : map.entrySet()) {
            String s;
            byte[] data = Base64.decode((String)((String)entry.getValue()).substring(((String)entry.getValue()).indexOf(",") + 1));
            String hash = Hashing.sha1().hashBytes(data).toString();
            File imageFile = new File(this.parent, hash.substring(0, 2) + File.separator + hash);
            if (!imageFile.exists()) {
                imageFile.getParentFile().mkdirs();
                try {
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
                    ImageIO.write((RenderedImage)image, "png", imageFile);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ((s = (String)entry.getKey()).equals("all") || s.equals("side")) {
                block.textureSideString = "side/" + block.func_71917_a() + ":" + hash;
                continue;
            }
            if (s.equals("top")) {
                block.textureTopString = "top/" + block.func_71917_a() + ":" + hash;
                continue;
            }
            if (s.equals("bottom")) {
                block.textureBottomString = "bottom/" + block.func_71917_a() + ":" + hash;
                continue;
            }
            if (s.equals("front")) {
                block.textureFrontString = "front/" + block.func_71917_a() + ":" + hash;
                continue;
            }
            if (!s.equals("back")) continue;
            block.textureBackString = "back/" + block.func_71917_a() + ":" + hash;
        }
    }
}

