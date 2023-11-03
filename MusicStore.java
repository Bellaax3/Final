package music.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class MusicStore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long musicStoreId;
	
	private String musicStoreName;
	private String musicStoreAddress;
	private String musicStoreCity;
	private String musicStoreCountry;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "music_store_cds_sold", 
	joinColumns = @JoinColumn(name = "music_store_id"),
	inverseJoinColumns = @JoinColumn(name = "cd_id"))
	private Set<CdsSold> cdSold = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "musicStore", cascade = CascadeType.PERSIST)
	private Set<Employee> employees = new HashSet<>();

}
