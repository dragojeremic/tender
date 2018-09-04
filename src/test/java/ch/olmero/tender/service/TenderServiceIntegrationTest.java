package ch.olmero.tender.service;

import ch.olmero.tender.TenderApplication;
import ch.olmero.tender.domain.TenderStatus;
import ch.olmero.tender.service.dto.TenderDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TenderApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class TenderServiceIntegrationTest {

    private static final String TENDER_TEST_DESCRIPTION = "Tender Test Description";
    private static final long ISSUER_ID = 1L;

    @Autowired
    TenderService tenderService;

    private TenderDTO tenderDTO;

    @Before
    public void initTest() {
        tenderDTO = TenderDTO.builder()
                .description(TENDER_TEST_DESCRIPTION)
                .issuerId(ISSUER_ID)
                .build();
    }

    @Test
    public void testCreateTender() {
        TenderDTO dto = tenderService.create(this.tenderDTO);

        assertThat(dto).isNotNull();
        assertThat(dto.getDescription()).isEqualTo(TENDER_TEST_DESCRIPTION);
        assertThat(dto.getIssuerId()).isEqualTo(ISSUER_ID);
        assertThat(dto.getStatus()).isEqualByComparingTo(TenderStatus.OPEN);
    }

    @Test
    public void testCloseTender() {
        TenderDTO dto = tenderService.create(this.tenderDTO);

        tenderService.markAsClosed(dto.getId());

        assertThat(tenderService.findOne(dto.getId()).getStatus()).isEqualByComparingTo(TenderStatus.CLOSED);
    }

    @Test
    public void testFindMethods() {
        /**
         * take a look at tenders.csv file
         */
        assertThat(tenderService.findAll().size()).isEqualTo(3);
        assertThat(tenderService.findAllByIssuer(ISSUER_ID).size()).isEqualTo(1);
        assertThat(tenderService.findAllByIssuer(2L).size()).isEqualTo(2);
    }
}
