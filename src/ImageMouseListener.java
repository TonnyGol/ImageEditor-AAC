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
        this.moveSlider = e.getX() >= this.slider.getX() && e.getX() <= this.slider.getX() + this.slider.getWidth();

        if (this.moveSlider) {
            Point newPoint = new Point(e.getX(), (int) this.slider.getY());
            this.slider.setLocation(newPoint);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
