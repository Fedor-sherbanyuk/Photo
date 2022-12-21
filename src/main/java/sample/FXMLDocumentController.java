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
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {
    public ImageEdit Images = new ImageEdit();
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
    private Button circleFindButton = new Button();
    @FXML
    private Button triangleFindButton = new Button();
    @FXML
    private Button triangleFindButton2 = new Button();
    @FXML
    private Button triangleFindButton3 = new Button();
    @FXML
    private Button polygonCircleTriangle = new Button();
    @FXML
    private Button polygonFind = new Button();
    @FXML
    private Button infobtn = new Button();
    @FXML
    private Button videoButton = new Button();
    @FXML
    private Button htmlLoadButton = new Button();
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
    private void videoButton() {
        new VideoEdit().videoEditAndController();
    }

    @FXML
    private void triangleFindButton(ActionEvent event) {
        try {
            Image image = Images.getImage().getImage();
            ImageView imageView = new TriangleFind().triangleFindInImage(image);
            myImageView.setImage(imageView.getImage());
        } catch (NullPointerException | IOException e) {
            System.out.println("Надо загрузить изображение, а Вы точно загрузили картинку?");
        }
    }

    @FXML
    private void triangleFindButtonSecond(ActionEvent event) {
        try {
            Image image = Images.getImage().getImage();
            ImageView imageView = new TriangleFindSecond().triangleFindInImageSecond(image);
            myImageView.setImage(imageView.getImage());
        } catch (NullPointerException | IOException e) {
            System.out.println("Надо загрузить изображение, а Вы точно загрузили картинку?");
        }
    }

    @FXML
    private void polygonCircleTriangle(ActionEvent event) {
        try {
            Image image = Images.getImage().getImage();
            ImageView imageView = new PolygonCircleTriangle().polygonCircleTriangleFindInImage(image);
            myImageView.setImage(imageView.getImage());
        } catch (NullPointerException | IOException e) {
            System.out.println("Надо загрузить изображение, а Вы точно загрузили картинку?");
        }
    }

    @FXML
    private void triangleFindButtonThree(ActionEvent event) {
        try {
            Image image = Images.getImage().getImage();
            ImageView imageView = new TriangleFindThree().triangleFindInImageThree(image);
            myImageView.setImage(imageView.getImage());
        } catch (NullPointerException | IOException e) {
            System.out.println("Надо загрузить изображение, а Вы точно загрузили картинку?");
        }
    }

    @FXML
    private void circleFindButton(ActionEvent event) {
        try {
            Image image = Images.getImage().getImage();
            ImageView imageView = new CircleFind().circleFindInImage(image);
            myImageView.setImage(imageView.getImage());
        } catch (NullPointerException | IOException e) {
            System.out.println("Надо загрузить изображение, а Вы точно загрузили картинку?");
        }
    }

    @FXML
    private void polygonFindButton(ActionEvent event) {
        try {
            Image image = Images.getImage().getImage();
            ImageView imageView = new PolygonSelect().polygonFindInImage(image);
            myImageView.setImage(imageView.getImage());
        } catch (NullPointerException | IOException e) {
            System.out.println("Надо загрузить изображение, а Вы точно загрузили картинку?");
        }
    }

    @FXML
    private void htmlLoadButton(ActionEvent event) throws URISyntaxException, IOException {

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            URL url = new File("src/main/java/sample/index.html").toURI().toURL();
            Desktop.getDesktop().browse(url.toURI());
        }
//        TrustManager[] trustAllCerts = new TrustManager[] {
//                new X509TrustManager() {
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//                    public void checkClientTrusted(
//                            java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//                    public void checkServerTrusted(
//                            java.security.cert.X509Certificate[] certs, String authType) {
//                    }
//                }
//        };
//
//        try {
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//        } catch (NoSuchAlgorithmException | KeyManagementException | UnsupportedOperationException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            URL url = new URL("src/main/java/sample/index.html");
//        } catch (MalformedURLException e) {
//        }

        //-Dhttps.protocols=TLSv1.1,TLSv1.2
//        new WebEngine().load("src/main/java/sample/index.html");// Все не могу открыть html в окне fxml
        //https://ru.stackoverflow.com/questions/488763/%D0%9A%D0%B0%D0%BA-%D0%BE%D1%82%D0%BA%D1%80%D1%8B%D1%82%D1%8C-%D1%81%D0%B0%D0%B9%D1%82-%D0%B2-webview-javafx-%D0%B7%D0%B0%D0%BF%D1%80%D0%B0%D1%88%D0%B8%D0%B2%D0%B0%D1%8E%D1%89%D0%B8%D0%B9-pfx-%D1%81%D0%B5%D1%80%D1%82%D0%B8%D1%84%D0%B8%D0%BA%D0%B0%D1%82
    }


//    @Override
//    public String getName() {
//        throw new UnsupportedOperationException("not implemented");
//    }
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