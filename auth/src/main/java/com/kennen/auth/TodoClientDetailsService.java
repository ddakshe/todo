package com.kennen.auth;

import com.kennen.data.ClientEntity;
import com.kennen.repository.ClientRepository;
import com.kennen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;

import java.util.*;


public class TodoClientDetailsService implements ClientDetailsService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientEntity todoClient = clientRepository.findByClientId(clientId);
        if (todoClient == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        List<GrantedAuthority> grantedAuthorities = Arrays.asList(() -> "USER");
        TodoClientDetails details = new TodoClientDetails(todoClient);
        details.setAuthories(new ArrayList<>(grantedAuthorities));
        return details;
    }
}
