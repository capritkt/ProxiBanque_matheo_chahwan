package com.matheo.chahwan.proxibanque.repository;

import com.matheo.chahwan.proxibanque.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}