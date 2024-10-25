package ch1;

public class SonySpeaker implements Speaker {

    @Override
    public void volumeUp() {
        System.out.println("sony volume up");
    }

    @Override
    public void volumeDown() {
        System.out.println("sony volume down");
    }

}
