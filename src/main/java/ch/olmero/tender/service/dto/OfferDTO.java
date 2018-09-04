package ch.olmero.tender.service.dto;


import ch.olmero.tender.domain.OfferStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Offer entity.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferDTO implements Serializable {

    @ApiModelProperty(notes = "Generated id of an offer")
    private Long id;

    @ApiModelProperty(notes = "Description of the offer")
    private String description;

    @ApiModelProperty(notes = "Id of related tender", required = true)
    private Long tenderId;

    @ApiModelProperty(notes = "Id of bidder who is making the offet", required = true)
    private Long bidderId;

    @ApiModelProperty(notes = "Current status of the offer")
    private OfferStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfferDTO offerDTO = (OfferDTO) o;
        if (offerDTO.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, offerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OfferDTO{" +
                "id=" + id +
                ", description='" + description + "'" +
                "}";
    }
}
