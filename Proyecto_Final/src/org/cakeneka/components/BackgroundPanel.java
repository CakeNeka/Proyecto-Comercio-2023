package org.cakeneka.components;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Esta clase es una extensión de JPanel que añade la funcionalidad
 * de establecer una imagen de fondo
 * @author Neka
 */
public class BackgroundPanel extends JPanel{
    private final String BACKGROUND_PATH = "/org/cakeneka/resources/gradient.png";
    private Image backgroundImage;
    
    public BackgroundPanel(){
        backgroundImage = new ImageIcon(getClass().getResource(BACKGROUND_PATH)).getImage();

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
