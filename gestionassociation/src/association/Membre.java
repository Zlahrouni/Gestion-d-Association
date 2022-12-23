package association;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Membre de l'association.
 *
 * @author Ziad Lahrouni
 */
public class Membre implements InterMembre {
  
  /**
   * Numéro de sérialisation.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Informations personnelles du membre.
   */
  InformationPersonnelle infoMembre;
  
  /**
   * Liste d'événements du membre.
   */
  List<Evenement> evenements;
  
  /**
   * Constructeur de Membre.
   */
  public Membre() {
    evenements = new ArrayList<Evenement>();
  }
  
  /**
   * Renvoie les événements auxquels participe le membre.
   *
   * @return Liste d'événements
   */
  public List<Evenement> getEvenements() {
    return evenements;
  }
  
  /**
   * Ajoute un ensemble d'événements dans la liste d'événements du membre.

   * @param evenements Liste d'événements à ajouter
   */
  public void setEvenements(List<Evenement> evenements) {
    
    for (Evenement e : evenements) {
      if (e != null) {
        this.evenements.add(e);
      }
    }
  }
  
  /**
   * Retourne la liste des événements auxquels le membre est inscrit.

   * @return evenements Liste d'événements
   */
  @Override
  public List<Evenement> ensembleEvenements() {
    return evenements;
  }
  
  /**
   * Retourne la liste des événements à venir.
   *
   * @return Liste d'événements à venir
   */
  @Override
  public List<Evenement> ensembleEvenementsAvenir() {
    List<Evenement> eve = new ArrayList<>();
    
    for (Evenement l : this.evenements) {
      if (l.getDate().compareTo(LocalDateTime.now()) > 0) {
        eve.add(l);
      }
    }
    
    return eve;
  }
  
  /**
   * Définit les informations personnelles d'un membre.

   * @param info information personnelle d'un membre
   */
  @Override
  public void definirInformationPersonnnelle(InformationPersonnelle info) {
    if (info != null) {
      infoMembre = info;
    }
  }
  
  /**
   * Retourne les informations personnelles du membre.

   * @return infoMembre Informations personnelles du membre
   */
  @Override
  public InformationPersonnelle getInformationPersonnelle() {
    return this.infoMembre;
  }
}
