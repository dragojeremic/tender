package ch.olmero.tender.resource;

import ch.olmero.tender.service.TenderService;
import ch.olmero.tender.service.dto.TenderDTO;
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

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TenderDTO> create(@Valid @RequestBody TenderDTO tenderDTO) {
        return ResponseEntity.ok().body(
                tenderService.create(tenderDTO)
        );
    }

    @GetMapping
    public ResponseEntity<List<TenderDTO>> findAll() {
        return ResponseEntity.ok(
                tenderService.findAll()
        );
    }

    @GetMapping(path = "/filter")
    public ResponseEntity<List<TenderDTO>> findByIssuer(@RequestParam(required = true) Long issuerId) {
        return ResponseEntity.ok(
                tenderService.findAllByIssuer(issuerId)
        );
    }
}
