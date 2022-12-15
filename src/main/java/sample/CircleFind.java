package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class CircleFind {
    public static Mat imageToMat(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        byte[] buffer = new byte[width * height * 4];

        PixelReader reader = image.getPixelReader();
        WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();
        reader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);

        Mat mat = new Mat(height, width, CvType.CV_8UC4);
        mat.put(0, 0, buffer);
        return mat;
    }

    protected ImageView circleFindInImage(Image image) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Mat img = imageToMat(image);
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        Mat circles = new Mat();
        Imgproc.HoughCircles(imgGray, circles, Imgproc.HOUGH_GRADIENT, 2, imgGray.rows() / 4);
        Mat result = new Mat(img.size(), CvType.CV_8UC3, new Scalar(1.0f, 1.0f, 1.0f));
        for (
                int i = 0, r = circles.rows();
                i < r; i++) {
            for (int j = 0, c = circles.cols(); j < c; j++) {
                double[] circle = circles.get(i, j);
                Imgproc.circle(img, new Point(circle[0], circle[1]), (int) circle[2], new Scalar(255, 0, 0));
            }
        }
        Imgcodecs.imencode(".png", img, matOfByte);
        Image imgg = new Image(new ByteArrayInputStream(matOfByte.toArray()));
        Image imageNew = imgg;
        ImageView imageView = new ImageView(imageNew);
        return imageView;
    }
}
