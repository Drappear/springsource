package ch1;

public class LgTV implements TV {

    private Speaker speaker;

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void powerOn() {
        System.out.println("LgTV power on");
    }

    @Override
    public void powerOff() {
        System.out.println("LgTV power off");
    }

    @Override
    public void volumeUp() {
        // System.out.println("LgTV volume up");
        speaker.volumeUp();
    }

    @Override
    public void volumeDown() {
        // System.out.println("LgTV volume down");
        speaker.volumeDown();
    }

}
