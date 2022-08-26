import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Coin extends Sprite
{
	static BufferedImage coin_image = null;
	Model model;
	
	double hori_vel;
	boolean offScreen;
	
	public Coin(int x_coin, int y_coin, Model m) 
	{
		x = x_coin;
		x_CurrentPos = x;
		y = y_coin;
		y_CurrentPos = y;
		w = 60;
		h = 60;
		model = m;
		hori_vel = new Random().nextInt(16 + 16) - 16;
		vert_vel = -10;
		offScreen = false;
		loadImage();
	}

	@Override
	boolean isCoin()
	{
		return true;
	}
	
	void loadImage()
	{
		if(coin_image == null)
			coin_image = View.loadImage("Coin.png");
	}
	
	void Physics()
	{
		vert_vel += 1.2;
		
		y += vert_vel;
		x += hori_vel;
		
		if(y > 715)
		{
			vert_vel = 0;
			y = 715;
		}
		if((x + w < model.mario.x - model.mario.screenPos) || (x > model.mario.x + 1400))
		{
			offScreen = true;
		}
		
	}
	
	void update()
	{
		this.Physics();
	}
	
	void draw(Graphics g)
	{
		g.drawImage(Coin.coin_image, x - model.mario.x + model.mario.screenPos, y, w, h, null);
	}
}
