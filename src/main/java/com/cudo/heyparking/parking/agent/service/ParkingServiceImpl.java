package com.cudo.heyparking.parking.agent.service;

import com.cudo.heyparking.parking.repository.ParkingRepository;
import com.cudo.heyparking.util.ParameterUtils;
import com.cudo.heyparking.util.ResponseCode;
import com.cudo.heyparking.vo.ParkingVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
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


            URI addrToLatLon = urlAddrToLatLon("addrToLatLon", param);
            log.info("[@External URI>> KAKAO] - {}", addrToLatLon);

            Map<String, Object> restTemplateResponseMap = restTemplateFunction("addrToLatLon", addrToLatLon);
            Map<String, Object> responseDataMap = (Map<String, Object>) restTemplateResponseMap.get("data");
            beforeParkingVo.setParkingLat((String) responseDataMap.get("lat"));
            beforeParkingVo.setParkingLon((String) responseDataMap.get("lon"));

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


    public Map<String, Object> restTemplateFunction(String type, URI uri){
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK 13cd2d706608b4236574bbd82dba46c6");
        headers.set("Accept", "*/*;q=0.9"); // HTTP_ERROR 방지
        HttpEntity<String> httpRequest = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();

        HttpStatus httpStatus = null;
        ResponseEntity<Map> httpResponse = null;

        try {
            httpResponse = restTemplate.exchange(uri, HttpMethod.GET, httpRequest, Map.class);
            List<Map<String, Object>> responseList = (List<Map<String, Object>>) httpResponse.getBody().get("documents");

            if (responseList.isEmpty()) {
                throw new Exception("[FAIL][KAKAO SEARCH] - No data available.");
            }

            if(type.equals("addrToLatLon")) {
                Map<String, Object> addressInfoLatest = responseList.get(0);

                String lat = String.format("%.6f", Double.parseDouble((String) addressInfoLatest.get("y")));
                String lon = String.format("%.6f", Double.parseDouble((String) addressInfoLatest.get("x")));

                dataMap.put("lat", lat);
                dataMap.put("lon", lon);

                returnMap.put("data", dataMap);
                returnMap.putAll(ParameterUtils.responseOption(ResponseCode.SUCCESS.getCodeName()));
            }
            else{
                log.info("[FAIL][restTemplate]");
                returnMap.putAll(ParameterUtils.responseOption(ResponseCode.FAIL.getCodeName()));
            }

        } catch (Exception exception) {
            if(exception.getMessage().equals("[FAIL][KAKAO SEARCH] - No data available.")){
                returnMap.putAll(ParameterUtils.responseOption(ResponseCode.NO_DATA.getCodeName()));
            }
            else{
                returnMap.putAll(ParameterUtils.responseOption(ResponseCode.FAIL.getCodeName()));
            }
            log.error("[FAIL][KAKAO SEARCH]", exception.getMessage());
        }
        return returnMap;
    }
    public URI urlAddrToLatLon(String type, Map<String, Object> data){

        String baseUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String analyzeType = "similar";
        String address = (String) data.get("parkingAddr");
        String page = "1";
        String size = "10";

        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);

        URI urlBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("analyze_type", analyzeType)
                .queryParam("query", encodedAddress)
                .queryParam("page", page)
                .queryParam("size", size)
                .build(true)
                .toUri();

        try {
            return new URI(urlBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
