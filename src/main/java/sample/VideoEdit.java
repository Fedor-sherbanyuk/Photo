package sample;

import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class VideoEdit {

    protected void videoEditAndController() {
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
}
