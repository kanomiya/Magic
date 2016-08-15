package com.kanomiya.mcmod.gearui;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class GearUI
{
    public static final int MAX_GEAR = 4;

    private final GearState[] gears;
    public final GearUICenterCircleState centerCircle;
    public boolean visible;

    boolean hasAlreadyNotified;
    protected Integer[] resultSet;
    protected Consumer<Integer[]> callback;

    public GearUI(Consumer<Integer[]> callback, GearState... gears)
    {
        if (gears.length == 0) throw new RuntimeException("Gear amount cannot be zero");
        if (MAX_GEAR < gears.length) throw new RuntimeException("Gear amount cannot be above " + MAX_GEAR);

        this.callback = callback;
        this.gears = gears;
        this.centerCircle = new GearUICenterCircleState(this);

        resultSet = new Integer[gears.length];

        forEach((index, gear) ->
        {
            GearState gear0 = gears[0];

            gear.callback = tooth ->
            {
                resultSet[index] = tooth;

                if (! hasAlreadyNotified)
                {
                    boolean shouldNotify = true;
                    for (int i=0,len=resultSet.length; i<len; i++)
                    {
                        if (resultSet[i] == null) shouldNotify = false;
                    }

                    if (shouldNotify)
                    {
                        callback.accept(resultSet.clone());
                        hasAlreadyNotified = true;
                    }
                }

            };

            if (index == 1)
            {
                gear.x = gear0.radius_large_circle*2;
                gear.initial_phase = 0.5d;
            } else if (index == 2)
            {
                gear.x = gear0.radius_large_circle;
                gear.y = -gear0.radius_large_circle;
                gear.initial_phase = 0.25d;
            } else if (index == 3)
            {
                gear.x = gear0.radius_large_circle;
                gear.y = gear0.radius_large_circle;
                gear.initial_phase = 0.75d;
            }


        });
    }

   //  public double gap_rotation_right_circle = 0.375d;

    public void reset()
    {
        forEach((index, gear) ->
        {
            resultSet[index] = null;
            gear.reset();
        });

        hasAlreadyNotified = false;
    }

    public void tickAll()
    {
        forEach((i, gear) -> gear.tick());
    }


    public int getGearCenterX(int index)
    {
        return 0;
    }

    public int getGearCenterY(int index)
    {
        return 0;
    }

    public int getGearNumber()
    {
        return gears.length;
    }

    public GearState getGear(int index)
    {
        return gears[index];
    }


    public void forEach(BiConsumer<Integer, GearState> func)
    {
        for (int i=0,len=gears.length; i<len; i++) func.accept(i, gears[i]);
    }


}
