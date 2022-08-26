::@echo off
javac Game.java View.java Controller.java Model.java Mario.java Brick.java Json.java Sprite.java CoinBlock.java Coin.java
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Game	
)

