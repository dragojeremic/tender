package ch.olmero.tender.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tender.
 */
@Entity
@Table(name = "tender")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tender implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    private TenderStatus status;

    @OneToMany(mappedBy = "tender")
    @JsonIgnore
    private Set<Offer> offers = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name="issuer_id", nullable=false)
    private Issuer issuer;

}
