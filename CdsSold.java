package music.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class CdsSold {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cdId;
	
	private String cdsName;
	private String cdsArtistName;
	private Long cdsAlbumYear;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "cdSold", cascade = CascadeType.PERSIST)
	private Set<MusicStore> musicStores = new HashSet<>();

}
