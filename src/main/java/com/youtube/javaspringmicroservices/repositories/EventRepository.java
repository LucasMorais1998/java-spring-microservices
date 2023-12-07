package com.youtube.javaspringmicroservices.repositories;

import com.youtube.javaspringmicroservices.domain.Event;
import feign.Param;
import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {
    @Query(value = "SELECT * FROM events e WHERE PARSE(e.date AS TIMESTAMP) > :currentDate", nativeQuery = true)
    List<Event> findUpcomingEvents(@Param("currentDate")LocalDateTime currentDate);

    @Nonnull
    Optional<Event> findById(@NonNull String id);
}
