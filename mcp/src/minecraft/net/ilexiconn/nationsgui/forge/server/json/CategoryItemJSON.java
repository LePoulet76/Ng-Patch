/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package net.ilexiconn.nationsgui.forge.server.json;

import com.google.gson.annotations.SerializedName;

public class CategoryItemJSON {
    public int id;
    public String name;
    public int metadata;
    public double price;
    public String description = "";
    @SerializedName(value="max_amount")
    public int maxAmount = 64;
    public String command;
    @SerializedName(value="image_preview")
    public String imagePreview;
    public String image = this.imagePreview;
    @SerializedName(value="nbt")
    public String nbt;
    @SerializedName(value="nbt_item")
    public String nbtItem;
}

