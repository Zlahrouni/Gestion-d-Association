package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import association.Evenement;
import association.GestionAssociation;
import association.GestionEvenement;
import association.InformationPersonnelle;
import association.InterMembre;
import association.Membre;
import java.io.IOException;
import java.time.Month;
import org.junit.jupiter.api.Test;

/**
 * Test unitaire de la classe GestionAssociation.
 *
 * @author Hanane Erraji
 *
 */
class TestGestionAssociation extends GestionAssociation {
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * Classe gestionnaire de l'association.
   */
  GestionAssociation asso;
  
  /**
   * Classe gestionnaire d'événements.
   */
  GestionEvenement eve;
  
  /**
   * Test de constructeur.
   */
  @Test
  void testCreationassociation() {
    asso = new GestionAssociation();
    assertTrue(this.asso != null);
  }
  
  /**
   * Test d'initialisation de la classe gestionnaire d'événements.
   */
  @Test
  void testRetourEvenement() {
    asso = new GestionAssociation();
    eve = (GestionEvenement) asso.gestionnaireEvenements();
    assertTrue(this.eve != null);
  }
  
  /**
   * Test de création d'événements.
   */
  @Test
  void creationEvenement() {
    
    asso = new GestionAssociation();
    eve = (GestionEvenement) asso.gestionnaireEvenements();
    assertTrue(this.eve != null);
    
    // un événement avec un jour incompatible avec son mois
    // ne va pas être crée
    Evenement evenement = asso.gestionnaireEvenements().creerEvenement("anniv",
        "Sandiego", 31, Month.FEBRUARY, 2023, 9, 23, 120, 0);
    assertTrue(evenement == null);
    
    // un événement avec date inférieur à la date courante
    // na va pas être crée
    evenement = asso.gestionnaireEvenements().creerEvenement("anniv",
        "Sandiego", 9, Month.FEBRUARY, 2022, 9, 23, 120, 0);
    assertTrue(evenement == null);
    
    // un événement avec une heure qui dépasse 23.
    // ne va pas être créé
    evenement = asso.gestionnaireEvenements().creerEvenement("anniv",
        "Sandiego", 9, Month.FEBRUARY, 2022, 36, 23, 120, 0);
    assertTrue(evenement == null);
    
    // un événement avec une heure negative.
    // ne va pas être créé
    evenement = asso.gestionnaireEvenements().creerEvenement("anniv",
        "Sandiego", 9, Month.FEBRUARY, 2022, -10, 23, 120, 0);
    assertTrue(evenement == null);
    
    // un événement avec des minutes qui dépassent 59.
    // ne va pas être créé
    evenement = asso.gestionnaireEvenements().creerEvenement("anniv",
        "Sandiego", 9, Month.FEBRUARY, 2022, 10, 60, 120, 0);
    assertTrue(evenement == null);
    
    // un événement avec des minutes négatives.
    // ne va pas être créé
    evenement = asso.gestionnaireEvenements().creerEvenement("anniv",
        "Sandiego", 9, Month.FEBRUARY, 2022, 10, -10, 120, 0);
    assertTrue(evenement == null);
  }
  
  /**
   * Création d'une association. Ajout des événements et des membres à
   * association. Sauvegarder association dans un fichier nommé
   * InformationAssociation Instancier un nouveau objet Association chargé les
   * données dans association à partir du fichier InformationAssociation.
   */
  
  @Test
  void testSauvgardeChargeDonnees() {
    
    
    InterMembre membre = new Membre();
    InformationPersonnelle info =
        new InformationPersonnelle("Hanane", "ERRAJI");
    membre.definirInformationPersonnnelle(info);
    asso = new GestionAssociation();
    eve = (GestionEvenement) asso.gestionnaireEvenements();
    
    
    Evenement evenement = asso.gestionnaireEvenements().creerEvenement("anniv",
        "Sandiego", 9, Month.FEBRUARY, 2023, 10, 10, 120, 40);
    
    // L'ajout d'un membre à un événement
    // On ne peut pas ajouter un événement dans la liste d'un membre sans que ce
    // membre soit inscrit
    // une fois inscrit le membre va directement avoir cet événement dans sa
    // liste d'évenement
    assertTrue(eve.inscriptionEvenement(evenement, membre));
    
    // l'evenement a été bien ajouté dans la liste d'evenement de membre
    assertTrue(membre.ensembleEvenements().contains(evenement));
    
    // sauvegarder les objets evenements dans un fichier
    // "InformationAssociation"
    try {
      asso.sauvegarderDonnees("InformatiosAssociation");
    } catch (IOException e) {
      
      e.printStackTrace();
    }
    
    // Création d'un nouveau objet GestionAssociation
    // Charger les données stockées dans "InformationAssociation"
    
    GestionAssociation association = new GestionAssociation();
    
    try {
      association.chargerDonnees("InformatiosAssociation");
    } catch (IOException e) {
      
      e.printStackTrace();
    }
    // Vérifier si les deux Objet GestionAssociation ont la même liste
    // d'événements.

    assertTrue(
        asso.gestionnaireEvenements().ensembleEvenements().size() == association
            .gestionnaireEvenements().ensembleEvenements().size());
  }
}