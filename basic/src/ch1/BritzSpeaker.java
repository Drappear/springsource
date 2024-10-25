package ch1;

public class BritzSpeaker implements Speaker {

    @Override
    public void volumeUp() {
        System.out.println("britz volume up");
    }

    @Override
    public void volumeDown() {
        System.out.println("britz volume down");
    }

}
