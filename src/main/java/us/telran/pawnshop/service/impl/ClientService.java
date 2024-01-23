package us.telran.pawnshop.service.impl;

import org.springframework.stereotype.Service;
import us.telran.pawnshop.entity.Client;

@Service
public interface ClientService {
    Client postNewClient();
    Client getById(Long id);
}
