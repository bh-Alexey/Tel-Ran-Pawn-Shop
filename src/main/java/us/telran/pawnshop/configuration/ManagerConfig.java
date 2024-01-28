package us.telran.pawnshop.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.telran.pawnshop.entity.Manager;
import us.telran.pawnshop.repository.ManagerRepository;

import java.util.List;

import static us.telran.pawnshop.entity.enums.ManagerStatus.*;

@Configuration
public class ManagerConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            ManagerRepository managerRepository) {
        return args -> {
            Manager julia = new Manager(
                    "Julia",
                    "Senok",
                    "julia.senok@gmail.com",
                    EXPERT_APPRAISER
            );

            Manager david = new Manager(
                    "David",
                    "Clarks",
                    "david.clarks@gmail.com",
                    EXPERT_APPRAISER
            );

            Manager corry = new Manager(
                    "Corry",
                    "Wase",
                    "corry.wase@gmail.com",
                    REGION_DIRECTOR
            );

            managerRepository.saveAll(
                    List.of(julia, david, corry)
            );
        };
    }
}
