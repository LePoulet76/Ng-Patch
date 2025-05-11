/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package net.ilexiconn.nationsgui.forge.server.json;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.nationsgui.forge.server.json.CategoryItemJSON;

public class CategoryJSON {
    public String name;
    public String type;
    public boolean permission;
    public String icon;
    public CategoryItemJSON[] items = new CategoryItemJSON[0];
    @SerializedName(value="img")
    public String image;
    @SerializedName(value="img_hover")
    public String imageHover = this.image;
    public String url;
}

