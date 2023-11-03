package music.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import music.store.entity.MusicStore;

public interface MusicStoreDao extends JpaRepository<MusicStore, Long> {

}
