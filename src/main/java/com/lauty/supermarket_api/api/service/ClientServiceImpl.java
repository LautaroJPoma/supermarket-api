package com.lauty.supermarket_api.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lauty.supermarket_api.api.dto.ClientDTO;
import com.lauty.supermarket_api.api.mapper.ClientMapper;
import com.lauty.supermarket_api.api.repository.ClientRepository;

import com.lauty.supermarket_api.api.model.Client;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDTO(savedClient);
    }

    @Override
    public ClientDTO getClientById(Long id) {
        // Busca el cliente
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            // Retorna null si no se encuentra
            return null;
        }
        return clientMapper.toDTO(client);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        Iterable<Client> clientsIterable = clientRepository.findAll();
        List<ClientDTO> clientDTOs = new ArrayList<>();
        for (Client client : clientsIterable) {
            ClientDTO clientDTO = clientMapper.toDTO(client);
            clientDTOs.add(clientDTO);
        }
        return clientDTOs;
    }

    @Override
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id).orElse(null);
        if (existingClient == null) {
            return null; // Retorna null si no se encuentra
        }

        // Actualiza los campos
        existingClient.setName(clientDTO.getName());
        existingClient.setEmail(clientDTO.getEmail());

        // Guarda y retorna el cliente actualizado
        Client updatedClient = clientRepository.save(existingClient);
        return clientMapper.toDTO(updatedClient);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client != null) {
            clientRepository.delete(client); // Solo se elimina si existe
        }
    }
}