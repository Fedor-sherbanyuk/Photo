package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;

public class FXMLDocumentController implements Initializable {
    static {

    }

    private ImageEdit Images = new ImageEdit();
    @FXML
    private CheckBox tenu;
    @FXML
    private CheckBox pazmutue;
    @FXML
    private CheckBox light;
    @FXML
    private CheckBox innshadow;
    @FXML
    private Slider sovetFotoEffect;
    @FXML
    private Slider svetitca;
    @FXML
    private Slider pazmutueGauss;
    @FXML
    private Slider pazmutueBloom;
    @FXML
    private Slider motionRadius;
    @FXML
    private Slider motionAngle;
    @FXML
    private Slider bright;
    @FXML
    private Slider saturation;
    @FXML
    private Slider ottenok;
    @FXML
    private Slider contrast;
    @FXML
    private StackPane root;
    @FXML
    private Button buttonLoadImage = new Button();
    @FXML
    private Button buttonSaveImage = new Button();
    @FXML
    private Button resetBtn = new Button();
    @FXML
    private Button videoButton = new Button();
    @FXML
    private Button circleFindButton = new Button();
    @FXML
    private Button triangleFindButton = new Button();
    @FXML
    private Button infobtn = new Button();
    @FXML
    private ImageView myImageView = new ImageView();

    @FXML
    private void reset() {
        contrast.setValue(0);
        ottenok.setValue(0);
        saturation.setValue(0);
        bright.setValue(0);
        tenu.setSelected(false);
        pazmutue.setSelected(false);
        sovetFotoEffect.setValue(0);
        svetitca.setValue(0);
        pazmutueGauss.setValue(0);
        pazmutueBloom.setValue(1);
        motionRadius.setValue(0);
        motionAngle.setValue(0);
        innshadow.setSelected(false);
        light.setSelected(false);
    }

    @FXML
    private void triangleFindButton() {

        // Найти треугольник начало
        JFrame window = new JFrame();
        JLabel screen = new JLabel();
        ImageIcon imageIcon;
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        try {
            Image image = Images.getImage().getImage();
            Mat img = imageToMat(image);
            if (img.empty()) {
                System.out.println("Не удалось загрузить изображение");
                return;
            }
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
                        MatOfByte matOfByte = new MatOfByte();
                        Imgcodecs.imencode(".png", img, matOfByte);
                        imageIcon = new ImageIcon(matOfByte.toArray());
                        screen.setIcon(imageIcon);
                        window.getContentPane().add(screen);
                        window.pack();
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Надо загрузить изображение, а Вы точно загрузили картинку?");
        }
    }

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

