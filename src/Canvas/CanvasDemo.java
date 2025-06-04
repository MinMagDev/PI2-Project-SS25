package Canvas;

import Particle.DebugParticle;
import Social.SocialParticleRenderer;

import javax.swing.*;

public class CanvasDemo {

    public static int screenWidth;
    public static int screenHeight;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Circle Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            screenWidth = frame.getWidth();
            screenHeight = frame.getHeight();

            frame.add(new RendererPanel(400, 400, SocialParticleRenderer.createExample()));
            frame.setVisible(true);

        });
    }
}
