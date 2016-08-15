package com.kanomiya.mcmod.magic;

import org.apache.logging.log4j.Logger;

import com.kanomiya.mcmod.gearui.GearState;
import com.kanomiya.mcmod.gearui.GearUI;
import com.kanomiya.mcmod.gearui.RenderGearUI;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.actors.threadpool.Arrays;

@Mod(modid = MagicMod.MODID, name="Magic", version="@VERSION@")
public class MagicMod
{
    public static final String MODID = "com.kanomiya.mcmod.magic";

    @Mod.Instance(MODID)
    public static MagicMod instance;

    public static Logger logger;

    public static GearUI gear_ui;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        /*
        gear_ui = new GearUI((rslt) ->
        {

        }, new GearState(450, 5, false), new GearState(300, 4, true), new GearState(200, 3, false), new GearState(500, 7, true));
        */

        ResourceLocation gearUI_icons = new ResourceLocation(MagicMod.MODID, "textures/gui/icons.png");

        gear_ui = new GearUI((rslt) ->
        {
            System.out.println(Arrays.asList(rslt));
        }, new GearState(450, 5, false, gearUI_icons), new GearState(300, 4, true, gearUI_icons),
           new GearState(200, 3, false, gearUI_icons), new GearState(500, 7, true, gearUI_icons));
        MagicMod.gear_ui.visible = true;

        if (event.getSide().isClient())
        {
            ClientRegistry.registerKeyBinding(KeyHandler.KEY_GEAR_UI_STOP);
            ClientRegistry.registerKeyBinding(KeyHandler.KEY_GEAR_UI_RESET);

            MinecraftForge.EVENT_BUS.register(new RenderGearUI(gear_ui));
            MinecraftForge.EVENT_BUS.register(new KeyHandler());
        }

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

}
