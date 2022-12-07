package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, NullPointerException {

        URL url = new File("src/main/java/fxml/FXMLDocument.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

//        String url = "src/main/java/sample/my_foto.jpg";
//        File file = new File(url);
//        FileInputStream fileIn = new FileInputStream(file);
//        //Можно просто ссылку писать и будет работать.
//        Image image = new Image(fileIn);
//
////        ImageView imageView = new ImageView(raster);
//        ImageView colorView = new ImageView(image);
//        ImageView bhView = new ImageView(image);
//
//        ColorAdjust colorAdjust = new ColorAdjust();
//        colorAdjust.setBrightness(-0.8);
//        colorAdjust.setContrast(-0.7);
//
//        bhView.setEffect(colorAdjust);
//
//        primaryStage.setScene(new Scene(new VBox(bhView, colorView)));
//        primaryStage.setTitle("MyFoto");
//        FlowPane flowPane = new FlowPane();
//        flowPane.setAlignment(Pos.TOP_CENTER);
//        Scene scene = new Scene(flowPane, 1200, 800);
//        primaryStage.setScene(scene);
//        flowPane.getChildren().addAll(bhView, colorView);
//        primaryStage.show();
    }
}
//        WritableImage raster = new WritableImage(image.getPixelReader(), (int) image.getWidth(), (int) image.getHeight());
//        PixelWriter pixelWriter = raster.getPixelWriter();
//        PixelReader pixelReader = raster.getPixelReader();


//        BufferedImage bufferedImage= ImageIO.read(file);
//        WritableRaster writableRaster=bufferedImage.getRaster();
//
//
//        Object data=writableRaster.getDataElements(0,0,null);
//        ColorModel colorModel=bufferedImage.getColorModel();
//        Color color=new Color(colorModel.getRGB(data),true);
//        writableRaster.setDataElements(100,100,data);
//        bufferedImage.setData(writableRaster);
//        ImageIO.write(bufferedImage,"jpg",file);


//        for (int i = 0; i < raster.getHeight(); i++) {
//            for (int j = 0; j < raster.getWidth(); j++) {
//                Color c = pixelReader.getColor(j, i);
//                if (c.getOpacity() < 1) {
//                    pixelWriter.setColor(j, i, Color.WHITE);
//                }
//                if (c.getRed() > 0 || c.getGreen() > 0 || c.getBlue() > 0) {
//                    pixelWriter.setColor(j, i, Color.BLACK);
//                }
//            }
//        }

//        BufferedImage image1

//        for (int i = 0; i < raster.getWidth(); i++) {
//            int[] pixel= writableRaster.getPixel(i,0,new int[4]);
//            pixel[0]=1;
//            pixel[1]=8;
//            pixel[2]=9;
//            writableRaster.setPixel(i,0,pixel);
//        }
//        writableRaster.getPixels(0,0,bufferedImage.getHeight(),bufferedImage.getWidth(),new int[4 * bufferedImage.getHeight()* bufferedImage.getWidth()]);
//        writableRaster.setPixels(0,0,bufferedImage.getHeight(),bufferedImage.getWidth(),new int[4 * bufferedImage.getHeight()* bufferedImage.getWidth()]);

