import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class CoinBlock extends Sprite 
{
	static BufferedImage coinBlockOn_image = null;
	static BufferedImage coinBlockOff_image = null;
	Model model;
	
	int coinCount;
	boolean coinOn = true;
	
	public CoinBlock(Model m) 
	{
		x = 0;
		y = 0;
		w = 89;
		h = 83;
		coinCount = 0;
		model = m;
		loadImage();
	}
	
	// Unmarshalling constructor
	public CoinBlock(Json ob, Model m)
	{
	    x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");	
		model = m;
		loadImage();
	}

	// Marshals this object into JSON DOM
	public Json marshal()
	{
		Json ob = Json.newObject();
		ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		return ob;
	}
	
	@Override
	boolean isCoinBlock()
	{
		return true;
	}
	
	@Override
	boolean isCoinBlockOn()
	{
		return coinOn;
	}
	
	@Override
	public void draw(Graphics g)
	{
		if(isCoinBlockOn())
		{
			g.drawImage(CoinBlock.coinBlockOn_image, x - model.mario.x + model.mario.screenPos, y, w, h, null);
		}
		else
		{
			g.drawImage(CoinBlock.coinBlockOff_image, x - model.mario.x + model.mario.screenPos, y, w, h, null);
		}
	}
	
	@Override
	public void update()
	{
		
	}
	
	void loadImage()
	{
		if(coinBlockOn_image == null & coinBlockOff_image == null)
		{
			coinBlockOn_image = View.loadImage("CoinBlockOn.png");
			coinBlockOff_image = View.loadImage("CoinBlockOff.png");
		}
			
	}

}
