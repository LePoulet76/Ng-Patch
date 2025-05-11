/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  org.bouncycastle.util.encoders.Base64
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import org.bouncycastle.util.encoders.Base64;

@SideOnly(value=Side.CLIENT)
public class IconProperty
implements JSONProperty<JSONArmorSet> {
    private File parent = new File(".", "nationsgui");

    @Override
    public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet) {
        return element.isJsonObject();
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONArmorSet armorSet) {
        JsonObject object = element.getAsJsonObject();
        if (object.has("icon")) {
            String texture = object.get("icon").getAsString();
            byte[] data = Base64.decode((String)texture.substring(texture.indexOf(",") + 1));
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
            if (name.equals("helmet")) {
                armorSet.getArmorSet()[0].iconHash = "helmet:" + hash;
            } else if (name.equals("chestplate")) {
                armorSet.getArmorSet()[1].iconHash = "chestplate:" + hash;
            } else if (name.equals("leggings")) {
                armorSet.getArmorSet()[2].iconHash = "leggings:" + hash;
            } else if (name.equals("boots")) {
                armorSet.getArmorSet()[3].iconHash = "boots:" + hash;
            }
        }
    }
}

