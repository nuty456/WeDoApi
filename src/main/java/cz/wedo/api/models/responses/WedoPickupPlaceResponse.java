package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;

/**
 * WeDo pickup place response.
 */
@Data
public class WedoPickupPlaceResponse {
	/**
	 * Pickup places.
	 */
	@Expose
	@SerializedName("pickup_places")
	private ArrayList<PickupPlaceItemResponse> pickupPlaces = new ArrayList<>();

}
