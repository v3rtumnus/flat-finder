package at.michaelaltenburger.flatfinder.dao;

import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.entity.RealEstateState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, String> {

    List<RealEstate> findByIdIn(List<String> ids);

    List<RealEstate> findByStateIs(RealEstateState state);

    @Modifying
    @Transactional
    int deleteByIdNotIn(List<String> ids);
}
