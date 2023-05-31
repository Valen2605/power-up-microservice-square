package com.pragma.powerup.squaremicroservice.configuration;

public class Constants {


    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final Long ADMIN_ROLE_ID = 1L;
    public static final Long OWNER_ROLE_ID = 2L;
    public static final Long EMPLOYEE_ROLE_ID = 3L;
    public static final Long CLIENT_ROLE_ID = 4L;
    public static final int MAX_PAGE_SIZE = 2;
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "No restaurant found with the id provided";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "No category found with the id provided";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant has been successfully created";
    public static final String DISH_CREATED_MESSAGE = "Dish has been successfully created";
    public static final String DISH_UPDATED_MESSAGE = "Dish has been successfully updated";
    public static final String DISH_NOT_FOUND_MESSAGE = "The dish you want to modify does not exist";
    public static final String CATEGORY_CREATED_MESSAGE = "Category has been successfully created";
    public static final String RESTAURANT_ALREADY_EXISTS_MESSAGE = "There is already a restaurant associated with this identification number";
    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "There is already a category associated with this name";
    public static final String DISH_ALREADY_EXISTS_MESSAGE = "This dish already exists for this restaurant";
    public static final String USER_NOT_A_OWNER_MESSAGE = "The user is not an owner";

    public static final String INCORRECT_NUMBER_LENGHT = "The number of charact cannot be empty or exceed the number of characters";

    public static final String NOT_RESTAURANT_OWNER_MESSAGE = "The user is not the owner of the restaurant";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
}
