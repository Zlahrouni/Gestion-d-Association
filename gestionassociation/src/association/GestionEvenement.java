package association;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Gestion des évènements d'une association.
 *
 * @author Youenn Robitzer
 *
 */
public class GestionEvenement implements InterGestionEvenements {
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Listes d'événements.
   */
  private List<Evenement> evenements;
  
  /**
   * Constructeur permettant d'instancier les listes.
   */
  public GestionEvenement() {
    evenements = new ArrayList<Evenement>();
  }
  
  @Override
  public Evenement creerEvenement(String nom, String lieu, int jour, Month mois,
      int annee, int heure, int minutes, int duree, int nbParticipants) {
    GregorianCalendar c = new GregorianCalendar();
    if (-999_999_999 > annee || annee > 999_999_999) {
      return null;
    }
    
    if (mois.getValue() < 1 || mois.getValue() > 12) {
      return null;
    }
    
    if (jour < 1 || jour > mois.length(c.isLeapYear(annee))) {
      return null;
    }
    
    if (heure < 0 || heure > 23 || minutes < 0 || minutes > 59) {
      return null;
    }
    LocalDateTime date =
        LocalDateTime.of(annee, mois.getValue(), jour, heure, minutes);
    if (date.compareTo(LocalDateTime.now()) < 0) {
      return null;
    }
    Evenement evt = new Evenement(nom, lieu, date, nbParticipants, duree);
    for (Evenement e : evenements) {
      if (!e.pasDeChevauchementLieu(evt)) {
        return null;
      }
    }
    
    evenements.add(evt);
    return evt;
  }
  
  @Override
  public void supprimerEvenement(Evenement evt) {
    
    if (!evenements.contains(evt)) {
      return;
    }
    
    for (InterMembre im : evt.getParticipants()) {
      im.ensembleEvenements().remove(evt);
    }
    
    evenements.remove(evt);
  }
  
  @Override
  public List<Evenement> ensembleEvenements() {
    return this.evenements;
  }
  
  @Override
  public List<Evenement> ensembleEvenementAvenir() {
    List<Evenement> eve = new ArrayList<>();
    
    for (Evenement l : this.evenements) {
      if (l.getDate().compareTo(LocalDateTime.now()) > 0) {
        eve.add(l);
      }
    }
    
    return eve;
  }
  
  @Override
  public boolean inscriptionEvenement(Evenement evt, InterMembre mbr) {
    if (evt == null) {
      return false;
    }
    if(mbr == null) {
      return false;
    }
    for (Evenement e : mbr.ensembleEvenements()) {
      if (!e.pasDeChevauchementTemps(evt)) {
        return false;
      }
    }
    
    return evt.ajoutParticipant(mbr);
  }
  
  @Override
  public boolean annulerEvenement(Evenement evt, InterMembre mbr) {
    return evt.supressionParticipant(mbr);
  }
}
