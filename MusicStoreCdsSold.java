package music.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import music.store.entity.CdsSold;

@Data
@NoArgsConstructor
public class MusicStoreCdsSold {
	
	private Long cdId;
	
	private String cdsName;
	private String cdsArtistName;
	private Long cdsAlbumYear;
	
	public MusicStoreCdsSold(CdsSold cdsSold) {
		cdId = cdsSold.getCdId();
		cdsName = cdsSold.getCdsName();
		cdsArtistName = cdsSold.getCdsArtistName();
		cdsAlbumYear = cdsSold.getCdsAlbumYear();
	}

}
