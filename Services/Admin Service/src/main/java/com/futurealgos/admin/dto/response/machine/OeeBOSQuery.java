package com.futurealgos.admin.dto.response.machine;

import java.time.LocalDate;

public interface OeeBOSQuery {

    LocalDate getTimestamp();

    LocalDate getTstart();

    LocalDate getTend();

    double getOee();

    Double getOeeTarget();

    Double getLsaTarget();

    long getShifts();
}
