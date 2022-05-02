package com.stations.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.stations.dto.TrainsDto;
import com.stations.repository.TrainNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stations.dto.StationsDto;
import com.stations.entity.Stations;
import com.stations.repository.StationsRepository;
import com.stations.service.IStationsService;
import org.springframework.web.client.RestTemplate;

@Service
public class StationsServiceImpl implements IStationsService {

    @Autowired
    StationsRepository repository;

    @Autowired
    TrainNameRepository repository2;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<StationsDto> stationsDtoList(Integer trainNo) {
        List<StationsDto> stationsDtoList = new ArrayList<>();
        if (repository.existsByTrainNo(trainNo)) {
            List<Stations> stationsList = repository.findByTrainNo(trainNo);
            stationsList.forEach(stations -> {
                StationsDto stationsDto = new StationsDto();
                stationsDto.setStationName(stations.getStationName());
                stationsDto.setArrivalTime(stations.getArrivalTime());
                stationsDto.setDepartureTime(stations.getDepartureTime());
                stationsDtoList.add(stationsDto);
            });
            return stationsDtoList;
        }
        return stationsDtoList;
    }

    @Override
    public List<TrainsDto> trainsList(String fromStation, String toStation) {
        List<Stations> from = repository.findByStationName(fromStation);
        List<Stations> to = repository.findByStationName(toStation);

        List<Stations> filter1 = from.stream().filter(f -> repository.existsByTrainNoAndStationName(f.getTrainNo(), toStation)).collect(Collectors.toList());
        List<Stations> filter2 = to.stream().filter(f -> repository.existsByTrainNoAndStationName(f.getTrainNo(), fromStation)).collect(Collectors.toList());

        Stations[] sArray1 = new Stations[filter1.size()];
        Stations[] sArray2 = new Stations[filter2.size()];
        filter1.toArray(sArray1);
        filter2.toArray(sArray2);

        List<Stations> fromfinalList = new ArrayList<>();
        List<Stations> tofinalList = new ArrayList<>();

        for (int i=0; i<sArray1.length; i++) {
            for (int j=0; j<sArray2.length; j++) {
                if (sArray1[i].getTrainNo() == sArray2[j].getTrainNo()) {
                    if (sArray1[i].getId() < sArray2[j].getId()) {
                        fromfinalList.add(sArray1[i]);
                        tofinalList.add(sArray2[j]);
                    }
                }
            }
        }

        Stations[] fil1 = new Stations[fromfinalList.size()];
        Stations[] fil2 = new Stations[tofinalList.size()];
        fromfinalList.toArray(fil1);
        tofinalList.toArray(fil2);

        List<TrainsDto> myTrainsList = new ArrayList<>();

        for (int i=0; i<fil1.length; i++) {
            TrainsDto obj = new TrainsDto();
            System.out.println("number: "+fil1[i].getTrainNo());
            obj.setTrainNo(fil1[i].getTrainNo());

            //Trains t = trainsRepository.findById(fil1[i].getTrainNo()).get();
            //System.out.println("name : "+t.getTrainName());
            //obj.setTrainName(repository2.getStationName(fil1[i].getTrainNo()));
            obj.setTrainName(/*new StationsServiceImpl().*/getTrainName(fil1[i].getTrainNo()));
            System.out.println("starttime : "+fil1[i].getDepartureTime());
            obj.setStartTime(fil1[i].getDepartureTime());
            System.out.println("endtime : "+fil2[i].getArrivalTime());
            obj.setEndTime(fil2[i].getArrivalTime());
            myTrainsList.add(obj);
        }
        return myTrainsList;
    }

    private String getTrainName(Integer trainNo) {
        return restTemplate.getForObject("http://localhost:4560/train/trainName/"+trainNo, String.class, trainNo);
    }
}
