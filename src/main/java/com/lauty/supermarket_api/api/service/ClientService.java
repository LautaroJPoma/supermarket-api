package com.lauty.supermarket_api.api.service;

import java.util.List;

import com.lauty.supermarket_api.api.dto.ClientDTO;

public interface ClientService {
    ClientDTO getClientById(Long id);

    List<ClientDTO> getAllClients();

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO updateClient(Long id, ClientDTO clientDTO);

    void deleteClient(Long id);

    boolean existsById(Long id);
}
