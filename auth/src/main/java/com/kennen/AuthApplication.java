package com.kennen;

import com.kennen.data.ClientEntity;
import com.kennen.repository.ClientRepository;
import com.kennen.data.UserEntity;
import com.kennen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories
public class AuthApplication implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        * 간편한 테스트를 위해 User, ClientEntity 를 등록하고 시작한다.
        * */
        saveClient();
        saveUser();
    }

    private void saveUser() {
        UserEntity user = new UserEntity();
        user.setEmail("kennen@google.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setName("김경태");
        userRepository.save(user);
    }

    private void saveClient() {
        ClientEntity client = new ClientEntity();
        client.setClientId("todo");
        client.setClientSecret(passwordEncoder.encode("todo"));
        client.setResourceIds("my-resource-id");
        client.setScopes("read");
        client.setGrantTypes("password,refresh_token");
        clientRepository.save(client);
    }
}
