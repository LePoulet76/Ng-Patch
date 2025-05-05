package net.ilexiconn.nationsgui.forge.server.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemRecord;

public class ChristmasRecord extends ItemRecord
{
    public ChristmasRecord()
    {
        super(3336, "christmas");
        this.setTextureName("nationsgui:christmas_record");
        this.setUnlocalizedName("christmas_record");
    }

    /**
     * Return the title for this record.
     */
    public String getRecordTitle()
    {
        return I18n.getString(this.getUnlocalizedName() + ".name");
    }
}