    @FXML
    private void circleFindButton() {
        // Найти круг начало
        JFrame window = new JFrame();
        JLabel screen = new JLabel();
        ImageIcon imageIcon;
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        try {
            Image image = Images.getImage().getImage();
            Mat img = imageToMat(image);
            MatOfByte matOfByte = new MatOfByte();
            if (img.empty()) {
                System.out.println("Не удалось загрузить изображение");
                return;
            }
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
                    Imgproc.circle(result, new Point(circle[0], circle[1]), (int) circle[2], new Scalar(255, 0, 0));
                }
            }
            Imgcodecs.imencode(".png", result, matOfByte);
            imageIcon = new ImageIcon(matOfByte.toArray());
            screen.setIcon(imageIcon);
            window.getContentPane().add(screen);
            window.pack();
        } catch (NullPointerException e) {
            System.out.println("Надо загрузить изображение, а Вы точно загрузили картинку?");
        }
        //Найти круг конец
    }

    @FXML
    private void videoButton() {
        JFrame window = new JFrame();
        JLabel screen = new JLabel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        VideoCapture cap = new VideoCapture(0);
        Mat frame = new Mat();
        Mat frameResired = new Mat();
        MatOfByte matOfByte = new MatOfByte();
        //Определяет точность или инициализация погрешности я сделал минимальные
        float minProbability = 0.5f;
        float threshold = 0.3f;
        int height;
        int width;
        ImageIcon imageIcon;
        BufferedImage img = null;

        String path = "src/main/java/sample/coco.names";
        List<String> labels = labels(path);
        //Количество наименований
        int amountOfClasses = labels.size();

        //Каждый класс отдельный цвет
        Random random = new Random();
        Scalar[] color = new Scalar[amountOfClasses];
        for (int i = 0; i < amountOfClasses; i++) {
            color[i] = new Scalar(random.nextInt(256),
                    random.nextInt(256),
                    random.nextInt(256)
            );
        }

        String cfgPath = "src/main/java/sample/yolov4.cfg";
        String weightsPath = "src/main/java/sample/yolov4.weights";
        Net network = Dnn.readNetFromDarknet(cfgPath, weightsPath);

        //Все слоя нейронной сети.
        List<String> nameOfAllLayers = network.getLayerNames();
        //Извлекаем индекс выходных слоев
        MatOfInt outputLayersIndexes = network.getUnconnectedOutLayers();
        //Кол-во входных слоев
        int amountOutputLayers = outputLayersIndexes.toArray().length;
        //В цикле фор извлекаем наименование выходных слоев из nameOfAllLayers
        List<String> outputLayersNames = new ArrayList<>();
        for (int i = 0; i < amountOutputLayers; i++) {
            outputLayersNames.add(nameOfAllLayers.get(outputLayersIndexes.toList().get(i) - 1));
        }
        //обрабатываем кадры
        while (true) {
            cap.read(frame);
            height = frame.height();
            width = frame.width();

             /* Записываем новое изображени, уменьшаем кадр с целью уменьшить нагрузку
               на нейронную сеть. Ширина и высота должны быть кратны 32. */
            Imgproc.resize(frame, frameResired, new Size(192, 192));


            // Подготавливаем blob (партии изображений которые пропустим через нейронную сеть)
            Mat blob = Dnn.blobFromImage(frameResired, 1 / 255.0);
            // Подготавливаем blob на вход нейронную сети
            network.setInput(blob);
            //Извлекаем данные с входных слоев нейронной сети
            List<Mat> outputFromNetworks = new ArrayList<>();
//            for (int i = 0; i < amountOutputLayers; i++) {
//                outputFromNetworks.add(network.forward(outputLayersNames.get(i)));
//            }
            network.forward(outputFromNetworks, outputLayersNames);
              /* Координаты обнаруженных ограничительных рамок будут записыватся в список,
               а за тем конвертироватся MatOfRect2d. */
            List<Rect2d> boundingBoxesList = new ArrayList();
            MatOfRect2d boundingBoxes = new MatOfRect2d();

            /* Предсказаные вероятности будут записыватся в список,
               а за тем конвертироватся MatOfFloat. */
            List<Float> confidencesList = new ArrayList();
            MatOfFloat confidences = new MatOfFloat();

            // Индексы предсказаных классов будут записыватся в список.
            List<Integer> classIndexes = new ArrayList();

            // Проходим через все предсказания из выходных слоёв по очереди.
            // В цикле проходим через слои:
            for (int i = 0; i < amountOutputLayers; i++) {
                System.out.println(outputFromNetworks.get(i).size());
                // Проходим через все предсказания из слоя:
                for (int b = 0; b < outputFromNetworks.get(i).size().height; b++) {
                    // Записываем в список вероятность для каждого класса из слоя.
                    //List <Double> scores = new ArrayList();
                    double[] scores = new double[amountOfClasses];
                    for (int c = 0; c < amountOfClasses; c++) {
//                        scores.add(outputFromNetwork.get(i).get(b,c+5)[0]);
                        scores[c] = outputFromNetworks.get(i).get(b, c + 5)[0];
                    }

                    // Получаем индекс класса с максимальным значением в строке в которой находимся.
                    int indexOfMaxValue = 0;
                    for (int c = 0; c < amountOfClasses; c++) {
//                        indexOfMaxValue = (scores.get(c) > scores.get(indexOfMaxValue)) ? c : indexOfMaxValue;
                        indexOfMaxValue = (scores[c] > scores[indexOfMaxValue]) ? c : indexOfMaxValue;
                    }

                    // Максимальное значение вероятности в строке.
//                    Double maxProbability = scores.get(indexOfMaxValue);
                    Double maxProbability = scores[indexOfMaxValue];

                    // Если вероятность больше заданого минимума,
                    if (maxProbability > minProbability) {
                        /* то извлекаем значения точек ограничительной рамки из слоя, расчитываем нужные значения,
                           получаем ширину, высоту, начальные координаты по "x" и "y",
                           заносим значения в объект типа Rect2d. */

                        double boxWidth = outputFromNetworks.get(i).get(b, 2)[0] * width;
                        double boxHeight = outputFromNetworks.get(i).get(b, 3)[0] * height;
                        Rect2d boxRect2d = new Rect2d(
                                (outputFromNetworks.get(i).get(b, 0)[0] * width) - (boxWidth / 2),
                                (outputFromNetworks.get(i).get(b, 1)[0] * height) - (boxHeight / 2),
                                boxWidth,
                                boxHeight
                        );

                        // Записываем параметры ограничительной рамки в список.
                        boundingBoxesList.add(boxRect2d);
                        // Записываем максимальную вероятность в спсок.
                        confidencesList.add(maxProbability.floatValue());
                        // Записываем индекс предполагаемого класса в список.
                        classIndexes.add(indexOfMaxValue);
                    }
                }
            }

            // Конвертируем списки в соответсвутующие матрицы.
            boundingBoxes.fromList(boundingBoxesList);
            confidences.fromList(confidencesList);


            /* Так как каждому объекту на изображении как правило соответсвует несколько
               ограничительных рамок, нам требуется выбрать наиболее подходящую для каждого обьекта.
               Для этого пропускаем все обнаруженные рамки через алгоритм "non-maximum suppression".
               Функция Dnn.NMSBoxes возвращает матрицу с индексами (MatOfInt indices) для
               наиболее подходящихограничительных рамок. */

            // Инициализируем матрицу для NMSBoxes.
            MatOfInt indices = new MatOfInt();
            Dnn.NMSBoxes(boundingBoxes, confidences, minProbability, threshold, indices);


            // Если алгоритм "non-maximum suppression" выявил ограничительные рамки,
            if (indices.size().height > 0) {
                // то наносим выявленные рамки на изображения.
                for (int i = 0; i < indices.toList().size(); i++) {
                    /* Создаём объект класса Rect на основе которого
                       будет нанесена ограничительная рамка */
                    Rect rect = new Rect(
                            (int) boundingBoxes.toList().get(indices.toList().get(i)).x,
                            (int) boundingBoxes.toList().get(indices.toList().get(i)).y,
                            (int) boundingBoxes.toList().get(indices.toList().get(i)).width,
                            (int) boundingBoxes.toList().get(indices.toList().get(i)).height
                    );

                    // Извлекаем индекс выявлнгого класса (объекта на изображении).
                    int classIndex = classIndexes.get(indices.toList().get(i));

                    // Наносим ограничительную рамку.
                    Imgproc.rectangle(frame, rect, color[classIndex], 2);

                    // Форматируем строку для нанесения на изображение:
                    // Выявленный клас: вероятность
                    String Text = labels.get(classIndex) + ": " + Float.toString(confidences.toList().get(i));
                    // Инициализируем точку для нанесения текста.
                    Point textPoint = new Point(
                            (int) boundingBoxes.toList().get(indices.toList().get(i)).x,
                            (int) boundingBoxes.toList().get(indices.toList().get(i)).y - 10
                    );
                    // Наносим текст на изображение.
                    Imgproc.putText(frame, Text, textPoint, 1, 1.5, color[classIndex]);

                }
            }

            /* Преобразуем изображение в матрицу байтов с целью
               получить массив байтов (пикселей). */
            Imgcodecs.imencode(".png", frame, matOfByte);

            /* Преобразуем массив пикселей в ImageIcon,
               изображение которое будет отображатся. */
            imageIcon = new ImageIcon(matOfByte.toArray());

            // Привязываем изображение к контейнеру.
            screen.setIcon(imageIcon);
            screen.repaint();
            // Привязываем контейнер к окну отображения.
            window.setContentPane(screen);
            window.pack();

        }
    }

    public static List<String> labels(String path) {
        List<String> labels = new ArrayList<>();
        try {
            Scanner scnLabels = new Scanner(new File(path));
            while (scnLabels.hasNext()) {      //Строчка для сканирования
                String label = scnLabels.nextLine();
                labels.add(label);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return labels;
    }

    @FXML
    private void LoadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            myImageView.setImage(image);
            reset();
            Images.setImage(myImageView);
        } catch (IOException ex) {

        }
    }

    @FXML
    private void SaveImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                BufferedImage bImage = SwingFXUtils.fromFXImage(myImageView.snapshot(null, null), null);
                ImageIO.write(bImage, "png", file);
            } catch (IOException ex) {
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Images.Light(light);
        Images.InnerShadow(innshadow);
        Images.BloomSliderEvent(pazmutueBloom);
        Images.GaussSliderEvent(pazmutueGauss);
        Images.GlowSliderEvent(svetitca);
        Images.SepiaSliderEvent(sovetFotoEffect);
        Images.Blur(pazmutue);
        Images.MotionAngleEvent(motionAngle);
        Images.MotionRadiusEvent(motionRadius);
        Images.DropShadow(tenu);
        Images.BrightSliderEvent(bright);
        Images.SaturationSliderEvent(saturation);
        Images.HueSliderEvent(ottenok);
        Images.ContrastSliderEvent(contrast);
        myImageView.fitWidthProperty().bind(root.widthProperty());
        myImageView.fitHeightProperty().bind(root.heightProperty());
    }
}