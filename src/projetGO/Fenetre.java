package projetGO;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Fenetre extends JFrame implements ActionListener {
	
	
	
  private JMenuBar menuBar = new JMenuBar();
  private JMenu Partie = new JMenu("Partie");
  private JMenu About = new JMenu("A propos");

  private JMenuItem Nouvelle = new JMenuItem("Nouvelle partie");
  private JMenuItem save = new JMenuItem("Sauvegarder");
  private JMenuItem quit = new JMenuItem("Quitter");


  public Fenetre(){
    this.setSize(400, 400);
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

	public void pannel(){
		
		JButton[][] cases = new JButton[13][13];
		
		   JPanel pan = new JPanel();
		   pan.setLayout(new GridLayout(13, 13));
		   pan.setSize(400,400);
		   this.setLocationRelativeTo(null);
		   for (int i = 0; i < 13; i++){
			   for (int j = 0; j < 13; j++){
				   cases[i][j] = new JButton();
			   }
		   }	   
		   for (int i = 0; i < 13; i++){
			   for (int j = 0; j < 13; j++){
				   pan.add(cases[i][j]);
			   }
		   }
		   setContentPane(pan);
		   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   setSize(300,300);
		   setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
  		// TODO Auto-generated method stub
  		System.exit(0);
  	}
}