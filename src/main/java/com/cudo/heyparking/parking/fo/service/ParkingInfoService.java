package com.cudo.heyparking.parking.fo.service;

import java.util.Map;

public interface ParkingInfoService {

    Map<String, Object> getParkingList();
//    Map<String, Object> getParking(Integer parkingId);
    Map<String, Object> getParking(String parkingUq);
}