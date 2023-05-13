package org.cakeneka.components;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Esta clase es una extensión de JPanel que añade la funcionalidad
 * de establecer una imagen de fondo
 * @author Neka
 */
public class BackgroundPanel extends JPanel{
    private final String imagePath = "src/org/cakeneka/resources/gradient.png";
    private BufferedImage backgroundImage;
    
    public BackgroundPanel(){
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
