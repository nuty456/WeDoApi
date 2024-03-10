package cz.wedo.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility class for creating Gson objects with different date formats.
 */
public class GsonUtils {

  /**
   * Returns a Gson object configured with a standard date format.
   * The date format is specified as "yyyy-MM-dd HH:mm:ss".
   *
   * @return a Gson object with standard date format
   */
  public static Gson getGsonStandardDate() {
    return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
  }

  /**
   * Returns a Gson object with a custom date format of "yyyy-MM-dd'T'HH:mm:ss".
   *
   * @return a Gson object with a custom date format.
   */
  public static Gson getGsonTDate() {
    return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
  }

}
