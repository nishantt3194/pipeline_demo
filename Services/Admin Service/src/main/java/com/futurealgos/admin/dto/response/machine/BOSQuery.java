package com.futurealgos.admin.dto.response.machine;

import java.time.LocalDate;

public interface BOSQuery {
    LocalDate getTimestamp();

    LocalDate getTstart();

    LocalDate getTend();

    long getQty();

    long getShifts();
}
