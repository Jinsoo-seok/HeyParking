package com.cudo.heyparking.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TB_PARKING_INFO")
public class ParkingVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_PARKING")
    private Integer parkingId;

    @Column(name = "UQ_PARKING", nullable = false)
    private String parkingUq;

    @Column(name = "PARKING_NAME", nullable = false)
    private String parkingName;

    @Column(name = "PARKING_ADDR", nullable = false)
    private String parkingAddress;

    @Column(name = "PARKING_TOTAL_NUM", nullable = false)
    private String parkingTotalNumber;

    @Column(name = "PARKING_EMPTY_NUM", nullable = false)
    private String parkingEmptyNumber = "-1";

    @Column(name = "PARKING_LAT", nullable = false)
    private String parkingLat = "0";

    @Column(name = "PARKING_LON", nullable = false)
    private String parkingLon = "0";

    @Transient
    private String parkingColor = "none";

    @Column(name = "UPDATE_DATE", nullable = false)
    private Date updateDate;

    @Column(name = "UPDATE_TIME", nullable = false)
    private Time updateTime;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        updateDate = Date.valueOf(now.toLocalDate());
        updateTime = Time.valueOf(now.toLocalTime());
    }
}
