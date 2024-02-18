package us.telran.pawnshop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PledgeCategoryRepositoryTest {

    @Autowired
    private PledgeCategoryRepository underTest;

    @AfterEach
    void TearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfCategoryExist() {
        //given
        PreciousMetal categoryName = PreciousMetal.GOLD;
        PledgeCategory category = new PledgeCategory();
        category.setCategoryName(categoryName);

        underTest.save(category);

        //when
        Optional<PledgeCategory> expected = underTest.findByCategoryName(categoryName);

        //then
        assertThat(expected).isPresent();
        assertThat(expected).get().isEqualTo(category);
    }

    @Test
    void itShouldCheckIfCategoryDoesNotExist() {
        //given
        PreciousMetal categoryName = PreciousMetal.GOLD;

        //when
        Optional<PledgeCategory> expected = underTest.findByCategoryName(categoryName);

        //then
        assertThat(expected).isEmpty();
    }

}