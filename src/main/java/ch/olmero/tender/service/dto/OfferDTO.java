package ch.olmero.tender.service.dto;


import ch.olmero.tender.domain.OfferStatus;
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

    private Long id;

    private String description;

    private Long tenderId;

    private Long bidderId;

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
