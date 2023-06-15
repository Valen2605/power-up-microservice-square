package com.pragma.powerup.squaremicroservice.adapters.driving.http.controller;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.OrderDishRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.OrderUpdateRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IOrderDishHandler;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.squaremicroservice.configuration.Constants;
import com.pragma.powerup.squaremicroservice.domain.utility.StatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class OrderRestController {

    private final IOrderHandler orderHandler;
    private final IOrderDishHandler orderDishHandler;

    @Operation(summary = "Add a new order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Order already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PostMapping("/createOrder")
    public ResponseEntity<Map<String, String>> saveOrder(@Valid Long idRestaurant){
        orderHandler.saveOrder(idRestaurant);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_CREATED_MESSAGE));
    }

    @Operation(summary = "Add a dish to the order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "OrderDish created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "OrderDish already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PostMapping("/orderDish")
    public ResponseEntity<Map<String, String>> saveOrderDish(@RequestBody OrderDishRequestDto orderDishRequestDto){
        orderDishHandler.saveOrderDish(orderDishRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDERDISH_CREATED_MESSAGE));
    }


    @Operation(summary = "Get Orders",
            responses = {
                    @ApiResponse(responseCode = "201", description = " All Orders",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Restaurant not found ",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@RequestParam StatusEnum status , @RequestParam Long idRestaurant, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(orderHandler.getOrders(status.toString(),idRestaurant,page, size));
    }

    @Operation(summary = "Assign an order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dish Updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishUpdateRequestDto.class))),
                    @ApiResponse(responseCode = "404", description = "Order not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PatchMapping("/assignOrder/{id}")
    public ResponseEntity<Map<String, String>> assignOrder(@PathVariable Long id, @Schema(implementation = OrderUpdateRequestDto.class) @Valid @RequestBody OrderUpdateRequestDto orderUpdateRequestDto) {
        orderHandler.assignOrder(id, orderUpdateRequestDto);
        return ResponseEntity.ok()
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_UPDATED_MESSAGE));
    }



}
