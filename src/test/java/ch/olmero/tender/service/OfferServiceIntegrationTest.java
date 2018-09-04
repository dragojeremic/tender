package ch.olmero.tender.service;

import ch.olmero.tender.TenderApplication;
import ch.olmero.tender.domain.OfferStatus;
import ch.olmero.tender.domain.TenderStatus;
import ch.olmero.tender.service.dto.OfferDTO;
import ch.olmero.tender.service.dto.TenderDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TenderApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class OfferServiceIntegrationTest {

    private static final String OFFER_TEST_DESCRIPTION = "Offer Test Description";
    private static final long TENDER_ID = 1L;
    private static final long BIDDER_ID = 1L;

    @Autowired
    OfferService offerService;

    @Autowired
    TenderService tenderService;

    private OfferDTO offerDTO;


    private OfferDTO createDTO() {
        OfferDTO offer = OfferDTO.builder()
                .description(OFFER_TEST_DESCRIPTION)
                .tenderId(TENDER_ID)
                .bidderId(BIDDER_ID)
                .build();
        return offer;
    }


    @Before
    public void initTest() {
        offerDTO = createDTO();
    }

    @Test
    public void testCreateOfferForValidTender() {
        OfferDTO newOffer = offerService.create(offerDTO);
        assertThat(newOffer).isNotNull();
        assertThat(newOffer.getDescription()).isEqualTo(OFFER_TEST_DESCRIPTION);
        assertThat(newOffer.getBidderId()).isEqualTo(BIDDER_ID);
        assertThat(newOffer.getStatus()).isEqualByComparingTo(OfferStatus.PENDING);
    }

    @Test()
    public void testCreateOfferForClosedTender() {
        tenderService.markAsClosed(TENDER_ID);
        assertThatThrownBy(() -> offerService.create(offerDTO)).isInstanceOf(IllegalStateException.class);
    }


    @Test
    public void testCreateOfferEmptyBidder() {
        OfferDTO offer = OfferDTO.builder()
                .tenderId(TENDER_ID)
                .description(OFFER_TEST_DESCRIPTION)
                .build();

        assertThatThrownBy(() -> offerService.create(offer)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Bidder");
    }

    @Test
    public void acceptOffer() {
        OfferDTO newOffer = offerService.create(offerDTO);
        offerService.create(offerDTO);
        offerService.create(offerDTO);

        offerService.acceptOffer(newOffer.getId());
        newOffer = offerService.findOne(newOffer.getId());
        final Long id = newOffer.getId();

        TenderDTO tenderDTO = tenderService.findOne(newOffer.getTenderId());
        List<OfferDTO> allByTender = offerService.findAllByTender(tenderDTO.getId());

        assertThat(newOffer.getStatus()).isEqualByComparingTo(OfferStatus.ACCEPTED);
        assertThat(tenderDTO.getStatus()).isEqualByComparingTo(TenderStatus.CLOSED);

        allByTender.stream()
                .filter(offer -> !offer.getId().equals(id))
                .collect(Collectors.toList()).stream()
                .forEach(rejcted -> assertThat(rejcted.getStatus()).isEqualByComparingTo(OfferStatus.REJECTED));
    }

    @Test
    public void testAcceptNonExistingOffer() {
        assertThatThrownBy(() -> offerService.acceptOffer(100L)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testAcceptNonPendingOffer() {
        OfferDTO newOffer = offerService.create(offerDTO);
        OfferDTO newOffer1 = offerService.create(this.offerDTO);

        offerService.acceptOffer(newOffer.getId());

        assertThatThrownBy(() -> offerService.acceptOffer(newOffer.getId())).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> offerService.acceptOffer(newOffer1.getId())).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testOfferFilter(){
        OfferDTO offerDTO2 = OfferDTO.builder()
                .bidderId(2L)
                .tenderId(2L)
                .build();

        offerService.create(offerDTO);
        offerService.create(offerDTO);
        offerService.create(offerDTO2);

        assertThat(offerService.filterByBidderAndTender(null, null).size()).isEqualTo(3);
        assertThat(offerService.filterByBidderAndTender(BIDDER_ID, 2L).size()).isEqualTo(0);
        assertThat(offerService.filterByBidderAndTender(BIDDER_ID, TENDER_ID).size()).isEqualTo(2);
        assertThat(offerService.filterByBidderAndTender(BIDDER_ID, null).size()).isEqualTo(2);
        assertThat(offerService.filterByBidderAndTender(null, TENDER_ID).size()).isEqualTo(2);
        assertThat(offerService.filterByBidderAndTender(null, 2L).size()).isEqualTo(1);
        assertThat(offerService.filterByBidderAndTender(2L, null).size()).isEqualTo(1);
    }
}
