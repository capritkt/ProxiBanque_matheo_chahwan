package com.matheo.chahwan.proxibanque.service;

import com.matheo.chahwan.proxibanque.entity.*;
import com.matheo.chahwan.proxibanque.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepo;
    private final CurrentAccountRepository currentRepo;
    private final SavingsAccountRepository savingsRepo;
    private final CardRepository cardRepo;

    @Transactional
    public Client createClient(Client client) {

        CurrentAccount current = new CurrentAccount();
        current.setNumber(generateAccountNumber());
        current.setBalance(client.getInitialCurrentBalance() != null ? client.getInitialCurrentBalance() : BigDecimal.ZERO);
        current.setOpeningDate(LocalDate.now());
        current.setOwner(client);
        current.setOverdraft(client.getOverdraft() != null ? client.getOverdraft() : BigDecimal.valueOf(1000));

        SavingsAccount savings = new SavingsAccount();
        savings.setNumber(generateAccountNumber());
        savings.setBalance(client.getInitialSavingsBalance() != null ? client.getInitialSavingsBalance() : BigDecimal.ZERO);
        savings.setOpeningDate(LocalDate.now());
        savings.setOwner(client);
        savings.setInterestRate(client.getInterestRate() != null ? client.getInterestRate() : 3.0);

        client.getAccounts().add(current);
        client.getAccounts().add(savings);

        if (client.isAddVisaElectron()) {
            Card visaE = new Card();
            visaE.setCardType("VISA_ELECTRON");
            visaE.setActive(true);
            visaE.setOwner(client);
            client.getCards().add(visaE);
        }

        if (client.isAddVisaPremier()) {
            Card visaP = new Card();
            visaP.setCardType("VISA_PREMIER");
            visaP.setActive(true);
            visaP.setOwner(client);
            client.getCards().add(visaP);
        }

        return clientRepo.save(client);
    }

    @Transactional
    public void deleteClient(Long clientId) {
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new NoSuchElementException("Client not found"));
        if (client.getCards() != null) {
            client.getCards().forEach(c -> c.setActive(false));
            cardRepo.saveAll(client.getCards());
        }
        clientRepo.delete(client);
    }

    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().replace("-", "").substring(0,12).toUpperCase();
    }
}
