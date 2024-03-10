package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Delete response.
 */
@Data
public class DeleteResponse {
	/**
	 * Code.
	 */
	@Expose
	@SerializedName("code")
	@Size(max = 255)
	private String code;

	/**
	 * Order number.
	 */
	@Expose
	@SerializedName("order_number")
	@Size(max = 255)
	private String orderNumber;

	/**
	 * Barcode.
	 */
	@Expose
	@SerializedName("barcode")
	private List<String> barcode;
}
