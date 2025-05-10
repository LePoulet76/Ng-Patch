package net.ilexiconn.nationsgui.forge.server.json.registry.armor;

public class JSONArmorSet
{
    private JSONArmor[] armorSet;

    private JSONArmorSet(JSONArmor[] armorSet)
    {
        this.armorSet = armorSet;
    }

    public static JSONArmorSet create(JSONArmor helmet, JSONArmor chestplate, JSONArmor leggings, JSONArmor boots)
    {
        return new JSONArmorSet(new JSONArmor[] {helmet, chestplate, leggings, boots});
    }

    public JSONArmor getHelmet()
    {
        return this.armorSet[0];
    }

    public JSONArmor getChestplate()
    {
        return this.armorSet[1];
    }

    public JSONArmor getLeggings()
    {
        return this.armorSet[2];
    }

    public JSONArmor getBoots()
    {
        return this.armorSet[3];
    }

    public JSONArmor[] getArmorSet()
    {
        return this.armorSet;
    }
}
