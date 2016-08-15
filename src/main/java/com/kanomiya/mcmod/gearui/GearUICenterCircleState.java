package com.kanomiya.mcmod.gearui;

public class GearUICenterCircleState
{
    public int slice = 1000;

    public int radius = 48;
    public int color = 0xffff0000;

    private final GearUI ui;

    public GearUICenterCircleState(GearUI ui)
    {
        this.ui = ui;
    }

    public int getX()
    {
        return ui.getGear(0).radius_large_circle +(int) (ui.getGear(0).radius_large_circle*Math.cos(Math.PI));
    }

    public int getY()
    {
        return (int) (ui.getGear(0).radius_large_circle*Math.sin(Math.PI));
    }

}
