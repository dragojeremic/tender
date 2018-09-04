package ch.olmero.tender.service;


import ch.olmero.tender.service.dto.TenderDTO;

import java.util.List;

/**
 * Service Interface for managing Tender.
 */
public interface TenderService {

    /**
     * Save a tender.
     *
     * @param tender the entity to create
     * @return the persisted entity DTO
     */
    TenderDTO create(TenderDTO tender);

    /**
     * Get all the tenders.
     *
     * @return the list of DTOs
     */
    List<TenderDTO> findAll();

    /**
     * Get all the tenders that are created by issuer.
     *
     * @param issuerId id of an issuer
     * @return the list of DTOs
     */
    List<TenderDTO> findAllByIssuer(Long issuerId);

    /**
     * Get the "id" tender.
     *
     * @param id the id of the entity
     * @return the DTO
     */
    TenderDTO findOne(Long id);

    /**
     * Delete the "id" tender.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Marks Tender entity as closed.
     *
     * @param id the id of the entity
     */
    void markAsClosed(Long id);
}
