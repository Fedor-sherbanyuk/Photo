package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static sample.CircleFind.imageToMat;

public class TriangleFind {

    protected ImageView triangleFindInImage(Image image) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Mat img = imageToMat(image);
        Mat destination = new Mat(img.rows(), img.cols(), img.type());
        //Преобразует изображение из одного цветового пространства в другое,
        // где исходное изображение хранится в двух плоскостях.
        // На данный момент эта функция поддерживает только преобразование YUV420 в RGB.
        Imgproc.cvtColor(img, destination, Imgproc.COLOR_BGR2GRAY);

        //Выполнить автоматическое выравнивание гистограммы для 8-битного изображения в
        // градациях серого можно с помощью метода equalizeHist()
        Imgproc.equalizeHist(destination, destination);
        //Размытие по Гауссу реализуется с помощью статического метода GaussianBlur() из класса Imgproc
        // В первом параметре указывается исходное изображение (глубина CV_8U, CV_16U, CV_16S, CV_32F или CV_64F),
        // а во втором — матрица, в которую будет записан результат операции. В параметре ksize указываются размеры ядра фильтра.
        // Ширина и высота ядра могут различаться, но оба они должны быть положительными и нечетными.
        Imgproc.GaussianBlur(destination, destination, new Size(5, 5), Core.BORDER_DEFAULT);

        //Выделить границы объектов на изображении позволяет статический метод Canny() из класса Imgproc
        Imgproc.Canny(destination, destination, 50, 200);
        //Кратко Отбрасывает четные строки и столбцы  Элементы исходного изображения должны состоять из одного канала
        // и иметь глубину цвета 8 битов или 32 бита (вещественное число).
        // Параметр thresh задает пороговое  значение, а параметр maxval — максимальное значение
        // (используется только в алгоритмах THRESH_BINARY и THRESH_BINARY_INV).
        // В параметре type можно указать следующие алгоритмы (константы из класса Imgproc):
        //THRESH_BINARY — если src(x, y) больше thresh, то dst(x, y) будет иметь значение maxval, в противном случае 0.
        Imgproc.threshold(destination, destination, 0, 255, Imgproc.THRESH_BINARY);
        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat edges = new Mat();
        Imgproc.Canny(destination, edges, 80, 200);
        //Находит контуры в бинарном изображении.
        // Функция извлекает контуры из бинарного изображения с помощью алгоритма CITE: Suzuki85.
        // Контуры — полезный инструмент для анализа формы, обнаружения и распознавания объектов
        Imgproc.findContours(destination, contours, new Mat(),
                Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (int i = 0, j = contours.size(); i < j; i++) {
            System.out.println(Imgproc.contourArea(contours.get(i)));
            Rect r = Imgproc.boundingRect(contours.get(i));
            System.out.println("boundingRect = " + r);
            double len = Imgproc.arcLength(new MatOfPoint2f(contours.get(i).toArray()), true);
            System.out.println("arcLength = " + len);
            if (14.8 < (len * len) / Imgproc.contourArea(contours.get(i)) && Imgproc.contourArea(contours.get(i)) < 200) {
                if (12 < (len * len) / Imgproc.contourArea(contours.get(i)) && Imgproc.contourArea(contours.get(i)) < 14.8) {
                    Imgproc.rectangle(img, new Point(r.x, r.y), new Point(r.x + r.width - 1, r.y + r.height - 1), new Scalar(0, 0, 255));
                    Imgcodecs.imencode(".png", img, matOfByte);
                    Image imgg = new Image(new ByteArrayInputStream(matOfByte.toArray()));
                    Image imageNew = imgg;
                    ImageView imageView = new ImageView(imageNew);
                    return imageView;
                }
            }
        }
        return null;
    }
}

