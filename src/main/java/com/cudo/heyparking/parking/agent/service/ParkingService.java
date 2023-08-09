package com.cudo.heyparking.parking.agent.service;

import java.util.Map;

public interface ParkingService {

    Map<String, Object> postParking(Map<String, Object> param);

    Map<String, Object> putParking(Map<String, Object> param);

    Map<String, Object> deleteParking(Map<String, Object> param);

    Map<String, Object> putHealthCheckAndUpdate(Map<String, Object> param);

}