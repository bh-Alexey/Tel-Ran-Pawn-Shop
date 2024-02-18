package us.telran.pawnshop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.PreciousMetalPrice;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PreciousMetalPriceRepositoryTest {

    @Autowired
    private PledgeCategoryRepository pledgeCategoryRepository;

    @Autowired
    private PreciousMetalPriceRepository underTest;

    private PledgeCategory testCategory;

    @BeforeEach
    void setUp() {
        testCategory = pledgeCategoryRepository.save(new PledgeCategory(PreciousMetal.GOLD));
    }

    @AfterEach
    void TearDown() {
        underTest.deleteAll();
        pledgeCategoryRepository.deleteAll();
    }

    @Test
    void itShouldCheckIfPurityPriceExist() {
        //given
        MetalPurity purity = MetalPurity.GOLD_375;
        PreciousMetalPrice price = new PreciousMetalPrice();
        price.setCategory(testCategory);
        price.setPurity(purity);
        price.setMetalPrice(BigDecimal.TEN);


        underTest.save(price);

        //when
        Optional<PreciousMetalPrice> expected = underTest.findByPurity(purity);

        //then
        assertThat(expected).isPresent();
        assertThat(expected).get().isEqualTo(price);


    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExist() {
        //given
        MetalPurity purity = MetalPurity.GOLD_375;

        //when
        Optional<PreciousMetalPrice> expected = underTest.findByPurity(purity);

        //then
        assertThat(expected).isEmpty();
    }

}