package net.ilexiconn.nationsgui.forge.server.json;

import com.google.gson.annotations.SerializedName;

public class CategoryJSON
{
    public String name;
    public String type;
    public boolean permission;
    public String icon;
    public CategoryItemJSON[] items = new CategoryItemJSON[0];
    @SerializedName("img")
    public String image;
    @SerializedName("img_hover")
    public String imageHover;
    public String url;

    public CategoryJSON()
    {
        this.imageHover = this.image;
    }
}
