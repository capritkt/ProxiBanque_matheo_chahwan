package com.matheo.chahwan.proxibanque.repository;

import com.matheo.chahwan.proxibanque.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {}
