package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import org.bouncycastle.util.encoders.Base64;

@SideOnly(Side.CLIENT)
public class IconProperty implements JSONProperty<JSONArmorSet>
{
    private File parent = new File(".", "nationsgui");

    public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet)
    {
        return element.isJsonObject();
    }

    public void setProperty(String name, JsonElement element, JSONArmorSet armorSet)
    {
        JsonObject object = element.getAsJsonObject();

        if (object.has("icon"))
        {
            String texture = object.get("icon").getAsString();
            byte[] data = Base64.decode(texture.substring(texture.indexOf(",") + 1));
            String hash = Hashing.sha1().hashBytes(data).toString();
            File imageFile = new File(this.parent, hash.substring(0, 2) + File.separator + hash);

            if (!imageFile.exists())
            {
                imageFile.getParentFile().mkdirs();

                try
                {
                    BufferedImage e = ImageIO.read(new ByteArrayInputStream(data));
                    ImageIO.write(e, "png", imageFile);
                }
                catch (IOException var10)
                {
                    var10.printStackTrace();
                }
            }

            if (name.equals("helmet"))
            {
                armorSet.getArmorSet()[0].iconHash = "helmet:" + hash;
            }
            else if (name.equals("chestplate"))
            {
                armorSet.getArmorSet()[1].iconHash = "chestplate:" + hash;
            }
            else if (name.equals("leggings"))
            {
                armorSet.getArmorSet()[2].iconHash = "leggings:" + hash;
            }
            else if (name.equals("boots"))
            {
                armorSet.getArmorSet()[3].iconHash = "boots:" + hash;
            }
        }
    }

    public void setProperty(String var1, JsonElement var2, Object var3)
    {
        this.setProperty(var1, var2, (JSONArmorSet)var3);
    }

    public boolean isApplicable(String var1, JsonElement var2, Object var3)
    {
        return this.isApplicable(var1, var2, (JSONArmorSet)var3);
    }
}
