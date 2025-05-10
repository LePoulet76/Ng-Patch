package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.util.BodyPart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import org.json.simple.JSONObject;

public class HandSkin extends AbstractSkin
{
    private final HashMap<String, HashMap<String, BodyPart>> items = new HashMap();
    private final ModelBiped modelBiped = new ModelBiped();

    public HandSkin(JSONObject object)
    {
        super(object);

        if (object.containsKey("sides"))
        {
            HashMap bodyPartHashMap = new HashMap();
            Iterator var3 = ((JSONObject)object.get("sides")).entrySet().iterator();

            while (var3.hasNext())
            {
                Object o = var3.next();
                Entry entry = (Entry)o;
                bodyPartHashMap.put(entry.getKey(), new BodyPart((JSONObject)entry.getValue(), "handskins"));
            }

            this.items.put((String)object.get("id"), bodyPartHashMap);
        }

        this.loadTransforms(object);
    }

    public void loadTransforms(JSONObject object)
    {
        super.loadTransforms(object);

        if (this.items != null && !this.items.isEmpty() && object.containsKey("sides"))
        {
            Iterator var2 = ((JSONObject)object.get("sides")).entrySet().iterator();

            while (var2.hasNext())
            {
                Object o = var2.next();
                Entry entry = (Entry)o;
                HashMap bodyPartHashMap = (HashMap)this.items.get(object.get("id"));

                if (bodyPartHashMap != null)
                {
                    BodyPart bodyPart = (BodyPart)bodyPartHashMap.get(entry.getKey());
                    bodyPart.loadTransforms((JSONObject)entry.getValue());
                }
            }
        }
    }

    protected void render(float partialTick)
    {
        this.clearCubes(this.modelBiped.bipedBody);
        this.clearCubes(this.modelBiped.bipedHead);
        this.clearCubes(this.modelBiped.bipedHeadwear);
        this.clearCubes(this.modelBiped.bipedRightLeg);
        this.clearCubes(this.modelBiped.bipedLeftLeg);
        this.clearCubes(this.modelBiped.bipedLeftArm);
        this.clearCubes(this.modelBiped.bipedRightArm);
        Iterator var2 = this.items.values().iterator();

        while (var2.hasNext())
        {
            HashMap value = (HashMap)var2.next();
            this.applyToBody(value, this.modelBiped);
        }

        this.modelBiped.render(Minecraft.getMinecraft().thePlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
    }

    private void clearCubes(ModelRenderer model)
    {
        model.cubeList.clear();
    }

    public void applyToBody(String name, ModelBiped modelPlayer)
    {
        if (name != null && this.items.containsKey(name))
        {
            this.applyToBody((HashMap)this.items.get(name), modelPlayer);
        }
    }

    public void applyToBody(HashMap<String, BodyPart> bodyPartHashMap, ModelBiped modelPlayer)
    {
        if (bodyPartHashMap != null)
        {
            Iterator var3 = bodyPartHashMap.entrySet().iterator();

            while (var3.hasNext())
            {
                Entry stringBodyPartEntry = (Entry)var3.next();
                BodyPart bodyPart = (BodyPart)stringBodyPartEntry.getValue();

                if (bodyPart.getModel().isReady())
                {
                    bodyPart.getModel().updateModel(bodyPart.getTransform());
                    ModelRenderer model = this.getBodyPartFromName((String)stringBodyPartEntry.getKey(), modelPlayer);

                    if (model != null)
                    {
                        model.addChild(bodyPart.getModel().getModel());
                    }
                }
            }
        }
    }

    private ModelRenderer getBodyPartFromName(String name, ModelBiped modelBiped)
    {
        byte var4 = -1;

        switch (name.hashCode())
        {
            case -1436108128:
                if (name.equals("rightArm"))
                {
                    var4 = 1;
                }

                break;

            case 55414997:
                if (name.equals("leftArm"))
                {
                    var4 = 0;
                }
        }

        switch (var4)
        {
            case 0:
                return modelBiped.bipedLeftArm;

            case 1:
                return modelBiped.bipedRightArm;

            default:
                return null;
        }
    }

    public void reload()
    {
        Iterator var1 = this.items.values().iterator();

        while (var1.hasNext())
        {
            HashMap value = (HashMap)var1.next();
            Iterator var3 = value.values().iterator();

            while (var3.hasNext())
            {
                BodyPart bodyPart = (BodyPart)var3.next();
                bodyPart.reload();
            }
        }
    }
}
