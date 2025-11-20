package com.matheo.chahwan.proxibanque.controller;

import com.matheo.chahwan.proxibanque.dto.AdvisorDTO;
import com.matheo.chahwan.proxibanque.entity.Advisor;
import com.matheo.chahwan.proxibanque.service.AdvisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/advisors")
@RequiredArgsConstructor
public class AdvisorController {

    private final AdvisorService advisorService;

    @PostMapping
    public Advisor create(@RequestBody AdvisorDTO dto) {
        return advisorService.createAdvisor(dto);
    }

    @GetMapping("/{id}")
    public Advisor get(@PathVariable Long id) {
        return advisorService.getAdvisor(id);
    }

    @PutMapping("/{id}")
    public Advisor updateAdvisor(@PathVariable Long id, @RequestBody AdvisorDTO dto) {
        return advisorService.updateAdvisor(id, dto);
    }
}
