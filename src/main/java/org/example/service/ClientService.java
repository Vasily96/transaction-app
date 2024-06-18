package org.example.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Client;
import org.example.entity.dto.ClientDto;
import org.example.entity.factory.ClientFactory;
import org.example.repository.ClientRepository;
import org.example.utils.validator.ClientValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientValidator clientValidator;


    @Transactional(readOnly = true)
    public Client getClient(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public ClientDto check(ClientDto clientDto) {
        final DataBinder dataBinder = new DataBinder(clientDto);
        dataBinder.addValidators(clientValidator);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            dataBinder.getBindingResult().getAllErrors().forEach(
                    objectError ->
                            log.error(objectError.getDefaultMessage())
            );
            log.error("Client wasn't created");
            return null;
        }
        return clientDto;
    }

    public Client createClient(ClientDto clientDto) {
        if (check(clientDto) == null) return null;
        if (clientRepository.findClientByName(clientDto.getName()).isPresent()) {
            log.error("Client with name {} is exist", clientDto.getName());
            return null;
        }
        Client client = ClientFactory.toEntity(clientDto);
        log.info("client with name {} was created", clientDto.getName());
        return clientRepository.save(client);
    }

    public void updateClient(Long id, ClientDto clientDto) {
        if (clientRepository.findClientByName(clientDto.getName()).isPresent()) {
            log.error("Client with name {} is exist", clientDto.getName());
            return;
        }
        clientRepository.findById(id).ifPresentOrElse(client -> {
            if (clientDto.getName() != null)
                client.setName(clientDto.getName());
            if (clientDto.getType() != null)
                client.setType(clientDto.getType());
        }, () -> {
            throw new NoSuchElementException();
        });
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
        log.info("client with id {} was removed", id);
    }

    @Transactional(readOnly = true)
    public Client getAccount(Long id) {
        return clientRepository.fetchClientWithAccount(id).orElseThrow();
    }
}
