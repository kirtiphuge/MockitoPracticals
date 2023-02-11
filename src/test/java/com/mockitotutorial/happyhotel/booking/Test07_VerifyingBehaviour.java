package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class Test07_VerifyingBehaviour {
    private BookingService bookingService;

    private PaymentService paymentService;
    private RoomService roomService;
    private BookingDAO bookingDAO;
    private MailSender mailSender;

    @BeforeEach
    void setup(){
        this.paymentService = mock(PaymentService.class);
        this.roomService = mock(RoomService.class);
        this.bookingDAO = mock(BookingDAO.class);
        this.mailSender = mock(MailSender.class);

        this.bookingService = new BookingService(paymentService, roomService,bookingDAO,mailSender);
    }

    @Test
    void should_invoke_payment_when_prepaid() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020,01,05),2, true);

        //when
        bookingService.makeBooking(bookingRequest);
        //then
        verify(paymentService).pay(bookingRequest, 400.0);
        verifyNoMoreInteractions(paymentService);
    }

    @Test
    void should_not_invoke_payment_when_not_prepaid() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020,01,05),2, false);

        //when
        bookingService.makeBooking(bookingRequest);
        //then
        verify(paymentService, never()).pay(bookingRequest, 400.0);
        //never() ->not called even once
        verifyNoInteractions(paymentService);
    }
}
