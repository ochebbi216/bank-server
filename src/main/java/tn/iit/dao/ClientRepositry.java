package tn.iit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.entity.Client;

public interface ClientRepositry extends JpaRepository<Client, Integer> {

}
