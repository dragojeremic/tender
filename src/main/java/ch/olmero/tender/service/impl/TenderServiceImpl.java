package ch.olmero.tender.service.impl;

import ch.olmero.tender.domain.Tender;
import ch.olmero.tender.domain.TenderStatus;
import ch.olmero.tender.repository.TenderRepository;
import ch.olmero.tender.service.TenderService;
import ch.olmero.tender.service.dto.TenderDTO;
import ch.olmero.tender.service.mapper.TenderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Tender.
 */
@Service
public class TenderServiceImpl implements TenderService {

    private final Logger log = LoggerFactory.getLogger(TenderServiceImpl.class);

    private final TenderRepository tenderRepository;
    private final TenderMapper mapper;

    public TenderServiceImpl(TenderRepository tenderRepository, TenderMapper mapper) {
        this.tenderRepository = tenderRepository;
        this.mapper = mapper;
    }

    /**
     * Save a tender.
     *
     * @param tender the entity DTO to create
     * @return the persisted entity DTO
     */
    @Override
    @Transactional
    public TenderDTO create(TenderDTO tender) {
        log.debug("Request to create Tender : {}", tender);
        tender.setStatus(TenderStatus.OPEN);
        Tender entity = tenderRepository.save(mapper.toEntity(tender));
        return mapper.toDto(entity);
    }

    /**
     * Get all the tenders.
     *
     * @return the list of DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public List<TenderDTO> findAll() {
        log.debug("Request to get all Tenders");
        return mapper.toDto(tenderRepository.findAll());
    }

    /**
     * Get all the tenders that are created by issuer.
     *
     * @param issuerId id of an issuer
     * @return the list of DTOs
     */
    @Override
    public List<TenderDTO> findAllByIssuer(Long issuerId) {
        log.debug("Request to get all Tenders for issuer {}", issuerId);
        return mapper.toDto(tenderRepository.findAllByIssuerId(issuerId));
    }

    /**
     * Get one tender by id.
     *
     * @param id the id of the entity
     * @return the DTO
     */
    @Override
    @Transactional(readOnly = true)
    public TenderDTO findOne(Long id) {
        log.debug("Request to get Tender : {}", id);
        return mapper.toDto(tenderRepository.getOne(id));
    }

    /**
     * Delete the tender by id.
     *
     * @param id the id of the entity
     */
    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Tender : {}", id);
        tenderRepository.deleteById(id);
    }

    /**
     * Marks Tender entity as closed.
     *
     * @param id the id of the entity
     */
    @Override
    @Transactional
    public void markAsClosed(Long id) {
        Optional<Tender> optionalTender = tenderRepository.findById(id);

        if (!optionalTender.isPresent()) {
            throw new IllegalArgumentException("There is not tender with id: " + id);
        }

        if (optionalTender.get().getStatus() == TenderStatus.CLOSED) {
            throw new IllegalStateException("Tender is already closed");
        }

        Tender tender = optionalTender.get();
        tender.setStatus(TenderStatus.CLOSED);
        tenderRepository.save(tender);
    }
}
