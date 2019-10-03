package org.sid.web;



import java.util.Date;

import org.sid.dao.ClientRepository;
import org.sid.entities.Client;
import org.sid.entities.Compte;
import org.sid.util.*;
import org.sid.entities.Operation;
import org.sid.metier.IBanqueMetier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BanqueController {
	@Autowired
private IBanqueMetier iBanqueMetier;
	@Autowired
private ClientRepository clientRepository;
	/*
	 * @Autowired private DateUtil1 dateUtil1;
	 */
	@RequestMapping("/operations")
public String index() {
	return "comptes";
}


	/*
	 * @RequestMapping("/test") public String test() { return "test"; }
	 */
	@RequestMapping("/consulterCompte")
	public String consulter(Model model, String codeCompte, 
		@RequestParam(name="page",defaultValue="0")int page,
		@RequestParam(name="size",defaultValue="8")int size) {
		model.addAttribute("codeCompte", codeCompte);
		try {
		Compte cp = iBanqueMetier.consulterCompte(codeCompte);
		Page<Operation> pageOperations = iBanqueMetier.listOperation(codeCompte, page, size);
		model.addAttribute("listOperations", pageOperations.getContent());
		int[] pages = new int[pageOperations.getTotalPages()];
		model.addAttribute("pages",pages);
		model.addAttribute("creation",DateUtil1.formateDate("dd-MM-YYYY",cp.getDateDeCreation()));
		model.addAttribute("compte",cp);
		}catch (Exception e) {
			model.addAttribute("exception", e);
		}
		return "comptes";
	}
	@RequestMapping(value="/saveOperation", method=RequestMethod.POST)
	public String saveOperation(Model model,String typeOp, String codeCompte, double montant, String codeCompte2) 
	{ try {
		System.out.println("i save");
		if(typeOp.equals("VERS")) {
			System.out.println("je suis dans vers");
			iBanqueMetier.verser(codeCompte, montant);
		}else if(typeOp.equals("retr")) {
			iBanqueMetier.retirer(codeCompte, montant);
		}if(typeOp.equals("vir")) {
			iBanqueMetier.virement(codeCompte,codeCompte2, montant);
		}
	}catch (Exception e ) {
		System.out.println("i don't save");
		model.addAttribute("error",e);
		return "redirect:/consulterCompte?codeCompte="+codeCompte+"&error="+e.getMessage();
	}
		return "redirect:/consulterCompte?codeCompte="+codeCompte;
	}
	@RequestMapping(value="/creerCompte", method=RequestMethod.POST)
	public String creerCompte(Model model,String codeCompte, Double solde,Long code_client,Double type1,Double type2 , String champ) {
		
		Compte c1 = iBanqueMetier.creer(codeCompte, solde, code_client, type1,type2, champ);
		 ;
		
		model.addAttribute("compte",c1 );
		return "redirect:/Comptes";
	}
	@RequestMapping("/Comptes")
	public String AllComptes(Model model) {
		model.addAttribute("lsClients", iBanqueMetier.listClients());
		model.addAttribute("lsComptes", iBanqueMetier.listComptes());

		return "gestionComptes";
	}
	
	@RequestMapping("/modification")
	public String edit(Model model, String codeC) {
		model.addAttribute("lsClients", iBanqueMetier.listClients());
		model.addAttribute("lsComptes", iBanqueMetier.listComptes());
		model.addAttribute("account", iBanqueMetier.consulterCompte(codeC));

		return "modification";
}
	@RequestMapping("/edit")
	public String edit_compte(String codeCompte, Double solde, Double taux_ou_dcv, Long code_client) {
			iBanqueMetier.edit(codeCompte, solde, taux_ou_dcv, code_client);
		return "redirect:/Comptes";
}
	
	@RequestMapping("/Suppression")
	public String supprimer(String code) {
		iBanqueMetier.deleteCompte(iBanqueMetier.consulterCompte(code));
		return "redirect:/Comptes";
	}
	@RequestMapping(value="/creerClient", method=RequestMethod.POST)
	public String creerClient(Model model,Long code, String nom,String prenom, String email, String adresse, String tel  ) {
		System.out.println("i am in creer clients");

		Client c = iBanqueMetier.creerClient(code, nom, prenom, email, adresse, tel);
		
		model.addAttribute("client",c );
		System.out.println("i am out creer clients");
		return "redirect:/clients";
	}
	@RequestMapping("/clients")
	public String AllClients(Model model) {
		System.out.println("i am in all clients");
		model.addAttribute("lsClients", iBanqueMetier.listClients());
		System.out.println("i am out all clients");

		return "clients";
	}
		@RequestMapping("/SupprimerClient")
		public String supprimerClient(Long code) {
			iBanqueMetier.deleteClient(iBanqueMetier.consulterClient(code));
			return "redirect:/clients";
		}
		
		@RequestMapping("/consulterClient")
	public String consulter(Model model,
			@RequestParam(name="page",defaultValue="0")int page,
			@RequestParam(name="motCle",defaultValue="")String mc) {
			
			Page<Client> pageClients = clientRepository.findByNomContains(mc, new PageRequest(page,5));
			 
			model.addAttribute("lsClients", pageClients.getContent());
			model.addAttribute("pages",new int[pageClients.getTotalPages()]);
			model.addAttribute("currentPage", page);

			
			return "clients";
		}
		@RequestMapping("/editClient")
		public String edit_client(Long code, String nom,String prenom, String email, String adresse, String tel ) {
				iBanqueMetier.editClient(code, nom, prenom, email, adresse, tel);
			return "redirect:/clients";
	}
		@RequestMapping("/modifierClient")
		public String editc(Model model, Long code) {
			model.addAttribute("lsClients", iBanqueMetier.listClients());
			model.addAttribute("client", iBanqueMetier.consulterClient(code));

			return "modifierClient";
	}
}
