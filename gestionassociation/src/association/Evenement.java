package association;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Événement d'une association.
 *
 * @author Hanane Erraji / Youenn Robitzer
 *
 */
public class Evenement implements java.io.Serializable {
  
  /**
   * Numéro de sérialisation.
   */
  private static final long serialVersionUID = -6189875735852385723L;
  
  /**
   * Nom de l'événement.
   */
  private String nom;
  
  /**
   * Lieu de l'événement.
   */
  private String lieu;
  
  /**
   * Date de l'événément.
   */
  private LocalDateTime date;
  
  /**
   * Durée de l'événement.
   */
  private int duree;
  
  /**
   * Nombre de participants maximum à l'événement.
   */
  private int nbParticipantsMax;
  
  /**
   * Liste de participants à l'événement.
   */
  private Set<InterMembre> participants;
  
  /**
   * Premier constructeur de la classe évènement, récupère la date actuelle si
   * celle passé en paramètre est <code>null</code> et une chaîne vide pour le
   * lieu si le paramètre est <code>null</code>.

   * @param nom     Nom de l'événement, ne doit pas être <code>null</code>
   * @param lieu    Nom du lieu de l'événement, ne doit pas être <code>null</code>
   * @param date    Date de l'evenement, ne doit pas être <code>null</code>
   * @param max     Nombre maximal de participant
   * @param duree   Durée de l'événememt (en minutes)
   */
  public Evenement(String nom, String lieu, LocalDateTime date, int max,
      int duree) {
    
    if (nom == null) {
      this.nom = "";
    } else {
      this.nom = nom;
    }
    
    if (lieu == null) {
      this.lieu = "";
    } else {
      this.lieu = lieu;
    }
    
    if (date == null) {
      this.date = LocalDateTime.now();
    } else {
      this.date = date;
    }
    
    this.nbParticipantsMax = max;
    this.duree = duree;
    this.participants = new HashSet<InterMembre>();
  }
  
  /**
   * Second constructeur de la classe évènement, sans date. Ce constructeur fait
   * appel au 1er constructeur.
   *
   * @param nom     Nom de l'événement, ne doit pas être <code>null</code>
   * @param lieu    Nom du lieu de l'événement, ne doit pas être <code>null</code>
   * @param max     Nombre maximal de participant
   * @param duree   Durée de l'événememt (en minutes)
   */
  
  public Evenement(String nom, String lieu, int max, int duree) {
    this(nom, lieu, LocalDateTime.now(), max, duree);
  }
  
  /**
   * Récupère le hashCode d'un objet Événement.

   * @return Le hash généré
   */
  @Override
  public int hashCode() {
    return Objects.hash(date, duree, lieu, nbParticipantsMax, nom,
        participants);
  }
  
  /**
   * Compare deux objets Événement par rapport à leurs attributs.

   * @return <code>true</code> Les objets sont égaux, 
   *         <code>false</code> sinon
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    
    Evenement other = (Evenement) obj;
    return Objects.equals(date, other.date) && duree == other.duree
        && Objects.equals(lieu, other.lieu)
        && nbParticipantsMax == other.nbParticipantsMax
        && Objects.equals(nom, other.nom)
        && Objects.equals(participants, other.participants);
  }
  
  /**
   * Affiche les informations d'un événement.

   * @return Informations de l'événement
   */
  @Override
  public String toString() {
    String[] par = new String[this.participants.size()];
    String res = "";
    
    this.participants.forEach(p -> {
      for (int i = 0; i < par.length; i++) {
        par[i] = p.getInformationPersonnelle().toString();
      }
    });
    
    for (int i = 0; i < par.length; i++) {
      res += par[i];
    }
    
    return "L'evenement " + nom + " aura lieu à" + lieu + " le " + date
        + ", la durrée est de " + duree + " minutes \nParticipants : "
        + participants.size() + "/" + nbParticipantsMax + res;
  }
  
  /**
   * Récupère le nom d'un événement.

   * @return Nom de l'événement
   */
  
  public String getNom() {
    return nom;
  }
  
  /**
   * Modifie le nom d'un événement.

   * @param nom Nom de l'événement
   */
  public void setNom(String nom) {
    if (nom != null && nom != " " && nom != "") {
      this.nom = nom;
    }
    
  }
  
  /**
   * Récupère le lieu d'un événement.

   * @return Lieu de l'événement.
   */
  public String getLieu() {
    return lieu;
  }
  
  /**
   * Modifie le lieu d'un événement.
   *
   * @param lieu Lieu de l'événement
   */
  
  public void setLieu(String lieu) {
    if (lieu != null && lieu != " " && lieu != "") {
      this.lieu = lieu;
    }
  }
  
  /**
   * Récupère la date d'un événement.
   *
   * @return la date de l'événement.
   */
  public LocalDateTime getDate() {
    return this.date;
  }
  
