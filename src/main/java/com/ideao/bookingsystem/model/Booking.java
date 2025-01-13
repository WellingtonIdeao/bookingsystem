package com.ideao.bookingsystem.model;

import java.math.BigDecimal;
import java.util.Date;

public class Booking {
    private Long id;
    private Date entryDate;
    private Date departureDate;
    private BigDecimal value;
    private String paymentMethod;
}
