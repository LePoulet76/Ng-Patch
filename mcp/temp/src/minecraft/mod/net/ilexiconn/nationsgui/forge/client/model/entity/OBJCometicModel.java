package net.ilexiconn.nationsgui.forge.client.model.entity;

import fr.nationsglory.modelapi.ModelAPI;
import fr.nationsglory.modelapi.ModelOBJ;
import net.ilexiconn.nationsgui.forge.client.model.entity.CosmeticModel;
import net.ilexiconn.nationsgui.forge.client.util.Transform;
import net.minecraft.client.model.ModelRenderer;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.GL11;

public class OBJCometicModel extends CosmeticModel {

   private final ModelOBJ modelOBJ;


   public OBJCometicModel(JSONObject object, String domain, String id) {
      super(object);
      this.modelOBJ = ModelAPI.registerModel(domain, id, (String)object.get("modelName"), (String)object.get("textureName"));
   }

   public void render(float partialTicks) {
      GL11.glEnable('\u803a');
      this.modelOBJ.render(partialTicks);
   }

   public void updateModel(Transform transform) {
      this.modelOBJ.applyTexture();
      transform.applyToModel(this.modelOBJ.getModel());
   }

   public ModelRenderer getModel() {
      return this.modelOBJ.getModel();
   }

   public void reload() {
      this.modelOBJ.resetTexture();
      this.modelOBJ.resetModel(true);
   }

   public boolean isReady() {
      return this.modelOBJ.getModel() != null && this.modelOBJ.loadTexture();
   }
}
