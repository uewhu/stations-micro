package com.stations.service;



import java.util.List;

import com.stations.dto.StationsDto;
import com.stations.dto.TrainsDto;
import com.stations.entity.Stations;

public interface IStationsService {
    List<StationsDto> stationsDtoList(Integer trainNo);
    List<TrainsDto> trainsList(String fromStation, String toStation);
}
