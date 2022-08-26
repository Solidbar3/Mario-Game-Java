import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Mario extends Sprite
{
	static BufferedImage mario_image = null;
	Image[] mario_images;
	int direction, imageNum, jumpFrameCounter, screenPos;
	boolean running;

	
	public Mario() 
	{
		
		x = 0;
		x_CurrentPos = x;
		y = 0;
		y_CurrentPos = y;
		w = 60;
		h = 95;
		vert_vel = 0;
		screenPos = 300;
		direction = 1;
		imageNum = 0;
		mario_images = new Image[5];
		loadImage();
	}
	
	@Override
	boolean isMario()
	{
		return true;
	}
	
	void loadImage()
	{
		if(mario_image == null)
		{
			for(int i = 0; i < mario_images.length; i++)
			{
				mario_image = View.loadImage("mario" + (i+1) + ".png");
				mario_images[i] = mario_image;
			}
		}
	}
	
	@Override
	void draw(Graphics g)
	{
		if(running)
		{
			if(direction == 1)
			{
				this.imageNum++;
				if(this.imageNum >= mario_images.length)
				{
					this.imageNum = 0;
				}
				g.drawImage(mario_images[this.imageNum], screenPos, y, w, h, null);
			}
			else
			{
				this.imageNum++;
				if(this.imageNum >= mario_images.length)
				{
					this.imageNum = 0;
				}
				g.drawImage(mario_images[this.imageNum], w + screenPos, y, -w, h, null);
			}
		}
		else
		{
			if(direction == 1)
			{
				g.drawImage(mario_images[this.imageNum], screenPos, y, w, h, null);
			}
			else
			{
				g.drawImage(mario_images[this.imageNum], w + screenPos, y, -w, h, null);
			}
		}
		running = false;
	}
	
	public void Physics()
	{
		vert_vel += 1.2; // Gravity
		
		y += vert_vel;
		
		if(y > 680)
		{
			vert_vel = 0.0;
			y = 680; // snap back to the ground
			jumpFrameCounter = 0;
		}
	
	}
	
	
	public void update()
	{	
		this.Physics();
	}

}