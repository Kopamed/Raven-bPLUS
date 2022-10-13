package keystrokesmod.client.utils;

public class CoolDown {
    private long start;
    private long lasts;
    private boolean checkedFinish;

    public CoolDown(long lasts) {
        this.lasts = lasts;
    }

    public void start() {
        this.start = System.currentTimeMillis();
        checkedFinish = false;
    }

    public boolean hasFinished() {
        return System.currentTimeMillis() >= (start + lasts);
    }

    public boolean firstFinish() {
        if ((System.currentTimeMillis() >= (start + lasts)) && !checkedFinish) {
            checkedFinish = true;
            return true;
        }
        return false;
    }

    public void setCooldown(long time) {
        this.lasts = time;
    }

    public long getCooldownTime() {
        return lasts;
    }

    public long getElapsedTime() {
        long et = System.currentTimeMillis() - this.start;
        return et > lasts ? lasts : et;
    }

    public long getTimeLeft() {
        long tl = lasts - (System.currentTimeMillis() - start);
        return tl < 0 ? 0 : tl;
    }
}