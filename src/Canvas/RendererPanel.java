package Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RendererPanel extends JPanel implements ActionListener {
    /**
     * the timer updates the JPanel periodically
     */
    private Timer timer;

    private Drawable drawable;

    public RendererPanel(int width, int height, Drawable drawable) {
        this.drawable = drawable;
        this.timer = new Timer(10, this);
        setSize(width, height);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
       drawable.draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        drawable.update();
        repaint();
    }
}
