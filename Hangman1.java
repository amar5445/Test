//*************************************************************************************************************
// Amarender R Nampally                Hangman's program.     Problem- 4.A
//*************************************************************************************************************

import java.awt.*; 
import java.applet.*; 
import javax.swing.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.lang.*;

public class Hangman1 extends Applet{
// guessWord is when the users guesses the entire word at once
// guessList is which characters the user has guessed so far
String hiddenWord="", guessWord, guessList;
// number of wrong letter guesses
int missCount;
// maximum number of misses allowed
int maxMisses;
// mouseOver is boolean to the Mouse being over the "new game" button
boolean win, gameOver, mouseOver = true;
// this varible is needed to tell MouseMove not to repaint() if already hanged
boolean hanged;
// array corresponding to the hiddenWord, defining user's knowledge of each character
boolean[] knownChars;
// background color
Color bgColor = new Color(0xFF66CC);
char[] ch = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
int index=0;

	public void init()
	{
		// adjust window size, and background color
		setSize(1000,625);
		setBackground(bgColor);
		newGame();
	}

	public void newGame()
	{
        // set the random hidden word
		hiddenWord = getHiddenWord();
        guessList = "";
        guessWord = "";
        index=0;
        // define size of knownChars by the length of the hidden word
        knownChars = new boolean[hiddenWord.length()];
        // make known characters false unless it happens to be a space
        for (int i=0; i<hiddenWord.length(); i++)
        {	
        	if (hiddenWord.charAt(i) == ' ')
        		knownChars[i] = true;
        	else
        		knownChars[i] = false;
        }
        // initiate variables for a new game
        win = false;
        missCount = 0;
        maxMisses = 22;
        gameOver = false;
        hanged = false;
	}
	
