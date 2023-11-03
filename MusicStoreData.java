package music.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import music.store.entity.CdsSold;
import music.store.entity.Employee;
import music.store.entity.MusicStore;

@Data
@NoArgsConstructor
public class MusicStoreData {
	
	private Long musicStoreId;
	private String musicStoreName;
	private String musicStoreAddress;
	private String musicStoreCity;
	private String musicStoreCountry;
	private Set<MusicStoreCdsSold> cdSold = new HashSet<>();
	private Set<MusicStoreEmployee> employees = new HashSet<>();
	
	public MusicStoreData(MusicStore musicStore) {
		musicStoreId = musicStore.getMusicStoreId();
		musicStoreName = musicStore.getMusicStoreName();
		musicStoreAddress = musicStore.getMusicStoreAddress();
		musicStoreCity = musicStore.getMusicStoreCity();
		musicStoreCountry = musicStore.getMusicStoreCountry();
		
		for (CdsSold cdsSold : musicStore.getCdSold()) {
			MusicStoreCdsSold musicStoreCdsSold = new MusicStoreCdsSold(cdsSold);
			cdSold.add(musicStoreCdsSold);
		}
		for(Employee employee : musicStore.getEmployees()) {
			MusicStoreEmployee musicStoreEmployee = new MusicStoreEmployee(employee);
			employees.add(musicStoreEmployee);
		}
	}
	
	@Data
	@NoArgsConstructor
	static class MusicStoreCdsSold{
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
	
	@Data
	@NoArgsConstructor
	static class MusicStoreEmployee{
		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeeEmail;
		private Long employeePhoneNumber;
		
		public MusicStoreEmployee(Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstName = employee.getEmployeeFirstName();
		employeeLastName = employee.getEmployeeLastName();
		employeeEmail = employee.getEmployeeEmail();
		employeePhoneNumber = employee.getEmployeePhoneNumber();
		}
	}

}
