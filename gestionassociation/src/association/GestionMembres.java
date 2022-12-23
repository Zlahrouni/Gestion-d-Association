package association;

import java.util.HashSet;
import java.util.Set;

/**
 * Gestion des membres de l'association.
 *
 * @author Kilian Malherbe
 */
public class GestionMembres implements InterGestionMembres {
  
  /**
   * Numéro de sérialisation.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Listes de membres.
   */
  private Set<InterMembre> membres;
  
  /**
   * Président de l'association.
   */
  private InterMembre president;
  
  /**
   * Constructeur permettant d'initialiser la liste de membres.
   */
  public GestionMembres() {
    this.membres = new HashSet<InterMembre>();
  }
  
  @Override
  public boolean ajouterMembre(InterMembre membre) {
    String nomM = membre.getInformationPersonnelle().getNom();
    String prenomM = membre.getInformationPersonnelle().getPrenom();
    
    // Deux membres ne peuvent pas avoir le même nom et le même prénom
    for (InterMembre m : membres) {
      if (nomM.equals(m.getInformationPersonnelle().getNom())) {
        if (prenomM.equals(m.getInformationPersonnelle().getPrenom())) {
          return false;
        }
      }
    }
    
    membres.add(membre);
    return true;
  }
  
  @Override
  public boolean supprimerMembre(InterMembre membre) {
    String nomM = membre.getInformationPersonnelle().getNom();
    String prenomM = membre.getInformationPersonnelle().getPrenom();
    for (Evenement e : membre.ensembleEvenements()) {
      e.supressionParticipant(membre);
    }
    for (InterMembre m : membres) {
      if (nomM.equals(m.getInformationPersonnelle().getNom())) {
        if (prenomM.equals(m.getInformationPersonnelle().getPrenom())) {
          membres.remove(m);
          
          return true;
        }
      }
    }
    
    return false;
  }
  
  @Override
  public boolean designerPresident(InterMembre membre) {
    String nomM = membre.getInformationPersonnelle().getNom();
    String prenomM = membre.getInformationPersonnelle().getPrenom();
    
    for (InterMembre m : membres) {
      if (nomM.equals(m.getInformationPersonnelle().getNom())) {
        if (prenomM.equals(m.getInformationPersonnelle().getPrenom())) {
          president = membre;
          return true;
        }
      }
    }
    
    return false;
  }
  
  @Override
  public Set<InterMembre> ensembleMembres() {
    return membres;
  }
  
  @Override
  public InterMembre president() {
    return president;
  }
}
