package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Package response.
 */
@Data
public class PkgResponse {
  /**
   * Referenční číslo/ID zásilky.
   */
  @Expose
  @SerializedName("reference_number")
  @Size(max = 20)
  private String referenceNumber;

  /**
   * Code.
   */
  @Expose
  @SerializedName("code")
  @Size(max = 255)
  private String code;

  /**
   * Labels pdf.
   */
  @Expose
  @SerializedName("labels_pdf")
  private List<String> labelsPdf;

  /**
   * Sorting Code.
   */
  @Expose
  @SerializedName("sorting_code")
  @Size(max = 255)
  private String sortingCode;

  /**
   * Barcode.
   */
  @Expose
  @SerializedName("barcode")
  @Size(max = 255)
  private List<String> barcode;

  /**
   * Delivery price.
   */
  @Expose
  @SerializedName("delivery_price")
  @NotNull
  private Double deliveryPrice;

  /**
   * Order number.
   */
  @Expose
  @SerializedName("order_number")
  @Size(max = 255)
  private String orderNumber;

  /**
   * Product name.
   */
  @Expose
  @SerializedName("product_name")
  @Size(max = 255)
  private String productName;

  /**
   * Product code.
   */
  @Expose
  @SerializedName("product_code")
  @Size(max = 255)
  private String productCode;

  /**
   * Last state.
   */
  @Expose
  @SerializedName("last_state")
  private PkgResponseState lastState;
}
