package com.cudo.heyparking.parking.agent.controller;

import com.cudo.heyparking.config.ParamException;
import com.cudo.heyparking.parking.agent.service.ParkingService;
import com.cudo.heyparking.util.ParameterUtils;
import com.cudo.heyparking.util.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.cudo.heyparking.util.ParameterUtils.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/agent/parking")
public class ParkingController {

    final ParkingService parkingService;

    @Value("${value.parking.maxNum}")
    private Integer maxParkingNum;

    @PostMapping
    public Map<String, Object> postParking(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        long startTime = System.currentTimeMillis();
        String apiInfo = "["+ request.getRequestURI() + "] [" + request.getMethod() + "]";
        log.info("{} [START] [{}] - {}", apiInfo, startTime, param);
        Map<String, Object> responseMap = ParameterUtils.responseOption(ResponseCode.FAIL.getCodeName());


        String[] keyList = {"parkingName", "parkingAddr", "parkingTotalNum"};

        try {
            parameterValidation(param, keyList);
            parameterString("parkingName", param.get("parkingName"), true, 0, null);
            parameterString("parkingAddr", param.get("parkingAddr"), true, 0, null);
            parameterString("parkingTotalNum", param.get("parkingTotalNum"), true, 0, null);
//            parameterInt("parkingTotalNum", param.get("parkingTotalNum"), true);

            String parkingNumStr = (String) param.get("parkingTotalNum");
            Integer parkingNum = -1;

            try{
                parkingNum = Integer.parseInt(parkingNumStr);
            } catch (NumberFormatException e) {
                responseMap.put("code", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getCode());
                responseMap.put("message", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getMessage());
            }
            if (parkingNum < 0) {
                responseMap.put("code", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getCode());
                responseMap.put("message", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getMessage());
            }
            else if (parkingNum > maxParkingNum) {
                responseMap.put("code", ResponseCode.FAIL_EXCEED_PARKING_CAPACITY.getCode());
                responseMap.put("message", ResponseCode.FAIL_EXCEED_PARKING_CAPACITY.getMessage());
            }
            else{
                responseMap = parkingService.postParking(param);
            }
        }
        catch (ParamException paramException){
            log.error("[paramException][postParking] - {}", paramException.getMessage());
            responseMap.put("code", paramException.getCode());
            responseMap.put("message", paramException.getMessage());
        }
        catch (Exception exception) {
            log.error("[Exception][postParking] - {}", exception.getMessage());
            responseMap.put("exceptionMessage", exception.getMessage());
        }

        log.info("{} [END] [{}] - {}", apiInfo, (System.currentTimeMillis()-startTime), responseMap.get("code"));
        return responseMap;
    }

    @PutMapping
    public Map<String, Object> putParking(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        long startTime = System.currentTimeMillis();
        String apiInfo = "["+ request.getRequestURI() + "] [" + request.getMethod() + "]";
        log.info("{} [START] [{}] - {}", apiInfo, startTime, param);
        Map<String, Object> responseMap = ParameterUtils.responseOption(ResponseCode.FAIL.getCodeName());


        String[] keyList = {"parkingUq", "parkingName", "parkingAddr", "parkingTotalNum"};

        try {
            parameterValidation(param, keyList);
            parameterString("parkingUq", param.get("parkingUq"), true, 0, null);
            parameterString("parkingName", param.get("parkingName"), true, 0, null);
            parameterString("parkingAddr", param.get("parkingAddr"), true, 0, null);
            parameterString("parkingTotalNum", param.get("parkingTotalNum"), true, 0, null);
//            parameterInt("parkingTotalNum", param.get("parkingTotalNum"), true);

            String parkingNumStr = (String) param.get("parkingTotalNum");
            Integer parkingNum = -1;

            try{
                parkingNum = Integer.parseInt(parkingNumStr);
            } catch (NumberFormatException e) {
                responseMap.put("code", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getCode());
                responseMap.put("message", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getMessage());
            }
            if (parkingNum < 0) {
                responseMap.put("code", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getCode());
                responseMap.put("message", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getMessage());
            }
            else{
                responseMap = parkingService.putParking(param);
            }
        }
        catch (ParamException paramException){
            log.error("[paramException][putParking] - {}", paramException.getMessage());
            responseMap.put("code", paramException.getCode());
            responseMap.put("message", paramException.getMessage());
        }
        catch (Exception exception) {
            log.error("[Exception][putParking] - {}", exception.getMessage());
            responseMap.put("exceptionMessage", exception.getMessage());
        }

        log.info("{} [END] [{}] - {}", apiInfo, (System.currentTimeMillis()-startTime), responseMap.get("code"));
        return responseMap;
    }