  /**
   * Modifie la date d'un événement par une nouvelle date si cette dernière
   * n'est pas <code>null</code>.
   *
   * @param date Date de l'événement
   */
  
  public void setDate(LocalDateTime date) {
    if (date != null) {
      this.date = date;
    }
  }
  
  /**
   * Récupère la durée d'un événement.
   *
   * @return Durée de l'événement.
   */
  public int getDuree() {
    return duree;
  }
  
  /**
   * Modifie la durée d'un événement.

   * @param duree durée de l'événement
   */
  public void setDuree(int duree) {
    if (duree != 0) {
      this.duree = duree;
    }
  }
  
  /**
   * Récupère le nombre de participant d'un événement.

   * @return le nombre de participant max
   */
  public int getNbParticipantsMax() {
    return nbParticipantsMax;
  }
  
  /**
   * Modifie le nombre maximum de participants si il est supérieur au nombre de
   * participants actuel.

   * @param nbParticipantsMax Nombre de participants maximum à l'évènement
   */
  
  public void setNbParticipantsMax(int nbParticipantsMax) {
    if (this.participants.size() <= nbParticipantsMax) {
      this.nbParticipantsMax = nbParticipantsMax;
    }
  }
  
  /**
   * Récupère la liste des participants d'un évènement.

   * @return Renvoie la set de participants qui sont inscrits à l'évènement.
   */
  
  public Set<InterMembre> getParticipants() {
    return this.participants;
  }
  
  /**
   * Modifie la liste de participants à un événements si la liste sa taille est
   * inférieur au nombre maximum de participants.

   * @param participants Liste de participants
   */
  public void setParticipants(Set<InterMembre> participants) {
    if (participants.size() <= this.nbParticipantsMax) {
      this.participants = participants;
    }
  }
  
  
  /**
   * Vérifie si deux événements ne chevauchent pas dans le même lieu et temps.

   * @param evt Événement à vérifier
   * @return <code>true</code> Les deux événement ne se chevauchent pas dans le
   *         lieu/temps, <code>false</code> sinon
   */
  public boolean pasDeChevauchementLieu(Evenement evt) {
    
    // Si l'événement comparé n'existe pas, il n'y a pas de chevauchement
    if (evt == null) {
      return true;
    }
    
    if (this == evt) {
      return false;
    }
    
    // Les événements sont dans le même lieu et se chevauchent dans le temps
    if (this.lieu.equals(evt.lieu) && !this.pasDeChevauchementTemps(evt)) {
      return false;
    }
    
    return true;
  }
  
  /**
   * Vérifie si deux événements ne chevauchent pas dans le temps.

   * @param evt Événement à vérifier
   * @return <code>true</code> Les deux événement ne se chevauchent pas dans le
   *         temps, <code>false</code> sinon
   */
  public boolean pasDeChevauchementTemps(Evenement evt) {
    
    // Si l'événement comparé n'existe pas, il n'y a pas de chevauchement
    if (evt == null) {
      return true;
    }
    
    if (this == evt) {
      return false;
    }
    
    int comp = this.getDate().compareTo(evt.getDate());
    
    // Les deux évènement commencent en même temps
    if (comp == 0) {
      return false;
    }
    
    // Les deux événement se chevauchent par rapport à leurs durées
    if (comp < 0) {
      comp = this.getDate().plusMinutes(this.duree).compareTo(evt.getDate());
    } else {
      comp = evt.getDate().plusMinutes(evt.duree).compareTo(this.getDate());
    }
    
    // L'un des deux événements commence dès la fin ou après le deuxième
    // évènement : il n' y a pas de chevauchement
    if (comp <= 0) {
      return true;
    } else { // L'un des deux évènement commence avant que le deuxième se termine
      return false;
    }
  }
  
  /**
   * Ajoute un participant à un événement, à condition que le membre existe, que
   * l'événement ne soit pas passé et que le nombre maximal de membre ne soit pas dépassé.
   *
   * @param m Membre à rajouter
   * @return <code>true</code>Le membre a été ajouté, <code>false</code> sinon
   */
  public boolean ajoutParticipant(InterMembre m) {
    
    if (m == null || this.date.compareTo(LocalDateTime.now()) < 0) {
      return false;
    }
    
    if (this.participants.size() >= this.nbParticipantsMax) {
      return false;
    }
    
    if (!this.participants.add(m)) {
      return false;
    }
    
    if (!m.ensembleEvenements().add(this)) {
      return false;
    }
    
    return true;
  }
  
  /**
   * Supprime un participant d'un événement, à condition que le membre existe.
   *
   * @param m Membre à supprimer
   * @return <code>true</code>Le membre a été supprimé, <code>false</code> sinon
   */
  public boolean supressionParticipant(InterMembre m) {
    
    if (m == null) {
      return false;
    }
    
    if (!this.participants.remove(m)) {
      return false;
    }
    
    if (!m.ensembleEvenements().remove(this)) {
      return false;
    }
    
    return true;
  }
}