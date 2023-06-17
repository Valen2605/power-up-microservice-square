package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.domain.model.User;

public interface IClientHttpAdapterPersistencePort {

    User getClient(Long id);
}
