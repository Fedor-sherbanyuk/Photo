package sample;

import javafx.scene.CacheHint;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ImageEdit {

    private ImageView Image;
    private ColorAdjust AdjustEffect = new ColorAdjust();
    private DropShadow tenu = new DropShadow(5, Color.CRIMSON);
    private BoxBlur pazmutue = new BoxBlur();
    private MotionBlur motion = new MotionBlur();
    private SepiaTone sovetFotoEffect = new SepiaTone();
    private Glow svetitca = new Glow();
    private GaussianBlur pazmutueGauss = new GaussianBlur();
    private Bloom pazmutueBloom = new Bloom();
    private Lighting light = new Lighting();
    private InnerShadow shad = new InnerShadow();

    public ImageView getImage() {
        return Image;
    }

    public void setImage(ImageView myImage) {
        this.Image = myImage;
        Init();

    }

    public void DropShadow(CheckBox myCheckbox) {
        myCheckbox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue == false) {
                tenu.setHeight(40);
                tenu.setWidth(40);
            } else {
                tenu.setWidth(0);
                tenu.setHeight(0);
            }
        }));
    }

    public void Blur(CheckBox myCheckbox) {
        myCheckbox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue == false) {
                pazmutue.setHeight(8);
                pazmutue.setWidth(6);
                pazmutue.setIterations(3);
            } else {
                pazmutue.setIterations(0);
                pazmutue.setWidth(0);
                pazmutue.setHeight(0);
            }
        }));
    }

    public void Light(CheckBox myCheckbox) {
        myCheckbox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue == false) {
                light.setDiffuseConstant(2.0);
                light.setSpecularConstant(2.0);
                light.setSpecularExponent(40);
                light.setSurfaceScale(6);

            } else {
       light.setDiffuseConstant(2.0);
        light.setSpecularConstant(0.3);
        light.setSpecularExponent(20);
        light.setSurfaceScale(1.5);
            }
        }));
    }

    public void InnerShadow(CheckBox myCheckbox) {
        myCheckbox.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (oldValue == false) {
                shad.setBlurType(BlurType.GAUSSIAN);
                shad.setColor(Color.WHEAT);
                shad.setRadius(70);
                shad.setHeight(90);
                shad.setWidth(90);

            } else {
                shad.setHeight(0);
                shad.setWidth(0);
                shad.setRadius(0);
            }
        }));
    }

    public void BrightSliderEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            AdjustEffect.setBrightness(newValue.doubleValue());

        });
    }

    public void BloomSliderEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            pazmutueBloom.setThreshold(newValue.doubleValue());

        });
    }

    public void SepiaSliderEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            sovetFotoEffect.setLevel(newValue.doubleValue());

        });
    }

    public void GlowSliderEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            svetitca.setLevel(newValue.doubleValue());

        });

    }

    public void GaussSliderEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            pazmutueGauss.setRadius(newValue.doubleValue());

        });
    }

    public void MotionRadiusEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            motion.setRadius(newValue.doubleValue());

        });
    }

    public void MotionAngleEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            motion.setAngle(newValue.doubleValue());

        });
    }

    public void SaturationSliderEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            AdjustEffect.setSaturation(newValue.doubleValue());

        });
    }

    public void HueSliderEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            AdjustEffect.setHue(newValue.doubleValue());

        });
    }

    public void ContrastSliderEvent(Slider mySlider) {
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            AdjustEffect.setContrast(newValue.doubleValue());

        });
    }

    public void Init() {
        tenu.setWidth(0);
        tenu.setHeight(0);
        light.setDiffuseConstant(2.0);
        light.setSpecularConstant(0.3);
        light.setSpecularExponent(20);
        light.setSurfaceScale(1.5);
        pazmutue.setIterations(0);
        pazmutue.setWidth(0);
        pazmutue.setHeight(0);
        motion.setAngle(0);
        motion.setRadius(0);
        svetitca.setLevel(0);
        sovetFotoEffect.setLevel(0);
        pazmutueGauss.setRadius(0);
        pazmutueBloom.setThreshold(1);
        shad.setHeight(0);
        shad.setWidth(0);
        shad.setRadius(0);
        light.setContentInput(shad);
        pazmutueBloom.setInput(light);
        pazmutueGauss.setInput(pazmutueBloom);
        sovetFotoEffect.setInput(pazmutueGauss);
        svetitca.setInput(sovetFotoEffect);
        motion.setInput(svetitca);
        pazmutue.setInput(motion);
        tenu.setInput(pazmutue);
        AdjustEffect.setInput(tenu);
        Image.setEffect(AdjustEffect);
        Image.setCache(true);
        Image.setCacheHint(CacheHint.SPEED);
    }
}
