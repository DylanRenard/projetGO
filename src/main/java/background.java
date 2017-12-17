

import java.awt.Graphics;

import javax.swing.JPanel;

public class background  extends  JPanel{
	public void PaintComponent(Graphics g){
		System.out.println("background appelé");
		g.fillOval(20,20,5,30);
	}
}
