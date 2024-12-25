package tn.iit.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.iit.entity.Client;
import tn.iit.service.ClientService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        Client client = clientService.findById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.save(client);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody Client clientDetails) {
    try {
        Client existingClient = clientService.findById(id);
        if (existingClient == null) {
            return ResponseEntity.notFound().build();
        }

        // Update client name
        existingClient.setNom(clientDetails.getNom() != null ? clientDetails.getNom() : existingClient.getNom());
        existingClient.setPrenom(clientDetails.getPrenom() != null ? clientDetails.getPrenom() : existingClient.getPrenom());

        // Update nomClient in comptes
        String updatedNomClient = existingClient.getNom() + " " + existingClient.getPrenom();
        existingClient.getComptes().forEach(compte -> {
            compte.setNomClient(updatedNomClient);
        });

        // Save updated client
        clientService.save(existingClient);
        return ResponseEntity.ok(existingClient);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        Client existingClient = clientService.findById(id);
        if (existingClient == null) {
            return ResponseEntity.notFound().build();
        }
        clientService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
