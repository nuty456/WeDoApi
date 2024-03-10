package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Package list.
 */
@Data
public class PkgList {
  /**
   * Referenční číslo/ID zásilky.
   */
  @Expose
  @SerializedName("reference_number")
  @Size(max = 20)
  private String referenceNumber;

  /**
   * Order number.
   */
  @Expose
  @SerializedName("order_number")
  @Size(max = 255)
  private String orderNumber;

  /**
   * Last change.
   */
  @Expose
  @SerializedName("last_change")
  private Date lastChange;

  /**
   * Delivered.
   */
  @Expose
  @SerializedName("delivered")
  @Size(max = 255)
  private String delivered;
}
