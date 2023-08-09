package com.cudo.heyparking.parking.fo.controller;

import com.cudo.heyparking.parking.fo.service.ParkingInfoService;
import com.cudo.heyparking.util.ParameterUtils;
import com.cudo.heyparking.util.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/fo/parking")
public class ParkingInfoController {

    final ParkingInfoService parkingInfoService;

    @GetMapping
    public Map<String, Object> getParkingList(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        String apiInfo = "["+ request.getRequestURI() + "] [" + request.getMethod() + "]";
        log.info("{} [START] [{}]", apiInfo, startTime);
        Map<String, Object> responseMap = ParameterUtils.responseOption(ResponseCode.FAIL.getCodeName());


        try {
            responseMap = parkingInfoService.getParkingList();
        }
        catch (Exception exception) {
            log.error("[Exception][getParkingList] - {}", exception.getMessage());
            responseMap.put("exceptionMessage", exception.getMessage());
        }

        log.info("{} [END] [{}] - {}", apiInfo, (System.currentTimeMillis()-startTime), responseMap.get("code"));
        return responseMap;
    }

    @GetMapping("/{parkingUq}")
    public Map<String, Object> getParking(HttpServletRequest request, @PathVariable String parkingUq) {
        long startTime = System.currentTimeMillis();
        String apiInfo = "["+ request.getRequestURI() + "] [" + request.getMethod() + "]";
        log.info("{} [START] [{}] - {}", apiInfo, startTime, parkingUq);
        Map<String, Object> responseMap = ParameterUtils.responseOption(ResponseCode.FAIL.getCodeName());


        try {
            responseMap = parkingInfoService.getParking(parkingUq);
        }
        catch (Exception exception) {
            log.error("[Exception][getParking] - {}", exception.getMessage());
            responseMap.put("exceptionMessage", exception.getMessage());
        }

        log.info("{} [END] [{}] - {}", apiInfo, (System.currentTimeMillis()-startTime), responseMap.get("code"));
        return responseMap;
    }

}