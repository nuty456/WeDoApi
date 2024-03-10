package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Batch.
 */
@Data
public class Batch {
	/**
	 * Protocol url.
	 */
	@Expose
	@SerializedName("protocol_url")
	@Size(max = 255)
	private String protocolUrl;

	/**
	 * Id.
	 */
	@Expose
	@SerializedName("id")
	private String id;

	/**
	 * Number.
	 */
	@Expose
	@SerializedName("number")
	@Size(max = 255)
	private String number;
}
