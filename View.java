/*
   Matthew Clemence
   9/2/2021
   Homework 2
*/

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

class View extends JPanel
{
	Model model;
	int currentFrame;
	boolean running;
	static BufferedImage background_image = null;
	
	View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
		this.currentFrame = 0;
		running = false;
		if(background_image == null)
		{
			background_image = loadImage("marioBackground2.png");
		}

	}
		
	public void paintComponent(Graphics g)
	{
		// Draw Background
		g.drawImage(background_image, 0 - (model.mario.x / 2), 0, background_image.getWidth(), this.getHeight(), null);
		
		
		// Draw Sprites
		for(int i = 0; i < model.sprites.size(); i++)
		{
			model.sprites.get(i).draw(g);
		}
	}	
	
	static BufferedImage loadImage(String filename)
	{
		System.out.println("loading " + filename);
		BufferedImage im = null;
		try
		{
			im = ImageIO.read(new File(filename));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return im;
	}
}