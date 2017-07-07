/*
 * Author: Matt Tucker
 * 
 * Date: 12APR16
 * 
 * Description: Models a game of pursue/evade between a cat(red) and mouse(blue). If mouse reaches the edge of the
 * screen without being "caught" it wins. The purpose of this program is to provide data for the game theoretic 
 * setup of this game.
 */


import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class PlayGame {

	public static void main(String[] args) {
		CatMouseGame cmg = new CatMouseGame();
		//cmg.playGame();
		cmg.playGame(new JFrame("Cat Mouse Game"));
	}
  
}
