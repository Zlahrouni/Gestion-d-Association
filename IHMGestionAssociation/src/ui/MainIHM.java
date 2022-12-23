package ui;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * L'IHM de l'application.

 * @author Ziad Lahrouni / Hanane Erraji / Kilian Malherbe / Youenn Robitzer
 *
 */
public final class MainIHM extends Application {
  
  @Override
  public void start(Stage primaryStage) {
    
    try {
      final URL url = getClass().getResource("association.fxml");
      final FXMLLoader fxmlLoader = new FXMLLoader(url);
      final VBox root = (VBox) fxmlLoader.load();
      final Scene scene = new Scene(root, 850, 600);
      primaryStage.setScene(scene);
      primaryStage.setResizable(true);
      Controleur c = fxmlLoader.getController();
      c.setStage(primaryStage);
    } catch (IOException ex) {
      System.err.println("Erreur au chargement : " + ex);
    }
    primaryStage.setTitle("Association");
    primaryStage.show();
  }
  
  /**
   * Lancement de l'application.

   * @param args Arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
