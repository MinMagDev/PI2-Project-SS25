package Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RendererPanel extends JPanel implements ActionListener {
    private Timer timer;



    public DrawableParticle[] getParticles() {
        return particles;
    }

    private DrawableParticle[] particles;

    public RendererPanel(int width, int height, DrawableParticle[] particles) {
        this.timer = new Timer(10, this);
        setSize(width, height);
        this.particles = particles;
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        for (final DrawableParticle particle : particles) {
            g.setColor(particle.getColor());
            final int particleDiameter = particle.getRadius() * 2;
            g.fillOval(particle.getX(), particle.getY(), particleDiameter, particleDiameter);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(var p: particles){
            p.update();
        }
        repaint();
    }
}
