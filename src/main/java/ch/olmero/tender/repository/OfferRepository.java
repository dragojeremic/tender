package ch.olmero.tender.repository;

import ch.olmero.tender.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByTenderId(Long tenderId);

    List<Offer> findByTenderIdAndBidderId(Long tenderId, Long bidderId);

    List<Offer> findByBidderId(Long bidderId);
}
