import javax.swing.*;
import java.awt.*;

public class WindowFrame extends JFrame {
    private final int BUTTON_WIDTH = 300;
    private final int BUTTON_HEIGHT = 300;
    private final int BUTTON_X = 100;
    private final int BUTTON_Y = 100;
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 1000;

    private JButton ImageButton;


    public WindowFrame(){
        this.setTitle("Image Editor");
        GridLayout gridLayout = new GridLayout(5,5);
        this.setLayout(gridLayout);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setFocusable(true);
        this.setResizable(true);
        this.ImageButton = new JButton("Open Image");
        this.add(ImageButton);
        this.ImageButton.setVisible(true);
        this.ImageButton.setBounds(BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        JTextField textField = new JTextField();
        textField.setFocusable(true);
        textField.setEditable(true);
        textField.setVisible(true);
        textField.setBounds(600, 600, 100, 100);
        this.ImageButton.setMaximumSize(new Dimension(200, 200));
        this.ImageButton.setMinimumSize(new Dimension(200, 200));
        this.ImageButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    }
}
