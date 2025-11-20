package com.matheo.chahwan.proxibanque.repository;

import com.matheo.chahwan.proxibanque.entity.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {}