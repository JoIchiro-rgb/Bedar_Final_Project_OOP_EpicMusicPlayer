import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // To be able to input  
import javax.sound.sampled.*; // Package to handle audio files, allowing playback and control of .wav files.

// AudioManager Class // Encapsulation
class AudioManager {
    protected Clip clip;

    public void load(String filePath) {
        try {
            File musicFile = new File(filePath);
            if (!musicFile.exists()) {
                System.out.println("File does not exist: " + filePath);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Error loading audio file: " + e.getMessage());
        }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Reset to the beginning
            clip.start();
            System.out.println("Playing...");
        } else {
            System.out.println("No audio loaded.");
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            System.out.println("Playback stopped.");
        } else if (clip == null) {
            System.out.println("No audio loaded to stop.");
        } else {
            System.out.println("Audio is not playing.");
        }
    }

    public void close() {
        if (clip != null) {
            clip.close();
            System.out.println("Audio manager closed.");
        }
    }
}

// Playlist Class // Encapsulation // manages a list of audio tracks. 
//It supports adding tracks and cycling through them, providing functionality for both sequential playback and auto-play.
class Playlist {
    private final List<String> tracks = new ArrayList<>();
    private int currentIndex = -1;

    public Playlist(List<String> initialTracks) {
        tracks.addAll(initialTracks);
        System.out.println("Playlist initialized with " + initialTracks.size() + " track(s).");
    }

    public void addTrack(String filePath) {
        tracks.add(filePath);
        System.out.println("Added to playlist: " + filePath);
    }

    public String getNextTrack() {
        if (tracks.isEmpty()) {
            return null;
        }
        currentIndex = (currentIndex + 1) % tracks.size();
        return tracks.get(currentIndex);
    }

    public boolean isEmpty() {
        return tracks.isEmpty();
    }
}

// MusicPlayer Class // Inheritance
class MusicPlayer extends AudioManager {
    private String currentTrack;
    private Playlist playlist;

    public void loadTrack(String filePath) {
        load(filePath);
        currentTrack = filePath;

        if (clip != null) {
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && clip.getFramePosition() == clip.getFrameLength()) {
                    autoPlayNext();
                }
            });
        }
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public String getCurrentTrack() {
        return currentTrack;
    }

    private void autoPlayNext() {
        if (playlist != null && !playlist.isEmpty()) {
            String nextTrack = playlist.getNextTrack();
            System.out.println("Auto-playing next track: " + nextTrack);
            stop();
            loadTrack(nextTrack);
            play();
        }
    }
}

// MusicPlayerController Class //Abstraction
class MusicPlayerController {
    private final MusicPlayer player;

