package org.sid.metier;

import java.util.Date;
import java.util.List;

import org.sid.entities.Client;
import org.sid.entities.Compte;
import org.sid.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

public interface IBanqueMetier {
public Compte consulterCompte(String codeCompte);
public void verser(String codeCompte,double montant);
public void retirer(String codeCompte,double montant);
public void virement(String codeCompte1, String codeCompte2, double montant);
public Page <Operation> listOperation(String codeCompte,int page, int size);
public Compte creerCompte1(String codeCompte, Double solde,Client client,Double type, String champ);
public Compte creerCompte(String codeCompte, Double solde, Client client, Double type, String champ);
public Compte creer(String codeCompte, Double solde,Long code_client, Double type1,Double type2, String champ);
public List<Client> listClients() ;
public List<Compte> listComptes() ;
public void deleteCompte(Compte compte);
public void edit(String codeCompte, Double solde, Double taux_ou_dcv, Long code_client);
public Client creerClient(Long code, String nom,String prenom, String email, String adresse, String tel  ) ;
public Client consulterClient(Long code) ;
public Page <Client> listClients(Long code,int page, int size);
public void deleteClient(Client client);
public void editClient(Long code, String nom,String prenom, String email, String adresse, String tel );


}
