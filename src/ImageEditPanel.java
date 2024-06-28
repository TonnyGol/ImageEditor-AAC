import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class ImageEditPanel extends JPanel {
    private final String[] FILTERS = {"noFilter","negativeFilter", "colorShiftLeftFilter", "colorShiftRightFilter",
            "mirrorFilter", "pixelateFilter", "bordersFilter", "grayscaleFilter", "blackAndWhiteFilter",
            "posterizeFilter", "pinkTintFilter", "noiseFilter", "sepiaFilter", "vintageFilter"};
    private WindowFrame window;
//    private JOptionPane optionPane;
    private BufferedImage image;
    private JComboBox filterChoice;
    private Rectangle slider;


    public ImageEditPanel(int width, int height, WindowFrame window){
        this.setBounds(0,0,width,height);
        this.setLayout(new BorderLayout());
        this.slider = new Rectangle(0,25,20,100);
        this.window = window;
        this.image = null;
        this.filterChoice = new JComboBox(FILTERS);
        this.filterChoice.setBounds(0,0,200,200);
        this.add(filterChoice,BorderLayout.NORTH);
        this.addMouseMotionListener(new ImageMouseListener(this.slider));

        this.filterChoice.setVisible(true);
        this.filterChoice.setFocusable(true);
        this.appLoop();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            String currentChoice = (String) Objects.requireNonNull(this.filterChoice.getSelectedItem());
            switch (currentChoice) {
                case "noFilter":
                    g.drawImage(this.image, 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "negativeFilter":
                    g.drawImage(negativeFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "colorShiftLeftFilter":
                    g.drawImage(colorShiftLeftFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "colorShiftRightFilter":
                    g.drawImage(colorShiftRightFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "mirrorFilter":
                    g.drawImage(mirrorFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "pixelateFilter":
                    g.drawImage(pixelateFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "bordersFilter":
                    g.drawImage(bordersFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "grayscaleFilter":
                    g.drawImage(grayscaleFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "blackAndWhiteFilter":
                    g.drawImage(blackAndWhiteFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "posterizeFilter":
                    g.drawImage(posterizeFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "pinkTintFilter":
                    g.drawImage(pinkTintFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "noiseFilter":
                    g.drawImage(applyNoiseFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "sepiaFilter":
                    g.drawImage(applySepiaFilter(this.image), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
                case "vintageFilter":
                    g.drawImage(vintageNoiseFilter(), 0, 23, this.getWidth(), this.getHeight(), this);
                    break;
            }
            g.fillRect(this.slider.x, this.slider.y, this.slider.width, this.window.getHeight());
        }
    }

    private BufferedImage negativeFilter (BufferedImage bufferedImage) {

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                Color currentColor = new Color(bufferedImage.getRGB(x, y));
                int red = 255 - currentColor.getRed();
                int green = 255 - currentColor.getGreen();
                int blue = 255 - currentColor.getBlue();
                Color updatedColor = new Color(red, green, blue);
                bufferedImage.setRGB(x, y, updatedColor.getRGB());
            }
        }
        return bufferedImage;
    }

    public static BufferedImage colorShiftLeftFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage shiftedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));

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

    public static BufferedImage colorShiftRightFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage shiftedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));

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

    public static BufferedImage mirrorFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage mirroredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int mirroredX = width - x - 1; // Calculate mirrored x coordinate

                // Get RGB value from original image
                int rgb = image.getRGB(x, y);

                // Set RGB value to mirrored image
                mirroredImage.setRGB(mirroredX, y, rgb);
            }
        }

        return mirroredImage;
    }

    public static BufferedImage pixelateFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage pixelatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Process each block of pixels
        for (int y = 0; y < height; y += 10) {
            for (int x = 0; x < width; x += 10) {
                // Calculate average color for the block
                int avgRGB = calculateAverageRGB(image, x, y, 10);

                // Fill the block with the average color
                for (int blockY = y; blockY < y + 10 && blockY < height; blockY++) {
                    for (int blockX = x; blockX < x + 10 && blockX < width; blockX++) {
                        pixelatedImage.setRGB(blockX, blockY, avgRGB);
                    }
                }
            }
        }

        return pixelatedImage;
    }

    public static int calculateAverageRGB(BufferedImage image, int startX, int startY, int size) {
        int totalR = 0, totalG = 0, totalB = 0;
        int count = 0;

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

    private BufferedImage bordersFilter (BufferedImage bufferedImage) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage thickenedBorderImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Threshold for edge detection
        int gradientThreshold = 30; // Adjust as needed

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Apply edge detection (simple gradient magnitude approximation)
                int gradientMagnitude = calculateGradientMagnitude(image, x, y);

                // Set border color if gradient magnitude exceeds threshold
                if (gradientMagnitude > gradientThreshold) {
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
                    thickenedBorderImage.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }

        return thickenedBorderImage;
    }

    public static int calculateGradientMagnitude(BufferedImage image, int x, int y) {
        Color currentPixel = new Color(image.getRGB(x, y));
        Color nextPixel = (x < image.getWidth() - 1) ? new Color(image.getRGB(x + 1, y)) : currentPixel;
        Color bottomPixel = (y < image.getHeight() - 1) ? new Color(image.getRGB(x, y + 1)) : currentPixel;

        // Calculate gradient magnitude (simple approximation)
        int dx = Math.abs(nextPixel.getRed() - currentPixel.getRed());
        int dy = Math.abs(bottomPixel.getRed() - currentPixel.getRed());
        return (int) Math.sqrt(dx * dx + dy * dy);
    }

    public static BufferedImage grayscaleFilter(BufferedImage original) {
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

    public static BufferedImage blackAndWhiteFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage blackAndWhite = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);
                Color color = new Color(rgb);

                int grayValue = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());

                int threshold = 128;
                int bwValue = grayValue < threshold ? 0 : 255;

                int bwRGB = new Color(bwValue, bwValue, bwValue).getRGB();
                blackAndWhite.setRGB(x, y, bwRGB);
            }
        }
        return blackAndWhite;
    }

    public static BufferedImage posterizeFilter(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage posterized = new BufferedImage(width, height, original.getType());

        // Define custom thresholds for each color channel
        int[] thresholds = {64, 128, 192};

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = original.getRGB(x, y);
                Color color = new Color(rgb);

                // Posterize each color channel
                int red = posterizeColor(color.getRed(), thresholds);
                int green = posterizeColor(color.getGreen(), thresholds);
                int blue = posterizeColor(color.getBlue(), thresholds) ;

                // Set the new color
                int newColor = new Color(red, green, blue).getRGB();
                posterized.setRGB(x, y, newColor);
            }
        }

        return posterized;
    }

    private static int posterizeColor(int colorValue, int[] thresholds) {
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

    public static BufferedImage pinkTintFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage tintedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Pink tint RGB values
        int tintR = 255; // max red
        int tintG = 192; // less green
        int tintB = 203; // more blue

        // Process each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));

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

    public static BufferedImage applyNoiseFilter(BufferedImage image) {
        Scanner scanner = new Scanner(System.in);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage noisyImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Random random = new Random();

        // Loop through each pixel and add random noise
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

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

    public static BufferedImage applySepiaFilter(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Loop through each pixel and apply sepia filter
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                // Extract RGB components
                int r = (rgb >> 16) & 255;
                int g = (rgb >> 8) & 255;
                int b = rgb & 255;

                // Calculate sepia-toned RGB values
                int sepiaR = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int sepiaG = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int sepiaB = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                // Ensure sepia-toned RGB values are within 0-255 range
                sepiaR = (sepiaR > 255) ? 255 : sepiaR;
                sepiaG = (sepiaG > 255) ? 255 : sepiaG;
                sepiaB = (sepiaB > 255) ? 255 : sepiaB;

                // Combine sepia-toned RGB values
                int sepiaRGB = (sepiaR << 16) | (sepiaG << 8) | sepiaB;
                sepiaImage.setRGB(x, y, sepiaRGB);
            }
        }

        return sepiaImage;
    }

    public BufferedImage vintageNoiseFilter() {
        BufferedImage tempImage = this.image;
        return applyNoiseFilter(applySepiaFilter(tempImage));
    }

    private static int clamp(int value) {
        return Math.min(255, Math.max(0, value));
    }

    private void update(){
        if (!this.window.getImagePath().isEmpty()){
            try {
                this.image = ImageIO.read(new File(this.window.getImagePath()));
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
