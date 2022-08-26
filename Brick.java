import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Brick extends Sprite
{
	static BufferedImage brick_image = null;
	Model model;
	
	public Brick(int x_brick, int y_brick, int w_brick, int h_brick, Model m)
	{
		x = x_brick;								
		y = y_brick;
		w = w_brick;
		h = h_brick;
		vert_vel = 0;
		model = m;
		loadImage();
	}
	
	// Unmarshalling constructor
	public Brick(Json ob, Model m)
	{
	    x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		vert_vel = 0;
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
	boolean isBrick()
	{
		return true;
	}
	
	void loadImage()
	{
		if(brick_image == null)
			brick_image = View.loadImage("brick.png");
	}
	
	@Override
	public void draw(Graphics g)
	{
		g.drawImage(Brick.brick_image, x - model.mario.x + model.mario.screenPos, y, w, h, null);
	}
	
	public void update()
	{
		
	}
}