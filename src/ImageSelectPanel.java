import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageSelectPanel extends JPanel {
    private final int BUTTON_WIDTH = 250;
    private final int BUTTON_HEIGHT = 100;
    private final String OPEN_IMAGE_PATH = "resources\\Images\\AddImage.png";

    private JTextField textPathHolder;
    private JButton imageSelectButton;
    private WindowFrame window;

    public ImageSelectPanel(int width, int height, WindowFrame window) {
        this.setBounds(0, 0, width, height);
        this.window = window;

        this.imageSelectButton = new JButton("Open Image");
        this.imageSelectButton.setIcon(new ImageIcon(OPEN_IMAGE_PATH));
        this.imageSelectButton.setActionCommand("Select");
        this.imageSelectButton.addActionListener(new ButtonListener(this.window));
        this.imageSelectButton.setBounds(this.window.getWidth()/2,
                this.window.getHeight()/4, this.BUTTON_WIDTH, this.BUTTON_HEIGHT);
        this.imageSelectButton.setFocusable(true);
        this.imageSelectButton.setVisible(true);
        this.add(imageSelectButton);

        this.textPathHolder = new JTextField();
        this.textPathHolder.setText("You can enter the full file path here directly and press enter");
        this.textPathHolder.setEditable(true);
        textPathHolder.addActionListener(e -> loadImage());
        this.textPathHolder.setFocusable(true);
        this.textPathHolder.setVisible(true);
        this.textPathHolder.setBounds(imageSelectButton.getX() - BUTTON_WIDTH/2,
                imageSelectButton.getY() + 150, BUTTON_WIDTH*2, BUTTON_HEIGHT/2);
        this.add(this.textPathHolder);

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints selectButtonConstraints = new GridBagConstraints();
        selectButtonConstraints.gridy = 0;
        GridBagConstraints textConstraints = new GridBagConstraints();
        textConstraints.gridy = 1;
        gridBagLayout.setConstraints(this.imageSelectButton, selectButtonConstraints);
        gridBagLayout.setConstraints(this.textPathHolder, textConstraints);
        this.setLayout(gridBagLayout);

    }

    private void loadImage() {
        String path = this.textPathHolder.getText();
        File checkFile = new File(path);
        if(checkFile.exists()){
            this.window.setImagePath(path);
            this.window.changePanel("editImage");
        }else {
            JOptionPane.showMessageDialog(this, "Image not found or invalid path", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
