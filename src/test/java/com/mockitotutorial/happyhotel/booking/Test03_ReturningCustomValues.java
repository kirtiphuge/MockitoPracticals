package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test03_ReturningCustomValues {
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

        // Nice mocks default values
        // 1. empty list
        // 2. null object
        // 3. 0/false primitive

        System.out.println("List returned : "+roomService.getAvailableRooms());
        System.out.println("Object returned : "+roomService.findAvailableRoomId(null));
        System.out.println("Primitive returned : "+roomService.getRoomCount());
    }

    @Test
    void should_CountAvailablePlaces_When_OneRoomAvailable(){
        //given
        when(roomService.getAvailableRooms()).thenReturn(Collections.singletonList(new Room("Room 1", 5)));
        int expected = 5;

        //when
         int actual = bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_CountAvailablePlaces_When_MultipleRoomsAvailable(){
        //given
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Room 1", 5));
        rooms.add(new Room("Room 2", 2));
        when(roomService.getAvailableRooms()).thenReturn(rooms);
        int expected = 7;

        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);
    }
}
