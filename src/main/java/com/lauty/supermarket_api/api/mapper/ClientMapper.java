package com.lauty.supermarket_api.api.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lauty.supermarket_api.api.dto.ClientDTO;

import com.lauty.supermarket_api.api.model.Client;
import com.lauty.supermarket_api.api.model.PurchaseOrder;

@Component
public class ClientMapper {

    public ClientDTO toDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());

        if (client.getOrders() != null) {
            List<Long> purchaseOrderIds = new ArrayList<Long>();
            for (PurchaseOrder order : client.getOrders()) {
                purchaseOrderIds.add(order.getId());
            }
            clientDTO.setPurchaseOrderIds(purchaseOrderIds);
        } else {
            clientDTO.setPurchaseOrderIds(Collections.emptyList());
        }

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
