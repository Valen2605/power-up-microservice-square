package com.pragma.powerup.squaremicroservice.domain.spi;

import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.TraceabilityRequestDto;

public interface ITraceabilityHttpAdapterPersistencePort {

    void getTraceability(TraceabilityRequestDto traceabilityRequestDto);
}
