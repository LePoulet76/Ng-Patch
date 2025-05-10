package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.util.Tuple;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class ArmorSimpleSkin extends AbstractSkin
{
    private final HashMap<Integer, ResourceLocation[]> textureMap = new HashMap();
    private final List<Tuple<Integer, ResourceLocation[]>> renderList = new ArrayList();
    protected ModelBiped field_82423_g;
    protected ModelBiped field_82425_h;

    public ArmorSimpleSkin(JSONObject object)
    {
        super(object);

        if (object.containsKey("items"))
        {
            Iterator var2 = ((JSONObject)object.get("items")).entrySet().iterator();

            while (var2.hasNext())
            {
                Object o = var2.next();

                try
                {
                    Entry e = (Entry)o;
                    String textureName = (String)e.getValue();
                    int itemID = Integer.parseInt((String)e.getKey());
                    ResourceLocation[] locations = new ResourceLocation[] {this.getResourceTexture(textureName + "_layer_1"), this.getResourceTexture(textureName + "_layer_2")};
                    this.textureMap.put(Integer.valueOf(itemID), locations);
                    ItemStack itemStack = new ItemStack(itemID, 1, 0);

                    if (itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor)
                    {
                        ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
                        this.renderList.add(new Tuple(Integer.valueOf(itemArmor.armorType), locations));
                    }
                }
                catch (Exception var10)
                {
                    var10.printStackTrace();
                }
            }
        }

        this.field_82423_g = new ModelBiped(1.0F);
        this.field_82425_h = new ModelBiped(0.5F);
    }

    private ResourceLocation getResourceTexture(String textureName) throws IOException
    {
        ResourceLocation resourceLocation = new ResourceLocation("skins/armors/" + textureName);

        if (Minecraft.getMinecraft().getTextureManager().getTexture(resourceLocation) == null)
        {
            BufferedImage textureBuffer = ImageIO.read(new File("assets/textures/armorskins/" + textureName + ".png"));
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, new DynamicTexture(textureBuffer));
        }

        return resourceLocation;
    }

    protected void render(float partialTick)
    {
        GL11.glTranslatef(-0.63F, 0.8F, 0.0F);
        Iterator var2 = this.renderList.iterator();

        while (var2.hasNext())
        {
            Tuple tuple = (Tuple)var2.next();
            this.renderItem(((Integer)tuple.a).intValue(), (ResourceLocation[])tuple.b);
        }
    }

    private void renderItem(int par2, ResourceLocation[] resourceLocation)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation[par2 == 2 ? 1 : 0]);
        ModelBiped modelbiped = par2 == 2 ? this.field_82425_h : this.field_82423_g;
        modelbiped.isChild = false;
        modelbiped.bipedHead.showModel = par2 == 0;
        modelbiped.bipedHeadwear.showModel = par2 == 0;
        modelbiped.bipedBody.showModel = par2 == 1 || par2 == 2;
        modelbiped.bipedRightArm.showModel = par2 == 1;
        modelbiped.bipedLeftArm.showModel = par2 == 1;
        modelbiped.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
        modelbiped.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
        modelbiped.render(Minecraft.getMinecraft().thePlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
    }

    public static String getTexture(String base, Entity entity, ItemStack itemStack)
    {
        if (entity instanceof EntityPlayer && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor)
        {
            EntityPlayer player = (EntityPlayer)entity;
            Iterator var4 = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(player.username, SkinType.ARMOR_SIMPLE).iterator();

            while (var4.hasNext())
            {
                AbstractSkin playerActiveSkin = (AbstractSkin)var4.next();
                ArmorSimpleSkin armorSimpleSkin = (ArmorSimpleSkin)playerActiveSkin;
                ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
                ResourceLocation[] resourceLocation = (ResourceLocation[])armorSimpleSkin.textureMap.get(Integer.valueOf(itemStack.itemID));

                if (resourceLocation != null)
                {
                    return resourceLocation[itemArmor.armorType == 2 ? 1 : 0].toString();
                }
            }
        }

        return base;
    }
}
