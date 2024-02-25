package us.telran.pawnshop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static us.telran.pawnshop.entity.enums.ClientStatus.*;
import static us.telran.pawnshop.entity.enums.ItemType.*;
import static us.telran.pawnshop.entity.enums.ManagerStatus.*;
import static us.telran.pawnshop.entity.enums.PledgeStatus.*;
import static us.telran.pawnshop.entity.enums.ProductStatus.*;

@DataJpaTest
class PledgeRepositoryTest {

    @Autowired
    private PledgeRepository underTest;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PledgeCategoryRepository categoryRepository;

    @AfterEach
    void TearDown() {
        underTest.deleteAll();
    }
    @Test
    void canGetAllByStatus() {
        // Given
        Product firstProduct = new Product("BORROW",
                ACTIVE,
                BigDecimal.valueOf(33)
        );
        Manager firstManager = new Manager("Nick",
                "Carter",
                "nick.carter@yahoo.com",
                "kKjdhdjnsbHfHxdv",
                EXPERT_APPRAISER
        );

        Client firstClient = new Client(REGULAR,
                123412345,
                LocalDate.of(1978, Month.APRIL,21),
                "James",
                "Bearell",
                "james.bearell@gmail.com",
                "455 Conduit Blvd Brooklyn, NY 11208"
        );

        PledgeCategory firstCategory = new PledgeCategory(PreciousMetal.GOLD);

        productRepository.save(firstProduct);
        managerRepository.save(firstManager);
        clientRepository.save(firstClient);
        categoryRepository.save(firstCategory);

        Pledge firstPledge = new Pledge(1L,
                firstProduct,
                firstManager,
                firstClient,
                firstCategory,
                RING,
                "Wedding ring",
                1,
                MetalPurity.GOLD_585,
                BigDecimal.valueOf(4.5),
                BigDecimal.valueOf(4.5),
                BigDecimal.valueOf(148.5),
                PLEDGED,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        Client secondClient = new Client(REGULAR,
                975432186,
                LocalDate.of(1982, Month.MARCH,8),
                "Lea",
                "Fox",
                "lea.fox@gmail.com",
                "60 Madison Pl, Roslyn Heights, NY 11577"
        );

        clientRepository.save(secondClient);

        Pledge secondPledge = new Pledge(2L,
                firstProduct,
                firstManager,
                secondClient,
                firstCategory,
                NECKLESS,
                "Neckless with pearls",
                1,
                MetalPurity.GOLD_585,
                BigDecimal.valueOf(7.1),
                BigDecimal.valueOf(4.8),
                BigDecimal.valueOf(158.4),
                PLEDGED,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        Manager secondManager = new Manager("Nency",
                "Jobs",
                "nency.jobs@uotlook.com",
                "pmhsJCvfs>Jusm",
                EXPERT_APPRAISER
        );

        Client thirdClient = new Client(REGULAR,
                555324287,
                LocalDate.of(1984, Month.MAY,5),
                "Tatyana",
                "Taro",
                "tatyana.taro@gmail.com",
                "2241 Newbold Ave Apt 17B Bronx, NY 10462"
        );

        PledgeCategory secondCategory = new PledgeCategory(PreciousMetal.SILVER);


        Pledge thirdPledge = new Pledge(3L,
                firstProduct,
                secondManager,
                thirdClient,
                secondCategory,
                SCRAP,
                "Spork",
                18,
                MetalPurity.SILVER_925,
                BigDecimal.valueOf(56),
                BigDecimal.valueOf(56),
                BigDecimal.valueOf(46),
                PENDING,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        underTest.save(firstPledge);
        underTest.save(secondPledge);
        underTest.save(thirdPledge);

        // When
        List<Pledge> receivedPledges = underTest.findAllByStatus(PLEDGED);

        // Then
        assertThat(receivedPledges).hasSize(2);
    }
}