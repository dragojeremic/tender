package ch.olmero.tender.service.dto;


import ch.olmero.tender.domain.TenderStatus;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "Generated id of a tender")
    private Long id;

    @ApiModelProperty(notes = "Short description of a tender")
    private String description;

    @ApiModelProperty(notes = "Id of an issues of a tender", required = true)
    private Long issuerId;

    @ApiModelProperty(notes = "Current status of a tender")
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
