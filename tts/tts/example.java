package tts;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;	
public class example {
    private static final String VOICENAME_kevin = "kevin16";

    public static void main(String[] args) {
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();

        voice = voiceManager.getVoice(VOICENAME_kevin);

        if (voice != null) {
            voice.allocate();
        }

        try {
            voice.speak("Hello, how are you today?");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (voice != null) {
                voice.deallocate();
            }
        }
    }
}
