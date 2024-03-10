package cz.wedo.api.services;

import cz.wedo.api.exceptions.BadRequestException;
import cz.wedo.api.exceptions.UnauthorizedException;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Validator class is responsible for validating status codes and response messages.
 */
@Data
public class Validator {
  /**
   * Validates the status code and response message based on the given parameters.
   *
   * @param statusCode the status code to be validated
   * @param response   the response object to be checked for status and error messages
   * @throws UnauthorizedException if the status code is 401 or 403, indicating unauthorized access
   * @throws BadRequestException   if the status code is 400 or higher, indicating a bad request
   */
  public void validateStatus(int statusCode, HashMap<Object, Object> response) throws UnauthorizedException, BadRequestException {
    // unauthorized
    if (statusCode == 401 || statusCode == 403) {
      throw new UnauthorizedException(null, statusCode);
    }

    // request error
    if (statusCode >= 400) {
      StringBuilder statusMessage = new StringBuilder();
      if (response.containsKey("status_message")) {
        statusMessage = new StringBuilder((String) response.get("status_message"));
      } else if (response.containsKey("packages")) {
        final ArrayList<HashMap<Object, Object>> packages = (ArrayList<HashMap<Object, Object>>) response.get("packages");
        if (packages != null && !packages.isEmpty()) {
          for (HashMap<Object, Object> pkg : packages) {
            if (pkg.containsKey("errors")) {
              final ArrayList<HashMap<Object, Object>> errors = (ArrayList<HashMap<Object, Object>>) pkg.get("errors");
              for (HashMap<Object, Object> error : errors) {
                if (error.containsKey("attribute")) {
                  final String attribute = (String) error.get("attribute");
                  final String message = (String) error.get("message");
                  statusMessage.append(String.format("%s - %s", attribute, message)).append("||");
                }
              }
            }
          }
        }
      } else if (response.containsKey("errors")) {
        final ArrayList<HashMap<Object, Object>> errors = (ArrayList<HashMap<Object, Object>>) response.get("errors");
        if (errors != null && !errors.isEmpty()) {
          for (HashMap<Object, Object> error : errors) {
            if (error.containsKey("status_message")) {
              final String packageId = (String) error.get("package_id");
              final String message = (String) error.get("status_message");
              statusMessage.append(String.format("%s - %s", packageId, message)).append("||");
            }
          }
        }
      }
      final int code = response.get("status") != null ? (int) response.get("status") : statusCode;
      throw new BadRequestException(statusMessage.toString(), code);
    }
  }

  /**
   * Validates the response status based on the given parameters.
   *
   * @param responseItem    the response item to be validated
   * @param response        the response object to be checked for status
   * @param shouldHaveStatus a boolean flag indicating whether the response should have a status
   * @return true if the response status is valid, otherwise false
   * @throws UnauthorizedException  if the user is not authorized to perform the action
   * @throws BadRequestException    if a bad request is made
   */
  public boolean validateResponseStatus(
      HashMap<Object, Object> responseItem,
      HashMap<Object, Object> response,
      Boolean shouldHaveStatus
  ) throws UnauthorizedException, BadRequestException {
    if (!shouldHaveStatus && (responseItem != null && responseItem.containsKey("status") && !((boolean) responseItem.get("status")))) {
      return true;
    }
    // todo 500 ale musi se upravit to nad tim
    this.validateStatus(responseItem != null && responseItem.get("status") != null ? (int) responseItem.get("status") : 200, response != null ? response : responseItem);
    return true;
  }

  /**
   * Validates that each item in the given list has the specified attribute.
   * Throws a BadRequestException if an item is missing the attribute.
   *
   * @param items    the list of items to validate
   * @param attribute   the attribute to validate against
   * @param response   the response object, used for error handling
   * @throws BadRequestException if an item is missing the attribute
   */
  public void validateResponseItemHasAttribute(ArrayList<HashMap<Object, Object>> items, String attribute, HashMap<Object, Object> response) throws BadRequestException {
    for (HashMap<Object, Object> item : items) {
      if (!item.containsKey(attribute)) {
        throw new BadRequestException(response);
      }
    }
  }

}
