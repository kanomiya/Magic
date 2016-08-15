package com.kanomiya.mcmod.gearui.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderHelper
{
    public static Minecraft minecraft = Minecraft.getMinecraft();

    public static final double DOUBLE_PI = 2d *Math.PI;





    public static void drawCircle(int centerX, int centerY, double radius, int n, int color)
    {
        float alpha = (color >> 24 & 255) / 255.0F;
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;


        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(red, green, blue, alpha);

        vertexbuffer.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION);

        for (int i=0; i<n; i++)
        {
            double rate = (double) i/n;

            double x = centerX +radius *Math.cos(2.0d *Math.PI *rate);
            double y = centerY +radius *Math.sin(2.0d *Math.PI *rate);

            vertexbuffer.pos(x, y, 0d).endVertex();
        }

        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void bindTexture(ResourceLocation texture)
    {
        minecraft.getTextureManager().bindTexture(texture);
    }

}
