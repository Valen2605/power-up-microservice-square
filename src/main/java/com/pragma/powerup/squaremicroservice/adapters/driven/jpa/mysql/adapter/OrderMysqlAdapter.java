package com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderInProcessException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantNotFoundException;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.squaremicroservice.domain.model.Order;
import com.pragma.powerup.squaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class OrderMysqlAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public void saveOrder(Order order) {

        Long idRestaurant = order.getRestaurant().getId();

        if(!restaurantRepository.findById(idRestaurant).isPresent()){
            throw new RestaurantNotFoundException();
        }

        if(orderRepository.existsByIdClient(order.getIdClient())) {

            Optional<OrderEntity> orderEntity = orderRepository.findByIdClient(order.getIdClient());

           if(orderEntity.get().getStatus().equals(StatusEnum.ENTREGADO.name())
                || orderEntity.get().getStatus().equals(StatusEnum.CANCELADO.name())){
                 orderRepository.save(orderEntityMapper.toEntity(order));
            }
            if(orderEntity.get().getStatus().equals(StatusEnum.PENDIENTE.name())
                    || orderEntity.get().getStatus().equals(StatusEnum.EN_PREPARACION.name())
                    || orderEntity.get().getStatus().equals(StatusEnum.LISTO.name())){
                throw new OrderInProcessException();
            }

        }
           orderRepository.save(orderEntityMapper.toEntity(order));

    }

    @Override
    public void assignOrder(Long id, Order order) {
        OrderEntity orderEntityUpdate = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        orderEntityUpdate.setStatus(StatusEnum.EN_PREPARACION.name());
        orderEntityUpdate.setIdChef(order.getIdChef());
        orderRepository.save(orderEntityUpdate);
    }

    @Override
    public void updateOrderReady(Long id, StatusEnum status) {
        String s = status.toString();
        OrderEntity orderEntityReady = orderRepository.findByIdAndStatus(id,s).orElseThrow(OrderNotFoundException::new);
        orderEntityReady.setStatus(StatusEnum.LISTO.toString());
        orderRepository.save(orderEntityReady);
    }

    @Override
    public void updateOrderDelivered(Long id, StatusEnum status) {
        String s = status.toString();
        OrderEntity orderEntityDelivered = orderRepository.findByIdAndStatus(id,s).orElseThrow(OrderNotFoundException::new);
        orderEntityDelivered.setStatus(StatusEnum.ENTREGADO.toString());
        orderRepository.save(orderEntityDelivered);
    }

    @Override
    public void updateOrderCanceled(Long id, StatusEnum status) {
        String s = status.toString();
        OrderEntity orderEntityCanceled = orderRepository.findByIdAndStatus(id,s).orElseThrow(OrderNotFoundException::new);

        if(orderEntityCanceled.getStatus().contains(StatusEnum.PENDIENTE.toString())){
            orderEntityCanceled.setStatus(StatusEnum.CANCELADO.toString());
            orderRepository.save(orderEntityCanceled);
        }
    }

    @Override
    public List<Order> getOrders(String status, Long idRestaurant, int page, int pageSize) {

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by("status").ascending());
        Page<OrderEntity> orderPage;
        if(idRestaurant == 0L) {
            orderPage = orderRepository.findAllByStatusAndRestaurantEntityId(status,idRestaurant,pageRequest);
            return orderEntityMapper.toOrderList(orderPage.getContent());
        }
        if(idRestaurant > 0L){
            orderPage = orderRepository.findAllByStatusAndRestaurantEntityId(status,idRestaurant,pageRequest);
            return orderEntityMapper.toOrderList(orderPage.getContent());
        }
        throw new OrderNotFoundException();
    }
}

