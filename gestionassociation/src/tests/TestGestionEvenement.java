package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.Evenement;
import association.GestionEvenement;
import association.InformationPersonnelle;
import association.Membre;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test unitaire de la classe GestionEvenement.
 *
 * @author Youenn Robitzer
 *
 */
class TestGestionEvenement extends GestionEvenement {
  
  /**
   * Version de la classe.
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * objet GestionEvenement pour les tests.
   */
  private GestionEvenement ge;
  
  /**
   * Objet membre pour les tests.
   */
  private Membre mb;
  
  /**
   * Instancie un membre et ces information ainsi qu'un objet GestionEvenement
   * pour les tests.
   * 
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  void setUp() throws Exception {
    mb = new Membre();
    InformationPersonnelle info = new InformationPersonnelle("Kilian",
        "Malherbe", "12 rue des paquerette", 21);
    mb.definirInformationPersonnnelle(info);
    ge = new GestionEvenement();
  }
  
  /**
   * Vérifie la création d'un événement et que tout les paramètre soit les bons.
   */
  @Test
  void testCreerEvenement() {
    Evenement e = ge.creerEvenement("séminaire", "paris", 10, Month.APRIL, 2030,
        12, 12, 240, 60);
    
    assertEquals(e.getNom(), "séminaire");
    assertEquals(e.getLieu(), "paris");
    assertEquals(e.getDuree(), 240);
    assertEquals(e.getNbParticipantsMax(), 60);
    assertEquals(e.getDate(), LocalDateTime.of(2030, 4, 10, 12, 12));
  }
  
  /**
   * Vérifie que la suppression d'un événement est effective.
   */
  @Test
  void testSupprimerEvenement() {
    Evenement e = ge.creerEvenement("séminaire", "paris", 10, Month.APRIL, 2030,
        12, 12, 240, 60);
    assertEquals(ge.ensembleEvenements().size(), 1);
    ge.supprimerEvenement(e);
    assertEquals(ge.ensembleEvenements().size(), 0);
  }
  
  /**
   * Vérifie si la fonction ensembleEvenements() renvoie les bons événements.
   * 
   */
  @Test
  void testEnsembleEvenements() {
    Evenement e = ge.creerEvenement("séminaire", "paris", 10, Month.APRIL, 2030,
        12, 12, 240, 60);
    assertNotNull(e);
    assertEquals(e.getNom(), "séminaire");
    assertEquals(ge.ensembleEvenements().get(0), e);
    ge.supprimerEvenement(e);
    assertEquals(ge.ensembleEvenements().size(), 0);
  }
  
  /**
   * Vérifie si l'inscription d'un membre à un événement fonctionne.
   */
  @Test
  void testInscriptionEvenement() {
    Evenement e = ge.creerEvenement("séminaire", "paris", 10, Month.APRIL, 2030,
        12, 12, 240, 60);
    ge.inscriptionEvenement(e, mb);
    assertTrue(ge.ensembleEvenements().get(0).getParticipants().contains(mb));
    assertTrue(mb.ensembleEvenements().contains(e));
    ge.supprimerEvenement(e);
    assertEquals(ge.ensembleEvenements().size(), 0);
    
    assertFalse(mb.ensembleEvenements().contains(e));
    assertFalse(ge.inscriptionEvenement(e, null));
    assertFalse(ge.inscriptionEvenement(null, mb));
  }
  
  /**
   * Vérifie si la désinscription d'un membre à un événement est effective.
   */
  @Test
  void testAnnulerEvenement() {
    Evenement e = ge.creerEvenement("séminaire", "paris", 10, Month.APRIL, 2030,
        12, 12, 240, 60);
    ge.inscriptionEvenement(e, mb);
    assertTrue(mb.ensembleEvenements().contains(e));
    ge.annulerEvenement(e, mb);
    assertFalse(ge.ensembleEvenements().get(0).getParticipants().contains(mb));
  }
  
  /**
   * Test si le constructeur instancie bien la liste.
   */
  @Test
  void testConstructeur() {
    assertNotNull(ge.ensembleEvenements());
    assertNotNull(ge.ensembleEvenementAvenir());
  }
  
  /**
   * Vérifie si la fonction ensembleEvenementsAvenir() renvoie les bons
   * événements.
   */
  @Test
  void testEnsembleEvenementsAvenir() {
    Evenement e = ge.creerEvenement("séminaire", "paris", 10, Month.APRIL, 2030,
        12, 12, 240, 60);
    assertEquals(ge.ensembleEvenementAvenir().get(0), e);
    assertTrue(ge.ensembleEvenements().remove(e));
    assertEquals(0, ge.ensembleEvenements().size());
  }
}
