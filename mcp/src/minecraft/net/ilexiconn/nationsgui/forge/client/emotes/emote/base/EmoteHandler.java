package net.ilexiconn.nationsgui.forge.client.emotes.emote.base;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;

public final class EmoteHandler
{
    public static Map < String, Class <? extends EmoteBase >> emoteMap = new TreeMap();
    private static WeakHashMap<EntityPlayer, EmoteBase> playerEmotes = new WeakHashMap();
    private static List<EntityPlayer> updatedPlayers = new ArrayList();
    public static Map<String, ResourceLocation> cosmEmotesIcon = new HashMap();
    public static Map<String, ResourceLocation> cosmEmotesTexture = new HashMap();

    public static void putEmote(EntityPlayer player, String emoteName)
    {
        if (emoteMap.containsKey(emoteName))
        {
            putEmote(player, (Class)emoteMap.get(emoteName));
        }
        else
        {
            player.sendChatToPlayer(ChatMessageComponent.createFromText("\u00a7cThat emote doesn\'t exist. Try /emote list."));
        }
    }

    public static void putEmote(EntityPlayer player, Class <? extends EmoteBase > clazz)
    {
        ModelBiped model = getPlayerModel();
        ModelBiped armorModel = getPlayerArmorModel();
        ModelBiped armorLegModel = getPlayerArmorLegModel();

        if (model.bipedHead.rotateAngleY < 0.0F)
        {
            model.bipedHead.rotateAngleY = ((float)Math.PI * 2F) - model.bipedHead.rotateAngleY;
        }

        model.bipedCloak.isHidden = true;

        try
        {
            playerEmotes.put(player, clazz.getConstructor(new Class[] {EntityPlayer.class, ModelBiped.class, ModelBiped.class, ModelBiped.class}).newInstance(new Object[] {player, model, armorModel, armorLegModel}));
        }
        catch (Exception var6)
        {
            var6.printStackTrace();
        }
    }

    public static void updateEmotes(Entity e)
    {
        if (e instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)e;

            if (playerEmotes.containsKey(player))
            {
                EmoteBase emote = (EmoteBase)playerEmotes.get(player);

                if (emote.isDone())
                {
                    playerEmotes.remove(player);
                    resetModel(getPlayerModel());
                    resetModel(getPlayerArmorModel());
                    resetModel(getPlayerArmorLegModel());
                }
                else
                {
                    emote.update(!updatedPlayers.contains(player));
                }

                updatedPlayers.add(player);
            }
        }
    }

    public static void clearPlayerList()
    {
        updatedPlayers.clear();
    }

    private static ModelBiped getPlayerModel()
    {
        RenderPlayer render = (RenderPlayer)RenderManager.instance.entityRenderMap.get(EntityPlayer.class);
        return (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, render, new String[] {"modelBipedMain", "f", "modelBipedMain"});
    }

    private static ModelBiped getPlayerArmorModel()
    {
        RenderPlayer render = (RenderPlayer)RenderManager.instance.entityRenderMap.get(EntityPlayer.class);
        return (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, render, new String[] {"modelArmorChestplate", "g", "modelArmorChestplate"});
    }

    private static ModelBiped getPlayerArmorLegModel()
    {
        RenderPlayer render = (RenderPlayer)RenderManager.instance.entityRenderMap.get(EntityPlayer.class);
        return (ModelBiped)ReflectionHelper.getPrivateValue(RenderPlayer.class, render, new String[] {"modelArmor", "h", "modelArmor"});
    }

    private static void resetModel(ModelBiped model)
    {
        if (model != null)
        {
            resetPart(model.bipedHead);
            resetPart(model.bipedHeadwear);
            resetPart(model.bipedBody);
            resetPart(model.bipedLeftArm);
            resetPart(model.bipedRightArm);
            resetPart(model.bipedLeftLeg);
            resetPart(model.bipedRightLeg);
            resetPart(model.bipedCloak);
        }
    }

    private static void resetPart(ModelRenderer renderer)
    {
        if (renderer != null)
        {
            renderer.rotateAngleZ = renderer.offsetX = renderer.offsetY = renderer.offsetZ = 0.0F;
            renderer.isHidden = false;
        }
    }
}
