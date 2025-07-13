package Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * a JPanel for showing animated content
 */

public class RendererPanel extends JPanel implements ActionListener {
    /**
     * the timer updates the JPanel periodically
     */
    private final Timer timer;

    private final Drawable drawable;

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


    public void pause(){
        timer.stop();
    }

    public void resume(){
        timer.start();
    }
}
