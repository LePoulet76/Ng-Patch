/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.ChatLine
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.util.StringUtils
 */
package net.ilexiconn.nationsgui.forge.client.chat;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;

@SideOnly(value=Side.CLIENT)
public class ChatClickCountryData {
    public static final Pattern pattern = Pattern.compile("^\\-[a-zA-Z]+\\-$");
    private final FontRenderer fontR;
    private final ChatLine line;
    private final int field_78312_d;
    private final int field_78313_e;
    private final String field_78310_f;
    private final String clickedCountry;

    public ChatClickCountryData(FontRenderer par1FontRenderer, ChatLine par2ChatLine, int par3, int par4) {
        this.fontR = par1FontRenderer;
        this.line = par2ChatLine;
        this.field_78312_d = par3;
        this.field_78313_e = par4;
        this.field_78310_f = par1FontRenderer.func_78269_a(par2ChatLine.func_74538_a(), par3);
        this.clickedCountry = this.findClickedCountry();
    }

    public String getClickedCountry() {
        return this.clickedCountry;
    }

    public String getCountry() {
        String s = this.getClickedCountry();
        if (s == null) {
            return null;
        }
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return matcher.group(0).replace("-", "");
        }
        return null;
    }

    private String findClickedCountry() {
        int j;
        int i = this.field_78310_f.lastIndexOf(" ", this.field_78310_f.length()) + 1;
        if (i < 0) {
            i = 0;
        }
        if ((j = this.line.func_74538_a().indexOf(" ", i)) < 0) {
            j = this.line.func_74538_a().length();
        }
        return StringUtils.func_76338_a((String)this.line.func_74538_a().substring(i, j));
    }
}

