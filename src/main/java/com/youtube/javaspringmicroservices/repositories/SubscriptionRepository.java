package com.youtube.javaspringmicroservices.repositories;

import com.youtube.javaspringmicroservices.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
