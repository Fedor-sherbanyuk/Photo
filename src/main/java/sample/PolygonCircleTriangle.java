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

public class PolygonCircleTriangle {

    protected ImageView polygonCircleTriangleFindInImage(Image image) throws IOException {
        Mat img = imageToMat(image);
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(imgGray, imgGray, 210, 255, Imgproc.THRESH_BINARY_INV);
        Mat hierarchy = new Mat();
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(imgGray, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        imgGray.setTo(Scalar.all(255));
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(imgGray, contours, i, new Scalar(0, 0, 0), -1);
        }
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2, 2), new Point(1, 1));
        Imgproc.dilate(imgGray, imgGray, element);
        Imgproc.erode(imgGray, imgGray, element);
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", imgGray, matOfByte);
        Image imgg = new Image(new ByteArrayInputStream(matOfByte.toArray()));
        Image imageNew = imgg;
        ImageView imageView = new ImageView(imageNew);
        return imageView;
    }
}
