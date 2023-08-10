package com.cudo.heyparking.parking.agent.service;

import com.cudo.heyparking.parking.repository.ParkingRepository;
import com.cudo.heyparking.util.ParameterUtils;
import com.cudo.heyparking.util.ResponseCode;
import com.cudo.heyparking.vo.ParkingVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {

    final ParkingRepository parkingRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> postParking(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();

        ParkingVo checkParkingVo = parkingRepository.findByParkingUq((String) param.get("parkingUq"));

        if(checkParkingVo != null){
            resultMap.put("code", ResponseCode.FAIL_DUPLICATE_PARKING.getCode());
            resultMap.put("message", ResponseCode.FAIL_DUPLICATE_PARKING.getMessage());
        }
        else{
            ParkingVo beforeParkingVo = new ParkingVo();

            beforeParkingVo.setParkingUq((String) param.get("parkingUq"));
            beforeParkingVo.setParkingName((String) param.get("parkingName"));
            beforeParkingVo.setParkingAddress((String) param.get("parkingAddr"));
            beforeParkingVo.setParkingTotalNumber((String) param.get("parkingTotalNum"));

            ParkingVo afterParkingVo = parkingRepository.save(beforeParkingVo);

            if(afterParkingVo != null){
                resultMap.putAll(ParameterUtils.responseOption(ResponseCode.SUCCESS.getCodeName()));
                resultMap.put("data", afterParkingVo);
            }
            else{
                resultMap.put("code", ResponseCode.FAIL_INSERT_PARKING.getCode());
                resultMap.put("message", ResponseCode.FAIL_INSERT_PARKING.getMessage());
            }
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> putParking(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();

        ParkingVo checkParkingVo = parkingRepository.findByParkingUq((String) param.get("parkingUq"));

        if(checkParkingVo != null){
            checkParkingVo.setParkingName((String) param.get("parkingName"));
            checkParkingVo.setParkingAddress((String) param.get("parkingAddr"));
            checkParkingVo.setParkingTotalNumber((String) param.get("parkingTotalNum"));

            ParkingVo afterParkingVo = parkingRepository.save(checkParkingVo);

            if(afterParkingVo != null){
                resultMap.putAll(ParameterUtils.responseOption(ResponseCode.SUCCESS.getCodeName()));
                resultMap.put("data", afterParkingVo);
            }
            else{
                resultMap.put("code", ResponseCode.FAIL_UPDATE_PARKING.getCode());
                resultMap.put("message", ResponseCode.FAIL_UPDATE_PARKING.getMessage());
            }
        }
        else{
            resultMap.put("code", ResponseCode.FAIL_NOT_EXIST_PARKING.getCode());
            resultMap.put("message", ResponseCode.FAIL_NOT_EXIST_PARKING.getMessage());
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> deleteParking(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();

//        ParkingVo checkParkingVo = parkingRepository.findByParkingId((Integer) param.get("parkingId"));
        ParkingVo checkParkingVo = parkingRepository.findByParkingUq((String) param.get("parkingUq"));

        if(checkParkingVo != null){
//            checkParkingVo.setParkingId((Integer) param.get("parkingId"));
            checkParkingVo.setParkingUq((String) param.get("parkingUq"));

            int deletedCount = parkingRepository.deleteByParkingUq((String) param.get("parkingUq"));

            if (deletedCount > 0) {
                resultMap.putAll(ParameterUtils.responseOption(ResponseCode.SUCCESS.getCodeName()));
            } else {
                resultMap.put("code", ResponseCode.FAIL_DELETE_PARKING.getCode());
                resultMap.put("message", ResponseCode.FAIL_DELETE_PARKING.getMessage());
            }
        }
        else{
            resultMap.put("code", ResponseCode.FAIL_NOT_EXIST_PARKING.getCode());
            resultMap.put("message", ResponseCode.FAIL_NOT_EXIST_PARKING.getMessage());
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> putHealthCheckAndUpdate(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();

        ParkingVo checkParkingVo = parkingRepository.findByParkingUq((String) param.get("parkingUq"));

        if(checkParkingVo != null){
            String checkNum = checkParkingVo.getParkingTotalNumber();
            String parkingEmptyNum = (String) param.get("parkingEmptyNum");

            if (Integer.parseInt(checkNum) < Integer.parseInt(parkingEmptyNum)) {
                resultMap.put("code", ResponseCode.FAIL_EXCEED_PARKING_CAPACITY.getCode());
                resultMap.put("message", ResponseCode.FAIL_EXCEED_PARKING_CAPACITY.getMessage());
            }
            else{
                checkParkingVo.setParkingEmptyNumber((String) param.get("parkingEmptyNum"));
                checkParkingVo.setParkingLat((String) param.get("parkingLat"));
                checkParkingVo.setParkingLon((String) param.get("parkingLon"));

                ParkingVo afterParkingVo = parkingRepository.save(checkParkingVo);

                if(afterParkingVo != null){
                    resultMap.putAll(ParameterUtils.responseOption(ResponseCode.SUCCESS.getCodeName()));
                    resultMap.put("data", afterParkingVo);
                }
                else{
                    resultMap.put("code", ResponseCode.FAIL_UPDATE_PARKING.getCode());
                    resultMap.put("message", ResponseCode.FAIL_UPDATE_PARKING.getMessage());
                }
            }
        }
        else{
            resultMap.put("code", ResponseCode.FAIL_NOT_EXIST_PARKING.getCode());
            resultMap.put("message", ResponseCode.FAIL_NOT_EXIST_PARKING.getMessage());
        }
        return resultMap;
    }

}
