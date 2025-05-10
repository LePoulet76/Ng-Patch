package net.ilexiconn.nationsgui.forge.server;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.ilexiconn.nationsgui.forge.server.item.*;

public class ModItems {
    public static Item MOB_SPAWNER;
    public static Item CHRISTMAS_DISK;
    public static Item ROTTEN_FOOD;
    public static Item ITEM_BADGE;
    public static Item PORTAL_ITEM;

    public static void init() {
        MOB_SPAWNER = new MobSpawnerItem();
        CHRISTMAS_DISK = new ChristmasRecord();
        ROTTEN_FOOD = new ItemRottenFood();
        ITEM_BADGE = (new ItemBadge(5090)).setUnlocalizedName("badgeItem");
        PORTAL_ITEM = new ItemBlockPortal(ModBlocks.PORTAL); // dépend du bloc PORTAL déjà initialisé

        GameRegistry.registerItem(MOB_SPAWNER, "ng_mob_spawner");
        GameRegistry.registerItem(CHRISTMAS_DISK, "christmas_disk");
        GameRegistry.registerItem(ROTTEN_FOOD, "rotten_food");
        GameRegistry.registerItem(ITEM_BADGE, "itemBadge");
        GameRegistry.registerItem(PORTAL_ITEM, "island_portal_item");
    }
}
