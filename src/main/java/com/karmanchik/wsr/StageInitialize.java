package com.karmanchik.wsr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.karmanchik.wsr.JavaFXApplication.*;

@Log4j2
@Component
public class StageInitialize implements ApplicationListener<StageReadyEvent> {
    private final ApplicationContext context;

    @Value("${classpath:/ui/main.fxml}")
    private Resource mainResource;
    @Value("${spring.ui.title}")
    private String appTitle;

    public StageInitialize(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(mainResource.getURL());
            loader.setControllerFactory(context::getBean);
            Parent parent = loader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 900, 600));
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setTitle(appTitle);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.toString(), ButtonType.OK).showAndWait();
            log.error(e.getMessage(), e);
        }
    }
}
