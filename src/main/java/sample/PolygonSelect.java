package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static sample.CircleFind.imageToMat;

public class PolygonSelect {
    protected ImageView polygonFindInImage(Image image) throws IOException {
        Mat img = imageToMat(image);
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);

        Imgproc.GaussianBlur(img, img, new Size(5, 5), Core.BORDER_DEFAULT);

        Imgproc.Canny(img, img, 50, 200, 3);
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(img, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(img, contours, i, new Scalar(0, 0, 255), 2);
        }
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", img, matOfByte);
        Image imgg = new Image(new ByteArrayInputStream(matOfByte.toArray()));
        Image imageNew = imgg;
        ImageView imageView = new ImageView(imageNew);
        return imageView;
    }
}
