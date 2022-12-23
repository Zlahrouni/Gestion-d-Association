package main;

import java.io.IOException;

/**
 * Classe de test.
 */
public class MainAssociation {
  
  /**
   * Lancement du programme.
   *
   * @param args Arguments du programme.
   */
  public static void main(String[] args) {
    System.out.println("Ca marche !");
    System.out.println("\nAppuyez sur Entr�e pour terminer le programme ...");
    
    try {
      System.in.read();
    } catch (IOException e) {
      System.err.println("Vous avez r�ussi � casser le clavier : " + e);
    }
  }
}