import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ImageMouseListener implements MouseMotionListener{

    private Rectangle slider;

    public ImageMouseListener(Rectangle slider) {
        this.slider = slider;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int currentMouseX = e.getX();
        this.slider.setLocation(new Point(currentMouseX, (int) this.slider.getY()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