    @DeleteMapping
    public Map<String, Object> deleteParking(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        long startTime = System.currentTimeMillis();
        String apiInfo = "["+ request.getRequestURI() + "] [" + request.getMethod() + "]";
        log.info("{} [START] [{}] - {}", apiInfo, startTime, param);
        Map<String, Object> responseMap = ParameterUtils.responseOption(ResponseCode.FAIL.getCodeName());


        String[] keyList = {"parkingId"};

        try {
            parameterValidation(param, keyList);
            parameterInt("parkingId", param.get("parkingId"), true);

            responseMap = parkingService.deleteParking(param);
        }
        catch (ParamException paramException){
            log.error("[paramException][deleteParking] - {}", paramException.getMessage());
            responseMap.put("code", paramException.getCode());
            responseMap.put("message", paramException.getMessage());
        }
        catch (Exception exception) {
            log.error("[Exception][deleteParking] - {}", exception.getMessage());
            responseMap.put("exceptionMessage", exception.getMessage());
        }

        log.info("{} [END] [{}] - {}", apiInfo, (System.currentTimeMillis()-startTime), responseMap.get("code"));
        return responseMap;
    }

    @PutMapping("health-check")
    public Map<String, Object> putHealthCheckAndUpdate(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        long startTime = System.currentTimeMillis();
        String apiInfo = "["+ request.getRequestURI() + "] [" + request.getMethod() + "]";
        log.info("{} [START] [{}] - {}", apiInfo, startTime, param);
        Map<String, Object> responseMap = ParameterUtils.responseOption(ResponseCode.FAIL.getCodeName());


        String[] keyList = {"parkingUq", "parkingEmptyNum"};

        try {
            parameterValidation(param, keyList);
            parameterString("parkingUq", param.get("parkingUq"), true, 0, null);
            parameterString("parkingEmptyNum", param.get("parkingEmptyNum"), true, 0, null);
//            parameterInt("parkingEmptyNum", param.get("parkingEmptyNum"), true);

            String parkingNumStr = (String) param.get("parkingEmptyNum");
            Integer parkingNum = -1;

            try{
                parkingNum = Integer.parseInt(parkingNumStr);
            } catch (NumberFormatException e) {
                responseMap.put("code", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getCode());
                responseMap.put("message", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getMessage());
            }
            if (parkingNum < 0) {
                responseMap.put("code", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getCode());
                responseMap.put("message", ResponseCode.FAIL_INVALID_PARKING_CAPACITY.getMessage());
            }
            else{
                responseMap = parkingService.putHealthCheckAndUpdate(param);
            }
        }
        catch (ParamException paramException){
            log.error("[paramException][putHealthCheckAndUpdate] - {}", paramException.getMessage());
            responseMap.put("code", paramException.getCode());
            responseMap.put("message", paramException.getMessage());
        }
        catch (Exception exception) {
            log.error("[Exception][putHealthCheckAndUpdate] - {}", exception.getMessage());
            responseMap.put("exceptionMessage", exception.getMessage());
        }

        log.info("{} [END] [{}] - {}", apiInfo, (System.currentTimeMillis()-startTime), responseMap.get("code"));
        return responseMap;
    }

}