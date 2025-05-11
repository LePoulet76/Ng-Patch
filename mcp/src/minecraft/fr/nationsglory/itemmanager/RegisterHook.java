/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.Loader
 *  net.minecraft.block.Block
 *  net.minecraft.item.Item
 */
package fr.nationsglory.itemmanager;

import cpw.mods.fml.common.Loader;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUITransformer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class RegisterHook {
    public static boolean blockIsBlacklisted(Object object, String modID) {
        List<Integer> list;
        if (modID == null) {
            modID = Loader.instance().activeModContainer().getModId();
        }
        if ((list = NationsGUITransformer.config.getItemBlacklist().get(modID)) != null && (object instanceof Block && list.contains(((Block)object).field_71990_ca) || object instanceof Item && list.contains(((Item)object).field_77779_bT))) {
            int id = object instanceof Block ? ((Block)object).field_71990_ca : ((Item)object).field_77779_bT;
            Block.field_71973_m[id] = null;
            Item.field_77698_e[id] = null;
            return true;
        }
        return false;
    }
}

