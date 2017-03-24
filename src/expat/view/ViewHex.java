package expat.view;

import expat.model.board.Hex;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

/**
 * Created by vanonir on 24.03.2017.
 */
public class ViewHex extends Polygon {
    double[] xPoints = new double[]{0.25, 0.75, 1, 0.75, 0.25, 0};
    double[] yPoints = new double[]{0, 0, 0.43879, 0.87758, 0.87758, 0.43879};
    double xOffset = 0.0;
    double yOffset = 0.0;
    double size = 100;
    Image wool = new Image("expat/img/Wiese_Sheep.png"); //TODO; set better path, use String variable;

    public ViewHex(double xOffset, double yOffset) {// TODO: Implement Hex as Parameter and CoordinateCalculator which calculates die actual position of the hex and returns xOffset and yOffset OR calculate offset for whole child.
        this.xOffset = xOffset;
        this.yOffset = yOffset;

        for (int i = 0; i < 6; i++) {
            this.getPoints().addAll(new Double[]{xPoints[i]*size,yPoints[i]*size});
        }

        this.setFill(new ImagePattern(wool,0,0,1,1,true));
        this.setLayoutX(xOffset);
        this.setLayoutY(yOffset);

    }
}
