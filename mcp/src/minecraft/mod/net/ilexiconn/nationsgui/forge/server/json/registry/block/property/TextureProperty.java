package net.ilexiconn.nationsgui.forge.server.json.registry.block.property;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.JSONBlock;
import net.ilexiconn.nationsgui.forge.server.json.registry.block.property.TextureProperty$1;
import org.bouncycastle.util.encoders.Base64;

@SideOnly(Side.CLIENT)
public class TextureProperty implements JSONProperty<JSONBlock>
{
    private Gson gson = new Gson();
    private File parent = new File(".", "nationsgui");

    public boolean isApplicable(String name, JsonElement element, JSONBlock block)
    {
        return name.equals("texture");
    }

    public void setProperty(String name, JsonElement element, JSONBlock block)
    {
        Map map = (Map)this.gson.fromJson(element, (new TextureProperty$1(this)).getType());
        Iterator var5 = map.entrySet().iterator();

        while (var5.hasNext())
        {
            Entry entry = (Entry)var5.next();
            byte[] data = Base64.decode(((String)entry.getValue()).substring(((String)entry.getValue()).indexOf(",") + 1));
            String hash = Hashing.sha1().hashBytes(data).toString();
            File imageFile = new File(this.parent, hash.substring(0, 2) + File.separator + hash);

            if (!imageFile.exists())
            {
                imageFile.getParentFile().mkdirs();

                try
                {
                    BufferedImage s = ImageIO.read(new ByteArrayInputStream(data));
                    ImageIO.write(s, "png", imageFile);
                }
                catch (IOException var11)
                {
                    var11.printStackTrace();
                }
            }

            String s1 = (String)entry.getKey();

            if (!s1.equals("all") && !s1.equals("side"))
            {
                if (s1.equals("top"))
                {
                    block.textureTopString = "top/" + block.getUnlocalizedName() + ":" + hash;
                }
                else if (s1.equals("bottom"))
                {
                    block.textureBottomString = "bottom/" + block.getUnlocalizedName() + ":" + hash;
                }
                else if (s1.equals("front"))
                {
                    block.textureFrontString = "front/" + block.getUnlocalizedName() + ":" + hash;
                }
                else if (s1.equals("back"))
                {
                    block.textureBackString = "back/" + block.getUnlocalizedName() + ":" + hash;
                }
            }
            else
            {
                block.textureSideString = "side/" + block.getUnlocalizedName() + ":" + hash;
            }
        }
    }

    public void setProperty(String var1, JsonElement var2, Object var3)
    {
        this.setProperty(var1, var2, (JSONBlock)var3);
    }

    public boolean isApplicable(String var1, JsonElement var2, Object var3)
    {
        return this.isApplicable(var1, var2, (JSONBlock)var3);
    }
}
