package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Pickup place response.
 */
@Data
public class PickupPlaceResponse {
	/**
	 * Wedo.
	 */
	@Expose
	@SerializedName("wedo")
	private WedoPickupPlaceResponse wedo = new WedoPickupPlaceResponse();

}
