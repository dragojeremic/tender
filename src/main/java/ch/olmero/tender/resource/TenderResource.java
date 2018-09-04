package ch.olmero.tender.resource;

import ch.olmero.tender.service.TenderService;
import ch.olmero.tender.service.dto.TenderDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/tenders", produces = APPLICATION_JSON_UTF8_VALUE)
public class TenderResource {

    private TenderService tenderService;

    public TenderResource(TenderService tenderService) {
        this.tenderService = tenderService;
    }

    @ApiOperation("Creates new tender in the system.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tender has been created successfully."),
            @ApiResponse(code = 400, message = "Tender is not valid."),
            @ApiResponse(code = 500, message = "An unexpected server error")
    })
    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TenderDTO> create(@Valid @RequestBody TenderDTO tenderDTO) {
        return ResponseEntity.ok().body(
                tenderService.create(tenderDTO)
        );
    }

    @ApiOperation("returns all tenders in the system.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of all tenders is returned."),
            @ApiResponse(code = 500, message = "An unexpected server error")
    })
    @GetMapping
    public ResponseEntity<List<TenderDTO>> findAll() {
        return ResponseEntity.ok(
                tenderService.findAll()
        );
    }

    @ApiOperation("Returns filtered list of tenders based on issuer id.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of tenders have been returned for selected issuer"),
            @ApiResponse(code = 500, message = "An unexpected server error")
    })
    @GetMapping(path = "/filter")
    public ResponseEntity<List<TenderDTO>> findByIssuer(@RequestParam(required = true) Long issuerId) {
        return ResponseEntity.ok(
                tenderService.findAllByIssuer(issuerId)
        );
    }
}
