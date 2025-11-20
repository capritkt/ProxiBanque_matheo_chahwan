package com.matheo.chahwan.proxibanque.repository;

import com.matheo.chahwan.proxibanque.entity.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {}