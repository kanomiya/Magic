package com.kanomiya.mcmod.gearui;

import static com.kanomiya.mcmod.gearui.render.RenderHelper.*;

import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGearUI extends Gui
{
    private final GearUIRenderer renderer;
    private final GearUI ui;


    public RenderGearUI(GearUI ui)
    {
        this.renderer = new GearUIRenderer();
        this.ui = ui;
    }


    @SubscribeEvent @SideOnly(Side.CLIENT)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        if (! ui.visible) return ;

        int width = event.getResolution().getScaledWidth();
        int height = event.getResolution().getScaledHeight();


        ui.forEach((index, gear) ->
        {
            renderer.renderGear(gear, width, height);
        });


        int centerX = width;
        int centerY = height;

        drawCircle(centerX +ui.centerCircle.getX(), centerY +ui.centerCircle.getY(), ui.centerCircle.radius, ui.centerCircle.slice, ui.centerCircle.color);


        ui.tickAll();
    }


}
