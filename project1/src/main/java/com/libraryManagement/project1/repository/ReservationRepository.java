package com.libraryManagement.project1.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libraryManagement.project1.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Count active reservations for a user
    long countByUserIdAndStatus(Long userId, String status);

    // Find a specific reservation for user and book
    Optional<Reservation> findByUserIdAndBookIdAndStatus(Long userId, Long bookId, String status);

    // Find all reservations by user with specific status
    List<Reservation> findByUserIdAndStatus(Long userId, String status);

    // Find pending reservations for a book in FIFO order
    List<Reservation> findByBookIdAndStatusOrderByReservationDateAsc(Long bookId, String status);
}
