package ch.olmero.tender.service.dto;


import ch.olmero.tender.domain.TenderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Tender entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenderDTO implements Serializable {

    private Long id;

    private String description;

    private Long issuerId;

    private TenderStatus status;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TenderDTO tenderDTO = (TenderDTO) o;
        if (tenderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tenderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TenderDTO{" +
                "id=" + getId() +
                ", description='" + getDescription() + "'" +
                "}";
    }
}
