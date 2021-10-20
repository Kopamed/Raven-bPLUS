package me.kopamed.lunarkeystrokes.keystroke.setting.settings;

import me.kopamed.lunarkeystrokes.keystroke.setting.Setting;

public class TickSetting extends Setting {
    private boolean ticked;

    public TickSetting(String name, boolean ticked){
        super(name, Type.TICK);
        this.ticked = ticked;
    }

    public void toggle(){
        this.ticked = !this.ticked;
    }

    public boolean isTicked(){
        return this.ticked;
    }

    public void setTicked(boolean shouldTick){
        this.ticked = shouldTick;
    }
}
