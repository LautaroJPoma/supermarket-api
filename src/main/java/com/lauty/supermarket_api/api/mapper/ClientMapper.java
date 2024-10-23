package com.lauty.supermarket_api.api.mapper;

import org.springframework.stereotype.Component;

import com.lauty.supermarket_api.api.dto.ClientDTO;

import com.lauty.supermarket_api.api.model.Client;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());
        return clientDTO;
    }

    public Client toEntity(ClientDTO clientDTO) {
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        return client;
    }
}

