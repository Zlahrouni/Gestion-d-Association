package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import association.Evenement;
import association.InformationPersonnelle;
import association.Membre;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Classe de test de la classe Membre.

 * @author Ziad Lahrouni
 *
 */
class TestMembre {
  /**
   * Informations personnelles du membre.
   */
  InformationPersonnelle information =
      new InformationPersonnelle("Lahrouni", "Ziad", "rue des avenues", 99);
  /**
   * Objet membre.
   */
  Membre ziad = new Membre();
  
  /**
   * Test Constructeur.
   */
  @Test
  void testConstructeur() {
    assertTrue(this.ziad != null);
    List<Evenement> eve = this.ziad.getEvenements();
    assertTrue(eve != null);
  }
   
  /**
   * Définie les informations personnelles à <code>null</code>.
   */
  @Test 
  void setNull() {
    InformationPersonnelle nouveau = null;
    this.ziad.definirInformationPersonnnelle(nouveau);
    assertTrue(this.ziad.getInformationPersonnelle() == null);
  }
  
  /**
   * Vérifie si on peut ajouter et retirer des informations.
   */
  @Test
  void testSetGetInformation() {
    InformationPersonnelle nouveau;
    this.ziad.definirInformationPersonnnelle(information);
    nouveau = this.ziad.getInformationPersonnelle();
    assertTrue(nouveau.getAge() == 99);
    assertTrue(nouveau.getNom() == "Lahrouni");
    assertTrue(nouveau.getPrenom() == "Ziad");
    assertTrue(nouveau.getAdresse() == "rue des avenues");
  }
  
  /**
   * Test du getter/setter d'un événement.
   */
  @Test
  void testSetGetEvenement() {    
    // On crée un événement
    LocalDateTime now = LocalDateTime.now();
    
    // Creation des evenements
    List<Evenement> listEvenement = new ArrayList<>();
    Evenement enull = null; // pour tester l'evenement null
    Evenement e1 = new Evenement("Conference", "Lieu", now, 40, 90);
        
    // On ajoute un événement
    listEvenement.add(enull);
    listEvenement.add(e1);
    
    // Vérifie s'il y a deux événement dans la liste
    assertEquals(2, listEvenement.size());
    
    // Ajoute la liste d'evenement
    this.ziad.setEvenements(listEvenement);
    
    // L'événement null ne va pas être ajouter, donc la taille reste à 1
    assertEquals(1, this.ziad.getEvenements().size());
   
    return;
  }
}