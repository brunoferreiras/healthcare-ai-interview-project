package com.company.appointments.incident;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReminderEvent {
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime scheduledAt;
}
