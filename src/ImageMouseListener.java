import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ImageMouseListener implements MouseMotionListener{

    private Rectangle slider;
    private boolean moveSlider;

    public ImageMouseListener(Rectangle slider) {
        this.slider = slider;
        this.moveSlider = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int currentX = e.getX();
        if(e.getX() < e.getComponent().getX() + e.getComponent().getWidth()) {
            this.moveSlider = e.getX() >= this.slider.getX() - 20 && e.getX() <= this.slider.getX() + this.slider.getWidth() + 20
                            && e.getY() >= 0 && e.getY() <= e.getComponent().getHeight();
            if(this.moveSlider) {
                this.slider.setLocation(new Point(currentX, (int) this.slider.getY()));
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
