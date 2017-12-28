

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

//TODO creer les images pour les boutons
//TODO ajouter le bouton "passer"
//TODO ajout d'info : couleur du joueur en cour, 
public class Fenetre extends JFrame implements ActionListener 
{
	private JMenuBar menuBar = new JMenuBar();
	private JMenu Partie = new JMenu("Partie");
	private JMenu About = new JMenu("A propos");
	private JMenuItem Nouvelle = new JMenuItem("Nouvelle partie");
	private JMenuItem Charger = new JMenuItem("Charger une partie");
	private JMenuItem save = new JMenuItem("Sauvegarder");
	private JMenuItem quit = new JMenuItem("Quitter");
	private JMenuItem infos = new JMenuItem("informations");
	
	private BufferedImage planche;
	
	
	private Grille g;
	
	public Fenetre(Grille g)
	{
		this.g=g;
		this.setSize(800, 900);
	    this.setTitle("GO");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	
	    //On initialise nos menus      
	    this.Partie.add(Nouvelle);
	    this.Partie.add(Charger);
	
	    //Ajout du sous-menu dans notre menu
	    this.Partie.add(save);
	    //Ajout d'un séparateur
	    this.Partie.addSeparator();
	    
	    this.About.add(infos);
	     
	    this.Partie.add(quit);
	
	    //L'ordre d'ajout va déterminer l'ordre d'apparition dans le menu de gauche à droite
	    //Le premier ajouté sera tout à gauche de la barre de menu et inversement pour le dernier
	    this.menuBar.add(Partie);
	    this.menuBar.add(About);
	    this.setJMenuBar(menuBar);
	    this.setContentPane(new background());
	    this.setVisible(true);
	}

	public void pannel()
	{
		monMouseListener ml = new monMouseListener();
		
		JButton[][] cases = new JButton[g.getDim()][g.getDim()];
		
		background pan = new background();
		pan.setLayout(new GridLayout(g.getDim(), g.getDim()));
		pan.setSize(800,850);
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
		System.out.println("coucou");
  		// TODO Auto-generated method stub
  		System.exit(0);
  	}
}

//TODO deplacer dans un fichier apart
class monMouseListener implements MouseListener
{


	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		JButton b = (JButton)e.getSource();
		b.setBackground(Color.RED);
	}
	
	
}