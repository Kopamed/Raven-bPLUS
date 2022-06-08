package keystrokesmod.client.utils;

public class CoolDown {
    private long start;
    private long lasts;

    public CoolDown(long lasts){
        this.lasts = lasts;
    }

    public void start(){
        this.start = System.currentTimeMillis();
        //Utils.Player.sendMessageToSelf("Time started " + lasts/1000);
    }

    public boolean hasFinished(){
        if(System.currentTimeMillis() >= start + lasts) {
            //Utils.Player.sendMessageToSelf("Time finished");
            return true;
        }
        return false;
    }

    public void setCooldown(long time){
        //Utils.Player.sendMessageToSelf("Set cooldown to " + time);
        this.lasts = time;
    }

    public long getElapsedTime(){
        return System.currentTimeMillis() - this.start;
    }

    public long getTimeLeft(){
        return lasts - (System.currentTimeMillis() - start);
    }
}
