import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CatMouseGame {
	private Random rn= new Random();
	private int[] catpost;//cat current position
	private int[] mousepost;//mouse current position
	private int gridSize;//grid size
	private int catSpeed;//cat speed
	private int mouseSpeed;//mouse speed
	private final int PANELGAP = 5;//gap from edge of panel (makes it more readable)


	/*
	 * Constructor Options:
	 * CatMouseGame()//default
	 * CatMouseGame(int)//set grid size
	 * CatMouseGame(int, int)//set cat, mouse speed
	 * CatMouseGame(int, int, int)//set grid size, cat, mouse speed
	 */
	public CatMouseGame()
	{
		gridSize = 250;
		catpost = start();
		mousepost = start();
		catSpeed = 30;
		mouseSpeed = 10;
	}
	public CatMouseGame(int gs)
	{
		gridSize = gs;
		catpost = start();
		mousepost = start();
		catSpeed = 30;
		mouseSpeed = 10;
	}
	public CatMouseGame(int cs, int ms)
	{
		gridSize = 250;
		catpost = start();
		mousepost = start();
		catSpeed = cs;
		mouseSpeed = ms;
	}
	public CatMouseGame(int gs, int cs, int ms)
	{
		gridSize = gs;
		catpost = start();
		mousepost = start();
		catSpeed = cs;
		mouseSpeed = ms;
	}
	
	//Play game in console
	public void playGame()
	{
		catpost = start();//set fresh start point
		mousepost = start();//set fresh start point
		int count = 0;
		//print starting point
		System.out.println(count+" Cat: "+Arrays.toString(catpost));
		System.out.println(count+" Mouse: "+Arrays.toString(mousepost));
		//run until cat catches mouse
		while(!Arrays.equals(mousepost, catpost))
		{
			step();//single move
			count++;
			//print new positions
			System.out.println(count+" Cat: "+Arrays.toString(catpost));
			System.out.println(count+" Mouse: "+Arrays.toString(mousepost));
		}
		
	}
	//Play game in GUI
	public void playGame(JFrame frame)
	{
		//set up frame and panel
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new GamePanel());
		frame.pack();
		frame.setVisible(true);
	}
	
	/*
	 * private panel class for drawing lines
	 */
	private class GamePanel extends JPanel
	{
		/*
		 * Zoom can be used to increase grid view size
		 * Cat/Mouse still play on smaller board, making movements more apparent near the end
		 */
		private final int ZOOM = 2;
		//Constructor sets size based on gridSize and PANELGAP
		public GamePanel()
		{
			setPreferredSize(new Dimension((gridSize*ZOOM)+(2*PANELGAP), (gridSize*ZOOM)+(2*PANELGAP)));
		}
		//draw board and cat/mouse paths
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			//gridLines(g);//draw grid lines
			int[][] temp;//store previous positions
			catpost = start();//set fresh start point
			mousepost = start();//set fresh start point
			int count = 0;
			//run until cat catches mouse
			while(!Arrays.equals(mousepost, catpost) && mousepost[0]>0 && mousepost[0]<250 &&mousepost[1]>0 && mousepost[1]<250)
			{
				temp = step();//single move, store previous positions cat/mouse
				count++;
				g.setColor(Color.RED);//cat is Red
				g.drawLine(temp[0][0]*ZOOM+PANELGAP, temp[0][1]*ZOOM+PANELGAP, catpost[0]*ZOOM+PANELGAP, catpost[1]*ZOOM+PANELGAP);
				System.out.println(count + " - Cat: " + temp[0][0] + ", " + temp[0][1] + " : " + catpost[0] + ", " + catpost[1]);
				
				g.setColor(Color.BLUE);//mouse is Blue
				g.drawLine(temp[1][0]*ZOOM+PANELGAP, temp[1][1]*ZOOM+PANELGAP, mousepost[0]*ZOOM+PANELGAP, mousepost[1]*ZOOM+PANELGAP);
				System.out.println(count + " - Mouse: " + temp[1][0] + ", " + temp[1][1] + " : " + mousepost[0] + ", " + mousepost[1]);
			}
		}
	}
	
	//getter methods for grid size, cat speed, and mouse speed
	public int getGridSize()
	{
		return gridSize;
	}
	public int getCatSpeed()
	{
		return catSpeed;
	}
	public int getMouseSpeed()
	{
		return mouseSpeed;
	}
	
	//initialize random start location
	private int[] start()
	{
		return new int[]{rn.nextInt(gridSize+1), rn.nextInt(gridSize+1)};//Array with x,y location
	}
	//ensure cat/mouse values stay within grid
	private int fixValue(int pos)
	{
		if(pos > gridSize)
			pos = gridSize;
		else if(pos < 0)
			pos = 0;
		return pos;
	}
	//choose direction and part of speed
	private double[] choice()//Defines cardinal direction of motion---Unit Vector
	{
		double direction[]={0,0};
		Random rn= new Random();
		int cardinal;
		if(catpost[0]<mousepost[0] && catpost[1]<mousepost[1])//If cat is above and left of mouse
		{
			cardinal = rn.nextInt(2);
			if(cardinal==0)//move X
			{
				direction[0]=1;
			}
			else//move Y
			{
				direction[1]=1;
			}
			
		}
		else if (catpost[0]>mousepost[0] && catpost[1]<mousepost[1])//If cat is above and right of mouse
		{
			cardinal = rn.nextInt(2);
			if(cardinal==0)
			{
				direction[0]=-1;
			}
			else
			{
				direction[1]=1;
			}
				
		}
		else if (catpost[0]==mousepost[0] && catpost[1]<mousepost[1])//If cat is directly above mouse
		{
			cardinal = rn.nextInt(3);
			if(cardinal==0)//Move X
			{
				direction[0]=1;
				
			}
			else if(cardinal==1)//Move X
			{
				direction[0]=-1;
				
			}
			else//Move Y
			{
				direction[1]=1;
				
			}
			
		}
		else if (catpost[0]<mousepost[0] && catpost[1]>mousepost[1])//If cat is below and left of mouse
		{
			cardinal = rn.nextInt(2);
			if(cardinal==0)
			{
				direction[0]=1;
			}
			else
			{
				direction[1]=-1;
			}
			
		}
		else if (catpost[0]>mousepost[0] && catpost[1]>mousepost[1])//If cat is below and right of mouse
		{
			cardinal = rn.nextInt(2);
			if(cardinal==0)
			{
				direction[0]=-1;
			}
			else
			{
				direction[1]=-1;
			}
			
		}
		else if (catpost[0]==mousepost[0] && catpost[1]>mousepost[1])//If cat is directly below of mouse
		{
			cardinal = rn.nextInt(3);
			if(cardinal==0)
			{
				direction[0]=1;
				
			}
			else if(cardinal==1)
			{
				direction[0]=-1;
				
			}
			else
			{
				direction[1]=-1;
				
			}
			
		}
		else if(catpost[0]<mousepost[0] && catpost[1]==mousepost[1])//If cat is directly to the left of mouse
		{
			cardinal = rn.nextInt(3);
			if(cardinal==0)
			{
				direction[0]=1;
				
			}
			else if(cardinal==1)
			{
				direction[1]=1;
				
			}
			else
			{
				direction[1]=-1;
				
			}
			
		}
		else if (catpost[0]>mousepost[0] && catpost[1]==mousepost[1])//If cat is directly to the right of mouse
		{
			cardinal = rn.nextInt(3);
			if(cardinal==0)
			{
				direction[0]=-1;
				
			}
			else if(cardinal==1)
			{
				direction[1]=1;
				
			}
			else
			{
				direction[1]=-1;
				
			}
			
		}
		return direction;
	}
	//take single step, return previous positions for drawing
	private int[][] step()
	{
		int[][] temp = new int[2][2];
		//store previous positions
		temp[0][0] = catpost[0];
		temp[0][1] = catpost[1];
		temp[1][0] = mousepost[0];
		temp[1][1] = mousepost[1];
		//have cat and mouse make independent speed choices
		double[] catDirection = choice();
		double[] mouseDirection = choice();
		//move cat and mouse
		catpost[0] = catpost[0]+(int)(catSpeed*catDirection[0]);//new catpost
		catpost[1] = catpost[1]+(int)(catSpeed*catDirection[1]);//new catpost
		mousepost[0] = mousepost[0]+(int)(mouseSpeed*mouseDirection[0]);//new mousepost
		mousepost[1] = mousepost[1]+(int)(mouseSpeed*mouseDirection[1]);//new mousepost
		//ensure cat and mouse are still inside grid
		catpost[0] = fixValue(catpost[0]);
		catpost[1] = fixValue(catpost[1]);
		mousepost[0] = fixValue(mousepost[0]);
		mousepost[1] = fixValue(mousepost[1]);
		
		return temp;
	}
}
