
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;
/**
 * Description:
 * <br/>Copyright (C), 2005-2008, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class PinBall
{
	//×ÀÃæµÄ¿í¶È 
	private final int TABLE_WIDTH = 300;
	//×ÀÃæµÄ¸ß¶È
	private final int TABLE_HEIGHT = 400;
	//ÇòÅÄµÄ´¹Ö±Î»ÖÃ
	private final int RACKET_Y = 340;
	//ÏÂÃæ¶¨ÒåÇòÅÄµÄ¸ß¶ÈºÍ¿í¶È
	private final int RACKET_HEIGHT = 20;
	private final int RACKET_WIDTH = 60;
	//Ð¡ÇòµÄ´óÐ¡
	private final int BALL_SIZE = 16;
	private Frame f = new Frame("µ¯ÇòÓÎÏ·");
	Random rand = new Random();
	//Ð¡Çò×ÝÏòµÄÔËÐÐËÙ¶È
	private int ySpeed = 10;
	//·µ»ØÒ»¸ö-0.5~0.5µÄ±ÈÂÊ£¬ÓÃÓÚ¿ØÖÆÐ¡ÇòµÄÔËÐÐ·½Ïò¡£
	private double xyRate = rand.nextDouble() - 0.5;
	//Ð¡ÇòºáÏòµÄÔËÐÐËÙ¶È
	private int xSpeed = (int)(ySpeed * xyRate * 2);
	//ballXºÍballY´ú±íÐ¡ÇòµÄ×ù±ê
	private int ballX = rand.nextInt(200) + 20;
	private int ballY = rand.nextInt(10) + 20;
	//racketX´ú±íÇòÅÄµÄË®Æ½Î»ÖÃ
	private int racketX = rand.nextInt(200);
	private MyCanvas tableArea = new MyCanvas();
	//ÓÃÓÚ±£´æÐèÒª»æÖÆÊ²Ã´Í¼ÐÎµÄ×Ö·û´®ÊôÐÔ
	private String shape = "";
	Timer timer;
	//ÓÎÏ·ÊÇ·ñ½áÊøµÄÆì±ê
	private boolean isLose = false;
	public void init()
	{
		tableArea.setPreferredSize(new Dimension(TABLE_WIDTH , TABLE_HEIGHT));
		f.add(tableArea);
		//¶¨Òå¼üÅÌ¼àÌýÆ÷
		KeyAdapter keyProcessor = new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				//°´ÏÂÏò×ó¡¢ÏòÓÒ¼üÊ±£¬ÇòÅÄË®Æ½×ù±ê·Ö±ð¼õÉÙ¡¢Ôö¼Ó
				if (ke.getKeyCode() == KeyEvent.VK_LEFT)
				{
					if (racketX > 0)
						racketX -= 10;
				}
				if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
				{
					if (racketX < TABLE_WIDTH - RACKET_WIDTH)
						racketX += 10;
				}
			}
		};
		//Îª´°¿ÚºÍtableArea¶ÔÏó·Ö±ðÌí¼Ó¼üÅÌ¼àÌýÆ÷
		f.addKeyListener(keyProcessor);
		tableArea.addKeyListener(keyProcessor);
		//¶¨ÒåÃ¿0.1ÃëÖ´ÐÐÒ»´ÎµÄÊÂ¼þ¼àÌýÆ÷¡£
		ActionListener taskPerformer = new ActionListener() 
		{
			public void actionPerformed(ActionEvent evt)
			{
				//Èç¹ûÐ¡ÇòÅöµ½×ó±ß±ß¿ò
				if (ballX  <= 0 || ballX >= TABLE_WIDTH - BALL_SIZE)
				{
					xSpeed = -xSpeed;
				}
				//Èç¹ûÐ¡Çò¸ß¶È³¬³öÁËÇòÅÄÎ»ÖÃ£¬ÇÒºáÏò²»ÔÚÇòÅÄ·¶Î§Ö®ÄÚ£¬ÓÎÏ·½áÊø¡£
				if (ballY >= RACKET_Y - BALL_SIZE && 
					(ballX < racketX || ballX > racketX + RACKET_WIDTH))
				{
					timer.stop();
					//ÉèÖÃÓÎÏ·ÊÇ·ñ½áÊøµÄÆì±êÎªtrue¡£
					isLose = true;
					tableArea.repaint();
				}
				//Èç¹ûÐ¡ÇòÎ»ÓÚÇòÅÄÖ®ÄÚ£¬ÇÒµ½´ïÇòÅÄÎ»ÖÃ£¬Ð¡Çò·´µ¯
				else if (ballY  <= 0 || 
					(ballY >= RACKET_Y - BALL_SIZE
						&& ballX > racketX && ballX <= racketX + RACKET_WIDTH))
				{
					ySpeed = -ySpeed;
				}
				//Ð¡Çò×ù±êÔö¼Ó
				ballY += ySpeed;
				ballX += xSpeed;
				tableArea.repaint();
			}
		};
		timer = new Timer(100, taskPerformer);
		timer.start();
		f.pack();
		f.setVisible(true);
	}
	public static void main(String[] args) 
	{
		new PinBall().init();
	}
	class MyCanvas extends Canvas
	{
		//ÖØÐ´CanvasµÄpaint·½·¨£¬ÊµÏÖ»æ»­
		public void paint(Graphics g)
		{
			//Èç¹ûÓÎÏ·ÒÑ¾­½áÊø
			if (isLose)
			{
				g.setColor(new Color(255, 0, 0));
				g.setFont(new Font("Times" , Font.BOLD, 30));
				g.drawString("ÓÎÏ·ÒÑ½áÊø£¡" , 50 ,200);

			}
			//Èç¹ûÓÎÏ·»¹Î´½áÊø
			else
			{
				//ÉèÖÃÑÕÉ«£¬²¢»æÖÆÐ¡Çò
				g.setColor(new Color(240, 240, 80));
				g.fillOval(ballX , ballY , BALL_SIZE, BALL_SIZE);
				//ÉèÖÃÑÕÉ«£¬²¢»æÖÆÇòÅÄ
				g.setColor(new Color(80, 80, 200));
				g.fillRect(racketX , RACKET_Y , RACKET_WIDTH , RACKET_HEIGHT);
			}
		}
	}
}