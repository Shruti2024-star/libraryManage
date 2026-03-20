package com.libraryManagement.project1.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.libraryManagement.project1.dto.ReservationRequest;
import com.libraryManagement.project1.dto.ReservationResponse;
import com.libraryManagement.project1.entities.Permission;
import com.libraryManagement.project1.services.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // ================== MAKE RESERVATION ==================
    @PreAuthorize("hasAuthority('RESERVATION_CREATE')")
    @PostMapping("/reserve")
    public ResponseEntity<ReservationResponse> reserveBook(@Valid @RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.reserveBook(request);
        return ResponseEntity.ok(response);
    }

    // ================== GET ALL RESERVATIONS ==================
    @PreAuthorize("hasAuthority('RESERVATION_VIEWALL')")
    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // ================== GET RESERVATIONS BY USER ==================
    @PreAuthorize("hasAuthority('RESERVATION_VIEW')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByUser(@PathVariable Long userId) {
        List<ReservationResponse> reservations = reservationService.getReservationsByUser(userId);
        return ResponseEntity.ok(reservations);
    }

    // ================== CANCEL RESERVATION ==================
    
    @PreAuthorize("hasAuthority('RESERVATION_CANCEL')")
    @PutMapping("/cancel/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok("Reservation cancelled successfully");
    }
}