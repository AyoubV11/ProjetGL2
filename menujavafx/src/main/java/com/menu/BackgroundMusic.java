





import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.File;

public class BackgroundMusic extends Application {
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        // 🗂 Chemin vers le fichier MP3 (assurez-vous qu'il est accessible)
        String filePath = "chemin/vers/ton/fichier.mp3"; // Remplace par le bon chemin
        Media media = new Media(new File(filePath).toURI().toString());

        // 🎵 Initialisation du MediaPlayer
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 🔁 Lecture en boucle
        mediaPlayer.setVolume(0.5); // 🔊 Volume initial à 50%
        mediaPlayer.play();

        // 🎚 Slider pour régler le volume
        Slider volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setShowTickLabels(true);

        // Label pour afficher le volume
        Label volumeLabel = new Label("Volume : 50%");

        // Mettre à jour le volume et le label à chaque changement de slider
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mediaPlayer.setVolume(newVal.doubleValue());
            volumeLabel.setText("Volume : " + (int)(newVal.doubleValue() * 100) + "%");
        });

        // 📌 Interface
        VBox root = new VBox(10, volumeLabel, volumeSlider);
        Scene scene = new Scene(root, 400, 200);

        primaryStage.setTitle("Musique de Fond JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
