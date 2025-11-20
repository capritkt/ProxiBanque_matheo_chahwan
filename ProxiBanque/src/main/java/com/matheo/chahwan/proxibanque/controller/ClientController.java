package com.matheo.chahwan.proxibanque.controller;

import com.matheo.chahwan.proxibanque.entity.Client;
import com.matheo.chahwan.proxibanque.repository.ClientRepository;
import com.matheo.chahwan.proxibanque.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientRepository clientRepo;

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody Client client) {
        Client saved = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getOne(@PathVariable Long id){
        return clientRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
