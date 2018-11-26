package magicBoard;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class PaintController {

    @FXML
    private Canvas canvas_1;

    @FXML
    private Canvas canvas_2;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private CheckBox eraser;

    public void initialize() {
        GraphicsContext g = canvas_1.getGraphicsContext2D();
        GraphicsContext g2 = canvas_2.getGraphicsContext2D();

        canvas_1.setOnMouseDragged(e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            double normalX = x/g.getCanvas().getWidth();
            double normalY = y/g.getCanvas().getHeight();
            double targetWidth = g2.getCanvas().getWidth();
            double targetHeight = g2.getCanvas().getHeight();

            double xFlip = targetWidth-normalX*targetWidth;
            double yFlip = normalY*targetHeight;



            if (eraser.isSelected()) {
                g.clearRect(x, y, size, size);
            } else {
                g.setFill(colorPicker.getValue());
                g.fillRect(x, y, size, size);
            }

            if (eraser.isSelected()) {
                g2.clearRect(xFlip, yFlip, size, size);
            } else {
                g2.setFill(colorPicker.getValue());
                g2.fillRect(xFlip, yFlip, size, size);
            }

        });
    }

    public void onSave() {
        try {
            Image snapshot = canvas_1.snapshot(null, null);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e);
        }
    }

    public void onExit() {
        Platform.exit();
    }
}