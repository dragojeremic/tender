package ch.olmero.tender.service.mapper;

import ch.olmero.tender.domain.Issuer;
import ch.olmero.tender.domain.Offer;
import ch.olmero.tender.domain.Tender;
import ch.olmero.tender.service.dto.OfferDTO;
import ch.olmero.tender.service.dto.TenderDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * Mapper for the entity Tender and its DTO TenderDTO.
 */
@Component
public class TenderMapper implements EntityMapper<TenderDTO, Tender> {

    @Override
    public Tender toEntity(TenderDTO dto) {
        return Tender.builder()
                .id(dto.getId())
                .issuer(Issuer.builder().id(dto.getIssuerId()).build())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .build();
    }

    @Override
    public TenderDTO toDto(Tender entity) {
        return TenderDTO.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .issuerId(entity.getIssuer().getId())
                .status(entity.getStatus())
                .build();
    }

    @Override
    public List<Tender> toEntity(List<TenderDTO> dtoList) {
        List<Tender> entities = new LinkedList<>();
        dtoList.stream().forEach(tenderDTO -> entities.add(toEntity(tenderDTO)));
        return entities;
    }

    @Override
    public List<TenderDTO> toDto(List<Tender> entityList) {
        List<TenderDTO> dtoList = new LinkedList<>();
        entityList.stream().forEach(tender -> dtoList.add(toDto(tender)));
        return dtoList;
    }
}
