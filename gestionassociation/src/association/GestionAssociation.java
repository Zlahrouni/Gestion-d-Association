package association;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe Association. Chaque Objet Association a deux attribut : Un
 * gestionnaire d'evenements typé par InterGestionEvenements. Un gestionnaire de
 * membres typé par InterGestionMembres.

 * @author hanane
 *
 */
public class GestionAssociation implements InterGestionAssociation {
  
  /**
   * Numéro de sérialisation.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Gestionnaire des événements de l'association.
   */
  InterGestionEvenements gestionnaireEve;
  
  /**
   * Gestionnaire des membres de l'association.
   */
  InterGestionMembres gestionnaireMembre;
  
  /**
   * Constructeur sans paramètre initialisant les deux gestionnaires.
   */
  public GestionAssociation() {
    this.gestionnaireEve = new GestionEvenement();
    this.gestionnaireMembre = new GestionMembres();
  }
  
  /**
   * Récupère le gestionnaire d'événements.

   * @return InterGestionEvenements Gestionnaire d'événements
   */
  @Override
  public InterGestionEvenements gestionnaireEvenements() {
    return this.gestionnaireEve;
  }
  
  /**
   * Récupère le gestionnaire de membres.

   * @return InterGestionMembres Gestionnaire de membres
   */
  @Override
  public InterGestionMembres gestionnaireMembre() {
    return this.gestionnaireMembre;
  }
  
  /**
   * Stocke dans un fichier avec le chemin précisé en paramètre, les
   * informations concernant les événements et les membres de la société.

   * @param     nomFichier Chemin du fichier
   * @throws    IOException Le fichier n'existe pas
   */
  @Override
  public void sauvegarderDonnees(String nomFichier) throws IOException {
    
    ObjectOutputStream out =
        new ObjectOutputStream(new FileOutputStream(nomFichier));
    
    try {
      out.writeObject(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    out.flush();
    out.close();
  }
  
  /**
   * Affiche l'ensemble d'informations sur les membres et les événements à
   * partir d'un fichier dont le chemin est donné en paramètre (Cette méthode
   * peut générer une IOException).

   * @param     nomFichier Chemin du fichier
   * @throws    IOException Le fichier n'existe pas
   */
  public void chargerDonnees(String nomFichier) throws IOException {
    
    ObjectInputStream in =
        new ObjectInputStream(new FileInputStream(nomFichier));
    GestionAssociation a = null;
    
    try {
      a = (GestionAssociation) in.readObject();
      this.gestionnaireEve = a.gestionnaireEve;
      this.gestionnaireMembre = a.gestionnaireMembre();
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
    
    in.close();
  }
}


