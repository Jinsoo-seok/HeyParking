package com.cudo.heyparking.page.controller;

import com.cudo.heyparking.parking.fo.service.ParkingInfoService;
import com.cudo.heyparking.vo.ParkingVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/page")
@RequiredArgsConstructor
public class PageController {

    final ParkingInfoService parkingInfoService;

//    @GetMapping("/main")
//    public String showMainPage() {
//        return "/page/main/mainPage";
//    }

    @GetMapping("/main")
    public String showMainPage(Model model) {
        List<ParkingVo> parkingVoList = parkingInfoService.getParkingVoList();
        model.addAttribute("dataList", parkingVoList);
        return "/page/main/mainPage";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, HttpServletRequest request) {
        request.setAttribute("error", e.getMessage());
        return "forward:/page/error";
    }
}