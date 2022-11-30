package com.conal.exceptions

enum class ErrorCode {

    // RESOURCE
    ALREADY_EXISTS,                 // A resource or association specified to be created already exists
    DOES_NOT_EXIST,                 // An resource or association required does not exist
    NOT_UNIQUE,                     // If a uniqueness constraint applies to some resource and this has not been met
    RESOURCE_NOT_FOUND,             // A resource identified in the request cannot be found

    // CONFIG
    CONFIG_VERSION_MISMATCH,        // A mismatch in version config
    CONFIGURATION_NOT_SUPPORTED,    // A configuration was not supported

    // TIMEOUT
    INTERNAL_TIMEOUT,               // The system timed out internally

    // MARHSALLING
    MARSHALLING_ERROR,              // Error during marshalling
    UNMARSHALLING_ERROR,            // Error during unmarshalling

    // INPUT
    MISSING_MANDATORY_PARAMETER,    // Mandatory parameter was missing
    UNSUPPORTED_PARAMETER,          // Parameter was unsupported
    INVALID_INPUT_DATA,             // An input contained data that was invalid

    // AUTH
    AUTHENTICATION_FAILURE,         // User authentication failed
    AUTHORIZATION_FAILURE,          // User authorization failed
    FORBIDDEN,                      // User does not have necessary authorization to perform a request
    INVALID_USERNAME,               // Username was invalid
    INVALID_PASSWORD,               // Password was invalid
    USER_DISABLED,                  // User has been disabled

    // SYSTEM
    INTERNAL_SERVER_ERROR,          // General error code for internal errors.
    SERVICE_DOWN,                   // Indicates a operation could not be performed because a service is down.
    SERVICE_ERRORED,                // Indicates a operation could not be performed because a service is in an errored state.
    SYSTEM_ERROR,                   // Some system error occurred
    UNEXPECTED_ERROR,               // General error code when the error is unexpected / hasn't been explicitly mapped
}