package com.kanomiya.mcmod.magic;

import org.lwjgl.input.Keyboard;

import com.kanomiya.mcmod.gearui.GearState;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyHandler
{
    public static final KeyBinding KEY_GEAR_UI_STOP = new KeyBinding("Key.GearUI.Stop", Keyboard.KEY_V, MagicMod.MODID);
    public static final KeyBinding KEY_GEAR_UI_RESET = new KeyBinding("Key.GearUI.Reset", Keyboard.KEY_B, MagicMod.MODID);

    protected int next_stop = 0;

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event)
    {
        if (KEY_GEAR_UI_RESET.isPressed())
        {
            next_stop = 0;

            MagicMod.gear_ui.reset();
        }

        if (KEY_GEAR_UI_STOP.isPressed())
        {

            if (0 <= next_stop)
            {
                GearState gear = MagicMod.gear_ui.getGear(next_stop);
                gear.shouldStopAtNextTooth();

                next_stop ++;
                if (MagicMod.gear_ui.getGearNumber() <= next_stop)
                {
                    /* 通知はCallbackを介して行われます。ここはデバッグ用

                    StringBuilder sb = new StringBuilder();

                    MagicMod.gear_ui.forEach((index, g) ->
                    {
                        sb.append(index + ": " + g.getCurrentTooth() + " / ");
                    });
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString(sb.toString()));

                    */
                    next_stop = -1;
                }
            }

        }
    }

}
