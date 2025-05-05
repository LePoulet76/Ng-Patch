package net.ilexiconn.nationsgui.forge.client.itemskin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.itemskin.AbstractSkin;
import net.ilexiconn.nationsgui.forge.client.util.BodyPart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.ItemStack;
import org.json.simple.JSONObject;

public class ArmorSkin extends AbstractSkin {

   private final HashMap<Integer, HashMap<String, BodyPart>> items = new HashMap();
   private final ModelBiped modelBiped = new ModelBiped();


   public ArmorSkin(JSONObject object) {
      super(object);
      if(object.containsKey("items")) {
         Iterator var2 = ((JSONObject)object.get("items")).entrySet().iterator();

         while(var2.hasNext()) {
            Object o = var2.next();
            Entry entry = (Entry)o;
            HashMap bodyPartHashMap = new HashMap();
            Iterator var6 = ((JSONObject)entry.getValue()).entrySet().iterator();

            while(var6.hasNext()) {
               Object o2 = var6.next();
               Entry entry1 = (Entry)o2;
               bodyPartHashMap.put(entry1.getKey(), new BodyPart((JSONObject)entry1.getValue(), "armorskins"));
            }

            this.items.put(Integer.valueOf(Integer.parseInt((String)entry.getKey())), bodyPartHashMap);
         }
      }

      this.loadTransforms(object);
   }

   public void loadTransforms(JSONObject object) {
      super.loadTransforms(object);
      if(this.items != null && !this.items.isEmpty() && object.containsKey("items")) {
         Iterator var2 = ((JSONObject)object.get("items")).entrySet().iterator();

         while(var2.hasNext()) {
            Object o = var2.next();
            Entry entry = (Entry)o;
            HashMap bodyPartHashMap = (HashMap)this.items.get(Integer.valueOf(Integer.parseInt((String)entry.getKey())));
            if(bodyPartHashMap != null) {
               Iterator var6 = ((JSONObject)entry.getValue()).entrySet().iterator();

               while(var6.hasNext()) {
                  Object o1 = var6.next();
                  Entry entry2 = (Entry)o1;
                  BodyPart bodyPart = (BodyPart)bodyPartHashMap.get(entry2.getKey());
                  bodyPart.loadTransforms((JSONObject)entry2.getValue());
               }
            }
         }
      }

   }

   protected void render(float partialTick) {
      this.clearCubes(this.modelBiped.field_78115_e);
      this.clearCubes(this.modelBiped.field_78116_c);
      this.clearCubes(this.modelBiped.field_78114_d);
      this.clearCubes(this.modelBiped.field_78123_h);
      this.clearCubes(this.modelBiped.field_78124_i);
      this.clearCubes(this.modelBiped.field_78113_g);
      this.clearCubes(this.modelBiped.field_78112_f);
      Iterator var2 = this.items.values().iterator();

      while(var2.hasNext()) {
         HashMap value = (HashMap)var2.next();
         this.applyToBody(value, this.modelBiped);
      }

      this.modelBiped.func_78088_a(Minecraft.func_71410_x().field_71439_g, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
   }

   private void clearCubes(ModelRenderer model) {
      model.field_78804_l.clear();
   }

   public boolean isValidArmor(ItemStack itemStack) {
      return itemStack != null && this.items.containsKey(Integer.valueOf(itemStack.field_77993_c));
   }

   public void applyToBody(ItemStack itemStack, ModelBiped modelPlayer) {
      if(itemStack != null) {
         HashMap bodyPartHashMap = (HashMap)this.items.get(Integer.valueOf(itemStack.field_77993_c));
         this.applyToBody(bodyPartHashMap, modelPlayer);
      }
   }

   public void applyToBody(HashMap<String, BodyPart> bodyPartHashMap, ModelBiped modelPlayer) {
      if(bodyPartHashMap != null) {
         Iterator var3 = bodyPartHashMap.entrySet().iterator();

         while(var3.hasNext()) {
            Entry stringBodyPartEntry = (Entry)var3.next();
            BodyPart bodyPart = (BodyPart)stringBodyPartEntry.getValue();
            if(bodyPart.getModel().isReady()) {
               bodyPart.getModel().updateModel(bodyPart.getTransform());
               ModelRenderer model = this.getBodyPartFromName((String)stringBodyPartEntry.getKey(), modelPlayer);
               if(model != null) {
                  model.func_78792_a(bodyPart.getModel().getModel());
               }
            }
         }

      }
   }

   private ModelRenderer getBodyPartFromName(String name, ModelBiped modelBiped) {
      byte var4 = -1;
      switch(name.hashCode()) {
      case -1436108128:
         if(name.equals("rightArm")) {
            var4 = 3;
         }
         break;
      case -1436097966:
         if(name.equals("rightLeg")) {
            var4 = 5;
         }
         break;
      case 3029410:
         if(name.equals("body")) {
            var4 = 1;
         }
         break;
      case 3198432:
         if(name.equals("head")) {
            var4 = 0;
         }
         break;
      case 55414997:
         if(name.equals("leftArm")) {
            var4 = 2;
         }
         break;
      case 55425159:
         if(name.equals("leftLeg")) {
            var4 = 4;
         }
      }

      switch(var4) {
      case 0:
         return modelBiped.field_78116_c;
      case 1:
         return modelBiped.field_78115_e;
      case 2:
         return modelBiped.field_78113_g;
      case 3:
         return modelBiped.field_78112_f;
      case 4:
         return modelBiped.field_78124_i;
      case 5:
         return modelBiped.field_78123_h;
      default:
         return null;
      }
   }

   public void reload() {
      Iterator var1 = this.items.values().iterator();

      while(var1.hasNext()) {
         HashMap value = (HashMap)var1.next();
         Iterator var3 = value.values().iterator();

         while(var3.hasNext()) {
            BodyPart bodyPart = (BodyPart)var3.next();
            bodyPart.reload();
         }
      }

   }
}
