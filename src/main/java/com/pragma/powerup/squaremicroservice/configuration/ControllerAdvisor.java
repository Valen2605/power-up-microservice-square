package com.pragma.powerup.squaremicroservice.configuration;

import com.pragma.powerup.squaremicroservice.adapters.driven.jpa.mysql.exceptions.*;
import com.pragma.powerup.squaremicroservice.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.pragma.powerup.squaremicroservice.configuration.Constants.*;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NO_DATA_FOUND_MESSAGE));
    }

    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(
            RestaurantAlreadyExistsException restaurantAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_ALREADY_EXISTS_MESSAGE));
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantNotFoundException(
            RestaurantNotFoundException restaurantNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmployeeAlreadyExistsException(
            EmployeeAlreadyExistsException employeeAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, EMPLOYEE_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(UserNotBeAOwnerException.class)
    public ResponseEntity<Map<String, String>> handleUserNotBeAOwnerException(
            UserNotBeAOwnerException userNotBeAOwnerException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_NOT_A_OWNER_MESSAGE));
    }

    @ExceptionHandler(UserNotBeAEmployeeException.class)
    public ResponseEntity<Map<String, String>> handleUserNotBeAEmployeeException(
            UserNotBeAEmployeeException userNotBeAEmployeeException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_NOT_A_EMPLOYEE_MESSAGE));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_NOT_FOUND));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCategoryAlreadyExistsException(
            CategoryAlreadyExistsException categoryAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, CATEGORY_ALREADY_EXISTS_MESSAGE));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(
            CategoryNotFoundException categoryNotFoundException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, CATEGORY_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(DishAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDishAlreadyExistsException(
            DishAlreadyExistsException dishAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_ALREADY_EXISTS_MESSAGE));
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDishNotFoundException(
            DishNotFoundException dishNotFoundException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(NoRestaurantOwnerException.class)
    public ResponseEntity<Map<String, String>> handleNoRestaurantOwnerException(
            NoRestaurantOwnerException noRestaurantOwnerException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NOT_RESTAURANT_OWNER_MESSAGE));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INCORRECT_NUMBER_LENGHT));
    }

    @ExceptionHandler(PageNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePageNotFoundException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PAGE_NOT_FOUND));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> IllegalArgumentException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INCORRECT_VALUE));
    }

    @ExceptionHandler(OrderInProcessException.class)
    public ResponseEntity<Map<String, String>> OrderInProcessException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_CREATED));
    }

    @ExceptionHandler(DishNotFoundInRestaurantException.class)
    public ResponseEntity<Map<String, String>> DishNotFoundInRestaurantException() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY,DISH_NOT_FOUND_IN_RESTAURANT_MESSAGE));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotFoundExceptionException(
            OrderNotFoundException orderNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(OrderIsNotPreparationException.class)
    public ResponseEntity<Map<String, String>> handleOrderIsNotPreparationException(
            OrderIsNotPreparationException orderIsNotPreparationException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_PREPARATION_MESSAGE));
    }

    @ExceptionHandler(OrderIsNotReadyException.class)
    public ResponseEntity<Map<String, String>> handleOrderIsNotReadyException(
            OrderIsNotReadyException orderIsNotReadyException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_READY_MESSAGE));
    }

    @ExceptionHandler(IncorrectCodeException.class)
    public ResponseEntity<Map<String, String>> handleIncorrectCodeException(
            IncorrectCodeException incorrectCodeException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INCORRECT_CODE_MESSAGE));
    }

    @ExceptionHandler(OrderAlreadyCancelledException.class)
    public ResponseEntity<Map<String, String>> handleOrderAlreadyCancelledException(
            OrderAlreadyCancelledException orderAlreadyCancelledException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_ALREADY_CANCELED_MESSAGE));
    }

    @ExceptionHandler(OrderNotCanceledException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotCanceledException(
            OrderNotCanceledException orderNotCanceledException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_CANCELED_MESSAGE));
    }

}
