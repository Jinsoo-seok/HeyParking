package com.cudo.heyparking.parking.fo.service;

import com.cudo.heyparking.parking.repository.ParkingRepository;
import com.cudo.heyparking.util.ParameterUtils;
import com.cudo.heyparking.util.ResponseCode;
import com.cudo.heyparking.vo.ParkingVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingInfoServiceImpl implements ParkingInfoService {

    final ParkingRepository parkingRepository;

    @Override
    public Map<String, Object> getParkingList() {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();

        List<ParkingVo> parkingVoList = parkingRepository.findAll();

        if(parkingVoList != null){
            // TODO : 빈자리 정보 없으면 안내려줄지? 프론트에서 할지? >> 데이터 총 갯수는 알아야하니까 프론트?
//            List<ParkingVo> parkingVoListTemp = new ArrayList<>();
//            for (ParkingVo parkingVo : parkingVoList) {
//                Integer emptySpaces = Integer.parseInt(parkingVo.getParkingEmptyNumber());
//
//                if (emptySpaces > 0) {
//                    parkingVoListTemp.add(parkingVo);
//                }
//            }
//            dataMap.put("totalCount", parkingVoListTemp.size());
//            dataMap.put("parkingList", parkingVoListTemp);

            // TODO : 아이콘 색
//            for (ParkingVo parkingVo : parkingVoList) {
//                String totalNum = parkingVo.getParkingTotalNumber();
//                String emptyNum = parkingVo.getParkingEmptyNumber();
//
//                double occupancyPercentage = (Double.parseDouble(emptyNum) / Double.parseDouble(totalNum)) * 100;
//
//                if (occupancyPercentage >= 70) {
//                    parkingVo.setParkingColor("green");
//                } else if (occupancyPercentage >= 30) {
//                    parkingVo.setParkingColor("orange");
//                } else {
//                    parkingVo.setParkingColor("red");
//                }
//            }


            dataMap.put("totalCount", parkingVoList.size());
            dataMap.put("parkingList", parkingVoList);
            resultMap.put("data", dataMap);
            resultMap.putAll(ParameterUtils.responseOption(ResponseCode.SUCCESS.getCodeName()));
        }
        else{
            resultMap.putAll(ParameterUtils.responseOption(ResponseCode.NO_CONTENT.getCodeName()));
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getParking(String parkingUq) {
        Map<String, Object> resultMap = new HashMap<>();

        ParkingVo parkingVo = parkingRepository.findByParkingUq(parkingUq);

        if(parkingVo != null){
            resultMap.put("data", parkingVo);
            resultMap.putAll(ParameterUtils.responseOption(ResponseCode.SUCCESS.getCodeName()));
        }
        else{
            resultMap.putAll(ParameterUtils.responseOption(ResponseCode.NO_CONTENT.getCodeName()));
        }
        return resultMap;
    }
}
