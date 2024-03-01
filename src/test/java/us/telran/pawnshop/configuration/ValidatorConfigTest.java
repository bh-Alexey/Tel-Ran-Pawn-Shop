package us.telran.pawnshop.configuration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import us.telran.pawnshop.repository.PledgeCategoryRepository;
import us.telran.pawnshop.validation.MetalPurityValidator;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class ValidatorConfigTest {

    @Mock
    private PledgeCategoryRepository pledgeCategoryRepository;

    @MockBean
    private BranchConfig branchConfig;

    @InjectMocks
    private ValidatorConfig underTest;

    @Test
    public void testMetalPurityValidator() {
        MetalPurityValidator result = underTest.metalPurityValidator();
        Assertions.assertThat(result).isNotNull();
    }
}