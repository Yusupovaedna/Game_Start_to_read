package  com.company;
import com.sun.speech.freetts.*;


public class TextToSpeech {
    //String voiceName = "kevin16";
    Voice voice;


    public TextToSpeech(String words) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {

                voice.setRate(70);// Setting the rate of the voice
                voice.setPitch(80);// Setting the Pitch of the voice
                voice.setVolume(50);// Setting the volume of the voice
                voice.setStyle("breathy");


                SpeakText(words);// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }

    public void SpeakText(String words) {
        voice.speak(words);
    }}