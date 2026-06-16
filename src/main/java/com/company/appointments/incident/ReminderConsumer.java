package com.company.appointments.incident;

import com.company.appointments.dto.CreateAppointmentRequest;
import com.company.appointments.entity.Appointment;
import com.company.appointments.review.AiGeneratedAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReminderConsumer {

    private final AiGeneratedAppointmentService appointmentService;

    @KafkaListener(topics = "appointment-reminders", groupId = "reminder-group")
    public void process(String eventJson) {
        ReminderEvent event = parseEvent(eventJson);

        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setPatientId(event.getPatientId());
        request.setDoctorId(event.getDoctorId());
        request.setScheduledAt(event.getScheduledAt());

        Appointment appointment = appointmentService.createIfNotExists(request);
        sendReminderNotification(appointment);
    }

    private void sendReminderNotification(Appointment appointment) {
        // TODO: integrate with notification service
    }

    private ReminderEvent parseEvent(String json) {
        String[] parts = json.split(",");
        ReminderEvent event = new ReminderEvent();
        event.setAppointmentId(Long.parseLong(parts[0].split(":")[1].trim()));
        event.setPatientId(Long.parseLong(parts[1].split(":")[1].trim()));
        event.setDoctorId(Long.parseLong(parts[2].split(":")[1].trim()));
        return event;
    }
}
