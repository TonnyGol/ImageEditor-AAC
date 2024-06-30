import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSelectPanel extends JPanel {
    private final int BUTTON_WIDTH = 200;
    private final int BUTTON_HEIGHT = 100;
    private final int BUTTON_X = 10;
    private final int BUTTON_Y = 20;

    private JTextField textPathHolder;
    private JButton imageButton;
    private WindowFrame window;

    public ImageSelectPanel(int width, int height, WindowFrame window) {
        this.setBounds(0, 0, width, height);

        this.window = window;

//        this.flowLayout = new FlowLayout(FlowLayout.CENTER);
//        this.flowLayout.setVgap(100);
//        this.setLayout(this.flowLayout);

        this.textPathHolder = new JTextField(30);
        this.textPathHolder.setSize(600, 400);
        this.textPathHolder.setEditable(true);

        textPathHolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });


        this.textPathHolder.setFocusable(true);
        this.textPathHolder.setVisible(true);
        this.textPathHolder.setBounds(BUTTON_X, BUTTON_Y + 400, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.add(this.textPathHolder);

        this.imageButton = new JButton("Open Image");
        this.imageButton.setIcon(new ImageIcon("resources\\Images\\AddImage.png"));
        this.imageButton.setActionCommand("Select");
        this.imageButton.addActionListener(new ButtonListener(this.window));
        this.imageButton.setBounds(BUTTON_X, BUTTON_Y, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
        this.imageButton.setFocusable(true);
        this.imageButton.setVisible(true);
        this.add(imageButton);

    }

    private void loadImage() {
        String path = textPathHolder.getText();
        File checkFile = new File(path);
        if(checkFile.exists()){
            this.window.setImagePath(path);
            this.window.changePanel("editImage");
        }else {
            JOptionPane.showMessageDialog(this, "Image not found or invalid path", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
