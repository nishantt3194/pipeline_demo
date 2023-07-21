package com.futurealgos.admin.dto.payload.shifts;

public record EditShift(

        String id ,
        String name ,
        String area ,
        String startTime ,
        String stopTime
//        boolean status
) {
}
