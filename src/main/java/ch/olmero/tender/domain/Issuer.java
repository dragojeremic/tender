package ch.olmero.tender.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * An Issuer.
 */
@Entity
@Table(name = "issuer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issuer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "issuer")
    @JsonIgnore
    private Set<Tender> tenders = new HashSet<>();
}
