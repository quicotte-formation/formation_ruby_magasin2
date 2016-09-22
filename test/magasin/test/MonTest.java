/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasin.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import magasin.entity.Categorie;
import magasin.entity.Client;
import magasin.entity.Commande;
import magasin.entity.Produit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author tom
 */
public class MonTest {

    @Before
    public void avant() {
        // Vide toutes les tables
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        
        em.createQuery("DELETE FROM Commande c").executeUpdate();
        em.createQuery("DELETE FROM Client c").executeUpdate();
        em.createQuery("DELETE FROM Produit p").executeUpdate();
        em.createQuery("DELETE FROM Categorie p").executeUpdate();
        
        // Ajoutes des données en spécifiant les IDs que l'on va récup ds les tests unitaires

        // Persister en bases certaines données
        
        Client riri = new Client();// Persiste 3 clients
        riri.setId(1L);
        riri.setNom("riri");
        em.persist(riri);
        
        Client fifi = new Client();
        fifi.setId(2L);
        fifi.setNom("fifi");
        em.persist(fifi);
        
        Client loulou = new Client();
        loulou.setId(3L);
        loulou.setNom("loulou");
        em.persist(loulou);
        
        Commande com1 = new Commande();
        com1.setId(1L);
        com1.setClient(riri);
        riri.getCommandes().add(com1);
        com1.setPrixTotal(1000);
        em.persist(com1);
        
        Commande com2 = new Commande();
        com2.setId(2L);
        com2.setClient(loulou);
        loulou.getCommandes().add(com2);
        com2.setPrixTotal(5);
        em.persist(com2);
        
        Commande com3 = new Commande();
        com3.setId(3L);
        com3.setClient(loulou);
        loulou.getCommandes().add(com3);
        com3.setPrixTotal(2);
        em.persist(com3);
        
        Categorie c1 = new Categorie();
        c1.setId(1L);
        c1.setNom("Basket");
        em.persist(c1);

        Categorie c2 = new Categorie();
        c2.setId(2L);
        c2.setNom("Lunettes solaires");
        em.persist(c2);

        Produit rayBan = new Produit();
        rayBan.setId(1L);
        rayBan.setCategorie(c2);// a.setB(b)
        c2.getProduits().add(rayBan);// b.getAs().add(a)
        em.persist(rayBan);

        em.getTransaction().commit();
    }

    @Test
    public void verifCom2PasseeParRiriKO(){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        // Récup cmd
        Commande cmd = em.find(Commande.class, 2L);
        Assert.assertNotEquals("riri", cmd.getClient().getNom());
    }
    
    @Test
    public void verifQueCmd3PasseeParLoulouOK(){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        // Récup cmd
        Commande cmd = em.find(Commande.class, 3L);

        Assert.assertEquals("loulou", cmd.getClient().getNom());
    }
    
    @Test
    public void verifQueNbrCmdLoulouEst2OK(){
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Client c = em.find(Client.class, 3L);
        if( c.getCommandes().size()!=2 )
            Assert.fail();
    }
    
    @Test
    public void verifieQueCatId1EstBasketOK() {
        
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        
        Categorie cat = em.find(Categorie.class, 1L);
        
        if( cat.getNom().equals("Basket")==false ){
            Assert.fail("CA MARCHE PAS MON GARS!");
        }
    }

    @Test
    public void testCreateDB() {

    }

}
