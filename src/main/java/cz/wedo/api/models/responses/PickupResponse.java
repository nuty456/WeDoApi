package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Pickup response.
 */
@Data
public class PickupResponse {
  /**
   * Pickup address.
   */
  @Expose
  @SerializedName("pickup_address")
  private AddressResponse pickupAddress;

  /**
   * Id.
   */
  @Expose
  @SerializedName("id")
  @Size(max = 255)
  private String id;

  /**
   * Pickup type.
   */
  @Expose
  @SerializedName("pickup_type")
  @Size(max = 255)
  private String pickupType;

  /**
   * Pickup from.
   */
  @Expose
  @SerializedName("pickup_from")
  private String pickupFrom;

  /**
   * Pickup to.
   */
  @Expose
  @SerializedName("pickup_to")
  private String pickupTo;

  /**
   * Created.
   */
  @Expose
  @SerializedName("created")
  private Date created;

  /**
   * Is deletable.
   */
  @Expose
  @SerializedName("is_deletable")
  private boolean isDeletable;

}
