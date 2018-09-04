package ch.olmero.tender.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An Offer.
 */
@Entity
@Table(name = "offer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 500)
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name="tender_id", nullable=false)
    private Tender tender;

    @ManyToOne
    @JoinColumn(name="bidder_id", nullable=false)
    private Bidder bidder;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OfferStatus status;
}
