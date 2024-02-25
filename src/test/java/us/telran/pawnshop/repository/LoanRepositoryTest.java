package us.telran.pawnshop.repository;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.ItemType;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.entity.enums.PreciousMetal;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static us.telran.pawnshop.entity.enums.ClientStatus.REGULAR;
import static us.telran.pawnshop.entity.enums.ItemType.BRACELET;
import static us.telran.pawnshop.entity.enums.ManagerStatus.EXPERT_APPRAISER;
import static us.telran.pawnshop.entity.enums.PledgeStatus.PLEDGED;
import static us.telran.pawnshop.entity.enums.ProductStatus.ACTIVE;


@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository underTest;

    @Autowired
    private PledgeRepository pledgeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PledgeCategoryRepository categoryRepository;

    private Pledge existPledge;

    @BeforeEach
    void checkPledgeAllFieldsAndRelations() {
        //Given
        Product product = new Product("BORROW",
                ACTIVE,
                BigDecimal.valueOf(33)
        );
        Manager manager = new Manager("Antony",
                "Gut",
                "antony.gut@yahoo.com",
                "dcijJdsoPJpijAc",
                EXPERT_APPRAISER
        );

        Client client = new Client(REGULAR,
                123456789,
                LocalDate.of(1988, Month.DECEMBER,10),
                "Mark",
                "Aurelea",
                "mark.aurelea@gmail.com",
                "28 Tehama St Brooklyn, NY 11218"
        );

        PledgeCategory category = new PledgeCategory(PreciousMetal.GOLD);

        productRepository.save(product);
        managerRepository.save(manager);
        clientRepository.save(client);
        categoryRepository.save(category);

        //When
        Pledge pledge = new Pledge(1L,
                product,
                manager,
                client,
                category,
                BRACELET,
                "Bracelet",
                1,
                MetalPurity.GOLD_585,
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(60),
                PLEDGED,
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now())
        );

        Pledge existPledge = pledgeRepository.save(pledge);

        //Then
        AssertionsForClassTypes.assertThat(existPledge.getPledgeId()).isNotNull();
        AssertionsForClassTypes.assertThat(existPledge.getProduct()).isEqualTo(product);
        AssertionsForClassTypes.assertThat(existPledge.getManager()).isEqualTo(manager);
        AssertionsForClassTypes.assertThat(existPledge.getClient()).isEqualTo(client);
        AssertionsForClassTypes.assertThat(existPledge.getCategory()).isEqualTo(category);
        AssertionsForClassTypes.assertThat(existPledge.getItem()).isEqualTo(ItemType.BRACELET);
        AssertionsForClassTypes.assertThat(existPledge.getDescription()).isEqualTo("Bracelet");
        AssertionsForClassTypes.assertThat(existPledge.getItemQuantity()).isEqualTo(1);
        AssertionsForClassTypes.assertThat(existPledge.getPurity()).isEqualTo(MetalPurity.GOLD_585);
        AssertionsForClassTypes.assertThat(existPledge.getWeightGross()).isEqualTo(BigDecimal.valueOf(2));
        AssertionsForClassTypes.assertThat(existPledge.getWeightNet()).isEqualTo(BigDecimal.valueOf(2));
        AssertionsForClassTypes.assertThat(existPledge.getEstimatedPrice()).isNotNull();
        AssertionsForClassTypes.assertThat(existPledge.getStatus()).isEqualTo(PLEDGED);
        AssertionsForClassTypes.assertThat(existPledge.getCreatedAt()).isNotNull();
        AssertionsForClassTypes.assertThat(existPledge.getUpdatedAt()).isNotNull();
    }

    @Test
    public void itCheckOfGetAllExpiredLoans() {
        //Given
        Loan existLoan = new Loan(existPledge, BigDecimal.valueOf(100), LoanTerm.WEEK);
        existLoan.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        existLoan.setRansomAmount(BigDecimal.valueOf(107));
        LocalDateTime expireDate = existLoan.getCreatedAt().toLocalDateTime().plusDays(existLoan.getTerm().getDays());
        existLoan.setExpiredAt(expireDate);

        underTest.save(existLoan);
        underTest.flush();

        //When
        List<Loan> found = underTest.getLoansExpired(expireDate.plusDays(1));

        //Then
        assertThat(found.size()).isGreaterThan(0);
    }


}