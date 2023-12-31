package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.TraceabilityRequestDto;
import com.pragma.powerup.squaremicroservice.domain.exceptions.*;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;


import com.pragma.powerup.squaremicroservice.domain.model.User;
import com.pragma.powerup.squaremicroservice.domain.spi.*;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderRepository orderRepository;
    private final IEmployeeRepository employeeRepository;
    private final IClientHttpAdapterPersistencePort clientHttpAdapterPersistencePort;
    private final IMessagingTwilioHttpAdapterPersistencePort messagingTwilioHttpAdapterPersistencePort;

    private final ITraceabilityHttpAdapterPersistencePort traceabilityHttpAdapterPersistencePort;



    public OrderUseCase(IOrderRepository orderRepository, IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IEmployeeRepository employeeRepository, IClientHttpAdapterPersistencePort clientHttpAdapterPersistencePort, IMessagingTwilioHttpAdapterPersistencePort messagingTwilioHttpAdapterPersistencePort, ITraceabilityHttpAdapterPersistencePort traceabilityHttpAdapterPersistencePort) {
        this.orderRepository = orderRepository;
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.employeeRepository = employeeRepository;
        this.clientHttpAdapterPersistencePort = clientHttpAdapterPersistencePort;
        this.messagingTwilioHttpAdapterPersistencePort = messagingTwilioHttpAdapterPersistencePort;
        this.traceabilityHttpAdapterPersistencePort = traceabilityHttpAdapterPersistencePort;
    }


    @Override
    public void saveOrder(Long idRestaurant) {
        Restaurant restaurant = restaurantPersistencePort.findById(idRestaurant);
        Order order = new Order();
        order.setIdClient(Interceptor.getIdUser());
        order.setDateOrder(LocalDate.now());
        order.setRestaurant(restaurant);
        order.setStatus(StatusEnum.PENDIENTE.toString());
        orderPersistencePort.saveOrder(order);
    }

    @Override
    public void assignOrder(Long id, Order order) {

        if(!employeeRepository.existsByIdEmployee(order.getIdChef())){
            throw new UserNotBeAEmployeeException();
        }

        Optional<OrderEntity> idOrder = orderRepository.findByIdAndStatus(id, StatusEnum.PENDIENTE.toString());

        if(!orderRepository.findByIdAndStatus(id, StatusEnum.PENDIENTE.toString()).isPresent()){
            throw new OrderNotFoundException();
        }

        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();
        traceabilityRequestDto.setIdOrder(idOrder.get().getId().toString());
        traceabilityRequestDto.setIdClient(idOrder.get().getIdClient().toString());
        traceabilityRequestDto.setEmailClient(Interceptor.getEmailUser());
        traceabilityRequestDto.setDate(LocalDateTime.now());
        traceabilityRequestDto.setPreviousStatus(idOrder.get().getStatus());
        traceabilityRequestDto.setNewStatus(StatusEnum.EN_PREPARACION.toString());
        traceabilityRequestDto.setIdEmployee(Interceptor.getIdUser().toString());
        traceabilityRequestDto.setEmailEmployee(Interceptor.getEmailUser());
        traceabilityHttpAdapterPersistencePort.getTraceability(traceabilityRequestDto);

        orderPersistencePort.assignOrder(id, order);
    }

    @Override
    public void updateOrderReady(Long id, StatusEnum status) {

        String s = status.toString();
        OrderEntity orderEntityReady = orderRepository.findByIdAndStatus(id,s).orElseThrow(OrderNotFoundException::new);
        if(!orderEntityReady.getStatus().contains(StatusEnum.EN_PREPARACION.toString())) {
            throw new OrderIsNotPreparationException();
        }
        Long idClient = orderEntityReady.getIdClient();
        User user = clientHttpAdapterPersistencePort.getClient(idClient);
        String phoneNumber = user.getPhone();

        Integer lenght= 10;
        String str = randomCharacters(lenght);
        orderEntityReady.setCodeOrder(str);

        String message = "Estimado cliente su pedido está listo su código es " + str;
        messagingTwilioHttpAdapterPersistencePort.getMessaging(message, phoneNumber);

        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();
        traceabilityRequestDto.setIdOrder(orderEntityReady.getId().toString());
        traceabilityRequestDto.setIdClient(orderEntityReady.getIdClient().toString());
        traceabilityRequestDto.setEmailClient(Interceptor.getEmailUser());
        traceabilityRequestDto.setDate(LocalDateTime.now());
        traceabilityRequestDto.setPreviousStatus(orderEntityReady.getStatus());
        traceabilityRequestDto.setNewStatus(StatusEnum.LISTO.toString());
        traceabilityRequestDto.setIdEmployee(Interceptor.getIdUser().toString());
        traceabilityRequestDto.setEmailEmployee(Interceptor.getEmailUser());
        traceabilityHttpAdapterPersistencePort.getTraceability(traceabilityRequestDto);

        orderPersistencePort.updateOrderReady(id, status);
    }

    @Override
    public void updateOrderDelivered(Long id, StatusEnum status,String codeOrder) {
        String s = status.toString();
        OrderEntity orderEntityDelivered = orderRepository.findByIdAndStatus(id,s).orElseThrow(OrderNotFoundException::new);

        if(!orderEntityDelivered .getStatus().contains(StatusEnum.LISTO.toString())) {
            throw new OrderIsNotReadyException();
        }
        if(!orderEntityDelivered.getCodeOrder().equals(codeOrder)){
            throw new IncorrectCodeException();
        }

        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();
        traceabilityRequestDto.setIdOrder(orderEntityDelivered.getId().toString());
        traceabilityRequestDto.setIdClient(orderEntityDelivered.getIdClient().toString());
        traceabilityRequestDto.setEmailClient(Interceptor.getEmailUser());
        traceabilityRequestDto.setDate(LocalDateTime.now());
        traceabilityRequestDto.setPreviousStatus(orderEntityDelivered.getStatus());
        traceabilityRequestDto.setNewStatus(StatusEnum.ENTREGADO.toString());
        traceabilityRequestDto.setIdEmployee(Interceptor.getIdUser().toString());
        traceabilityRequestDto.setEmailEmployee(Interceptor.getEmailUser());
        traceabilityHttpAdapterPersistencePort.getTraceability(traceabilityRequestDto);

        orderPersistencePort.updateOrderDelivered(id, status);
    }

    @Override
    public List<Order> getOrders(String status, Long idRestaurant, int page, int pageSize) {
        return orderPersistencePort.getOrders(status, idRestaurant, page, pageSize);
    }

    @Override
    public void updateOrderCanceled(Long id, StatusEnum status) {
        String s = status.toString();
        OrderEntity orderEntityCanceled = orderRepository.findByIdAndStatus(id,s).orElseThrow(OrderNotFoundException::new);

        if(orderEntityCanceled.getStatus().contains(StatusEnum.EN_PREPARACION.toString())
            || orderEntityCanceled.getStatus().contains(StatusEnum.LISTO.toString())
            || orderEntityCanceled.getStatus().contains(StatusEnum.ENTREGADO.toString())){

            Long idClient = orderEntityCanceled.getIdClient();
            User user = clientHttpAdapterPersistencePort.getClient(idClient);
            String phoneNumber = user.getPhone();

            String message = "Lo sentimos, tu pedido ya está en preparación y no puede cancelarse";
            messagingTwilioHttpAdapterPersistencePort.getMessaging(message, phoneNumber);

            throw new OrderNotCanceledException();
        }

        if(orderEntityCanceled.getStatus().contains(StatusEnum.CANCELADO.toString())){
            throw new OrderAlreadyCancelledException();
        }

        TraceabilityRequestDto traceabilityRequestDto = new TraceabilityRequestDto();
        traceabilityRequestDto.setIdOrder(orderEntityCanceled.getId().toString());
        traceabilityRequestDto.setIdClient(orderEntityCanceled.getIdClient().toString());
        traceabilityRequestDto.setEmailClient(Interceptor.getEmailUser());
        traceabilityRequestDto.setDate(LocalDateTime.now());
        traceabilityRequestDto.setPreviousStatus(orderEntityCanceled.getStatus());
        traceabilityRequestDto.setNewStatus(StatusEnum.CANCELADO.toString());
        traceabilityRequestDto.setIdEmployee(Interceptor.getIdUser().toString());
        traceabilityRequestDto.setEmailEmployee(Interceptor.getEmailUser());
        traceabilityHttpAdapterPersistencePort.getTraceability(traceabilityRequestDto);

        orderPersistencePort.updateOrderCanceled(id, status);
    }

    public static String randomCharacters(Integer lenght) {

        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String str = "";

        for (int x = 0; x < lenght; x++) {
            Integer index = randomNumbers(0, letters.length() - 1);
            char character = letters.charAt(index);
            str += character;
        }
        return str;
    }

    public static int randomNumbers(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
