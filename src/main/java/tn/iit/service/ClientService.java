package tn.iit.service;


import tn.iit.dao.ClientRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.iit.entity.Client;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepositry clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }
    public Client save(Client client) {
        // Update associated comptes
        String updatedNomClient = client.getNom() + " " + client.getPrenom();
    
        if (client.getComptes() != null) {
            client.getComptes().forEach(compte -> {
                compte.setNomClient(updatedNomClient);
            });
        }
    
        return clientRepository.save(client);
    }
    

    public Client findById(Integer id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void deleteById(Integer id) {
        clientRepository.deleteById(id);
    }
}
