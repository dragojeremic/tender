package ch.olmero.tender.service.impl;

import ch.olmero.tender.domain.Offer;
import ch.olmero.tender.domain.OfferStatus;
import ch.olmero.tender.domain.TenderStatus;
import ch.olmero.tender.repository.OfferRepository;
import ch.olmero.tender.service.OfferService;
import ch.olmero.tender.service.TenderService;
import ch.olmero.tender.service.dto.OfferDTO;
import ch.olmero.tender.service.dto.TenderDTO;
import ch.olmero.tender.service.mapper.OfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Offer.
 */
@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);

    private final OfferRepository offerRepository;

    private final OfferMapper offerMapper;

    private final TenderService tenderService;

    public OfferServiceImpl(OfferRepository offerRepository, OfferMapper offerMapper, TenderService tenderService) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.tenderService = tenderService;
    }

    /**
     * Save a offer.
     *
     * @param offerDTO the entity to create
     * @return the persisted entity
     */
    @Override
    public OfferDTO create(OfferDTO offerDTO) {
        log.debug("Request to create Offer : {}", offerDTO);
        Assert.notNull(offerDTO.getBidderId(), "Bidder is required");
        Assert.notNull(offerDTO.getTenderId(), "Tender is required");

        if (checkIfTenderIsClosed(offerDTO.getTenderId())) {
            throw new IllegalStateException("Tender is closed");
        }

        Offer offer = offerMapper.toEntity(offerDTO);
        offer.setStatus(OfferStatus.PENDING);
        offer = offerRepository.save(offer);
        return offerMapper.toDto(offer);
    }

    private boolean checkIfTenderIsClosed(Long tenderId) {
        TenderDTO tenderDTO = tenderService.findOne(tenderId);
        return tenderDTO == null || tenderDTO.getStatus().equals(TenderStatus.CLOSED);
    }

    /**
     * Get all the offers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OfferDTO> findAll() {
        log.debug("Request to get all Offers");
        return offerRepository.findAll().stream()
                .map(offerMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one offer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OfferDTO findOne(Long id) {
        log.debug("Request to get Offer : {}", id);
        Offer offer = offerRepository.getOne(id);
        return offerMapper.toDto(offer);
    }

    /**
     * Marks offer as accepted;
     *
     * @param id the id of the entity
     */
    @Transactional
    @Override
    public void acceptOffer(Long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (!optionalOffer.isPresent()) {
            throw new IllegalArgumentException("Offer does not exist with provided id!");
        }

        Offer offer = optionalOffer.get();

        if (offer.getStatus().equals(OfferStatus.REJECTED)) {
            throw new IllegalStateException("Tender is closed for selected offer.");
        }

        if (offer.getStatus().equals(OfferStatus.ACCEPTED)) {
            throw new IllegalStateException("Offer is already accepted.");
        }

        offer.setStatus(OfferStatus.ACCEPTED);
        List<Offer> byTender = offerRepository.findByTenderId(offer.getTender().getId());

        List<Offer> rejectedOffers = byTender.stream()
                .filter(offer1 -> !offer.getId().equals(offer1.getId()))
                .map(filteredOffer -> {
                    filteredOffer.setStatus(OfferStatus.REJECTED);
                    return filteredOffer;
                }).collect(Collectors.toList());

        offerRepository.save(offer);
        offerRepository.saveAll(rejectedOffers);
        tenderService.markAsClosed(offer.getTender().getId());
    }

    /**
     * Get all the offers that belong to tender.
     *
     * @return the list of DTOs
     */
    @Override
    public List<OfferDTO> findAllByTender(Long tenderId) {
        return offerMapper.toDto(offerRepository.findByTenderId(tenderId));
    }

    /**
     * Get all offers and filters them by bidder and tender.
     *
     * @param bidderId id if a bidder
     * @param tenderId id of tender
     * @return the list of entities
     */
    @Override
    public List<OfferDTO> filterByBidderAndTender(Long bidderId, Long tenderId) {
        if (bidderId != null && tenderId != null) {
            return offerMapper.toDto(offerRepository.findByTenderIdAndBidderId(tenderId, bidderId));
        } else if (bidderId != null) {
            return offerMapper.toDto(offerRepository.findByBidderId(bidderId));
        } else if (tenderId != null) {
            return findAllByTender(tenderId);
        }
        return offerMapper.toDto(offerRepository.findAll());
    }

}
