import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class ImageEditPanel extends JPanel {
    private final String[] FILTERS = {"noFilter", "Select new Image", "Save current Image", "negativeFilter", "colorShiftLeftFilter", "colorShiftRightFilter",
            "mirrorFilter", "pixelateFilter", "bordersFilter", "grayscaleFilter", "posterizeFilter",
            "pinkTintFilter", "noiseFilter", "sepiaFilter", "vintageFilter"};

    private final int BOX_WIDTH = 200;
    private final int BOX_HEIGHT = 200;
    private final int IMAGE_DEFAULT_Y = 23;
    private final int MAX_COLOR_RANGE = 255;

    private WindowFrame window;
    private BufferedImage originalImage;
    private BufferedImage editedImage;
    private JComboBox filterChoice;
    private Rectangle slider;

    public ImageEditPanel(int width, int height, WindowFrame window){
        this.setBounds(0,0,width,height);
        this.setLayout(new BorderLayout());
        this.window = window;
        this.window.setLocationRelativeTo(null);

        this.originalImage = null;
        this.editedImage = null;
        this.filterChoice = new JComboBox(FILTERS);
        this.filterChoice.setBounds(0,0,BOX_WIDTH,BOX_HEIGHT);
        this.filterChoice.setVisible(true);
        this.filterChoice.setFocusable(true);
        this.add(filterChoice,BorderLayout.NORTH);

        this.slider = new Rectangle(0,0,BOX_WIDTH/20,BOX_HEIGHT/2);
        this.addMouseMotionListener(new ImageMouseListener(this.slider));

        this.appLoop();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (originalImage != null) {
            String currentChoice = (String) Objects.requireNonNull(this.filterChoice.getSelectedItem());
            switch (currentChoice) {
                case "Select new Image":
                    this.window.changePanel("selectImage");
                    this.filterChoice.setSelectedIndex(0);
                    break;
                case "Save current Image":
                    File outputPath = new File("savedImages\\lastSavedImage");
                    try {
                        ImageIO.write(this.editedImage, "jpg", outputPath);
                        System.exit(0);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "noFilter":
                    try {
                        this.editedImage = ImageIO.read(new File(this.window.getImagePath()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    g.drawImage(this.originalImage, 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "negativeFilter":
                    g.drawImage(negativeFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "colorShiftLeftFilter":
                    g.drawImage(colorShiftLeftFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "colorShiftRightFilter":
                    g.drawImage(colorShiftRightFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "mirrorFilter":
                    g.drawImage(mirrorFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "pixelateFilter":
                    g.drawImage(pixelateFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "bordersFilter":
                    g.drawImage(bordersFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "grayscaleFilter":
                    g.drawImage(grayscaleFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "posterizeFilter":
                    g.drawImage(posterizeFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "pinkTintFilter":
                    g.drawImage(pinkTintFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "noiseFilter":
                    g.drawImage(applyNoiseFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "sepiaFilter":
                    g.drawImage(applySepiaFilter(this.originalImage), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
                case "vintageFilter":
                    g.drawImage(vintageNoiseFilter(), 0, IMAGE_DEFAULT_Y, this.getWidth(), this.getHeight(), this);
                    break;
            }

            g.setColor(Color.ORANGE);
            g.fillRect((int) this.slider.getX(), (int) this.slider.getY()+IMAGE_DEFAULT_Y, (int) this.slider.getWidth(), this.window.getHeight());
        }
    }

    private BufferedImage negativeFilter (BufferedImage original) {
        this.editedImage = original;

        for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                Color currentColor = new Color(original.getRGB(x, y));
                int red = MAX_COLOR_RANGE - currentColor.getRed();
                int green = MAX_COLOR_RANGE - currentColor.getGreen();
                int blue = MAX_COLOR_RANGE - currentColor.getBlue();
                Color updatedColor = new Color(red, green, blue);
                this.editedImage.setRGB(x, y, updatedColor.getRGB());
            }
        }
        return this.editedImage;
    }

    private BufferedImage colorShiftLeftFilter(BufferedImage original) {
        this.editedImage = original;

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < this.slider.getX() && x <= original.getWidth()-1; x++) {
                Color color = new Color(original.getRGB(x, y));

                int newB = color.getRed();
                int newR = color.getGreen();
                int newG = color.getBlue();

                Color newColor = new Color(newR, newG, newB);

                this.editedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return this.editedImage;
    }

    private BufferedImage colorShiftRightFilter(BufferedImage original) {
        this.editedImage = original;

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x++) {
                Color color = new Color(original.getRGB(x, y));

                int newG = color.getRed();
                int newB = color.getGreen();
                int newR = color.getBlue();

                Color newColor = new Color(newR, newG, newB);

                this.editedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return this.editedImage;
    }

    private BufferedImage mirrorFilter(BufferedImage original) {
        this.editedImage = original;

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1 && x <= original.getWidth()/2; x++) {
                int mirroredX = original.getWidth() - x - 1; // Calculate mirrored x coordinate

                int rgb = original.getRGB(x, y);
                int mirroredRgb = original.getRGB(mirroredX, y);

                this.editedImage.setRGB(mirroredX, y, rgb);
                if (mirroredX <= this.slider.x){
                    this.editedImage.setRGB(x, y, mirroredRgb);
                }
            }
        }
        return this.editedImage;
    }

    private BufferedImage pixelateFilter(BufferedImage original) {
        this.editedImage = original;
        final int PIXELATE_SIZE = 10;

        for (int y = 0; y < original.getHeight(); y += PIXELATE_SIZE) {
            for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x += PIXELATE_SIZE) {

                int avgRGB = calculateAverageRGB(original, x, y, PIXELATE_SIZE);

                for (int blockY = y; blockY < y + PIXELATE_SIZE && blockY < original.getHeight(); blockY++) {
                    for (int blockX = x; blockX < x + PIXELATE_SIZE && blockX < original.getWidth(); blockX++) {
                        this.editedImage.setRGB(blockX, blockY, avgRGB);
                    }
                }
            }
        }
        return this.editedImage;
    }

    private int calculateAverageRGB(BufferedImage image, int startX, int startY, int size) {
        int totalR = 0, totalG = 0, totalB = 0, count = 0;

        for (int y = startY; y < startY + size && y < image.getHeight(); y++) {
            for (int x = startX; x < startX + size && x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                totalR += color.getRed();
                totalG += color.getGreen();
                totalB += color.getBlue();
                count++;
            }
        }

        int avgR = totalR / count;
        int avgG = totalG / count;
        int avgB = totalB / count;

        return new Color(avgR, avgG, avgB).getRGB();
    }

    private BufferedImage bordersFilter (BufferedImage original) {
        this.editedImage = original;

        final int GRADIENT_THRESHOLD = 30;

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x++) {

                int gradientMagnitude = calculateGradientMagnitude(originalImage, x, y);

                if (gradientMagnitude > GRADIENT_THRESHOLD) {
                    this.editedImage.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    this.editedImage.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return this.editedImage;
    }

    private int calculateGradientMagnitude(BufferedImage original, int x, int y) {
        Color currentPixel = new Color(original.getRGB(x, y));
        Color nextPixel = (x < original.getWidth() - 1) ? new Color(original.getRGB(x + 1, y)) : currentPixel;
        Color bottomPixel = (y < original.getHeight() - 1) ? new Color(original.getRGB(x, y + 1)) : currentPixel;

        int dx = Math.abs(nextPixel.getRed() - currentPixel.getRed()) +
                Math.abs(nextPixel.getGreen() - currentPixel.getGreen()) +
                Math.abs(nextPixel.getBlue() - currentPixel.getBlue());

        int dy = Math.abs(bottomPixel.getRed() - currentPixel.getRed()) +
                Math.abs(bottomPixel.getGreen() - currentPixel.getGreen()) +
                Math.abs(bottomPixel.getBlue() - currentPixel.getBlue());
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    private BufferedImage grayscaleFilter(BufferedImage original) {
        this.editedImage = original;

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x++) {
                int rgb = original.getRGB(x, y);
                Color color = new Color(rgb);

                int grayValue = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
                int grayRGB = new Color(grayValue, grayValue, grayValue).getRGB();

                this.editedImage.setRGB(x, y, grayRGB);
            }
        }
        return this.editedImage;
    }


    private BufferedImage posterizeFilter(BufferedImage original) {
        this.editedImage = original;

        final int[] COLOR_THRESHOLDS = {64, 128, 192};

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x++) {
                int rgb = original.getRGB(x, y);
                Color color = new Color(rgb);

                int red = posterizeColor(color.getRed(), COLOR_THRESHOLDS);
                int green = posterizeColor(color.getGreen(), COLOR_THRESHOLDS);
                int blue = posterizeColor(color.getBlue(), COLOR_THRESHOLDS) ;

                int newColor = new Color(red, green, blue).getRGB();
                this.editedImage.setRGB(x, y, newColor);
            }
        }
        return this.editedImage;
    }

    private int posterizeColor(int colorValue, int[] thresholds) {
        int newColorValue = 0;
        for (int threshold : thresholds) {
            if (colorValue < threshold) {
                newColorValue = threshold - 64; // Adjust to midpoint of each threshold range
                break;
            }
            newColorValue = MAX_COLOR_RANGE;
        }
        return newColorValue;
    }

    private BufferedImage pinkTintFilter(BufferedImage original) {
        this.editedImage = original;

        final int TINT_G = 192;
        final int TINT_B = 203;

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x++) {
                Color color = new Color(original.getRGB(x, y));

                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                int mixedR = (r + MAX_COLOR_RANGE) / 2;
                int mixedG = (g + TINT_G) / 2;
                int mixedB = (b + TINT_B) / 2;

                Color newColor = new Color(mixedR, mixedG, mixedB);

                this.editedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return this.editedImage;
    }

    private BufferedImage applyNoiseFilter(BufferedImage original) {
        this.editedImage = original;
        Random random = new Random();

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x++) {
                int rgb = original.getRGB(x, y);

                int r = (rgb >> 16) & MAX_COLOR_RANGE;
                int g = (rgb >> 8) & MAX_COLOR_RANGE;
                int b = rgb & MAX_COLOR_RANGE;

                int noise = random.nextInt(41) - 20;

                int noisyR = clamp(r + noise);
                int noisyG = clamp(g + noise);
                int noisyB = clamp(b + noise);

                int noisyRGB = (noisyR << 16) | (noisyG << 8) | noisyB;
                this.editedImage.setRGB(x, y, noisyRGB);
            }
        }
        return this.editedImage;
    }

    private BufferedImage applySepiaFilter(BufferedImage original) {
        this.editedImage = original;

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x++) {
                int rgb = original.getRGB(x, y);

                int r = (rgb >> 16) & MAX_COLOR_RANGE;
                int g = (rgb >> 8) & MAX_COLOR_RANGE;
                int b = rgb & MAX_COLOR_RANGE;

                int sepiaR = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int sepiaG = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int sepiaB = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                sepiaR = Math.min(sepiaR, MAX_COLOR_RANGE);
                sepiaG = Math.min(sepiaG, MAX_COLOR_RANGE);
                sepiaB = Math.min(sepiaB, MAX_COLOR_RANGE);

                int sepiaRGB = (sepiaR << 16) | (sepiaG << 8) | sepiaB;
                this.editedImage.setRGB(x, y, sepiaRGB);
            }
        }
        return this.editedImage;
    }

    private BufferedImage vintageNoiseFilter() {
        BufferedImage tempImage = this.originalImage;
        return applyNoiseFilter(applySepiaFilter(tempImage));
    }

    private static int clamp(int value) {
        return Math.min(255, Math.max(0, value));
    }

    private void update(){

        if (!this.window.getImagePath().isEmpty()){
            try {
                this.originalImage = ImageIO.read(new File(this.window.getImagePath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(this.window.getHeight() != this.originalImage.getHeight() || this.window.getWidth() != this.originalImage.getWidth()) {
                this.window.setSize(this.originalImage.getWidth()+20, this.originalImage.getHeight());
                this.setSize(this.originalImage.getWidth()+20, this.originalImage.getHeight());
            }
            if(this.slider.getHeight() != this.window.getHeight()){
                this.slider.setBounds(this.originalImage.getWidth()-20, (int) this.slider.getY(), (int) this.slider.getWidth(), this.window.getHeight());
            }
        }
    }

    private void appLoop(){
        new Thread(()->{
            while (true){
                update();
                repaint();
            }
        }).start();
    }
}
