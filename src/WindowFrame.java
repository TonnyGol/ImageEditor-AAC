import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WindowFrame extends JFrame {
    private final int WINDOW_WIDTH = 1920;
    private final int WINDOW_HEIGHT = 1080;

    private String imagePath;

    private JPanel mainPanel;
    private ImageSelectPanel imageSelectPanel;
    private ImageEditPanel imageEditPanel;
    private CardLayout cardLayout;


    public WindowFrame(){
        this.setTitle("Image Editor");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setFocusable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.mainPanel = new JPanel(new CardLayout());

        this.imagePath = "";
        this.imageSelectPanel = new ImageSelectPanel(WINDOW_WIDTH, WINDOW_HEIGHT, this);
        this.mainPanel.add(this.imageSelectPanel, "selectImage");

        this.imageEditPanel = new ImageEditPanel(WINDOW_WIDTH, WINDOW_HEIGHT, this);
        this.mainPanel.add(this.imageEditPanel, "editImage");

        this.add(mainPanel);
        this.cardLayout = (CardLayout) this.mainPanel.getLayout();

        this.cardLayout.show(this.mainPanel, "selectImage");
        this.setVisible(true);
    }

    public void setImagePath(String path){
        this.imagePath = path;
    }

    public void changePanel(String screen){
        this.cardLayout.show(this.mainPanel, screen);
    }

    public String getImagePath(){
        return this.imagePath;
    }
}
