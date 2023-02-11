package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class Test10_Annotations {
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

}
