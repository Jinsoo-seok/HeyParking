package com.cudo.heyparking.parking.fo.service;

import com.cudo.heyparking.vo.ParkingVo;

import java.util.List;
import java.util.Map;

public interface ParkingInfoService {

    List<ParkingVo> getParkingVoList();

    Map<String, Object> getParkingList();
//    Map<String, Object> getParking(Integer parkingId);
    Map<String, Object> getParking(String parkingUq);
}