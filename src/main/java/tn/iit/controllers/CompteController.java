package tn.iit.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.iit.entity.Client;
import tn.iit.entity.Compte;
import tn.iit.service.ClientService;
import tn.iit.service.CompteService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comptes")
public class CompteController {

    @Autowired
    private CompteService compteService;
    @Autowired
    private ClientService clientService;


    @GetMapping
    public ResponseEntity<?> getAllComptes() {
        return ResponseEntity.ok(compteService.findAll());
    }

    @GetMapping("/{rib}")
    public ResponseEntity<?> getCompteByRIB(@PathVariable Integer rib) {
        Compte compte = compteService.findById(rib);
        if (compte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(compte);
    }

    @PostMapping
public ResponseEntity<?> createCompte(@RequestBody Map<String, Object> requestBody) {
    try {
        // Extract client_id and solde from the request body
        float solde = Float.parseFloat(requestBody.get("solde").toString());
        Integer clientId = (Integer) requestBody.get("client_id");

        // Fetch the client by ID
        Client client = clientService.findById(clientId);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found with ID: " + clientId);
        }

        // Construct the compte
        Compte compte = new Compte();
        compte.setNomClient(client.getNom() + " " + client.getPrenom());
        compte.setSolde(solde);
        compte.setClient(client);

        // Save and return the new compte
        Compte savedCompte = compteService.save(compte);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompte);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}


    @PutMapping("/{rib}")
    public ResponseEntity<?> updateCompte(@PathVariable Integer rib, @RequestBody Compte compteDetails) {
        Compte existingCompte = compteService.findById(rib);
        if (existingCompte == null) {
            return ResponseEntity.notFound().build();
        }
        existingCompte.setClient(compteDetails.getClient() != null ? compteDetails.getClient() : existingCompte.getClient());
        existingCompte.setSolde(compteDetails.getSolde() != 0 ? compteDetails.getSolde() : existingCompte.getSolde());
        existingCompte.setNomClient(compteDetails.getNomClient() != null ? compteDetails.getNomClient() : existingCompte.getNomClient());
        compteService.save(existingCompte);
        return ResponseEntity.ok(existingCompte);
    }

    @DeleteMapping("/{rib}")
    public ResponseEntity<?> deleteCompte(@PathVariable Integer rib) {
        Compte existingCompte = compteService.findById(rib);
        if (existingCompte == null) {
            return ResponseEntity.notFound().build();
        }
        compteService.deleteById(rib);
        return ResponseEntity.ok().build();
    }
}