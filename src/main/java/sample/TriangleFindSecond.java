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

public class TriangleFindSecond {

    protected ImageView triangleFindInImageSecond(Image image) throws IOException {
        Mat img = imageToMat(image);
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges, 80, 200);
        Mat edgesCopy = edges.clone(); // Создаем копию
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(edgesCopy, contours, new Mat(),Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);
        MatOfPoint shape = new MatOfPoint();
        if (contours.size() >= 3) {
            shape = contours.get(3);
            Imgproc.drawContours(img, contours, 2, new Scalar(0,0,255));
        }
        double min = Double.MAX_VALUE, value = 0;
        int index = -1;
        for (int i = 0, j = contours.size(); i < j; i++) {
            value = Imgproc.matchShapes(contours.get(i), shape, Imgproc.CV_CONTOURS_MATCH_I3, 0);
            if (value < min) {
                min = value;
                index = i;
            }
            System.out.println("CV_CONTOURS_MATCH_I1: " + i + " " + Imgproc.matchShapes(contours.get(i),
                    shape,Imgproc.CV_CONTOURS_MATCH_I1, 0));
            System.out.println("CV_CONTOURS_MATCH_I2: " + i + " " + Imgproc.matchShapes(contours.get(i),
                    shape, Imgproc.CV_CONTOURS_MATCH_I2, 0));
            System.out.println("CV_CONTOURS_MATCH_I3: " + i + " " + Imgproc.matchShapes(contours.get(i),
                    shape, Imgproc.CV_CONTOURS_MATCH_I3, 0));
        }
        Rect r = Imgproc.boundingRect(contours.get(index));
        Imgproc.rectangle(img, new Point(r.x, r.y), new Point(r.x + r.width - 1, r.y + r.height - 1),
                new Scalar(0,0,255));
        System.out.println("Лучшее совпадение: индекс " + index + " значение " + min);
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", img, matOfByte);
        Image imgg = new Image(new ByteArrayInputStream(matOfByte.toArray()));
        Image imageNew = imgg;
        ImageView imageView = new ImageView(imageNew);
        return imageView;
    }
}

