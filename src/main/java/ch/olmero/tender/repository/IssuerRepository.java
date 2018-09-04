package ch.olmero.tender.repository;

import ch.olmero.tender.domain.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Issuer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssuerRepository extends JpaRepository<Issuer, Long> {

}
