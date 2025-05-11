/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.ngupgrades.client.model.GenericGeckoBlockModel
 *  fr.nationsglory.ngupgrades.common.item.ItemGenericGeckoBlock
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.client.IItemRenderer$ItemRenderType
 *  org.lwjgl.opengl.GL11
 *  software.bernie.geckolib3.model.AnimatedGeoModel
 *  software.bernie.geckolib3.renderers.geo.GeoItemRenderer
 */
package net.ilexiconn.nationsgui.forge.client.render.item;

import fr.nationsglory.ngupgrades.client.model.GenericGeckoBlockModel;
import fr.nationsglory.ngupgrades.common.item.ItemGenericGeckoBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class DebugGenericItemBlockGeckoRenderer
extends GeoItemRenderer<ItemGenericGeckoBlock> {
    public DebugGenericItemBlockGeckoRenderer() {
        super((AnimatedGeoModel)new GenericGeckoBlockModel());
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object ... data) {
        GL11.glEnable((int)32826);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glRotated((double)180.0, (double)0.0, (double)1.0, (double)0.0);
        switch (type) {
            case EQUIPPED_FIRST_PERSON: {
                GL11.glRotated((double)-90.0, (double)0.0, (double)1.0, (double)0.0);
                GL11.glTranslated((double)0.0, (double)1.0, (double)0.0);
            }
        }
        GL11.glTranslated((double)-0.5, (double)-1.0, (double)-0.5);
        GL11.glPushMatrix();
        this.render((ItemGenericGeckoBlock)item.func_77973_b(), item, Minecraft.func_71410_x().field_71428_T.field_74281_c);
        GL11.glPopMatrix();
        GL11.glDisable((int)3042);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public void render(ItemGenericGeckoBlock animatable, ItemStack itemStack, float partialTicks) {
        if (animatable.getBlockName().equals("earth_hub")) {
            GL11.glTranslated((double)0.0, (double)0.75, (double)0.0);
            GL11.glScaled((double)0.075, (double)0.075, (double)0.075);
        } else if (animatable.getBlockName().equals("giant_gift") || animatable.getBlockName().equals("calendar_stand")) {
            GL11.glTranslated((double)0.0, (double)0.75, (double)0.0);
            GL11.glScaled((double)0.19, (double)0.19, (double)0.19);
        } else if (animatable.getBlockName().equals("futurist_table1") || animatable.getBlockName().equals("futurist_table2") || animatable.getBlockName().equals("futurist_table3")) {
            GL11.glTranslated((double)0.0, (double)0.75, (double)0.0);
            GL11.glScaled((double)0.4, (double)0.4, (double)0.4);
        } else if (animatable.getBlockName().equals("fountain")) {
            GL11.glTranslated((double)0.0, (double)0.95, (double)0.0);
            GL11.glScaled((double)0.195, (double)0.195, (double)0.195);
        } else if (animatable.getBlockName().equals("tree_bush") || animatable.getBlockName().equals("statue_explorer")) {
            GL11.glTranslated((double)0.0, (double)0.3, (double)0.0);
            GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        } else if (animatable.getBlockName().equals("statue_trophy") || animatable.getBlockName().equals("statue_trophy_flip") || animatable.getBlockName().equals("statue_warrior")) {
            GL11.glTranslated((double)0.0, (double)0.55, (double)0.0);
            GL11.glScaled((double)0.28, (double)0.28, (double)0.28);
        } else if (animatable.getBlockName().equals("sign_cosmetics") || animatable.getBlockName().equals("banner_onu")) {
            GL11.glTranslated((double)0.0, (double)0.55, (double)0.0);
            GL11.glScaled((double)0.3, (double)0.3, (double)0.3);
        } else if (animatable.getBlockName().equals("stand_halloween")) {
            GL11.glTranslated((double)0.0, (double)0.95, (double)0.0);
            GL11.glScaled((double)0.45, (double)0.45, (double)0.45);
        } else if (animatable.getBlockName().equals("sugar_cane")) {
            GL11.glTranslated((double)0.0, (double)0.15, (double)0.0);
            GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        } else if (animatable.getBlockName().equals("airport_desk") || animatable.getBlockName().equals("airport_chair")) {
            GL11.glTranslated((double)0.0, (double)0.75, (double)0.0);
            GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        } else if (animatable.getBlockName().equals("scarecrow")) {
            GL11.glTranslated((double)0.0, (double)0.15, (double)0.0);
            GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        } else if (animatable.getBlockName().equals("stand_ngprime") || animatable.getBlockName().equals("stand_hero") || animatable.getBlockName().equals("stand_legend") || animatable.getBlockName().equals("stand_premium")) {
            GL11.glTranslated((double)0.0, (double)0.35, (double)0.0);
            GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        } else if (animatable.getBlockName().equals("antique_floor_lamp") || animatable.getBlockName().equals("old_lamp_post_tall") || animatable.getBlockName().equals("old_lamp_post_light") || animatable.getBlockName().equals("old_lamp_post") || animatable.getBlockName().equals("flag_easter_green") || animatable.getBlockName().equals("flag_easter_red") || animatable.getBlockName().equals("pole") || animatable.getBlockName().equals("flag_update_double") || animatable.getBlockName().equals("flag_update")) {
            GL11.glTranslated((double)0.0, (double)0.15, (double)0.0);
            GL11.glScaled((double)0.395, (double)0.395, (double)0.395);
        } else if (animatable.getBlockName().equals("airport_start")) {
            GL11.glTranslated((double)0.0, (double)1.45, (double)0.0);
            GL11.glScaled((double)0.6, (double)0.6, (double)0.6);
        } else if (animatable.getBlockName().contains("portal_server_") || animatable.getBlockName().equals("windmill") || animatable.getBlockName().equals("stand_flags") || animatable.getBlockName().equals("flower_palm") || animatable.getBlockName().equals("parrot_balloon") || animatable.getBlockName().equals("station_skye_terra") || animatable.getBlockName().equals("helico_skye")) {
            GL11.glTranslated((double)0.0, (double)0.95, (double)0.0);
            GL11.glScaled((double)0.195, (double)0.195, (double)0.195);
        } else if (animatable.getBlockName().equals("horse_statue") || animatable.getBlockName().equals("fish_stand")) {
            GL11.glTranslated((double)0.0, (double)0.1, (double)0.0);
            GL11.glScaled((double)0.6, (double)0.6, (double)0.6);
        } else if (animatable.getBlockName().equals("banner_shop_iron") || animatable.getBlockName().equals("frame_quartz_5x3") || animatable.getBlockName().equals("futurist_lamp1") || animatable.getBlockName().equals("futurist_lamp2")) {
            GL11.glTranslated((double)0.0, (double)0.6, (double)0.0);
            GL11.glScaled((double)0.3, (double)0.3, (double)0.3);
        } else if (animatable.getBlockName().equals("rocks") || animatable.getBlockName().equals("wooden_trailer") || animatable.getBlockName().equals("fish_trap") || animatable.getBlockName().equals("fish_barrel_close") || animatable.getBlockName().equals("fish_barrel_open") || animatable.getBlockName().equals("fish_barrel_full")) {
            GL11.glTranslated((double)0.0, (double)0.2, (double)0.0);
            GL11.glScaled((double)0.7, (double)0.7, (double)0.7);
        } else if (animatable.getBlockName().equals("banner_ng")) {
            GL11.glTranslated((double)0.0, (double)1.9, (double)0.0);
            GL11.glScaled((double)0.3, (double)0.3, (double)0.3);
        } else if (animatable.getBlockName().equals("frame_quartz_2x3") || animatable.getBlockName().equals("frame_quartz_3x2") || animatable.getBlockName().equals("frame_stone_3x2") || animatable.getBlockName().equals("frame_stone_2x3") || animatable.getBlockName().equals("frame_wood_2x3") || animatable.getBlockName().equals("frame_wood_3x2")) {
            GL11.glTranslated((double)0.0, (double)0.4, (double)0.0);
            GL11.glScaled((double)0.4, (double)0.4, (double)0.4);
        } else if (animatable.getBlockName().equals("stand_megalodon")) {
            GL11.glTranslated((double)0.0, (double)0.45, (double)0.0);
            GL11.glScaled((double)0.55, (double)0.55, (double)0.55);
        } else if (animatable.getBlockName().equals("panel_halloween")) {
            GL11.glTranslated((double)0.0, (double)0.75, (double)0.0);
            GL11.glScaled((double)0.295, (double)0.295, (double)0.295);
        } else if (animatable.getBlockName().equals("mannequin_pharaon") || animatable.getBlockName().equals("mannequin_megalodon") || animatable.getBlockName().equals("mannequin_dragon") || animatable.getBlockName().equals("mannequin_poubelle") || animatable.getBlockName().equals("mannequin_barbie")) {
            GL11.glTranslated((double)0.0, (double)0.75, (double)0.0);
            GL11.glScaled((double)0.6, (double)0.6, (double)0.6);
        } else if (animatable.getBlockName().equals("store_blue") || animatable.getBlockName().equals("store_purple")) {
            GL11.glTranslated((double)0.0, (double)0.75, (double)0.0);
            GL11.glScaled((double)0.6, (double)0.6, (double)0.6);
        } else if (animatable.getBlockName().equals("airport_time") || animatable.getBlockName().equals("bank_house") || animatable.getBlockName().equals("marketplace_cosmerio") || animatable.getBlockName().equals("marketplace_marketus")) {
            GL11.glTranslated((double)0.0, (double)0.75, (double)0.0);
            GL11.glScaled((double)0.295, (double)0.295, (double)0.295);
        } else if (animatable.getBlockName().equals("cart_cosmerio") || animatable.getBlockName().equals("cart_guideon") || animatable.getBlockName().equals("cart_weather") || animatable.getBlockName().equals("cart_harpagon") || animatable.getBlockName().equals("cart_marketus")) {
            GL11.glTranslated((double)0.0, (double)0.75, (double)0.0);
            GL11.glScaled((double)0.295, (double)0.295, (double)0.295);
        } else if (animatable.getBlockName().equals("frame_stand") || animatable.getBlockName().equals("frame_stand_stone") || animatable.getBlockName().equals("frame_stand_wood")) {
            GL11.glTranslated((double)0.0, (double)0.1, (double)0.0);
            GL11.glScaled((double)0.295, (double)0.295, (double)0.295);
        } else if (animatable.getBlockName().equals("ng_txt") || animatable.getBlockName().equals("stage")) {
            GL11.glTranslated((double)0.0, (double)0.95, (double)0.0);
            GL11.glScaled((double)0.125, (double)0.125, (double)0.125);
        } else if (animatable.getBlockName().equals("ruby_title") || animatable.getBlockName().equals("banner_shop_wood") || animatable.getBlockName().equals("logo_wonder") || animatable.getBlockName().equals("sign_parrot") || animatable.getBlockName().equals("sign_store") || animatable.getBlockName().equals("mmr") || animatable.getBlockName().equals("banner_3114") || animatable.getBlockName().equals("frame_stand_quartz_9x5") || animatable.getBlockName().equals("frame_stand_stone_9x5") || animatable.getBlockName().equals("frame_stand_wood_9x5") || animatable.getBlockName().equals("frame_easter_5x3") || animatable.getBlockName().equals("stand_cosmetics") || animatable.getBlockName().equals("stand_loto")) {
            GL11.glTranslated((double)0.0, (double)0.95, (double)0.0);
            GL11.glScaled((double)0.195, (double)0.195, (double)0.195);
        }
        super.render((Item)animatable, itemStack, partialTicks);
    }
}

