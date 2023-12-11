package com.sparta_a5.ootd.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class SearchRequestDto {

    private String classification;
    private String keyword;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
