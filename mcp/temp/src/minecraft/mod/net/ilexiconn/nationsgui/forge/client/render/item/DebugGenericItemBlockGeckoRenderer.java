package net.ilexiconn.nationsgui.forge.client.render.item;

import fr.nationsglory.ngupgrades.client.model.GenericGeckoBlockModel;
import fr.nationsglory.ngupgrades.common.item.ItemGenericGeckoBlock;
import net.ilexiconn.nationsgui.forge.client.render.item.DebugGenericItemBlockGeckoRenderer$1;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import org.lwjgl.opengl.GL11;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class DebugGenericItemBlockGeckoRenderer extends GeoItemRenderer<ItemGenericGeckoBlock> {

   public DebugGenericItemBlockGeckoRenderer() {
      super(new GenericGeckoBlockModel());
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object ... data) {
      GL11.glEnable('\u803a');
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glRotated(180.0D, 0.0D, 1.0D, 0.0D);
      switch(DebugGenericItemBlockGeckoRenderer$1.$SwitchMap$net$minecraftforge$client$IItemRenderer$ItemRenderType[type.ordinal()]) {
      case 1:
         GL11.glRotated(-90.0D, 0.0D, 1.0D, 0.0D);
         GL11.glTranslated(0.0D, 1.0D, 0.0D);
      default:
         GL11.glTranslated(-0.5D, -1.0D, -0.5D);
         GL11.glPushMatrix();
         this.render((ItemGenericGeckoBlock)item.func_77973_b(), item, Minecraft.func_71410_x().field_71428_T.field_74281_c);
         GL11.glPopMatrix();
         GL11.glDisable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   public void render(ItemGenericGeckoBlock animatable, ItemStack itemStack, float partialTicks) {
      if(animatable.getBlockName().equals("earth_hub")) {
         GL11.glTranslated(0.0D, 0.75D, 0.0D);
         GL11.glScaled(0.075D, 0.075D, 0.075D);
      } else if(!animatable.getBlockName().equals("giant_gift") && !animatable.getBlockName().equals("calendar_stand")) {
         if(!animatable.getBlockName().equals("futurist_table1") && !animatable.getBlockName().equals("futurist_table2") && !animatable.getBlockName().equals("futurist_table3")) {
            if(animatable.getBlockName().equals("fountain")) {
               GL11.glTranslated(0.0D, 0.95D, 0.0D);
               GL11.glScaled(0.195D, 0.195D, 0.195D);
            } else if(!animatable.getBlockName().equals("tree_bush") && !animatable.getBlockName().equals("statue_explorer")) {
               if(!animatable.getBlockName().equals("statue_trophy") && !animatable.getBlockName().equals("statue_trophy_flip") && !animatable.getBlockName().equals("statue_warrior")) {
                  if(!animatable.getBlockName().equals("sign_cosmetics") && !animatable.getBlockName().equals("banner_onu")) {
                     if(animatable.getBlockName().equals("stand_halloween")) {
                        GL11.glTranslated(0.0D, 0.95D, 0.0D);
                        GL11.glScaled(0.45D, 0.45D, 0.45D);
                     } else if(animatable.getBlockName().equals("sugar_cane")) {
                        GL11.glTranslated(0.0D, 0.15D, 0.0D);
                        GL11.glScaled(0.5D, 0.5D, 0.5D);
                     } else if(!animatable.getBlockName().equals("airport_desk") && !animatable.getBlockName().equals("airport_chair")) {
                        if(animatable.getBlockName().equals("scarecrow")) {
                           GL11.glTranslated(0.0D, 0.15D, 0.0D);
                           GL11.glScaled(0.5D, 0.5D, 0.5D);
                        } else if(!animatable.getBlockName().equals("stand_ngprime") && !animatable.getBlockName().equals("stand_hero") && !animatable.getBlockName().equals("stand_legend") && !animatable.getBlockName().equals("stand_premium")) {
                           if(!animatable.getBlockName().equals("antique_floor_lamp") && !animatable.getBlockName().equals("old_lamp_post_tall") && !animatable.getBlockName().equals("old_lamp_post_light") && !animatable.getBlockName().equals("old_lamp_post") && !animatable.getBlockName().equals("flag_easter_green") && !animatable.getBlockName().equals("flag_easter_red") && !animatable.getBlockName().equals("pole") && !animatable.getBlockName().equals("flag_update_double") && !animatable.getBlockName().equals("flag_update")) {
                              if(animatable.getBlockName().equals("airport_start")) {
                                 GL11.glTranslated(0.0D, 1.45D, 0.0D);
                                 GL11.glScaled(0.6D, 0.6D, 0.6D);
                              } else if(!animatable.getBlockName().contains("portal_server_") && !animatable.getBlockName().equals("windmill") && !animatable.getBlockName().equals("stand_flags") && !animatable.getBlockName().equals("flower_palm") && !animatable.getBlockName().equals("parrot_balloon") && !animatable.getBlockName().equals("station_skye_terra") && !animatable.getBlockName().equals("helico_skye")) {
                                 if(!animatable.getBlockName().equals("horse_statue") && !animatable.getBlockName().equals("fish_stand")) {
                                    if(!animatable.getBlockName().equals("banner_shop_iron") && !animatable.getBlockName().equals("frame_quartz_5x3") && !animatable.getBlockName().equals("futurist_lamp1") && !animatable.getBlockName().equals("futurist_lamp2")) {
                                       if(!animatable.getBlockName().equals("rocks") && !animatable.getBlockName().equals("wooden_trailer") && !animatable.getBlockName().equals("fish_trap") && !animatable.getBlockName().equals("fish_barrel_close") && !animatable.getBlockName().equals("fish_barrel_open") && !animatable.getBlockName().equals("fish_barrel_full")) {
                                          if(animatable.getBlockName().equals("banner_ng")) {
                                             GL11.glTranslated(0.0D, 1.9D, 0.0D);
                                             GL11.glScaled(0.3D, 0.3D, 0.3D);
                                          } else if(!animatable.getBlockName().equals("frame_quartz_2x3") && !animatable.getBlockName().equals("frame_quartz_3x2") && !animatable.getBlockName().equals("frame_stone_3x2") && !animatable.getBlockName().equals("frame_stone_2x3") && !animatable.getBlockName().equals("frame_wood_2x3") && !animatable.getBlockName().equals("frame_wood_3x2")) {
                                             if(animatable.getBlockName().equals("stand_megalodon")) {
                                                GL11.glTranslated(0.0D, 0.45D, 0.0D);
                                                GL11.glScaled(0.55D, 0.55D, 0.55D);
                                             } else if(animatable.getBlockName().equals("panel_halloween")) {
                                                GL11.glTranslated(0.0D, 0.75D, 0.0D);
                                                GL11.glScaled(0.295D, 0.295D, 0.295D);
                                             } else if(!animatable.getBlockName().equals("mannequin_pharaon") && !animatable.getBlockName().equals("mannequin_megalodon") && !animatable.getBlockName().equals("mannequin_dragon") && !animatable.getBlockName().equals("mannequin_poubelle") && !animatable.getBlockName().equals("mannequin_barbie")) {
                                                if(!animatable.getBlockName().equals("store_blue") && !animatable.getBlockName().equals("store_purple")) {
                                                   if(!animatable.getBlockName().equals("airport_time") && !animatable.getBlockName().equals("bank_house") && !animatable.getBlockName().equals("marketplace_cosmerio") && !animatable.getBlockName().equals("marketplace_marketus")) {
                                                      if(!animatable.getBlockName().equals("cart_cosmerio") && !animatable.getBlockName().equals("cart_guideon") && !animatable.getBlockName().equals("cart_weather") && !animatable.getBlockName().equals("cart_harpagon") && !animatable.getBlockName().equals("cart_marketus")) {
                                                         if(!animatable.getBlockName().equals("frame_stand") && !animatable.getBlockName().equals("frame_stand_stone") && !animatable.getBlockName().equals("frame_stand_wood")) {
                                                            if(!animatable.getBlockName().equals("ng_txt") && !animatable.getBlockName().equals("stage")) {
                                                               if(animatable.getBlockName().equals("ruby_title") || animatable.getBlockName().equals("banner_shop_wood") || animatable.getBlockName().equals("logo_wonder") || animatable.getBlockName().equals("sign_parrot") || animatable.getBlockName().equals("sign_store") || animatable.getBlockName().equals("mmr") || animatable.getBlockName().equals("banner_3114") || animatable.getBlockName().equals("frame_stand_quartz_9x5") || animatable.getBlockName().equals("frame_stand_stone_9x5") || animatable.getBlockName().equals("frame_stand_wood_9x5") || animatable.getBlockName().equals("frame_easter_5x3") || animatable.getBlockName().equals("stand_cosmetics") || animatable.getBlockName().equals("stand_loto")) {
                                                                  GL11.glTranslated(0.0D, 0.95D, 0.0D);
                                                                  GL11.glScaled(0.195D, 0.195D, 0.195D);
                                                               }
                                                            } else {
                                                               GL11.glTranslated(0.0D, 0.95D, 0.0D);
                                                               GL11.glScaled(0.125D, 0.125D, 0.125D);
                                                            }
                                                         } else {
                                                            GL11.glTranslated(0.0D, 0.1D, 0.0D);
                                                            GL11.glScaled(0.295D, 0.295D, 0.295D);
                                                         }
                                                      } else {
                                                         GL11.glTranslated(0.0D, 0.75D, 0.0D);
                                                         GL11.glScaled(0.295D, 0.295D, 0.295D);
                                                      }
                                                   } else {
                                                      GL11.glTranslated(0.0D, 0.75D, 0.0D);
                                                      GL11.glScaled(0.295D, 0.295D, 0.295D);
                                                   }
                                                } else {
                                                   GL11.glTranslated(0.0D, 0.75D, 0.0D);
                                                   GL11.glScaled(0.6D, 0.6D, 0.6D);
                                                }
                                             } else {
                                                GL11.glTranslated(0.0D, 0.75D, 0.0D);
                                                GL11.glScaled(0.6D, 0.6D, 0.6D);
                                             }
                                          } else {
                                             GL11.glTranslated(0.0D, 0.4D, 0.0D);
                                             GL11.glScaled(0.4D, 0.4D, 0.4D);
                                          }
                                       } else {
                                          GL11.glTranslated(0.0D, 0.2D, 0.0D);
                                          GL11.glScaled(0.7D, 0.7D, 0.7D);
                                       }
                                    } else {
                                       GL11.glTranslated(0.0D, 0.6D, 0.0D);
                                       GL11.glScaled(0.3D, 0.3D, 0.3D);
                                    }
                                 } else {
                                    GL11.glTranslated(0.0D, 0.1D, 0.0D);
                                    GL11.glScaled(0.6D, 0.6D, 0.6D);
                                 }
                              } else {
                                 GL11.glTranslated(0.0D, 0.95D, 0.0D);
                                 GL11.glScaled(0.195D, 0.195D, 0.195D);
                              }
                           } else {
                              GL11.glTranslated(0.0D, 0.15D, 0.0D);
                              GL11.glScaled(0.395D, 0.395D, 0.395D);
                           }
                        } else {
                           GL11.glTranslated(0.0D, 0.35D, 0.0D);
                           GL11.glScaled(0.5D, 0.5D, 0.5D);
                        }
                     } else {
                        GL11.glTranslated(0.0D, 0.75D, 0.0D);
                        GL11.glScaled(0.5D, 0.5D, 0.5D);
                     }
                  } else {
                     GL11.glTranslated(0.0D, 0.55D, 0.0D);
                     GL11.glScaled(0.3D, 0.3D, 0.3D);
                  }
               } else {
                  GL11.glTranslated(0.0D, 0.55D, 0.0D);
                  GL11.glScaled(0.28D, 0.28D, 0.28D);
               }
            } else {
               GL11.glTranslated(0.0D, 0.3D, 0.0D);
               GL11.glScaled(0.5D, 0.5D, 0.5D);
            }
         } else {
            GL11.glTranslated(0.0D, 0.75D, 0.0D);
            GL11.glScaled(0.4D, 0.4D, 0.4D);
         }
      } else {
         GL11.glTranslated(0.0D, 0.75D, 0.0D);
         GL11.glScaled(0.19D, 0.19D, 0.19D);
      }

      super.render(animatable, itemStack, partialTicks);
   }

   // $FF: synthetic method
   // $FF: bridge method
   public void render(Item var1, ItemStack var2, float var3) {
      this.render((ItemGenericGeckoBlock)var1, var2, var3);
   }
}
