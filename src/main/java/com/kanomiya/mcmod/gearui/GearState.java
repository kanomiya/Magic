package com.kanomiya.mcmod.gearui;

import java.util.Random;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import net.minecraft.util.ResourceLocation;

public class GearState
{
    private static Random rand = new Random();

    public int period;
    public int time;
    public int tooth_number = 6;
    public boolean rtl;

    public int shouldStop = -1;
    public boolean visible;

    protected boolean hasAlreadyNotified;
    protected Consumer<Integer> callback;

    public ResourceLocation icons;

    public GearState(int period, int tooth_number, boolean rtl, @Nonnull ResourceLocation icons)
    {
        if (period == 0) throw new RuntimeException("Period cannot be zero");
        if (tooth_number == 0) throw new RuntimeException("The number of Tooth cannot be zero");

        this.period = period;
        this.tooth_number = tooth_number;
        this.rtl = rtl;
        this.icons = icons;

        visible = true;
    }

    /**
     * 初期位相（initial_phase）とは違うので注意
     * initial_phaseは選択枠との位置調整用
     *
     * これはtimeを変更する
     *
     * @return
     */
    public GearState setPhaseRandom()
    {
        time = (int) (period *rand.nextDouble());
        return this;
    }


    public double getRotationRate()
    {
        return getPeriodRate();
    }

    public double getPeriodRate()
    {
        return (double) time/period;
    }

    public void reset()
    {
        time = 0;
        shouldStop = -1;
        hasAlreadyNotified = false;
    }

    public void tick()
    {
        if (time == shouldStop)
        {
            if (! hasAlreadyNotified)
            {
                callback.accept(getCurrentTooth());
                hasAlreadyNotified = true;
            }

            return;
        }

        time ++;
        if (period < time) time = 0;
    }

    public void setShouldStopAtTooth(int tooth_index)
    {
        this.shouldStop = (int) (period *getToothRate(tooth_index));
    }

    public void shouldStopAtNextTooth()
    {
        int tn = (int) (Math.floor(tooth_number *getPeriodRate()) +1);
        if (tooth_number <= tn) tn = 0;

        setShouldStopAtTooth(tn);
    }

    public double getToothRadianGap()
    {
        return getToothRate(1);
    }

    public double getToothRotationRate(int tooth_index)
    {
        return 1d -getToothRate(tooth_index);
    }

    public double getToothRate(int tooth_index)
    {
        return (double) tooth_index/tooth_number;
    }

    public int getCurrentTooth()
    {
        int num = (int) Math.ceil(getPeriodRate() *tooth_number); // 浮動小数の誤差を補正しなければならない
        return num < tooth_number ? num : 0;
    }


    public int x;
    public int y;
    public double initial_phase;

    public int radius_large_circle = 192;
    public int radius_small_circle = 48;

    public int slice_circle = 1000;

    public int size_icon = 64;

    public int color_circle = 0xffffffff;
    public int color_small_circle = color_circle;




}
