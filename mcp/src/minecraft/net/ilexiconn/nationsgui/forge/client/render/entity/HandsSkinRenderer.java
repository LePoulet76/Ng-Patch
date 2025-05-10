package net.ilexiconn.nationsgui.forge.client.render.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.cosmetic.CosmeticCategoryGUI;
import net.ilexiconn.nationsgui.forge.client.itemskin.HandSkin;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class HandsSkinRenderer
{
    @ForgeSubscribe(
        priority = EventPriority.LOW
    )
    public void onPlayerRenderer(Pre event)
    {
        if (ClientProxy.clientConfig.render3DSkins)
        {
            if (event.entityPlayer.getHeldItem() == null)
            {
                ModelBiped playerModel = (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, event.renderer, new String[] {"modelBipedMain", "modelBipedMain"});
                Minecraft minecraft = Minecraft.getMinecraft();

                if (event.entity == minecraft.thePlayer)
                {
                    if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !(Minecraft.getMinecraft().currentScreen instanceof CosmeticCategoryGUI))
                    {
                        return;
                    }
                }
                else if (event.entityPlayer.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))
                {
                    return;
                }

                List bippedSkins = ClientProxy.SKIN_MANAGER.getPlayerActiveSkins(event.entityPlayer.username, SkinType.HANDS);
                HandSkin handSkin = bippedSkins.size() > 0 ? (HandSkin)bippedSkins.get(0) : null;

                if (handSkin != null)
                {
                    handSkin.applyToBody(handSkin.getId(), playerModel);
                }
            }
        }
    }
}
