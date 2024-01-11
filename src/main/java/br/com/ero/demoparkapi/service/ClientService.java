package br.com.ero.demoparkapi.service;

import br.com.ero.demoparkapi.config.entity.Client;
import br.com.ero.demoparkapi.exception.CpfUniqueViolationException;
import br.com.ero.demoparkapi.exception.EntityNotFoundException;
import br.com.ero.demoparkapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client saveClient(Client client) throws CpfUniqueViolationException {
        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' cannot be registered already exists in the system", client.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Client getId(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Client id=%s - Not found in the system", id))
        );
    }
    @Transactional(readOnly = true)
    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }
}
