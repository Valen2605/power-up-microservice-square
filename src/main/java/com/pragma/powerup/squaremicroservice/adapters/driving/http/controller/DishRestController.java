package com.pragma.powerup.squaremicroservice.adapters.driving.http.controller;


import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.request.DishUpdateRequestDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.squaremicroservice.adapters.driving.http.handlers.IDishHandler;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class DishRestController {

    private final IDishHandler dishHandler;

    @Operation(summary = "Add a new dish",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Dish created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Dish already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PostMapping("/createDish")
    public ResponseEntity<Map<String, String>> saveDish(@Valid @RequestBody DishRequestDto dishRequestDto){
        dishHandler.saveDish(dishRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_CREATED_MESSAGE));
    }

    @Operation(summary = "Update a dish",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dish Updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishUpdateRequestDto.class))),
                    @ApiResponse(responseCode = "404", description = "Dish not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PatchMapping("/updateDish/{id}")
    public ResponseEntity<Map<String, String>> updateDish(@PathVariable Long id, @Schema(implementation = DishUpdateRequestDto.class) @Valid @RequestBody DishUpdateRequestDto dishUpdateRequestDto) {
        dishHandler.updateDish(id, dishUpdateRequestDto);
        return ResponseEntity.ok()
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_UPDATED_MESSAGE));
    }

    @Operation(summary = "Update a dish",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Enable/Disable Dish",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DishUpdateRequestDto.class))),
                    @ApiResponse(responseCode = "404", description = "Dish not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @PatchMapping("/enableDisableDish/{id}")
    public ResponseEntity<Map<String, String>> enableDisableDish(@PathVariable Long id) {
        dishHandler.enableDisableDish(id);
        return ResponseEntity.ok()
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.DISH_UPDATED_MESSAGE));
    }

    @Operation(summary = "Get Dishes",
            responses = {
                    @ApiResponse(responseCode = "201", description = " All Dishes",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "Restaurant not found ",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
            })
    @GetMapping("/dishes")
    public ResponseEntity<List<DishResponseDto>> getDishes(@RequestParam Long idRestaurant, @RequestParam Long idCategory, @RequestParam int page, @RequestParam int size) {

        return ResponseEntity.ok(dishHandler.getDishes(idRestaurant, idCategory,page, size));
    }

}
