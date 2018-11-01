package at.michaelaltenburger.flatfinder.dao;

import at.michaelaltenburger.flatfinder.entity.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, String> {

    List<RealEstate> findByIdIn(List<String> ids);

    @Modifying
    int deleteByIdNotIn(List<String> ids);
}
