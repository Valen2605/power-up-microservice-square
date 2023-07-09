package com.pragma.powerup.squaremicroservice.adapters.driving.http.controller;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishOrderRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishOrdersRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.DishOrderResponseDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IDishOrderHandler;
import com.pragma.powerup.squaremicroservice.configuration.Constants;
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

import java.util.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class DishOrderRestController {

    private final IDishOrderHandler dishOrderHandler;

    @Operation(summary = "Add order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Order already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PostMapping("/addOrder")
    public ResponseEntity<Map<String, String>> saveDishOrder(@Valid @RequestBody DishOrderRequestDto dishOrderRequestDto){
        dishOrderHandler.saveDishOrder(dishOrderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_CREATED_MESSAGE));
    }


    @Operation(summary = "Add orders",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Order already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PostMapping("/addOrders")
    public ResponseEntity<Map<String, String>> saveDishOrders(@RequestBody DishOrdersRequestDto dishOrdersRequestDto){
        dishOrderHandler.saveDishOrders(dishOrdersRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_CREATED_MESSAGE));
    }


    @Operation(summary = "Get all dishOrders",
            responses = {
                    @ApiResponse(responseCode = "201", description = " All dishOrders",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Order not found ",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @GetMapping("/takeOrder")
    public ResponseEntity<List<DishOrderResponseDto>> getOrders() {

        return ResponseEntity.ok(dishOrderHandler.getOrders());
    }


    @Operation(summary = "Get all dishOrders",
            responses = {
                    @ApiResponse(responseCode = "201", description = " All dishOrders",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Order not found ",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @GetMapping("/pendingOrders")
    public ResponseEntity<List<DishOrderResponseDto>> pendingOrders() {

        return ResponseEntity.ok(dishOrderHandler.pendingOrders());
    }



}
