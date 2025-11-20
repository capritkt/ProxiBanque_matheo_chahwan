package com.matheo.chahwan.proxibanque.service;

import com.matheo.chahwan.proxibanque.dto.AdvisorDTO;
import com.matheo.chahwan.proxibanque.entity.Advisor;
import com.matheo.chahwan.proxibanque.entity.Client;
import com.matheo.chahwan.proxibanque.repository.AdvisorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdvisorService {

    private final AdvisorRepository advisorRepo;

    @Transactional
    public Advisor createAdvisor(AdvisorDTO dto) {
        Advisor advisor = new Advisor();
        advisor.setFirstName(dto.getFirstName());
        advisor.setLastName(dto.getLastName());
        advisor.setEmail(dto.getEmail());
        advisor.setPhone(dto.getPhone());

        return advisorRepo.save(advisor);
    }

    public Advisor getAdvisor(Long id) {
        return advisorRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Advisor not found"));
    }

    @Transactional
    public Advisor updateAdvisor(Long id, AdvisorDTO dto) {
        Advisor advisor = advisorRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Advisor not found"));

        if (dto.getFirstName() != null) advisor.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) advisor.setLastName(dto.getLastName());
        if (dto.getEmail() != null) advisor.setEmail(dto.getEmail());
        if (dto.getPhone() != null) advisor.setPhone(dto.getPhone());

        return advisorRepo.save(advisor);
    }


    @Transactional
    public void assignClientToAdvisor(Advisor advisor, Client client) {

        if (!advisor.canAcceptMoreClients())
            throw new IllegalStateException("This advisor already manages 10 clients.");

        advisor.getClients().add(client);
        client.setAdvisor(advisor);

        advisorRepo.save(advisor);
    }
}
