package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

public class Test08_Spies_PartialMocks {
    private BookingService bookingService;

    private PaymentService paymentService;
    private RoomService roomService;
    private BookingDAO bookingDAO;
    private MailSender mailSender;

    @BeforeEach
    void setup(){
        this.paymentService = mock(PaymentService.class);
        this.roomService = mock(RoomService.class);
        this.bookingDAO = spy(BookingDAO.class);
        this.mailSender = mock(MailSender.class);

        this.bookingService = new BookingService(paymentService, roomService,bookingDAO,mailSender);
    }

    @Test
    void should_CompleteBooking_when_Input_Ok() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020,01,05),2, true);

        //when
        String bookingId = bookingService.makeBooking(bookingRequest);
        //then
        verify(bookingDAO).save(bookingRequest);
        System.out.println("BookingId = " +bookingId);
    }

    @Test
    void should_CancelBooking_when_Input_Ok() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020,01,05),2, true);
        bookingRequest.setRoomId("1.3");
        String bookingId = "1";

        doReturn(bookingRequest).when(bookingDAO).get(bookingId);

        // Spies - Partial Mocks
        // mock - dummy object with no real logic
        // spy - real objects with real logic that we can modify
        // spy - will invoke the actual methods
        // syntax - mocks : when(mock.method()).thenReturn()
        // spies : doReturn().when(spy).method())
        // mocks return the default values but spy invokes actual methods and return values

        //when
        bookingService.cancelBooking(bookingId);

        //then

    }

}
