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
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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