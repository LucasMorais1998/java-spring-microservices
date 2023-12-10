package com.youtube.javaspringmicroservices.services;

import com.youtube.javaspringmicroservices.domain.Event;
import com.youtube.javaspringmicroservices.domain.Subscription;
import com.youtube.javaspringmicroservices.dtos.EmailRequestDTO;
import com.youtube.javaspringmicroservices.dtos.EventRequestDTO;
import com.youtube.javaspringmicroservices.exceptions.EventFullException;
import com.youtube.javaspringmicroservices.exceptions.EventNotFoundException;
import com.youtube.javaspringmicroservices.repositories.EventRepository;
import com.youtube.javaspringmicroservices.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private EmailServiceClient emailServiceClient;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now());
    }

    public Event createEvent(EventRequestDTO eventRequest) {
        Event newEvent = new Event(eventRequest);
        return eventRepository.save(newEvent);
    }

    private Boolean isEventFull(Event event) {
        return event.getRegisteredParticipants() >= event.getMaxParticipants();
    }

    public void registerParticipant(String eventId, String participantEmail) {
        Event event = eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);

        if (isEventFull(event)) {
            throw new EventFullException();
        }

        Subscription subscription = new Subscription(event, participantEmail);
        subscriptionRepository.save(subscription);

        event.setRegisteredParticipants(event.getRegisteredParticipants() + 1);

        EmailRequestDTO emailRequest = new EmailRequestDTO(participantEmail, "Confirmação de Inscrição", "Você foi inscrito no evento com sucesso");

        emailServiceClient.sendEmail(emailRequest);
    }
}
