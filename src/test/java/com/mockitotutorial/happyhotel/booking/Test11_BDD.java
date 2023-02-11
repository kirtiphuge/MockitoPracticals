package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Test11_BDD {
    @InjectMocks
    private BookingService bookingService;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;
    @Captor
    private ArgumentCaptor<BookingRequest> bookingRequestArgumentCaptor;

    @Mock
    private PaymentService paymentService;
    @Mock
    private RoomService roomService;
    @Spy
    private BookingDAO bookingDAO;
    @Mock
    private MailSender mailSender;

    @Test
    void should_CountAvailablePlaces_When_MultipleRoomsAvailable(){
        //given
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Room 1", 5));
        rooms.add(new Room("Room 2", 2));

        // BDD - Behavioural Driven
        // given section contains when. Which is misleading
        // Just another way of writting
        given(roomService.getAvailableRooms()).willReturn(rooms);
        int expected = 7;

        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_invoke_payment_when_prepaid() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020,01,05),2, true);

        //when
        bookingService.makeBooking(bookingRequest);
        //then
        then(paymentService).should(times(1)).pay(bookingRequest, 400.0);
        verifyNoMoreInteractions(paymentService);
    }
}
