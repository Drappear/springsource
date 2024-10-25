package ch1;

public class SamsungTV implements TV {

    private Speaker speaker;

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    @Override
    public void powerOn() {
        System.out.println("SamsungTV power on");
    }

    @Override
    public void powerOff() {
        System.out.println("SamsungTV power off");
    }

    @Override
    public void volumeUp() {
        speaker.volumeUp();
    }

    @Override
    public void volumeDown() {
        speaker.volumeDown();
    }

}
