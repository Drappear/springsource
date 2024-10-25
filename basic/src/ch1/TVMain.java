package ch1;

public class TVMain {
    public static void main(String[] args) {
        // LgTV lgTv = new LgTV(new BritzSpeaker());

        // lgTv.setBritzSpeaker(new BritzSpeaker());

        // lgTv.powerOn();
        // lgTv.volumeUp();
        // lgTv.volumeDown();
        // lgTv.powerOff();

        // SamsungTV samsungTV = new SamsungTV();
        // samsungTV.setBritzSpeaker(new BritzSpeaker());
        // samsungTV.powerOn();
        // samsungTV.volumeUp();
        // samsungTV.volumeDown();
        // samsungTV.powerOff();

        TV tv = new SamsungTV();
        // ((SamsungTV) tv).setSpeaker(new BritzSpeaker());

        tv = new LgTV();
        ((LgTV) tv).setSpeaker(new BritzSpeaker());

        tv.powerOn();
        tv.volumeUp();
        tv.volumeDown();
        tv.powerOff();
    }
}
