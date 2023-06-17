package com.pragma.powerup.squaremicroservice.domain.spi;

public interface IMessagingTwilioHttpAdapterPersistencePort {

    void getMessaging(String message, String number);
}
