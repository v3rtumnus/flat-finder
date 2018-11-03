package at.michaelaltenburger.flatfinder.dao;

import at.michaelaltenburger.flatfinder.entity.PurchaseType;
import at.michaelaltenburger.flatfinder.entity.RealEstate;
import at.michaelaltenburger.flatfinder.entity.RealEstateState;
import at.michaelaltenburger.flatfinder.entity.RealEstateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, String> {

    List<RealEstate> findByIdIn(List<String> ids);

    List<RealEstate> findByStateIs(RealEstateState state);

    List<RealEstate> findByStateIsAndTypeIsAndPurchaseTypeIs(RealEstateState state, RealEstateType type, PurchaseType purchaseType);

    @Modifying
    @Transactional
    int deleteByIdNotIn(List<String> ids);
}
