package com.stations.controller;

import java.util.List;

import com.stations.dto.TrainsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.stations.dto.StationsDto;
import com.stations.service.impl.StationsServiceImpl;

@RestController
public class StationsRestController {

	 @Autowired
	 StationsServiceImpl stationsService;
	 
	 @GetMapping("/stationsList/{trainNo}")
	 public List<StationsDto> getStationsList(@PathVariable Integer trainNo) {
		 return stationsService.stationsDtoList(trainNo);
	 }

	@GetMapping("/listOfTrains/{from}/{to}")
	public ResponseEntity<Object> trainDetails(@PathVariable String from, @PathVariable String to) {
		List<TrainsDto>  list = stationsService.trainsList(from, to);
		return ResponseEntity.ok(list);
	}
}
