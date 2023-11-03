package music.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import music.store.controller.model.MusicStoreCdsSold;
import music.store.controller.model.MusicStoreData;
import music.store.controller.model.MusicStoreEmployee;
import music.store.dao.CdsSoldDao;
import music.store.dao.EmployeeDao;
import music.store.dao.MusicStoreDao;
import music.store.entity.CdsSold;
import music.store.entity.Employee;
import music.store.entity.MusicStore;

@Service
public class MusicStoreService {
	@Autowired
	private MusicStoreDao musicStoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CdsSoldDao cdsSoldDao;

	public MusicStoreData saveMusicStore(MusicStoreData musicStoreData) {
		
		MusicStore musicStore = findOrCreateMusicStore(musicStoreData.getMusicStoreId());
		copyMusicStoreFields(musicStore, musicStoreData);
		MusicStore dbMusicStore = musicStoreDao.save(musicStore);
		return new MusicStoreData(dbMusicStore);
	}

	private void copyMusicStoreFields(MusicStore musicStore, MusicStoreData musicStoreData) {
		musicStore.setMusicStoreId(musicStoreData.getMusicStoreId());
		musicStore.setMusicStoreName(musicStoreData.getMusicStoreName());
		musicStore.setMusicStoreAddress(musicStoreData.getMusicStoreAddress());
		musicStore.setMusicStoreCity(musicStoreData.getMusicStoreCity());
		musicStore.setMusicStoreCountry(musicStoreData.getMusicStoreCountry());
		
	}

	private MusicStore findOrCreateMusicStore(Long musicStoreId) {
		MusicStore musicStore;
		
		if(Objects.isNull(musicStoreId)) {
			musicStore = new MusicStore();
		} else {
			musicStore = findMusicStoreById(musicStoreId);
		}
		return musicStore;
	}

	private MusicStore findMusicStoreById(Long musicStoreId) {
		return musicStoreDao.findById(musicStoreId).orElseThrow(() 
				-> new NoSuchElementException("Music Store with ID=" +
		musicStoreId + " could not be found. Try again")) ;
	}
    @Transactional(readOnly = false)
	public MusicStoreEmployee saveEmployee(Long musicStoreId, 
			MusicStoreEmployee musicStoreEmployee) {
    	
		MusicStore musicStore = findMusicStoreById(musicStoreId);
		
		Employee employee = findOrCreateEmployee(musicStoreId, 
				musicStoreEmployee.getEmployeeId());
		
		copyEmployeeFields(employee, musicStoreEmployee);
		
		employee.setMusicStore(musicStore);
		musicStore.getEmployees().add(employee);
		
		return new MusicStoreEmployee(employeeDao.save(employee));
	}

	private void copyEmployeeFields(Employee employee, MusicStoreEmployee musicStoreEmployee) {
		employee.setEmployeeId(musicStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(musicStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(musicStoreEmployee.getEmployeeLastName());
		employee.setEmployeeEmail(musicStoreEmployee.getEmployeeEmail());
		employee.setEmployeePhoneNumber(musicStoreEmployee.getEmployeePhoneNumber());
		
	}

	private Employee findOrCreateEmployee(Long musicStoreId, Long employeeId) {
		if(Objects.isNull(employeeId)) {
			return new Employee();
		}else {
			return findEmployeeById(musicStoreId, employeeId);
		}
	}

	private Employee findEmployeeById(Long musicStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(()
				-> new NoSuchElementException("Employee with ID=" + employeeId
						+ " was not found."));
		
		if(employee.getMusicStore().getMusicStoreId() != musicStoreId) {
			throw new IllegalArgumentException("Employee with ID=" + 
		employeeId + " does not work at this music store with ID=" + musicStoreId);
		}
		return employee;
	}

	public MusicStoreCdsSold saveCdsSold(Long musicStoreId, 
			MusicStoreCdsSold musicStoreCdsSold) {
		MusicStore musicStore = findMusicStoreById(musicStoreId);
		CdsSold cdsSold = findOrCreateCdsSold(musicStoreId, 
				musicStoreCdsSold.getCdId());
		
		copyCdsSoldFields(cdsSold, musicStoreCdsSold);
		cdsSold.getMusicStores().add(musicStore);
		musicStore.getCdSold().add(cdsSold);

		return new MusicStoreCdsSold(cdsSoldDao.save(cdsSold));
	}

	private void copyCdsSoldFields(CdsSold cdsSold, MusicStoreCdsSold musicStoreCdsSold) {
		cdsSold.setCdId(musicStoreCdsSold.getCdId());
		cdsSold.setCdsName(musicStoreCdsSold.getCdsName());
		cdsSold.setCdsArtistName(musicStoreCdsSold.getCdsArtistName());
		cdsSold.setCdsAlbumYear(musicStoreCdsSold.getCdsAlbumYear());
	}

	private CdsSold findOrCreateCdsSold(Long musicStoreId, Long cdsSoldId) {
		if(Objects.isNull(cdsSoldId)) {
			return new CdsSold();
		} else {
			return findCdsSoldById(musicStoreId, cdsSoldId);
		}
	}

	private CdsSold findCdsSoldById(Long musicStoreId, Long cdsSoldId) {
		CdsSold cdsSold = cdsSoldDao.findById(cdsSoldId)
				.orElseThrow(() -> new NoSuchElementException("Cd with ID=" +
		cdsSoldId + " was not found."));
		
		boolean found = false;
		
		for(MusicStore musicStore : cdsSold.getMusicStores()) {
			if(musicStore.getMusicStoreId() == musicStoreId) {
				found = true;
				break;
			}
		}
		if(!found) {
			throw new IllegalArgumentException("Cd with ID=" + cdsSoldId + 
					" is not in stock at store with ID=" + musicStoreId);
		}
		return cdsSold;
	}

	public List<MusicStoreData> retrieveAllMusicStores() {
		List<MusicStoreData> musicStoreData = new LinkedList<>();
		
		for(MusicStore musicStore : musicStoreDao.findAll()) {
			MusicStoreData musicData = new MusicStoreData(musicStore);
			
			musicData.getCdSold().clear();
			musicData.getEmployees().clear();
			musicStoreData.add(musicData);
		}
		return musicStoreData;
	}
    @Transactional(readOnly = true)
		public MusicStoreEmployee retrieveEmployeeById(Long employeeId) {
    	return new MusicStoreEmployee(findEmployeeById(employeeId));
	}
    @Transactional(readOnly = true)
    public MusicStoreData retrieveMusicStoreById(Long musicStoreId) {
    	return new MusicStoreData(findMusicStoreById(musicStoreId));
    }
    	public void deleteMusicStoreById(Long musicStoreId) {
		MusicStore musicStore = findMusicStoreById(musicStoreId);
		musicStoreDao.delete(musicStore);
	}
	public void deleteEmployeeById(Long employeeId) {
		Employee employee = findEmployeeById(employeeId);
		employeeDao.delete(employee);
	}

	private Employee findEmployeeById(Long employeeId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(()
				-> new NoSuchElementException("Employee with ID=" + employeeId
						+ " was not found."));
		return employee;
	}



}
