package ch.olmero.tender.repository;

import ch.olmero.tender.domain.Bidder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Bidder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BidderRepository extends JpaRepository<Bidder, Long> {

}
