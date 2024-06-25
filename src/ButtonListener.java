import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ButtonListener implements ActionListener {
    private WindowFrame window;

    public ButtonListener(WindowFrame window){
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Select")){
            JFileChooser browseImageFile = new JFileChooser();
            int showOpenDialog = browseImageFile.showOpenDialog(null);
            if (showOpenDialog == JFileChooser.APPROVE_OPTION){
                File selectedImageFile = browseImageFile.getSelectedFile();
                String selectedImagePath = selectedImageFile.getAbsolutePath();
                this.window.setImagePath(selectedImagePath);
                System.out.println(selectedImagePath);
                this.window.changePanel("editImage");
            }
        }
    }
}
