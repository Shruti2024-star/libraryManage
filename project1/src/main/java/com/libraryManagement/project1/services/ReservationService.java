package com.libraryManagement.project1.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.libraryManagement.project1.dto.MessageResponse;
import com.libraryManagement.project1.dto.ReservationRequest;
import com.libraryManagement.project1.dto.ReservationResponse;
import com.libraryManagement.project1.entities.Reservation;
import com.libraryManagement.project1.entities.Book;
import com.libraryManagement.project1.entities.User;
import com.libraryManagement.project1.entities.IssueRecord;
import com.libraryManagement.project1.exceptions.BadRequestException;
import com.libraryManagement.project1.exceptions.ConflictException;
import com.libraryManagement.project1.exceptions.ResourceNotFoundException;
import com.libraryManagement.project1.mapper.ReservationMapper;
import com.libraryManagement.project1.repository.ReservationRepository;
import com.libraryManagement.project1.repository.bookRepository;
import com.libraryManagement.project1.repository.UserRepository;
import com.libraryManagement.project1.repository.IssueRecordRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final bookRepository bookRepository;
    private final UserRepository userRepository;
    private final IssueRecordRepository issueRecordRepository;

    // MAKE RESERVATION
    public ReservationResponse reserveBook(ReservationRequest request) {

        // CHECK USER
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        // CHECK BOOK
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + request.getBookId()));

        // CHECK BOOK AVAILABILITY
        if (book.getAvailableCopies() > 0) {
            throw new ConflictException("Book is available. No need to reserve.");
        }

        // MAX 2 RESERVATIONS RULE
        long activeReservations = reservationRepository.countByUserIdAndStatus(user.getId(), "PENDING");

        if (activeReservations >= 2) {
            throw new BadRequestException("User cannot have more than 2 active reservations");
        }

        // CHECK DUPLICATE RESERVATION
        Optional<Reservation> existingReservation = reservationRepository
                .findByUserIdAndBookIdAndStatus(user.getId(), book.getId(), "PENDING");

        if (existingReservation.isPresent()) {
            throw new ConflictException("User already has a pending reservation for this book");
        }

        Reservation reservation = Reservation.builder()
                .user(user)
                .book(book)
                .reservationDate(LocalDate.now())
                .status("PENDING")
                .build();

        reservationRepository.save(reservation);

        return ReservationMapper.toResponse(reservation);
    }

    // GET ALL RESERVATIONS
    public List<ReservationResponse> getAllReservations() {

        return reservationRepository.findAll()
                .stream()
                .map(ReservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    // GET RESERVATIONS BY USER
    public List<ReservationResponse> getReservationsByUser(Long userId) {

        return reservationRepository.findByUserIdAndStatus(userId, "PENDING")
                .stream()
                .map(ReservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    // CANCEL RESERVATION
    public MessageResponse cancelReservation(Long reservationId) {

        // CHECK RESERVATION
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reservation not found with id: " + reservationId));

        reservation.setStatus("CANCELLED");

        reservationRepository.save(reservation);
        
        return new MessageResponse("Reservation cancelled successfully");
    }

    // PROCESS RESERVATIONS ON BOOK RETURN
    public void processReservationsOnReturn(Book book) {

        List<Reservation> pendingReservations = reservationRepository
                .findByBookIdAndStatusOrderByReservationDateAsc(book.getId(), "PENDING");

        if (pendingReservations.isEmpty()) {
            return;
        }

        // ISSUE BOOK TO FIRST RESERVATION
        Reservation first = pendingReservations.get(0);

        IssueRecord issue = IssueRecord.builder()
                .user(first.getUser())
                .book(book)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .status("ISSUED")
                .fine(0)
                .build();

        issueRecordRepository.save(issue);

        // UPDATE BOOK COPIES
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // UPDATE RESERVATION STATUS
        first.setStatus("COMPLETED");
        reservationRepository.save(first);
        
        
    }
}