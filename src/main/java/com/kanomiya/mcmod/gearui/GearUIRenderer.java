package com.kanomiya.mcmod.gearui;

import static com.kanomiya.mcmod.gearui.render.RenderHelper.*;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GearUIRenderer extends Gui
{

    public void renderGear(GearState state, int width, int height)
    {
        if (! state.visible) return;

        int centerX = width -state.radius_large_circle;
        int centerY = height;

        drawCircle(centerX +state.x, centerY +state.y, state.radius_large_circle, state.slice_circle, state.color_circle);

        double rotation_radian_rate = state.getRotationRate() +state.initial_phase;


        for (int tooth_index=0; tooth_index<state.tooth_number; tooth_index++)
        {
            double tooth_radian_rate = state.getToothRotationRate(tooth_index);

            int dx = (int) (state.radius_large_circle *Math.cos(DOUBLE_PI * (rotation_radian_rate +tooth_radian_rate)));
            int dy = (int) (state.radius_large_circle *Math.sin(DOUBLE_PI * (rotation_radian_rate +tooth_radian_rate)));


            drawCircle(centerX +state.x +dx, centerY +state.y +dy, state.radius_small_circle, state.slice_circle, state.color_small_circle);

            bindTexture(state.icons);
            GlStateManager.enableAlpha();
            drawTexturedModalRect(centerX +state.x +dx -state.size_icon/2, centerY +state.y +dy -state.size_icon/2, 0,state.size_icon*tooth_index, state.size_icon, state.size_icon);
            GlStateManager.disableAlpha();

            // GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
            drawString(minecraft.fontRendererObj, "T" + tooth_index, centerX +state.x +dx, centerY +dy +state.y, 0xffffffff);
            // GL11.glPopAttrib();

        }

        bindTexture(Gui.ICONS);

    }




}
