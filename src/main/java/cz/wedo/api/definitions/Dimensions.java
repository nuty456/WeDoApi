package cz.wedo.api.definitions;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Labels printing dimensions.
 */
@Slf4j
@Getter
@ToString
public enum Dimensions {
  /**
   * label.
   */
  LABEL("label"),

  /**
   * label A6.
   */
  LABELA6("labela6"),

  /**
   * a4_2x2.
   */
  A4_2X2("a4_2x2"),

  /**
   * a4.
   */
  A4("a4"),

  /**
   * a6.
   */
  A6("a6");

  public final String label;

  /**
   * Represents the dimensions for labels printing.
   *
   * @param label value
   */
  Dimensions(String label) {
    this.label = label;
  }

  /**
   * Retrieves the {@link Dimensions} object associated with the given label.
   *
   * @param label The label to search for. Cannot be null.
   * @return The {@link Dimensions} object associated with the label, or null if no match is found.
   */
  public static Dimensions valueOfLabel(String label) {
    for (Dimensions e : values()) {
      if (e.label.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
