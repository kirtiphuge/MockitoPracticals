package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.LenientStubber;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Java12_StrictStubbing {
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
    void should_invoke_payment_when_prepaid() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020,01,05),2, false);

        // Unnecessary Stubbing is done here. below method will not even get called
        //So we get error
        // If we still want to keep this unnecessary stubbing then use Lenient()
        lenient().when(paymentService.pay(any(),anyDouble())).thenReturn("1");

        //when
        bookingService.makeBooking(bookingRequest);
        //then
        //no exception is thrown
    }
}
