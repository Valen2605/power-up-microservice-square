package com.pragma.powerup.squaremicroservice.domain.usecase;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.squaremicroservice.domain.exceptions.*;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.configuration.security.Interceptor;
import com.pragma.powerup.squaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.model.Restaurant;


import com.pragma.powerup.squaremicroservice.domain.model.User;
import com.pragma.powerup.squaremicroservice.domain.spi.IClientHttpAdapterPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IMessagingTwilioHttpAdapterPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;


import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderRepository orderRepository;
    private final IEmployeeRepository employeeRepository;
    private final IClientHttpAdapterPersistencePort clientHttpAdapterPersistencePort;
    private final IMessagingTwilioHttpAdapterPersistencePort messagingTwilioHttpAdapterPersistencePort;



    public OrderUseCase(IOrderRepository orderRepository, IOrderPersistencePort orderPersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IEmployeeRepository employeeRepository, IClientHttpAdapterPersistencePort clientHttpAdapterPersistencePort, IMessagingTwilioHttpAdapterPersistencePort messagingTwilioHttpAdapterPersistencePort) {
        this.orderRepository = orderRepository;
        this.orderPersistencePort = orderPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.employeeRepository = employeeRepository;
        this.clientHttpAdapterPersistencePort = clientHttpAdapterPersistencePort;
        this.messagingTwilioHttpAdapterPersistencePort = messagingTwilioHttpAdapterPersistencePort;
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

        orderPersistencePort.updateOrderDelivered(id, status);
    }

    @Override
    public List<Order> getOrders(String status, Long idRestaurant, int page, int pageSize) {
        return orderPersistencePort.getOrders(status, idRestaurant, page, pageSize);
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
