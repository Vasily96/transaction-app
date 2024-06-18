package org.example.entity.factory;

import org.example.entity.Client;
import org.example.entity.dto.ClientDto;

public class ClientFactory {

    public static Client toEntity(ClientDto clientDto) {
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setType(clientDto.getType());
        return client;
    }

    public static ClientDto toDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(client.getName());
        clientDto.setType(client.getType());
        return clientDto;
    }
}
