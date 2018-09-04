package ch.olmero.tender.service;

import ch.olmero.tender.service.dto.OfferDTO;

import java.util.List;

/**
 * Service Interface for managing Offer.
 */
public interface OfferService {

    /**
     * Save a offer.
     *
     * @param offerDTO the entity to create
     * @return the persisted entity
     */
    OfferDTO create(OfferDTO offerDTO);

    /**
     * Get all the offers.
     *
     * @return the list of entities
     */
    List<OfferDTO> findAll();

    /**
     * Get the "id" offer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OfferDTO findOne(Long id);

    /**
     * Marks offer as accepted;
     *
     * @param id the id of the entity
     */
    void acceptOffer(Long id);

    /**
     * Get all the offers that belong to tender.
     *
     * @return the list of DTOs
     */
    List<OfferDTO> findAllByTender(Long tenderId);

    /**
     * Get all offers and filters them by bidder and tender.
     *
     * @param bidderId id if a bidder
     * @param tenderId id of tender
     * @return the list of entities
     */
    List<OfferDTO> filterByBidderAndTender(Long bidderId, Long tenderId);
}
