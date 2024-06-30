import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class ImageEditPanel extends JPanel {
    private final String[] FILTERS = {"noFilter", "Save Image", "negativeFilter", "colorShiftLeftFilter", "colorShiftRightFilter",
            "mirrorFilter", "pixelateFilter", "bordersFilter", "grayscaleFilter", "blackAndWhiteFilter",
            "posterizeFilter", "pinkTintFilter", "noiseFilter", "sepiaFilter", "vintageFilter"};
    private WindowFrame window;
    //    private JOptionPane optionPane;
    private BufferedImage originalImage;
    private BufferedImage editedImage;
    private JComboBox filterChoice;
    private Rectangle slider;
    private boolean resize;


    public ImageEditPanel(int width, int height, WindowFrame window){
        this.setBounds(0,0,width,height);
        this.setLayout(new BorderLayout());
        this.slider = new Rectangle(0,0,10,100);
        this.window = window;
        this.originalImage = null;
        this.filterChoice = new JComboBox(FILTERS);
        this.filterChoice.setBounds(0,0,200,200);
        this.add(filterChoice,BorderLayout.NORTH);
        this.addMouseMotionListener(new ImageMouseListener(this.slider));

        this.editedImage = null;
        this.window.setLocationRelativeTo(null);
        this.resize = false;

        this.filterChoice.setVisible(true);
        this.filterChoice.setFocusable(true);
        this.appLoop();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (originalImage != null) {
            String currentChoice = (String) Objects.requireNonNull(this.filterChoice.getSelectedItem());
            if (currentChoice.equals("noFilter")) {
                try {
                    this.editedImage = ImageIO.read(new File(this.window.getImagePath()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            switch (currentChoice) {
                case "Save Image":
                    File outputPath = new File("C:\\EditedImages\\newImage.jpg");
                    try {
                        ImageIO.write(this.editedImage, "jpg", outputPath);
                        System.exit(-1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                case "noFilter":
                    g.drawImage(this.originalImage, 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "negativeFilter":
                    this.editedImage = negativeFilter(this.originalImage);
                    g.drawImage(negativeFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "colorShiftLeftFilter":
                    //this.editedImage = colorShiftLeftFilter(this.originalImage);
                    g.drawImage(colorShiftLeftFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "colorShiftRightFilter":
                    g.drawImage(colorShiftRightFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "mirrorFilter":
                    g.drawImage(mirrorFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "pixelateFilter":
                    g.drawImage(pixelateFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "bordersFilter":
                    g.drawImage(bordersFilter(), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "grayscaleFilter":
                    g.drawImage(grayscaleFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "blackAndWhiteFilter":
                    g.drawImage(blackAndWhiteFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "posterizeFilter":
                    g.drawImage(posterizeFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "pinkTintFilter":
                    g.drawImage(pinkTintFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "noiseFilter":
                    g.drawImage(applyNoiseFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "sepiaFilter":
                    g.drawImage(applySepiaFilter(this.originalImage), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "vintageFilter":
                    g.drawImage(vintageNoiseFilter(), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
            }
            if(this.slider.getX() <= -30 || this.slider.getHeight() != this.window.getHeight()){
                this.slider.setBounds(this.originalImage.getWidth()-20, (int) this.slider.getY(), (int) this.slider.getWidth(), this.window.getHeight());
            }
            if(this.window.getHeight() != this.originalImage.getHeight() && this.window.getWidth() != this.originalImage.getWidth() && !this.resize) {
                this.window.setBounds(0, 0, this.originalImage.getWidth()+20, this.originalImage.getHeight());
                resize = true;
            }
//            if(this.resize && !currentChoice.equals("noFilter")){
//                this.slider.setLocation(this.window.getLocation());
//            }
            g.fillRect((int) this.slider.getX(), (int) this.slider.getY()+23, (int) this.slider.getWidth(), this.window.getHeight());
            //         (int) this.slider.getX()
        }
    }

    private BufferedImage negativeFilter (BufferedImage original) {
        //BufferedImage negativeImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        //                 this.image.getWidth()
        for (int x = 0; x <= this.slider.getX() && x <= original.getWidth()-1; x++) {
            System.out.println("Slider X: " + this.slider.getX());
            System.out.println("Loop X: " + x);
            for (int y = 0; y < original.getHeight(); y++) {
                Color currentColor = new Color(original.getRGB(x, y));
                int red = 255 - currentColor.getRed();
                int green = 255 - currentColor.getGreen();
                int blue = 255 - currentColor.getBlue();
                Color updatedColor = new Color(red, green, blue);
                original.setRGB(x, y, updatedColor.getRGB());
            }
        }
        return original;
    }

    private BufferedImage colorShiftLeftFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage shiftedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Process each pixel
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < this.slider.getX() && x <= original.getWidth()-1; x++) {
                Color color = new Color(original.getRGB(x, y));

                // Get individual color components
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                // Shift the colors
                int newR = g; // Green to Red
                int newG = b; // Blue to Green
                int newB = r; // Red to Blue

                // Create a new color with the shifted RGB values
                Color newColor = new Color(newR, newG, newB);

                // Set the new color to the shifted image
                shiftedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return shiftedImage;
    }

    private BufferedImage colorShiftRightFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage shiftedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(original.getRGB(x, y));

                // Get individual color components
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                // Shift the colors
                int newR = b; // Blue to Red
                int newG = r; // Red to Green
                int newB = g; // Green to Blue

                // Create a new color with the shifted RGB values
                Color newColor = new Color(newR, newG, newB);

                // Set the new color to the shifted image
                shiftedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return shiftedImage;
    }

    private BufferedImage mirrorFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage mirroredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int mirroredX = width - x - 1; // Calculate mirrored x coordinate

                // Get RGB value from original image
                int rgb = original.getRGB(x, y);

                // Set RGB value to mirrored image
                mirroredImage.setRGB(mirroredX, y, rgb);
            }
        }
        return mirroredImage;
    }

    private BufferedImage pixelateFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage pixelatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final int PIXELATE_SIZE = 10;

        // Process each block of pixels
        for (int y = 0; y < height; y += PIXELATE_SIZE) {
            for (int x = 0; x < width; x += PIXELATE_SIZE) {
                // Calculate average color for the block
                int avgRGB = calculateAverageRGB(original, x, y, PIXELATE_SIZE);

                // Fill the block with the average color
                for (int blockY = y; blockY < y + PIXELATE_SIZE && blockY < height; blockY++) {
                    for (int blockX = x; blockX < x + PIXELATE_SIZE && blockX < width; blockX++) {
                        pixelatedImage.setRGB(blockX, blockY, avgRGB);
                    }
                }
            }
        }
        return pixelatedImage;
    }

    private int calculateAverageRGB(BufferedImage image, int startX, int startY, int size) {
        int totalR = 0, totalG = 0, totalB = 0, count = 0;

        // Sum up RGB values in the block
        for (int y = startY; y < startY + size && y < image.getHeight(); y++) {
            for (int x = startX; x < startX + size && x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                totalR += color.getRed();
                totalG += color.getGreen();
                totalB += color.getBlue();
                count++;
            }
        }

        // Calculate average RGB values
        int avgR = totalR / count;
        int avgG = totalG / count;
        int avgB = totalB / count;

        // Return the average RGB color as an integer
        return new Color(avgR, avgG, avgB).getRGB();
    }

    private BufferedImage bordersFilter () {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage thickenedBorderImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Threshold for edge detection
        final int GRADIENT_THRESHOLD = 30; // Adjust as needed

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Apply edge detection (simple gradient magnitude approximation)
                int gradientMagnitude = calculateGradientMagnitude(originalImage, x, y);

                // Set border color if gradient magnitude exceeds threshold
                if (gradientMagnitude > GRADIENT_THRESHOLD) {
                    // Highlight the pixel
                    thickenedBorderImage.setRGB(x, y, Color.BLACK.getRGB());

                    // Expand the border to make it thicker
                    for (int dy = -2; dy <= 2; dy++) { // Expand vertically
                        for (int dx = -2; dx <= 2; dx++) { // Expand horizontally
                            int nx = x + dx;
                            int ny = y + dy;
                            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                                thickenedBorderImage.setRGB(nx, ny, Color.BLACK.getRGB());
                            }
                        }
                    }
                } else {
                    // Keep the original pixel color
                    thickenedBorderImage.setRGB(x, y, originalImage.getRGB(x, y));
                }
            }
        }
        return thickenedBorderImage;
    }

    private int calculateGradientMagnitude(BufferedImage original, int x, int y) {
        Color currentPixel = new Color(original.getRGB(x, y));
        Color nextPixel = (x < original.getWidth() - 1) ? new Color(original.getRGB(x + 1, y)) : currentPixel;
        Color bottomPixel = (y < original.getHeight() - 1) ? new Color(original.getRGB(x, y + 1)) : currentPixel;

        // Calculate gradient magnitude (simple approximation)
        int dx = Math.abs(nextPixel.getRed() - currentPixel.getRed());
        int dy = Math.abs(bottomPixel.getRed() - currentPixel.getRed());
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    private BufferedImage grayscaleFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage grayscale = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);
                Color color = new Color(rgb);

                int grayValue = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
                int grayRGB = new Color(grayValue, grayValue, grayValue).getRGB();

                grayscale.setRGB(x, y, grayRGB);
            }
        }
        return grayscale;
    }

    private BufferedImage blackAndWhiteFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage blackAndWhiteImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        final int THRESHOLD = 200;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);
                Color color = new Color(rgb);

                int grayValue = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());

                int bwValue = grayValue < THRESHOLD ? 0 : 255;

                int bwRGB = new Color(bwValue, bwValue, bwValue).getRGB();
                blackAndWhiteImage.setRGB(x, y, bwRGB);
            }
        }
        return blackAndWhiteImage;
    }

    private BufferedImage posterizeFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage posterImage = new BufferedImage(width, height, original.getType());

        // Define custom thresholds for each color channel
        final int[] COLOR_THRESHOLDS = {64, 128, 192};

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);
                Color color = new Color(rgb);

                // Posterize each color channel
                int red = posterizeColor(color.getRed(), COLOR_THRESHOLDS);
                int green = posterizeColor(color.getGreen(), COLOR_THRESHOLDS);
                int blue = posterizeColor(color.getBlue(), COLOR_THRESHOLDS) ;

                // Set the new color
                int newColor = new Color(red, green, blue).getRGB();
                posterImage.setRGB(x, y, newColor);
            }
        }

        return posterImage;
    }

    private int posterizeColor(int colorValue, int[] thresholds) {
        int newColorValue = 0;
        for (int threshold : thresholds) {
            if (colorValue < threshold) {
                newColorValue = threshold - 64; // Adjust to midpoint of each threshold range
                break;
            }
            newColorValue = 255; // Maximum value for the color channel
        }
        return newColorValue;
    }

    private BufferedImage pinkTintFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage tintedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Pink tint RGB values
        int tintR = 255; // max red
        int tintG = 192; // less green
        int tintB = 203; // more blue

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(original.getRGB(x, y));

                // Get individual color components
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                // Mix original color with pink tint
                int mixedR = (r + tintR) / 2;
                int mixedG = (g + tintG) / 2;
                int mixedB = (b + tintB) / 2;

                // Create a new color with the mixed tint
                Color newColor = new Color(mixedR, mixedG, mixedB);

                // Set the new color to the tinted image
                tintedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return tintedImage;
    }

    private BufferedImage applyNoiseFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage noisyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Random random = new Random();

        // Loop through each pixel and add random noise
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);

                // Extract RGB components
                int r = (rgb >> 16) & 255;
                int g = (rgb >> 8) & 255;
                int b = rgb & 255;

                // Generate random noise value (between -20 to 20)
                int noise = random.nextInt(41) - 20;

                // Add noise to RGB values
                int noisyR = clamp(r + noise);
                int noisyG = clamp(g + noise);
                int noisyB = clamp(b + noise);

                // Combine noisy RGB values
                int noisyRGB = (noisyR << 16) | (noisyG << 8) | noisyB;
                noisyImage.setRGB(x, y, noisyRGB);
            }
        }
        return noisyImage;
    }

    private BufferedImage applySepiaFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Loop through each pixel and apply sepia filter
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);

                // Extract RGB components
                int r = (rgb >> 16) & 255;
                int g = (rgb >> 8) & 255;
                int b = rgb & 255;

                // Calculate sepia-toned RGB values
                int sepiaR = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int sepiaG = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int sepiaB = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                // Ensure sepia-toned RGB values are within 0-255 range
                sepiaR = Math.min(sepiaR, 255);
                sepiaG = Math.min(sepiaG, 255);
                sepiaB = Math.min(sepiaB, 255);

                // Combine sepia-toned RGB values
                int sepiaRGB = (sepiaR << 16) | (sepiaG << 8) | sepiaB;
                sepiaImage.setRGB(x, y, sepiaRGB);
            }
        }
        return sepiaImage;
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
