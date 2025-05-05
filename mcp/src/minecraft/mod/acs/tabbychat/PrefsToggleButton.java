package acs.tabbychat;

public class PrefsToggleButton extends PrefsButton
{
    protected int onColor = 1722148836;
    protected int offColor = -1728053248;
    protected String onTitle = "On";
    protected String offTitle = "Off";

    public PrefsToggleButton()
    {
        super(9999, 0, 0, 1, 1, "");
    }

    public PrefsToggleButton(int _id, int _x, int _y, int _w, int _h)
    {
        super(_id, _x, _y, _w, _h, "Off");
        this.bgcolor = this.offColor;
        this.displayString = this.offTitle;
    }

    public boolean state()
    {
        return this.displayString.equalsIgnoreCase(this.onTitle);
    }

    public void toggle()
    {
        if (this.displayString.equalsIgnoreCase(this.offTitle))
        {
            this.displayString = this.onTitle;
            this.bgcolor = this.onColor;
        }
        else
        {
            this.displayString = this.offTitle;
            this.bgcolor = this.offColor;
        }
    }

    public void updateTo(boolean theVar)
    {
        if (theVar)
        {
            this.displayString = this.onTitle;
            this.bgcolor = this.onColor;
        }
        else
        {
            this.displayString = this.offTitle;
            this.bgcolor = this.offColor;
        }
    }
}
