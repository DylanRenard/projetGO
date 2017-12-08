package projetGO;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements ActionListener 
{
	private JMenuBar menuBar = new JMenuBar();
	private JMenu Partie = new JMenu("Partie");
	private JMenu About = new JMenu("A propos");
	private JMenuItem Nouvelle = new JMenuItem("Nouvelle partie");
	private JMenuItem save = new JMenuItem("Sauvegarder");
	private JMenuItem quit = new JMenuItem("Quitter");
	
	private Grille g;
	
	public Fenetre(Grille g)
	{
		this.g=g;
		this.setSize(800, 800);
	    this.setTitle("GO");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	
	    //On initialise nos menus      
	    this.Partie.add(Nouvelle);
	
	
	    //Ajout du sous-menu dans notre menu
	    this.Partie.add(save);
	    //Ajout d'un séparateur
	    this.Partie.addSeparator();
	    
	            
	     
	    this.Partie.add(quit);
	
	    //L'ordre d'ajout va déterminer l'ordre d'apparition dans le menu de gauche à droite
	    //Le premier ajouté sera tout à gauche de la barre de menu et inversement pour le dernier
	    this.menuBar.add(Partie);
	    this.menuBar.add(About);
	    this.setJMenuBar(menuBar);
	    this.setVisible(true);
	}

	public void pannel()
	{
		monMouseListener ml = new monMouseListener();
		
		JButton[][] cases = new JButton[g.getDim()][g.getDim()];
		
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(g.getDim(), g.getDim()));
		pan.setSize(800,800);
		this.setLocationRelativeTo(null);
		for (int i = 0; i < g.getDim(); i++)
		{
			for (int j = 0; j < g.getDim(); j++)
			{
				cases[i][j] = new JButton();
				cases[i][j].setBorder(null);
				cases[i][j].setBackground(Color.lightGray);
				cases[i][j].addMouseListener(ml);
			}
		}	   
		for (int i = 0; i < g.getDim(); i++)
		{
			for (int j = 0; j < g.getDim(); j++)
			{
				pan.add(cases[i][j]);
			}
		}
		setContentPane(pan);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,600);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
  		// TODO Auto-generated method stub
  		System.exit(0);
  	}
}


class monMouseListener implements MouseListener
{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		JButton b = (JButton)e.getSource();
		b.setBackground(Color.RED);
	}
	
}