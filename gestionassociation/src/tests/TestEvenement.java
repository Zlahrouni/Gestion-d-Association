package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import association.Evenement;
import association.InformationPersonnelle;
import association.Membre;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test JUnit de Evenement.
 *
 * @author Youenn Robitzer
 *
 */

class TestEvenement {
  
  /**
   * evenement classique.
   */
  private Evenement evenementCl;
  
  /**
   * evenement sans date.
   */
  private Evenement evenementSd;
  
  /**
   * Date de test.
   */
  private LocalDateTime date;
  /**
   * Membre de test.
   */
  private Membre mb;
  /**
   * Objet info personnelle de test.
   */
  private InformationPersonnelle info;
  
  /**
   * Instancie un evenement classique et un sans date pour les tests.
   *
   * @throws Exception ne peut pas �tre lev�e ici
   */
  
  @BeforeEach
  void setUp() throws Exception {
    mb = new Membre();
    info = new InformationPersonnelle("Kilian", "Malherbe",
        "12 rue des paquerette", 21);
    date = LocalDateTime.of(2020, 4, 20, 12, 46);
    evenementSd = new Evenement("event sans date", "Brest", 12, 40);
    evenementCl = new Evenement("event classique", "Bordeau", date, 5, 90);
  }
  
  /**
   * Test d'assignement simple de lieu, exemple set le lieu sur Strasbourg.
   */
  @Test
  void testModifVilleStrasbourg() {
    evenementCl.setLieu("Strasbourg");
    assertEquals(evenementCl.getLieu(), "Strasbourg");
  }
  
  /**
   * Test d'assignement simple de nouveau nombre de participant , exemple set le
   * nombre sur 6.
   */
  @Test
  void testModifNbParticipants() {
    evenementCl.setDuree(6);
    assertEquals(evenementCl.getDuree(), 6);
  }
  
  /**
   * Test d'assignement simple de date.
   */
  @Test
  void testModifDate() {
    LocalDateTime dateMod = LocalDateTime.of(1992, 5, 11, 3, 6);
    
    evenementCl.setDate(dateMod);
    assertEquals(evenementCl.getDate(), dateMod);
  }
  
  /**
   * Test d'assignement simple d'une nouvelle duree , exemple set la duree sur
   * 3.
   */
  @Test
  void testModifDuree() {
    evenementCl.setDuree(3);
    assertEquals(evenementCl.getDuree(), 3);
  }
  
  
  /**
   * Test d'assignement simple de nom, exemple set le nom de l'evenement à
   * Seminaire moustiquaire.
   */
  @Test
  void testModifNomSeminaire() {
    evenementCl.setNom("Seminaire moustiquaire");
    assertEquals(evenementCl.getNom(), "Seminaire moustiquaire");
  }
  
  /**
   * V�rifie que les param�tres des constructeurs sont correctement géré.
   */
  @Test
  void testConstructeur() {
    LocalDateTime date = LocalDateTime.of(2020, 4, 20, 12, 46);
    Evenement evtCl = new Evenement("event classique", "Bordeau", date, 50, 90);
    assertEquals(evtCl.getNom(), "event classique");
    assertEquals(evtCl.getLieu(), "Bordeau");
    assertEquals(evtCl.getDate(), date);
    assertEquals(evtCl.getDuree(), 90);
    assertEquals(evtCl.getNbParticipantsMax(), 50);
    Evenement evtSd = new Evenement("event sans date", "Brest", 40, 12);
    assertEquals(evtSd.getNom(), "event sans date");
    assertEquals(evtSd.getLieu(), "Brest");
    assertEquals(evtSd.getDuree(), 12);
    assertEquals(evtSd.getNbParticipantsMax(), 40);
  }
  
  /**
   * Test d'assignement simple de nom, exemple set le nom de l'evenement à
   * Seminaire moustiquaire.
   */
  @Test
  void testChvauchementLieu() {
    
    assertTrue(evenementSd.pasDeChevauchementLieu(evenementCl));
    evenementCl.setLieu("Brest");
    evenementSd.setLieu("Brest");
    evenementSd.setDate(date);
    evenementCl.setDate(date);
    assertFalse(evenementSd.pasDeChevauchementLieu(evenementCl));
  }
  
  /**
   * Test si la methode ChevauchementTemps fonctionne comme prévue , exemple
   * test.
   */
  @Test
  void testChevauchementTemps() {
    assertTrue(evenementSd.pasDeChevauchementTemps(evenementCl));
    evenementSd.setDate(date);
    evenementCl.setDate(date);
    assertFalse(evenementSd.pasDeChevauchementTemps(evenementSd));
    assertFalse(evenementSd.pasDeChevauchementTemps(evenementCl));
    evenementSd.setDuree(41);
    evenementSd.setDate(LocalDateTime.of(2020, 4, 20, 12, 6));
    assertFalse(evenementSd.pasDeChevauchementTemps(evenementCl));
    evenementSd.setDuree(30);
    assertTrue(evenementSd.pasDeChevauchementTemps(evenementCl));
  }
  
  
  /**
   * Test si la methode ChevauchementLieu fonctionne comme prévue , exemple
   * test.
   */
  @Test
  void testChevauchementLieu() {
    assertTrue(evenementSd.pasDeChevauchementLieu(evenementCl));
    evenementSd.setDate(date);
    evenementCl.setDate(date);
    evenementSd.setLieu("Nice");
    evenementCl.setLieu("Nice");
    assertFalse(evenementSd.pasDeChevauchementLieu(evenementCl));
    evenementCl.setDate(LocalDateTime.of(2000, 4, 20, 12, 6));
    assertTrue(evenementSd.pasDeChevauchementLieu(evenementCl));
  }
  
  /**
   * Test si la methode ChevauchementLieu fonctionne comme prévue , exemple
   * test.
   */
  @Test
  void testAjoutParticipant() {
    mb.definirInformationPersonnnelle(info);
    assertEquals(0, evenementSd.getParticipants().size());
    assertTrue(evenementSd.ajoutParticipant(mb));
    assertEquals(1, evenementSd.getParticipants().size());
    assertFalse(evenementSd.ajoutParticipant(mb));
    assertEquals(1, evenementSd.getParticipants().size());
    assertFalse(evenementSd.ajoutParticipant(null));
  }
  
  /**
   * Test si la methode ChevauchementLieu fonctionne comme prévue , exemple
   * test.
   */
  @Test
  void testSupressionParticipant() {
    InformationPersonnelle i = new InformationPersonnelle("Jean", "Bonherbe",
        "76 rue des paquerette", 21);
    evenementSd.setDate(LocalDateTime.now().plusYears(4));
    mb.definirInformationPersonnnelle(i);
    assertEquals(0, evenementSd.getParticipants().size());
    assertTrue(evenementSd.ajoutParticipant(mb));
    assertEquals(1, evenementSd.getParticipants().size());
    assertTrue(evenementSd.supressionParticipant(mb));
    assertEquals(0, evenementSd.getParticipants().size());
    assertFalse(evenementSd.supressionParticipant(mb));
  }
}
