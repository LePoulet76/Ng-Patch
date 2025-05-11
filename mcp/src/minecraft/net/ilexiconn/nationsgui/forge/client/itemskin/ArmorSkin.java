/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.model.ModelRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.ItemStack
 */
package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.util.BodyPart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.json.simple.JSONObject;

public class ArmorSkin
extends AbstractSkin {
    private final HashMap<Integer, HashMap<String, BodyPart>> items = new HashMap();
    private final ModelBiped modelBiped = new ModelBiped();

    public ArmorSkin(JSONObject object) {
        super(object);
        if (object.containsKey("items")) {
            Iterator iterator = ((JSONObject)object.get("items")).entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry o;
                Map.Entry entry = o = iterator.next();
                HashMap bodyPartHashMap = new HashMap();
                Iterator iterator2 = ((JSONObject)entry.getValue()).entrySet().iterator();
                while (iterator2.hasNext()) {
                    Map.Entry o2;
                    Map.Entry entry1 = o2 = iterator2.next();
                    bodyPartHashMap.put(entry1.getKey(), new BodyPart((JSONObject)entry1.getValue(), "armorskins"));
                }
                this.items.put(Integer.parseInt((String)entry.getKey()), bodyPartHashMap);
            }
        }
        this.loadTransforms(object);
    }

    @Override
    public void loadTransforms(JSONObject object) {
        super.loadTransforms(object);
        if (this.items != null && !this.items.isEmpty() && object.containsKey("items")) {
            for (Map.Entry o : ((JSONObject)object.get("items")).entrySet()) {
                Map.Entry entry = o;
                HashMap<String, BodyPart> bodyPartHashMap = this.items.get(Integer.parseInt((String)entry.getKey()));
                if (bodyPartHashMap == null) continue;
                Iterator iterator = ((JSONObject)entry.getValue()).entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry o1;
                    Map.Entry entry2 = o1 = iterator.next();
                    BodyPart bodyPart = bodyPartHashMap.get(entry2.getKey());
                    bodyPart.loadTransforms((JSONObject)entry2.getValue());
                }
            }
        }
    }

    @Override
    protected void render(float partialTick) {
        this.clearCubes(this.modelBiped.field_78115_e);
        this.clearCubes(this.modelBiped.field_78116_c);
        this.clearCubes(this.modelBiped.field_78114_d);
        this.clearCubes(this.modelBiped.field_78123_h);
        this.clearCubes(this.modelBiped.field_78124_i);
        this.clearCubes(this.modelBiped.field_78113_g);
        this.clearCubes(this.modelBiped.field_78112_f);
        for (HashMap<String, BodyPart> value : this.items.values()) {
            this.applyToBody(value, this.modelBiped);
        }
        this.modelBiped.func_78088_a((Entity)Minecraft.func_71410_x().field_71439_g, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
    }

    private void clearCubes(ModelRenderer model) {
        model.field_78804_l.clear();
    }

    public boolean isValidArmor(ItemStack itemStack) {
        return itemStack != null && this.items.containsKey(itemStack.field_77993_c);
    }

    public void applyToBody(ItemStack itemStack, ModelBiped modelPlayer) {
        if (itemStack == null) {
            return;
        }
        HashMap<String, BodyPart> bodyPartHashMap = this.items.get(itemStack.field_77993_c);
        this.applyToBody(bodyPartHashMap, modelPlayer);
    }

    public void applyToBody(HashMap<String, BodyPart> bodyPartHashMap, ModelBiped modelPlayer) {
        if (bodyPartHashMap == null) {
            return;
        }
        for (Map.Entry<String, BodyPart> stringBodyPartEntry : bodyPartHashMap.entrySet()) {
            BodyPart bodyPart = stringBodyPartEntry.getValue();
            if (!bodyPart.getModel().isReady()) continue;
            bodyPart.getModel().updateModel(bodyPart.getTransform());
            ModelRenderer model = this.getBodyPartFromName(stringBodyPartEntry.getKey(), modelPlayer);
            if (model == null) continue;
            model.func_78792_a(bodyPart.getModel().getModel());
        }
    }

    private ModelRenderer getBodyPartFromName(String name, ModelBiped modelBiped) {
        switch (name) {
            case "head": {
                return modelBiped.field_78116_c;
            }
            case "body": {
                return modelBiped.field_78115_e;
            }
            case "leftArm": {
                return modelBiped.field_78113_g;
            }
            case "rightArm": {
                return modelBiped.field_78112_f;
            }
            case "leftLeg": {
                return modelBiped.field_78124_i;
            }
            case "rightLeg": {
                return modelBiped.field_78123_h;
            }
        }
        return null;
    }

    @Override
    public void reload() {
        for (HashMap<String, BodyPart> value : this.items.values()) {
            for (BodyPart bodyPart : value.values()) {
                bodyPart.reload();
            }
        }
    }
}

