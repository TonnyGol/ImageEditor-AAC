import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WindowFrame extends JFrame {
    private final int WINDOW_WIDTH = 1920;
    private final int WINDOW_HEIGHT = 1080;

    private ImageSelectPanel imageSelectPanel;

    public WindowFrame(){
        this.setTitle("Image Editor");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setFocusable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.imageSelectPanel = new ImageSelectPanel(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.add(imageSelectPanel);

        this.setVisible(true);
    }
}
