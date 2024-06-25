import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEditPanel extends JPanel {
    private WindowFrame window;
    private JOptionPane optionPane;
    private BufferedImage image;

    public ImageEditPanel(int width, int height, WindowFrame window){
        this.setBounds(0,0,width,height);
        this.setLayout(new BorderLayout());

        this.window = window;
        this.image = null;

        this.appLoop();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(image != null) {
            g.drawImage(image, this.getWidth() / 4, 0, this.getWidth()/4 * 3, this.getHeight(), this);
        }
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
