package tn.iit.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.iit.dao.CompteRepository;
import tn.iit.entity.Compte;
import tn.iit.exceptions.CompteNotFoundException;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    public List<Compte> findAll() {
        return compteRepository.findAll();
    }

    public Compte  save(Compte compte) {
        return compteRepository.save(compte);
    }

    public Compte findById(Integer rib) {
        return compteRepository.findById(rib).orElseThrow(() -> new CompteNotFoundException("rib =" + rib + " not found"));
    }

    public void deleteById(Integer id) {
        compteRepository.deleteById(id);
    }
}