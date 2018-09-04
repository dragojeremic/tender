package ch.olmero.tender.resource;

import ch.olmero.tender.service.OfferService;
import ch.olmero.tender.service.dto.OfferDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation("Creates new offer in the system.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Offer has been created successfully."),
            @ApiResponse(code = 400, message = "Offer is not valid."),
            @ApiResponse(code = 500, message = "An unexpected server error")
    })
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<OfferDTO> create(@Valid @RequestBody OfferDTO offerDTO) {

        return ResponseEntity.ok().body(
                offerService.create(offerDTO)
        );
    }

    @ApiOperation("Returns filtered list of offers based on parameters")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of offers have been returned for selected bidder-tender combination"),
            @ApiResponse(code = 500, message = "An unexpected server error")
    })
    @GetMapping
    public ResponseEntity<List<OfferDTO>> filterByBidderAndTender(@RequestParam(required = false) Long bidderId, @RequestParam(required = false) Long tenderId) {
        return ResponseEntity.ok().body(
                offerService.filterByBidderAndTender(bidderId, tenderId)
        );
    }

    @ApiOperation("Marks offer as accepted and closes related tender.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Offer has been accepted"),
            @ApiResponse(code = 400, message = "Offer doesn't exist"),
            @ApiResponse(code = 500, message = "An unexpected server error")
    })
    @PostMapping(path = "/{offerId}/accept")
    public ResponseEntity<Void> acceptOffer(@PathVariable Long offerId) {
        offerService.acceptOffer(offerId);
        return ResponseEntity.noContent().build();
    }
}
