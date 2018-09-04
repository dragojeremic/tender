package ch.olmero.tender.repository;

import ch.olmero.tender.domain.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Tender entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenderRepository extends JpaRepository<Tender, Long> {

    List<Tender> findAllByIssuerId(Long issuerId);
}
