package music.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import music.store.entity.CdsSold;

public interface CdsSoldDao extends JpaRepository<CdsSold, Long> {

}