    public MusicPlayerController() {
        player = new MusicPlayer();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==============================================");
            System.out.println("               Epic Music Player");
            System.out.println("           Journey of Odysseus of Ithica");
            System.out.println("==============================================");
            System.out.println("                 Choose a Saga                 ");
            System.out.println("----------------------------------------------");
            System.out.println("  1. Troy Saga");
            System.out.println("  2. Cyclops Saga");
            System.out.println("  3. Ocean Saga");
            System.out.println("  4. Circe Saga");
            System.out.println("  5. Underworld Saga");
            System.out.println("  6. Thunder Saga");
            System.out.println("  7. Wisdom Saga");
            System.out.println("  8. Vengeance Saga");
            System.out.println("  9. Exit");
            System.out.println("----------------------------------------------");
            System.out.print("Select a Saga (1-9): ");
            int sagaChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after nextInt()

            Playlist selectedPlaylist;

            switch (sagaChoice) {
                case 1: // Troy Saga
                    selectedPlaylist = new Playlist(List.of(
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Troy Saga(1)\\The Horse and the Infant - 1.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Troy Saga(1)\\Just a Man - 2.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Troy Saga(1)\\Full Speed Ahead - 3.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Troy Saga(1)\\Open Arms - 4.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Troy Saga(1)\\Warrior of the Mind - 5.wav"
                    ));
                    break;
                case 2: // Cyclops Saga
                    selectedPlaylist = new Playlist(List.of(
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Cyclops Saga(2)\\Polyphemus - 6.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Cyclops Saga(2)\\Survive - 7.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Cyclops Saga(2)\\Remember Them - 8.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Cyclops Saga(2)\\My Goodbye - 9.wav"
                    ));
                    break;
                case 3: // Ocean Saga
                    selectedPlaylist = new Playlist(List.of(
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Ocean Saga(3)\\Storm - 10.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Ocean Saga(3)\\Luck Runs Out - 11.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Ocean Saga(3)\\Keep Your Friends Close - 12.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Ocean Saga(3)\\Ruthlessness -13.wav"
                    ));
                    break;
                    case 4: // Circe Saga
                    selectedPlaylist = new Playlist(List.of(
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Circe Saga(4)\\Puppeteer - 14.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Circe Saga(4)\\Wouldn't You Like - 15.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Circe Saga(4)\\Done For - 16.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Circe Saga(4)\\There Are Other Ways - 17.wav"
                    ));
                    break;
                    case 5: // Underworld Saga
                    selectedPlaylist = new Playlist(List.of(
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Underworld Saga(5)\\The Underworld - 18.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Underworld Saga(5)\\No Longer You -19.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Underworld Saga(5)\\Monster -20.wav"
                    ));
                    break;
                    case 6: // Thunder Saga
                    selectedPlaylist = new Playlist(List.of(
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Thunder Saga(6)\\Suffering - 21.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Thunder Saga(6)\\Different Beast - 22.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Thunder Saga(6)\\Scylla - 23.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Thunder Saga(6)\\Mutiny - 24.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Thunder Saga(6)\\Thunder Bringer - 25.wav"
                    ));
                    break;
                    case 7: // Wisdom Saga
                    selectedPlaylist = new Playlist(List.of(
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Wisdom Saga(7)\\Legendary - 26.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Wisdom Saga(7)\\Little Wolf -27.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Wisdom Saga(7)\\Well Be Fine - 28.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Wisdom Saga(7)\\Love in Paradise - 29.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Wisdom Saga(7)\\God Games - 30.wav"
                    ));
                    break;
                    case 8: // Vengeance Saga
                    selectedPlaylist = new Playlist(List.of(
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Vengeance Saga(8)\\Not Sorry for Loving You -31.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Vengeance Saga(8)\\Dangerous - 32.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Vengeance Saga(8)\\Charybdis - 33.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Vengeance Saga(8)\\Get in the Water - 34.wav",
                        "C:\\Users\\Jomar Ichiro\\Desktop\\JAVA PROJECT\\EpicMusicPlayer\\src\\Vengeance Saga(8)\\Six Hundred Strike - 35.wav"
                    ));
                    break;
                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            // Play the selected Saga
            playSaga(selectedPlaylist, scanner);
        }
    }

    public void playSaga(Playlist playlist, Scanner scanner) {
        if (playlist.isEmpty()) {
            System.out.println("The selected saga playlist is empty.");
            return;
        }

        player.setPlaylist(playlist); // Set the playlist for auto-play
        boolean isRunning = true;

        while (isRunning) {
            String currentTrack = playlist.getNextTrack();
            System.out.println("Loading track: " + currentTrack);

            player.stop();
            player.loadTrack(currentTrack);
            player.play();

            while (true) {
                System.out.println("\nControls: [pause] [resume] [stop] [next] [exit]");
                System.out.print("Enter command: ");
                String command = scanner.nextLine().toLowerCase();

                switch (command) {
                    case "pause":
                        player.stop();
                        System.out.println("Paused.");
                        break;
                    case "resume":
                        player.play();
                        System.out.println("Resumed.");
                        break;
                    case "stop":
                        player.stop();
                        System.out.println("Playback stopped.");
                        isRunning = false;
                        break;
                    case "next":
                        System.out.println("Loading next track...");
                        break;
                    case "exit":
                        System.out.println("Exiting saga...");
                        player.stop();
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid command. Try again.");
                }

                // Break the inner loop to proceed to the next track
                if (command.equals("next") || command.equals("stop") || command.equals("exit")) {
                    break;
                }
            }
        }
    }
}

// Main Class
public class EpicMusicPlayer {
    public static void main(String[] args) {
        MusicPlayerController controller = new MusicPlayerController();
        controller.start();
    }
}
