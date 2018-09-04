package ch.olmero.tender.resource;

import ch.olmero.tender.service.OfferService;
import ch.olmero.tender.service.dto.OfferDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/offers", produces = APPLICATION_JSON_UTF8_VALUE)
public class OfferResource {

    private OfferService offerService;

    public OfferResource(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OfferDTO> create(@Valid @RequestBody OfferDTO offerDTO) {

        return ResponseEntity.ok().body(
                offerService.create(offerDTO)
        );
    }

    @GetMapping
    public ResponseEntity<List<OfferDTO>> filterByBidderAndTender(@RequestParam(required = false) Long bidderId, @RequestParam(required = false) Long tenderId) {
        return ResponseEntity.ok().body(
                offerService.filterByBidderAndTender(bidderId, tenderId)
        );
    }

    @PostMapping(path = "/{offerId}/accept")
    public ResponseEntity<Void> acceptOffer(@PathVariable Long offerId) {
        offerService.acceptOffer(offerId);
        return ResponseEntity.noContent().build();
    }
}
