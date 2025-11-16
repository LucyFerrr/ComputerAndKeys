package org.ksa.constants;

/**
 * Constants for error message used throughout the application.
 * Centralizes error messages for easier maintenance.
 */
public class ErrorMessages {

    private ErrorMessages() {
    }

    // Computer-related errors
    public static final String COMPUTER_NOT_FOUND = "Computer not found";
    public static final String COMPUTER_NOT_FOUND_FOR_MAKER = "Maker '%s' not found";
    public static final String COMPUTER_NOT_FOUND_FOR_MAKER_AND_MODEL = "Computer not found for maker '%s' and model '%s'";
    public static final String COMPUTER_ALREADY_EXISTS = "Computer already exists";
    public static final String MODEL_PARAMETER_REQUIRED = "Model parameter required";

    // SSH Key-related errors
    public static final String SSH_KEY_NOT_FOUND = "SSH key not found";
    public static final String SSH_KEY_ALREADY_EXISTS = "SSH key already exists";
    public static final String SSH_KEY_INVALID_RSA = "The content of the public key is invalid for the type 'ssh-rsa'";
    public static final String SSH_KEY_INVALID_ED25519 = "The content of the public key is invalid for the type 'ed25519'";

    // Validation messages
    public static final String VALIDATION_TYPE_REQUIRED = "Type is required";
    public static final String VALIDATION_MAKER_REQUIRED = "Maker is required";
    public static final String VALIDATION_MODEL_REQUIRED = "Model is required";
    public static final String VALIDATION_SSH_KEY_TYPE_REQUIRED = "SSH key type is required";
    public static final String VALIDATION_PUBLIC_KEY_REQUIRED = "Public key is required";
}
