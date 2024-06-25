import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImageSelectPanel extends JPanel {
    private final int BUTTON_WIDTH = 150;
    private final int BUTTON_HEIGHT = 50;
    private final int BUTTON_X = 10;
    private final int BUTTON_Y = 20;

    private JButton imageButton;
    private FlowLayout flowLayout;
    private WindowFrame window;

    public ImageSelectPanel(int width, int height, WindowFrame window){
        this.setBounds(0, 0, width, height);

        this.window = window;

        this.flowLayout = new FlowLayout(FlowLayout.CENTER);
        this.flowLayout.setVgap(100);
        this.setLayout(this.flowLayout);

        this.imageButton = new JButton("Open Image");
        this.imageButton.setActionCommand("Select");
        this.imageButton.addActionListener(new ButtonListener(this.window));
        this.imageButton.setBounds(BUTTON_X, BUTTON_Y, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
        this.imageButton.setFocusable(true);
        this.imageButton.setVisible(true);
        this.add(imageButton);
    }
}
