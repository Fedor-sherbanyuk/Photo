package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static sample.CircleFind.imageToMat;

public class TriangleFindThree {

    protected ImageView triangleFindInImageThree(Image image) throws IOException {
        Mat img = imageToMat(image);
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        Mat result = img.clone();
        Mat thresh = new Mat(imgGray.rows(), imgGray.cols(), imgGray.type());
        Imgproc.threshold(imgGray, thresh, 127, 255, Imgproc.THRESH_BINARY_INV);
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(thresh.clone(), contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        for (int i = 0; i < contours.size(); i++) {
            MatOfPoint2f approx = new MatOfPoint2f();
            double len = Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true) * .01;
            Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), approx, len, true);
            MatOfPoint matOfPoint = new MatOfPoint();
            approx.convertTo(matOfPoint, CvType.CV_32S);
            if (Math.abs(Imgproc.contourArea(contours.get(i))) < 1000
                    || !Imgproc.isContourConvex(matOfPoint))//15426
                continue;
            //triangle
            if (approx.rows() == 3 && approx.cols() == 1) {
                Imgproc.drawContours(result, contours, (int) i, new Scalar(255, 0, 0));
            }
        }
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", result, matOfByte);
        Image imgg = new Image(new ByteArrayInputStream(matOfByte.toArray()));
        Image imageNew = imgg;
        ImageView imageView = new ImageView(imageNew);
        return imageView;
    }
}

