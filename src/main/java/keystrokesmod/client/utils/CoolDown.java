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
        // Utils.Player.sendMessageToSelf("Time started " + lasts/1000);
    }

    public boolean hasFinished() {
        // Utils.Player.sendMessageToSelf("Time finished");
        return System.currentTimeMillis() >= start + lasts;
    }

    public boolean firstFinish() {
        if (System.currentTimeMillis() >= start + lasts && !checkedFinish) {
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
        return System.currentTimeMillis() - this.start;
    }

    public long getTimeLeft() {
        return lasts - (System.currentTimeMillis() - start);
    }
}