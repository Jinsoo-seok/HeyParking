package com.cudo.heyparking.parking.repository;

import com.cudo.heyparking.vo.ParkingVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingVo, Long> {

    ParkingVo findByParkingId(Integer parkingId);

    ParkingVo findByParkingUq(String parkingUq);

    Integer deleteByParkingId(Integer parkingId);
    Integer deleteByParkingUq(String parkingUq);

}
