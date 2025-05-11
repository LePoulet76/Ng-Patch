/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.Hashing
 *  com.google.gson.JsonElement
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.entity.RenderBiped
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.util.ResourceLocation
 *  org.bouncycastle.util.encoders.Base64
 */
package net.ilexiconn.nationsgui.forge.server.json.registry.armor.property;

import com.google.common.hash.Hashing;
import com.google.gson.JsonElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.server.json.registry.JSONProperty;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmor;
import net.ilexiconn.nationsgui.forge.server.json.registry.armor.JSONArmorSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.bouncycastle.util.encoders.Base64;

@SideOnly(value=Side.CLIENT)
public class TextureProperty
implements JSONProperty<JSONArmorSet> {
    private File parent = new File(".", "nationsgui");
    private Field field;

    public TextureProperty() {
        try {
            this.field = RenderBiped.class.getDeclaredField("field_110859_k");
            this.field.setAccessible(true);
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isApplicable(String name, JsonElement element, JSONArmorSet armorSet) {
        return name.equals("texture") || name.equals("texture_overlay");
    }

    @Override
    public void setProperty(String name, JsonElement element, JSONArmorSet armorSet) {
        String texture = element.getAsString();
        byte[] data = Base64.decode((String)texture.substring(texture.indexOf(",") + 1));
        String hash = Hashing.sha1().hashBytes(data).toString();
        File imageFile = new File(this.parent, hash.substring(0, 2) + File.separator + hash);
        BufferedImage image = null;
        if (!imageFile.exists()) {
            imageFile.getParentFile().mkdirs();
            try {
                image = ImageIO.read(new ByteArrayInputStream(data));
                ImageIO.write((RenderedImage)image, "png", imageFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                image = ImageIO.read(imageFile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < armorSet.getArmorSet().length; ++i) {
            JSONArmor armor = armorSet.getArmorSet()[i];
            if (armor == null) continue;
            if (name.equals("texture_overlay")) {
                armor.textureOverlayHash = "nationsgui/armor/" + hash;
                continue;
            }
            armor.textureHash = "nationsgui/armor/" + hash;
        }
        ResourceLocation location = Minecraft.func_71410_x().func_110434_K().func_110578_a("nationsgui/" + hash, new DynamicTexture(image));
        try {
            ((Map)this.field.get(null)).put("nationsgui/armor/" + hash, location);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

