package com.stations.repository;

import com.stations.entity.TrainName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrainNameRepository extends JpaRepository<TrainName, Integer> {
    /*@Query("select t.trainName from TrainName t where t.trainNo = ?1")
    String getStationName(Integer trainNo);*/
}
