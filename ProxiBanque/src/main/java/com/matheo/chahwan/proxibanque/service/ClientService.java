package com.matheo.chahwan.proxibanque.service;

import com.matheo.chahwan.proxibanque.dto.ClientDTO;
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
    private final AdvisorRepository advisorRepo;
    private final CurrentAccountRepository currentRepo;
    private final SavingsAccountRepository savingsRepo;
    private final CardRepository cardRepo;

    @Transactional
    public Client createClient(ClientDTO clientDTO) {
        Client client = new Client();

        client.setLastName(clientDTO.getLastName());
        client.setFirstName(clientDTO.getFirstName());
        client.setAddress(clientDTO.getAddress());
        client.setPostalCode(clientDTO.getPostalCode());
        client.setCity(clientDTO.getCity());
        client.setPhone(clientDTO.getPhone());

        client.setInitialCurrentBalance(clientDTO.getInitialCurrentBalance());
        client.setOverdraft(clientDTO.getOverdraft());
        client.setInitialSavingsBalance(clientDTO.getInitialSavingsBalance());
        client.setInterestRate(clientDTO.getInterestRate());

        client.setAddVisaElectron(clientDTO.isAddVisaElectron());
        client.setAddVisaPremier(clientDTO.isAddVisaPremier());

        // Accounts
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

        Advisor advisor;
        if (clientDTO.getAdvisorId() != null) {
            advisor = advisorRepo.findById(clientDTO.getAdvisorId())
                    .orElseThrow(() -> new NoSuchElementException("Advisor not found"));
            if (!advisor.canAcceptMoreClients())
                throw new IllegalStateException("This advisor cannot accept more clients.");
        } else {
            advisor = advisorRepo.findAll().stream()
                    .filter(Advisor::canAcceptMoreClients)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("This advisor cannot accept more clients."));
        }

        client.setAdvisor(advisor);
        advisor.getClients().add(client);

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

    @Transactional
    public Client updateClient(Long id, ClientDTO updatedDto) {
        Client client = clientRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client not found"));

        if (updatedDto.getLastName() != null) client.setLastName(updatedDto.getLastName());
        if (updatedDto.getFirstName() != null) client.setFirstName(updatedDto.getFirstName());
        if (updatedDto.getAddress() != null) client.setAddress(updatedDto.getAddress());
        if (updatedDto.getPostalCode() != null) client.setPostalCode(updatedDto.getPostalCode());
        if (updatedDto.getCity() != null) client.setCity(updatedDto.getCity());
        if (updatedDto.getPhone() != null) client.setPhone(updatedDto.getPhone());

        if (updatedDto.getOverdraft() != null) client.setOverdraft(updatedDto.getOverdraft());
        if (updatedDto.getInterestRate() != null) client.setInterestRate(updatedDto.getInterestRate());

        if (updatedDto.getAdvisorId() != null) {
            Advisor newAdvisor = advisorRepo.findById(updatedDto.getAdvisorId())
                    .orElseThrow(() -> new NoSuchElementException("Advisor not found"));
            if (!newAdvisor.canAcceptMoreClients())
                throw new IllegalStateException("This advisor cannot accept more clients.");

            client.setAdvisor(newAdvisor);
        }

        return clientRepo.save(client);
    }


    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().replace("-", "").substring(0,12).toUpperCase();
    }
}
