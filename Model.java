import java.util.ArrayList;
import java.util.Iterator;


class Model
{
	ArrayList<Sprite> sprites;
	Mario mario;
	
	Model()
	{
		sprites = new ArrayList<Sprite>();
		mario = new Mario();
		sprites.add(mario);
		
		Json j = Json.load("map.json");
		unmarshal(j);
		System.out.println("map loaded...");
	}
	
	public void update()
	{
		Iterator<Sprite> itr = sprites.iterator();
		while(itr.hasNext())
		{
			Sprite s = itr.next();
			s.update();
			if(s.isCoin())
			{
				Coin c1 = (Coin) s;
				if(c1.offScreen)
				{
					itr.remove();
				}
			}
		}

		this.snapIfOverlap();
	}
	
	// Marshals this object into JSON DOM
	public Json marshal()
	{
		Json ob = Json.newObject();
		return ob;
	}
	
	// Unmarshal Method
	public void unmarshal(Json ob)
	{
		
        Json tmpList1 = ob.get("bricks");
		for(int i = 0; i < tmpList1.size(); i++)
		{
            sprites.add(new Brick(tmpList1.get(i), this));
	    }
		Json tmpList2 = ob.get("coinBlocks");
		for(int i = 0; i < tmpList2.size(); i++)
		{
            sprites.add(new CoinBlock(tmpList2.get(i), this));
	    }
	}
	
	public void snapIfOverlap()
	{	
		Iterator<Sprite> itr1 = sprites.iterator();
		Coin c2 = null;
		
		while(itr1.hasNext())
		{
			Sprite sprite_1 = itr1.next();
			// Get Previous and Current position for moving sprites.
			if(sprite_1.isMario() || sprite_1.isCoin())
			{
				if(sprite_1.x_CurrentPos == sprite_1.x)
				{
					sprite_1.x_PreviousPos = sprite_1.x;
				}
				sprite_1.x_PreviousPos = sprite_1.x_CurrentPos;
				sprite_1.x_CurrentPos = sprite_1.x;
				
				if(sprite_1.y_CurrentPos == sprite_1.y)
				{
					sprite_1.y_PreviousPos = sprite_1.y;
				}
				sprite_1.y_PreviousPos = sprite_1.y_CurrentPos;
				sprite_1.y_CurrentPos = sprite_1.y;
			}
			
			Iterator<Sprite> itr2 = sprites.iterator();
			
			while(itr2.hasNext())
			{
				Sprite sprite_2 = itr2.next();
				
				boolean marioAndBrick = (sprite_1.isMario() && sprite_2.isBrick());
				boolean marioAndCoinBlock = (sprite_1.isMario() && sprite_2.isCoinBlock());
				boolean marioAndCoin = (sprite_1.isMario() && sprite_2.isCoin());
				boolean coinAndBrick = (sprite_1.isCoin() && sprite_2.isBrick());
				boolean coinAndCoinBlock = (sprite_1.isCoin() && sprite_2.isCoinBlock());
				
				if(!(sprite_1 == sprite_2) & (marioAndBrick || marioAndCoinBlock || marioAndCoin || coinAndBrick || coinAndCoinBlock))
				{						
					if(sprite_1.checkCollision(sprite_2))
					{	
						// Allow Mario to collect Coins
						if(marioAndCoin)
						{
							Coin c3 = (Coin) sprite_2;
							c3.offScreen = true;
						}
						else
						{
							
							boolean sprite_1AboveSprite_2 = sprite_1.y_PreviousPos + sprite_1.h <= sprite_2.y;
							boolean sprite_1BelowSprite_2 = sprite_1.y_PreviousPos >= sprite_2.y + sprite_2.h;
							
							// Sprite 1 runs into Sprite 2's left side
							if(sprite_1.x + sprite_1.w >= sprite_2.x && sprite_1.x_PreviousPos + sprite_1.w <= sprite_2.x)
							{
								// Check that Sprite 1 was not above or below Sprite 2 before collision
								if(!sprite_1AboveSprite_2 & !sprite_1BelowSprite_2)
								{
									// Snap Sprite 1 to pos before collision
									sprite_1.x = sprite_1.x_PreviousPos;
									sprite_1.x_CurrentPos = sprite_1.x;
								}
							}
							// Sprite 1 runs into Sprite 2's right side
							if(sprite_1.x <= sprite_2.x + sprite_2.w && sprite_1.x_PreviousPos >= sprite_2.x + sprite_2.w)
							{
								// Check that Sprite 1 was not above or below Sprite 2 before collision
								if(!sprite_1AboveSprite_2 & !sprite_1BelowSprite_2)
								{
									// Snap Sprite 1 to pos before collision
									sprite_1.x = sprite_1.x_PreviousPos;
									sprite_1.x_CurrentPos = sprite_1.x;
								}
							}
							// Sprite 1 falls onto Sprite 2
							if(sprite_1.y <= sprite_2.y + sprite_2.h && sprite_1.y + sprite_1.h <= sprite_2.y + sprite_2.h)
							{
								// Check if Sprite 1 came from above Sprite 2
								if(sprite_1.y_PreviousPos + sprite_1.h <= sprite_2.y)
								{
									// Snap Sprite 1 to top of Sprite 2
									sprite_1.vert_vel = 0;
									sprite_1.y = sprite_2.y - sprite_1.h;
									sprite_1.y_CurrentPos = sprite_1.y;
									
									if(sprite_1.isMario())
									{
										// Reset jump counter
										mario.jumpFrameCounter = 0;
									}
									
								}
							}
							// Sprite 1 jumps into Sprite 2
							if(sprite_1.y + sprite_1.h >= sprite_2.y && sprite_1.y >= sprite_2.y)
							{
								// Check if Sprite 1 came from below Sprite 2
								if(sprite_1.y_PreviousPos >= sprite_2.y + sprite_2.h)
								{	
									if(sprite_2.isCoinBlock())
									{
										CoinBlock cb = (CoinBlock) sprite_2;
										// Check if Coin Block is On
										if(cb.coinOn)
										{
											// Create new Coin and update count
											c2 = new Coin(sprite_2.x, sprite_2.y - 75, this);
											cb.coinCount++;
										}
										// Check coin count
										if(cb.coinCount >= 5)
										{
											// Turn Coin Block off and reset count
											cb.coinOn = false;
											cb.coinCount = 0;
										}
									}
									if(sprite_1.isMario())
									{
										// Snap Sprite 1 to bottom of Sprite 2
										sprite_1.vert_vel = 0;
										sprite_1.y = sprite_2.y + sprite_2.h;
										sprite_1.y_CurrentPos = sprite_1.y;
									}
								}
							}
						}
					}
				}
			}
		}
		// Add any new Coins to sprites list
		if(!(c2 == null))
		{
			sprites.add(c2);
		}
	}

	
	
}