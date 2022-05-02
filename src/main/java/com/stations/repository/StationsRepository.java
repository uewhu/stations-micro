package com.stations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stations.entity.Stations;

import java.util.List;

@Repository
public interface StationsRepository extends JpaRepository<Stations, Integer> {
    List<Stations> findByTrainNo(Integer trainNo);
    boolean existsByTrainNo(Integer trainNo);
    boolean existsByTrainNoAndStationName(Integer trainNo, String stationName);
    List<Stations> findByStationName(String stationName);
}
