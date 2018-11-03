package at.michaelaltenburger.flatfinder.dao;

import at.michaelaltenburger.flatfinder.entity.SearchConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchConfigurationRepository extends JpaRepository<SearchConfiguration, Long> {
}
