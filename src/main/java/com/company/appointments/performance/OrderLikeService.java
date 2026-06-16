
package com.company.appointments.performance;

import java.util.List;

public class OrderLikeService {

    public List<String> findAppointments(Long patientId) {

        List<String> appointments = loadEverything();

        return appointments.stream()
            .filter(a -> a.equals(patientId.toString()))
            .toList();
    }

    private List<String> loadEverything() {
        return List.of("1","2","3");
    }
}
