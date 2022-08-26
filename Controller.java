import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener // Lets the controller interact with the screen, mouse, and keyboard.
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	boolean editingMode = false;
	int b_corner_x;
	int b_corner_y;
	
	Controller(Model m)
	{
		model = m;
	}
	
	void setView(View v)
	{
		view = v;
	}
	
	public void actionPerformed(ActionEvent e)
	{
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(editingMode)
		{
			b_corner_x = e.getX() + model.mario.x;
			b_corner_y = e.getY();
		}
	}
	
	
	public void mouseEntered(MouseEvent e) {    }
	public void mouseReleased(MouseEvent e) 
	{   
		if(editingMode)
		{
			int width = 0;
			int height = 0;
			int brickX = 0;
			int brickY = 0;
			int b_opp_corner_x = e.getX() + model.mario.x;
	        int b_opp_corner_y = e.getY();
	
			// Allows user to draw brick from all directions
			if(b_opp_corner_x < b_corner_x)
			{
				width = b_corner_x - b_opp_corner_x;
	            brickX = b_opp_corner_x;			
			}
			else
			{
				width = b_opp_corner_x - b_corner_x;
				brickX = b_corner_x;
			}
			if(b_opp_corner_y < b_corner_y)
			{
				height = b_corner_y - b_opp_corner_y;
				brickY = b_opp_corner_y;
			}
			else
			{
				height = b_opp_corner_y - b_corner_y;
				brickY = b_corner_y;
			}
			
			// Adds Brick to Array
			Brick b = new Brick(brickX, brickY, width, height, model);
			model.sprites.add(b);
		}
	}
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_SPACE: keySpace = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE: keySpace = false; break;
			case KeyEvent.VK_ESCAPE:
				System.out.println("Exiting now...");
				System.exit(0);
				break;
		}
		// Key Assignments
		char c = e.getKeyChar();
		
		if(c == 'e' || c == 'E')
		{
			editingMode = !editingMode;
			System.out.println("Edit mode: " + editingMode);
		}
		
		// Editing Keys
		if(editingMode)
		{
			if(c == 's' || c == 'S')
			{
				model.marshal().save("map.json");
				System.out.println("map saved");
			}
			
			if(c == 'l' || c == 'L')
			{
				Json j = Json.load("map.json");
				model.unmarshal(j);
			}
		}
	}

	public void keyTyped(KeyEvent e){   }

	void update()
	{
		// Scrolling the Bricks
		if(keyRight && !keyLeft)
		{
			model.mario.direction = 1;
			model.mario.running = true;
			model.mario.x += 10;
			
		}
		if(keyLeft && !keyRight)
		{
			model.mario.direction = -1;
			model.mario.running = true;
			model.mario.x -= 10;
		}
		
		// Mario Jump
		if(keySpace)
		{
			model.mario.jumpFrameCounter += 1;
			if(model.mario.jumpFrameCounter < 7)
			{
				model.mario.vert_vel += -5.5;  // Jump Acceleration
			}
		}
		
		if(keyUp)
		{
			model.mario.jumpFrameCounter += 1;
			if(model.mario.jumpFrameCounter == 1)
			{
				model.mario.vert_vel += -20;  // Jump Acceleration
			}
		}
		
	}
}