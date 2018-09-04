package ch.olmero.tender.service.mapper;

import ch.olmero.tender.domain.Bidder;
import ch.olmero.tender.domain.Offer;
import ch.olmero.tender.domain.Tender;
import ch.olmero.tender.service.dto.OfferDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Mapper for the entity Offer and its DTO OfferDTO.
 */
@Component
public class OfferMapper implements EntityMapper<OfferDTO, Offer> {

    @Override
    public OfferDTO toDto(Offer offer) {
        return OfferDTO.builder()
                .id(offer.getId())
                .bidderId(offer.getBidder().getId())
                .tenderId(offer.getTender().getId())
                .description(offer.getDescription())
                .status(offer.getStatus())
                .build();
    }

    @Override
    public List<Offer> toEntity(List<OfferDTO> dtoList) {
        List<Offer> entities = new LinkedList<>();
        dtoList.stream().forEach(offerDTO -> entities.add(toEntity(offerDTO)));
        return entities;
    }

    @Override
    public List<OfferDTO> toDto(List<Offer> entityList) {
        List<OfferDTO> dtoList = new LinkedList<>();
        entityList.stream().forEach(offer -> dtoList.add(toDto(offer)));
        return dtoList;
    }

    @Override
    public Offer toEntity(OfferDTO offerDTO) {
        if (offerDTO == null) {
            return null;
        }

        return Offer.builder()
                .id(offerDTO.getId())
                .description(offerDTO.getDescription())
                .bidder(Bidder.builder().id(offerDTO.getBidderId()).build())
                .tender(Tender.builder().id(offerDTO.getTenderId()).build())
                .status(offerDTO.getStatus())
                .build();
    }
}
