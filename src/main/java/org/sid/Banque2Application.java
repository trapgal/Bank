package org.sid;

import java.util.Date;

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
import org.sid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Banque2Application implements CommandLineRunner {
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private IBanqueMetier ibanquemetier;
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Banque2Application.class, args);
	
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		Client c1 =clientRepository.save(new Client("Hajar", "hajar@gmail.com"));
		Client c2 =clientRepository.save(new Client("Oumaima", "oumaima@gmail.com"));
Compte cc1 = compteRepository.save(new CompteCourant("cp1", new Date(), 40000, c1, 1000));
Compte ce1 = compteRepository.save(new CompteEpargne("ce1", new Date(), 80000, c2, 1000));
operationRepository.save(new Versement(new Date(), 9000, cc1));
operationRepository.save(new Versement(new Date(), 5000, cc1));
operationRepository.save(new Versement(new Date(), 6000, cc1));
operationRepository.save(new Retrait(new Date(), 400, cc1));

operationRepository.save(new Versement(new Date(), 8000, ce1));
operationRepository.save(new Versement(new Date(), 7000, ce1));
operationRepository.save(new Versement(new Date(), 10000, ce1));
operationRepository.save(new Retrait(new Date(), 900, ce1));
ibanquemetier.verser("cp1", 888111);
*/
	}

}
