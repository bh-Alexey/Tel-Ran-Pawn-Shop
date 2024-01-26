package us.telran.pawnshop.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.repository.ClientRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static us.telran.pawnshop.entity.enums.ClientStatus.*;

@Configuration
public class ClientConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            ClientRepository clientRepository) {
        return args -> {
            Client mariam = new Client(
                REGULAR,
                423176123L,
                LocalDate.of(2000, Month.AUGUST, 15),
                "Mariam",
                "Jamal",
                "mariam.jamal@gmail.com",
                "235 West 32th Street Apt 6A"
            );

            Client alex = new Client(
                    REGULAR,
                    423345123L,
                    LocalDate.of(2004, Month.JANUARY, 24),
                    "Alex",
                    "Graddy",
                    "alex.graddy@gmail.com",
                    "1123 Madison Ave Apt 16R"
            );

            clientRepository.saveAll(
                    List.of(mariam, alex)
            );
        };
    }
}
