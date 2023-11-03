package music.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import music.store.controller.model.MusicStoreCdsSold;
import music.store.controller.model.MusicStoreData;
import music.store.controller.model.MusicStoreEmployee;
import music.store.service.MusicStoreService;

@RestController
@RequestMapping("/music_store")
@Slf4j
public class MusicStoreController {
	@Autowired
	private MusicStoreService musicStoreService;
	
	@PostMapping("/music_store")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MusicStoreData createMusicStore
	(@RequestBody MusicStoreData musicStoreData) {
		log.info("Creating music store {}", musicStoreData);
		
		return musicStoreService.saveMusicStore(musicStoreData);
	}
	
	@PutMapping("/music_store/{musicStoreId}")
	public MusicStoreData updateMusicStore(@PathVariable Long musicStoreId,
			@RequestBody MusicStoreData musicStoreData) {
		musicStoreData.setMusicStoreId(musicStoreId);
		log.info("Updating the music store {}", musicStoreId);
		
		return musicStoreService.saveMusicStore(musicStoreData);
	}
	
	@PostMapping("/{musicStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MusicStoreEmployee insertEmployee(@PathVariable Long musicStoreId,
			@RequestBody MusicStoreEmployee musicStoreEmployee) {
		log.info("The employee {} now works on store with ID=",
				musicStoreEmployee.getEmployeeId(), musicStoreId);
		
		return musicStoreService.saveEmployee(musicStoreId, musicStoreEmployee);
	}
	@PutMapping("/{musicStoreId}/employee/{employeeId}")
	public MusicStoreEmployee updateEmployee(@PathVariable Long musicStoreId,
			MusicStoreEmployee musicStoreEmployee) {
		log.info("Updating employee {} working at music store with ID=" + 
			musicStoreEmployee.getEmployeeId(),musicStoreId);
		
		return musicStoreService.saveEmployee(musicStoreId, musicStoreEmployee);
	}
	
	@PostMapping("/{musicStoreId}/cds_sold")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MusicStoreCdsSold insertCdsSold(@PathVariable Long musicStoreId,
			@RequestBody MusicStoreCdsSold musicStoreCdsSold) {
		log.info("The Cd {} is at store with ID=" + 
			musicStoreCdsSold.getCdId(), musicStoreId);
		
		return musicStoreService.saveCdsSold(musicStoreId, musicStoreCdsSold);
	}
	
	@PutMapping("/{musicStoreId}/cds_sold/{cdId}")
	public MusicStoreCdsSold updateCdsSold(@PathVariable Long musicStoreId,
			MusicStoreCdsSold musicStoreCdsSold) {
		log.info("Updating cd {} for music store ID=" +
			musicStoreCdsSold.getCdId(), musicStoreId);
		
		return musicStoreService.saveCdsSold(musicStoreId, musicStoreCdsSold);
	}
	
	@GetMapping("/music_store")
	public List<MusicStoreData> listAllMusicStores(){
		log.info("Listing all the stores information");
		
		return musicStoreService.retrieveAllMusicStores();
	}
		@GetMapping("/{musicStoreId}")
	public MusicStoreData getMusicStoreById(@PathVariable Long musicStoreId) {
		log.info("Retrieving music store {} with ID=" + musicStoreId);
		
		return musicStoreService.retrieveMusicStoreById(musicStoreId);
	}
	@GetMapping("/employee/{employeeId}")
	public MusicStoreEmployee getEmployeeById(@PathVariable Long employeeId) {
		log.info("Retrieving employee {} with ID={}" + employeeId);
		
		return musicStoreService.retrieveEmployeeById(employeeId);
	}
	@DeleteMapping("{musicStoreId}/employee/{employeeId}")
	public Map<String, String> deleteEmployeeById(@PathVariable Long employeeId){
		log.info("Deleting employee with ID={}" + employeeId);
		
		musicStoreService.deleteEmployeeById(employeeId);
		
		return Map.of("Message", "Employee with ID=" + employeeId 
				+ " deleted successfully");
	}
	@DeleteMapping("/{musicStoreId}")
	public Map<String, String> deleteMusicStoreById(@PathVariable Long musicStoreId){
		log.info("Deleting music store with ID={}" + musicStoreId);
		
		musicStoreService.deleteMusicStoreById(musicStoreId);
		
		return Map.of("Message", " Music store with ID=" + musicStoreId
				+ " deleted succesfully.");
	}
	

}
