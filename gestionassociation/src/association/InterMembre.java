package association;

import java.util.List;

/**
 * Définit les actions pour un membre d'une association qui peut participer à
 * des événements organisés par l'association.
 *
 * @author Eric Cariou
 */
public interface InterMembre extends java.io.Serializable {
  /**
   * La liste des événements auquel le membre est inscrit ou a participé.
   *
   * @return la liste des événements du membre
   */
  List<Evenement> ensembleEvenements();

  /**
   * La liste des événements auquel le membre est inscrit et qui n'ont pas
   * encore eu lieu.
   *
   * @return la liste des événements à venir du membre
   */
  List<Evenement> ensembleEvenementsAvenir();

  /**
   * Définit les informations personnelles du membre.
   *
   * @param info Les informations personnelles du membre.
   */
  void definirInformationPersonnnelle(InformationPersonnelle info);

  /**
   * Renvoie les informations personnelles du membre.
   *
   * @return l'objet contenant les informations personnelles du membre ou
   *         <code>null</code> si elles n'ont pas été définies
   */
  InformationPersonnelle getInformationPersonnelle();
}
