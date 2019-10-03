package org.sid.metier;

import java.util.Date;
import java.util.List;

import org.sid.dao.ClientRepository;
import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.Client;
import org.sid.entities.Compte;
import org.sid.entities.CompteCourant;
import org.sid.entities.CompteEpargne;
import org.sid.entities.Operation;
import org.sid.entities.Retrait;
import org.sid.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier{
	@Autowired
private CompteRepository compteRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Override
	public Compte consulterCompte(String codeCompte) {
Compte c= compteRepository.findOne(codeCompte);
if(c==null) throw new RuntimeException("Compte introuvable");
		return c;
	}
	

	
	@Override
	public void verser(String codeCompte, double montant) {
Compte cp=consulterCompte(codeCompte);
Versement v =  new Versement(new Date(), montant, cp);
operationRepository.save(v);
cp.setSolde(cp.getSolde()+montant);
compteRepository.save(cp);
System.out.println("je suis dans verser");
	}

	@Override
	public void retirer(String codeCompte, double montant) {
		Compte cp=consulterCompte(codeCompte);
		double i=0;
		if(cp instanceof CompteCourant) {
			i = ((CompteCourant) cp).getDecouvert();
			}
		if(cp.getSolde()+i<montant) {
			throw new RuntimeException("Solde insuffisant");
		}
		Retrait r =  new Retrait(new Date(), montant, cp);
		operationRepository.save(r);
		cp.setSolde(cp.getSolde()-montant);
		compteRepository.save(cp);
		
	}

	@Override
	public void virement(String codeCompte1, String codeCompte2, double montant) {
if(codeCompte1.equals(codeCompte2) ) {
	System.out.println("le virement n'est pas possible");

	throw new RuntimeException("Le virement sur le meme compte est impossible");
}
		retirer(codeCompte1,montant);
verser(codeCompte2,montant);
	}

	@Override
	public Page<Operation> listOperation(String codeCompte, int page, int size) {

		return operationRepository.listOp(codeCompte,new PageRequest(page, size));
	}


	
	
	


	@Override
	public Compte creerCompte1(String codeCompte, Double solde, Client client, Double type, String champ) {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public Compte creerCompte(String codeCompte, Double solde, Client client, Double type, String champ) {
			if (champ.equals("epargne") ) {
	Compte c1= new CompteEpargne(codeCompte,new Date(),solde,client,type) ;
	compteRepository.save(c1);
	return c1;

			}
			else if (champ.equals("courant") ){
				Compte c2= new CompteCourant(codeCompte,new Date(),solde,client,type) ;
				compteRepository.save(c2);
				return c2;
			}
			else {
				System.out.println("operation impossible");
				return null;
			}
	}


	@Override
	public List<Client> listClients() {
	

			return clientRepository.findAll();
		}






	





	@Override
	public void deleteCompte(Compte compte) {
		compteRepository.delete(compte);
		
	}






	













	






	



	@Override
	public List<Compte> listComptes() {
		
			return compteRepository.findAll();

		}






	@Override
	public Compte creer(String codeCompte, Double solde, Long code_client, Double type1, Double type2, String champ) {
		if (champ.equals("epargne") ) {
			Compte c1= new CompteEpargne(codeCompte,new Date(),solde,clientRepository.findOne(code_client),type1) ;
		type2=0.0;
			compteRepository.save(c1);
			return c1;

			}
			else if (champ.equals("courant") ){
				Compte c2= new CompteCourant(codeCompte,new Date(),solde,clientRepository.findOne(code_client),type2) ;
				type1=0.0;
				compteRepository.save(c2);
				return c2;
			}
			else {
				System.out.println("operation impossible");
				return null;
			}
	}






	@Override
	public void edit(String codeCompte, Double solde, Double taux_ou_dcv, Long code_client) {
		Compte c= compteRepository.findOne(codeCompte);
		
		Compte cf= null;
		
		if( c instanceof CompteCourant) {
			CompteCourant cc=  (CompteCourant) c;
			cc.setDecouvert(taux_ou_dcv);
			cf=cc;
			
		} if(c instanceof CompteEpargne){
			CompteEpargne ce= (CompteEpargne) c;
			cf=ce;
		}else {
		
		System.out.println("Type de compte introuvable");
		
	}
		cf.setClient(clientRepository.findOne(code_client));
		cf.setSolde(solde);
		compteRepository.save(cf);		
	}






	@Override
	public Client creerClient(Long code, String nom, String prenom, String email, String adresse, String tel) {
		
		Client c = new Client(code, nom, prenom, email, adresse, tel);
		clientRepository.save(c);
		return c;
	}



	@Override
	public Client consulterClient(Long code) {
		
	Client c= clientRepository.findOne(code);
	if(c==null) throw new RuntimeException("Compte introuvable");
			return c;
		
	}



	@Override
	public Page<Client> listClients(Long code, int page, int size) {

		return clientRepository.listClients(code,new PageRequest(page, size));
	}



	@Override
	public void deleteClient(Client client) {
		clientRepository.delete(client);		
	}



	@Override
	public void editClient(Long code, String nom, String prenom, String email, String adresse, String tel) {
Client c= clientRepository.findOne(code);
		c.setAdresse(adresse);
		c.setPrenom(prenom);
		c.setNom(nom);
		c.setTel(tel);
		c.setEmail(email);
		clientRepository.save(c);			
	}



	






	
}






	

