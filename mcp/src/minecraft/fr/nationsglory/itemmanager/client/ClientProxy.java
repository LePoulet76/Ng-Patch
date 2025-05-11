/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 */
package fr.nationsglory.itemmanager.client;

import fr.nationsglory.itemmanager.CommonProxy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ClientProxy
extends CommonProxy {
    @Override
    public void postInit() {
        super.postInit();
        for (String tabName : NationsGUITransformer.config.getCreativeTabBlacklist()) {
            ArrayList<CreativeTabs> creativeTabsList = new ArrayList<CreativeTabs>();
            CreativeTabs tabs = null;
            boolean passed = false;
            for (CreativeTabs creativeTabs : CreativeTabs.field_78032_a) {
                if (!creativeTabs.func_78013_b().equals(tabName)) {
                    if (passed) {
                        try {
                            Field field = CreativeTabs.class.getDeclaredField(NationsGUITransformer.inDevelopment ? "tabIndex" : "field_78033_n");
                            field.setAccessible(true);
                            field.set(creativeTabs, creativeTabs.func_78021_a() - 1);
                        }
                        catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    creativeTabsList.add(creativeTabs);
                    continue;
                }
                tabs = creativeTabs;
                passed = true;
            }
            if (tabs == null) continue;
            for (CreativeTabs creativeTabs : Item.field_77698_e) {
                if (creativeTabs == null || creativeTabs.func_77640_w() == null || !creativeTabs.func_77640_w().equals(tabs)) continue;
                creativeTabs.func_77637_a(CreativeTabs.field_78026_f);
                if (!(creativeTabs instanceof ItemBlock)) continue;
                Block.field_71973_m[creativeTabs.field_77779_bT].func_71849_a(CreativeTabs.field_78026_f);
            }
            CreativeTabs.field_78032_a = creativeTabsList.toArray(new CreativeTabs[creativeTabsList.size()]);
        }
    }
}

