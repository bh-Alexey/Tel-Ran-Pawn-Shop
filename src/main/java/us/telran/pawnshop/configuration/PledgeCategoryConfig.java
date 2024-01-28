package us.telran.pawnshop.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.repository.PledgeCategoryRepository;

import java.util.List;

import static us.telran.pawnshop.entity.enums.PreciousMetal.*;

@Configuration
public class PledgeCategoryConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            PledgeCategoryRepository pledgeCategoryRepository) {
        return args -> {
            PledgeCategory gold = new PledgeCategory(
                    0L,
                    GOLD
            );

            PledgeCategory silver = new PledgeCategory(
                    1L,
                    SILVER
            );

            pledgeCategoryRepository.saveAll(List.of(gold, silver));
        };
    }
}
