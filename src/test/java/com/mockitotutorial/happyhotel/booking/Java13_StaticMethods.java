package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class Java13_StaticMethods {
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
        try(MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)){
            //given
            BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                    LocalDate.of(2020,01,05),2, false);
            double expected = 400.0;
            mockedConverter.when(()-> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);
            //when
            double actual = bookingService.calculatePriceEuro(bookingRequest);
            //then
            assertEquals(expected, actual);
        }


    }
}