	public void paint(Graphics g)
	{
		// highlight the "new word" button
		if (mouseOver)
			g.setColor(Color.cyan);
		else
			g.setColor(new Color(0x00eeeeee));
		// "start guess" button
		g.fillRect(50, 60, 100, 30);
		g.setColor(new Color(0x00aaaaaa));
		g.drawRect(49, 59, 102, 32);
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.BOLD, 16));
		g.drawString("Start Guess", 60, 80);
		g.setFont(new Font("Helvetica", Font.BOLD, 32));
		for(int i=0; i<=(hiddenWord.length()-1)*2; i++)
		{
			if (i%2 == 0)
			{
				if (hiddenWord.charAt(i/2) != ' ')
					g.drawLine(i*15+225,100,(i+1)*15+225,100);
				if (knownChars[i/2] == true)
					g.drawString(""+hiddenWord.charAt(i/2), i*15+224, 95);
			}
		}
		// draw figure according to number of misses
		switch (missCount)
		{
		// string 'em up
		case 22: animateHang(g); break;
		// right leg
		case 21:g.drawLine(450,400,475,410);
      case 20: g.drawLine(395,325,450,400);
      // left leg
      case 19:g.drawLine(290,400,265,410);
      case 18:g.drawLine(355,325,290,400);
      // right arm
      case 17:g.drawLine(432,222,432,200);
     	case 16:g.drawLine(432,222,454,222);
      case 15:g.drawLine(395,275,450,200);
      // left arm
      case 14:g.drawLine(308,222,312,200);
      case 13:g.drawLine(308,222,286,222);
		case 12:g.drawLine(350,275,290,200);
      //stomach
      case 11:g.drawOval(370,295,10,10); 
      case 10:g.drawOval(370,275,10,10); 
      case 9:g.drawOval(370,255,10,10); 
      case 8:  g.drawOval(349,250,51,91); 
      // face
		case 7: g.drawOval(349,199,51,51);
      // rope
		case 6: g.drawLine(375,150,375,199);
      // cross-bar
      case 5: g.drawLine(150,150,375,150);
      case 4: g.drawLine(150,175,175,150);
      // vertical pole
      case 3: g.drawLine(150,450,150,150);
      // base
      case 2: g.drawLine(150,450,265,500);
 		case 1: g.drawLine(150,450,35,500);
 		}
		// display list of all character guesses
		g.setColor(Color.black);
		for(int i=0; i<guessList.length(); i++)
		{
			g.drawString(""+guessList.charAt(i),50+i*28,40);
		}
		// display winning acknowledgements, and display correct answer:
		// (to make up for a complete answer entry as well as single char guess)
		if (win == true)
		{
			// 0x00009900 is dark green
			g.setColor(new Color(0x00009900));
			g.drawString("You Win!",600,200);
			gameOver = true;
			for(int i=0; i<=(hiddenWord.length()-1)*2; i++)
			{
				if (i%2 == 0)
				{
					g.drawString(""+hiddenWord.charAt(i/2), i*15+224, 95);
				}
			}
      
      // display number of misses
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.BOLD, 16));
		g.drawString("Guesses: "+missCount,175,168);

      int opt=JOptionPane.showConfirmDialog(null,"Do you want to play again? ");
      
      if ( opt==JOptionPane.YES_OPTION)
         newGame();
         
                                 
		}
		// display losing acknowledgements, and display correct answer
		if (missCount == maxMisses)
		{
			g.setColor(Color.red);
			g.drawString("You Lose!",600,200);
         
     
      int another=JOptionPane.showConfirmDialog(null,"Do you want to continue? ");
      
      if ( another==JOptionPane.YES_OPTION)
          gameOver = false;     
               
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.BOLD, 16));
		g.drawString("Guesses: "+missCount,175,168);
        
		}
		
		// display number of misses
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.BOLD, 16));
		g.drawString("Guesses: "+missCount,175,168);
      
      
	}
	
	public String getHiddenWord() {
		 hiddenWord = JOptionPane.showInputDialog(null, "Please enter the word to be Guessed:");
      		
		while (hiddenWord.length() < 4 || hiddenWord.length() > 5 || !(Pattern.matches("[a-zA-Z]+", hiddenWord)))
      {
      hiddenWord = JOptionPane.showInputDialog(null, "Please enter minimum of 4 char and maximum of 5 char word:");
      }
      hiddenWord = hiddenWord.toLowerCase();
		return hiddenWord;
	}
		
			
	// to handle the "start guess" button
	public boolean mouseDown(Event evt, int x, int y)
	{
		if ((x>50 && x<150) && (y>60 && y<90))
		{
      
      
		if (!gameOver)      
		{
		boolean rightGuess = false;
		// Cast the "key pressed" to a character
		 char keyChar = ch[index];

				// if character has previously been choosen, then get out of this method
			for(int i=0; i<guessList.length(); i++)
			{
				if (keyChar == guessList.charAt(i) || keyChar == guessList.toUpperCase().charAt(i))
					return true;
			}
			// concatenate the "key pressed" to the list of all chars pressed
			guessList = guessList+keyChar;
			// is the "key pressed" is one of the chars in the hidden word, then define it as known
			// in the knownChars array
			for(int i=0; i<hiddenWord.length(); i++)
			{
				if (keyChar == hiddenWord.charAt(i) || keyChar == hiddenWord.toUpperCase().charAt(i))
				{
					rightGuess = true;
					knownChars[i] = true;
				}				
			}
			// this loop makes "win = false" if there are any unknown characters
			// otherwise "win = true"
			win = true;
			for(int i=0; i<hiddenWord.length(); i++)
			{
				if (knownChars[i] == false)
				{
					win = false;
				}
			}
			if (rightGuess == false)
				missCount++;
      
      index++;
		
		repaint();
		}
		return true;
	}
   return true;
}
			
	
	
	public boolean mouseMove(Event evt, int x, int y)
	{
		// to detect crosses over the boundaries of the button
		// otherwise it would repaint needlessly
		boolean mouseToogle = mouseOver;
		if ((x>50 && x<150) && (y>60 && y<90))
		{
			mouseOver = true;
		}
		else
		{
			mouseOver = false;		
		}
		// if mouse comes into or exits button, then repaint() with or w/o highlights
		// except: void this is the animation will reoccur as a result of repainting
		if (mouseToogle != mouseOver && !hanged)
			repaint();
		
		return true;		
	}
		
	public void animateHang(Graphics g)
	{
		/* Animation of hanging:
		*  Make him grab his neck and make face flash colors from red down to blue purple.
		*/
		
		// this varible is need to tell MouseMove not to repaint() if already hanged, 
		// that would show the animation all over again. So, you have to sacrifice the
		// "new word" button highlight feature
		hanged = true;
		
		// first draw main skeleton
		g.setColor(Color.black);
		// arms
		g.drawLine(375,270,335,280);
		g.drawLine(375,270,415,280);
		g.drawLine(335,280,375,250);
		g.drawLine(415,280,375,250);
		// legs
		g.drawLine(375,400,350,375);
		g.drawLine(375,400,400,375);
		g.drawLine(350,375,350,400);
		g.drawLine(400,375,400,400);
		// torso
		g.drawLine(375,250,375,400);
		// face outline
		g.drawOval(349,199,51,51);
		// animate face hue
		int c;
		for (int i=0; i<220000; i++)
		{
			// every thousand to slow down
			c = i/1000;
			Color faceHue = new Color(255-c,0,c);
			g.setColor(faceHue);
			// draw the face with the current color
			g.fillOval(350,200,50,50);
         

		}
      
				
	}
	
}
