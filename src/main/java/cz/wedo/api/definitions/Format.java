package cz.wedo.api.definitions;

import lombok.Getter;
import lombok.ToString;

/**
 * The Format class represents the different file formats that can be used.
 * It is an enumeration with two values: PDF and ZPL.
 * Each value has a corresponding label that represents the format.
 *
 * @see Format#PDF
 * @see Format#ZPL
 */
@Getter
@ToString
public enum Format {
  /**
   * PDF.
   */
  PDF("pdf"),

  /**
   * ZPL.
   */
  ZPL("zpl");

  public final String label;

  /**
   * Constructs a new Format object with the given label.
   *
   * @param label the label representing the format
   */
  Format(String label) {
    this.label = label;
  }

  /**
   * Retrieves the Format object corresponding to the given label.
   *
   * @param label the label representing the format
   * @return the Format object corresponding to the label, or null if no match is found
   */
  public static Format valueOfLabel(String label) {
    for (Format e : values()) {
      if (e.label.equals(label)) {
        return e;
      }
    }
    return null;
  }
}
