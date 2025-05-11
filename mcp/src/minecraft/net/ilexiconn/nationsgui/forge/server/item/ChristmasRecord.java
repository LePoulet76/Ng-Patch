/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemRecord
 */
package net.ilexiconn.nationsgui.forge.server.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemRecord;

public class ChristmasRecord
extends ItemRecord {
    public ChristmasRecord() {
        super(3336, "christmas");
        this.func_111206_d("nationsgui:christmas_record");
        this.func_77655_b("christmas_record");
    }

    public String func_90043_g() {
        return I18n.func_135053_a((String)(this.func_77658_a() + ".name"));
    }
}

