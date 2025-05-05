package net.ilexiconn.nationsgui.forge.client;

import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.client.render.entity.ExtendedPlayerSkinRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

class ClientProxy$5 extends ExtendedPlayerSkinRenderer {

   // $FF: synthetic field
   final ClientProxy this$0;


   ClientProxy$5(ClientProxy this$0, SkinType skinType, int slotIndex) {
      super(skinType, slotIndex);
      this.this$0 = this$0;
   }

   protected ModelRenderer getBodyPart(ModelBiped modelBiped) {
      return modelBiped.field_78116_c;
   }
}
