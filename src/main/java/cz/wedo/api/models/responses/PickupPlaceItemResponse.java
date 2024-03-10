package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Pickup place item response.
 */
@Data
public class PickupPlaceItemResponse {
	/**
	 * Name.
	 */
	@Expose
	@SerializedName("name")
	@Size(max = 255)
	private String name;

	/**
	 * Code.
	 */
	@Expose
	@SerializedName("code")
	@Size(max = 255)
	private String code;

	/**
	 * Type.
	 */
	@Expose
	@SerializedName("type")
	@Size(max = 255)
	private String type;

	/**
	 * Regular pickup.
	 */
	@Expose
	@SerializedName("regular_pickup")
	private boolean regularPickup;

	/**
	 * Address.
	 */
	@Expose
	@SerializedName("address")
	private AddressResponse address;

	/**
	 * Contact.
	 */
	@Expose
	@SerializedName("contact")
	private ContactResponse contact;
}