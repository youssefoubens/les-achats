package net.youssef.gestion_achats.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FxmlLoader {
    private final ApplicationContext context;

    public FxmlLoader(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Loads the FXML file and returns the Parent node.
     * This method does not cache the Parent node.
     *
     * @param fxmlPath the path to the FXML file
     * @return the Parent node loaded from the FXML file
     * @throws IOException if an error occurs while loading the FXML file
     */
    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(context::getBean);
        return loader.load();
    }

    /**
     * Loads the FXML file and returns its controller.
     * This method does not cache the controller.
     *
     * @param fxmlPath the path to the FXML file
     * @return the controller of the FXML file
     * @throws IOException if an error occurs while loading the FXML file
     */
    public Object getController(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        loader.setControllerFactory(context::getBean);
        loader.load(); // Load the FXML file
        return loader.getController(); // Return the controller
    }
}
