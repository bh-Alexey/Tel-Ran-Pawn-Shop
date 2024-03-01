package us.telran.pawnshop.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.telran.pawnshop.repository.PledgeCategoryRepository;
import us.telran.pawnshop.validation.MetalPurityValidator;

@Configuration
@RequiredArgsConstructor
public class ValidatorConfig {

    private final PledgeCategoryRepository pledgeCategoryRepository;

    @Bean
    public MetalPurityValidator metalPurityValidator() {
        return new MetalPurityValidator();
    }

}