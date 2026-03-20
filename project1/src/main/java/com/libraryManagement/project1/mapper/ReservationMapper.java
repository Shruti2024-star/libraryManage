package com.libraryManagement.project1.mapper;

import com.libraryManagement.project1.dto.ReservationResponse;
import com.libraryManagement.project1.entities.Reservation;

public class ReservationMapper {

    public static ReservationResponse toResponse(Reservation reservation){

        return ReservationResponse.builder()
                .id(reservation.getId())
                .userName(reservation.getUser().getName())
                .bookTitle(reservation.getBook().getTitle())
                .status(reservation.getStatus())
                .build();
    }
}