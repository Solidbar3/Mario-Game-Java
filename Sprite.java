import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

abstract class Sprite 
{
	int x, y, w, h;
	int x_CurrentPos, x_PreviousPos, y_CurrentPos, y_PreviousPos;
	double vert_vel;

	abstract void update();
	abstract void draw(Graphics g);
	
	boolean isMario()
	{
		return false;
	}
	
	boolean isBrick()
	{
		return false;
	}
	
	boolean isCoinBlock()
	{
		return false;
	}
	
	boolean isCoin()
	{
		return false;
	}
	
	boolean isCoinBlockOn()
	{
		return true;
	}
	
	public boolean checkCollision(Sprite s)
	{
		if(this.x + this.w <= s.x) // this right < sprite's left
			return false;
		if(this.x >= s.x + s.w) // this left > sprite's right
			return false;
		if(this.y + this.h <= s.y) // this botttom < sprite's top
			return false;
		if(this.y >= s.y + s.h) // this top > sprite's bottom
			return false;
		return true;
	}
}
