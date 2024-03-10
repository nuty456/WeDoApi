package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;

/**
 * Batch detail response.
 */
@Data
public class BatchDetailResponse {
	/**
	 * Transport date.
	 */
	@Expose
	@SerializedName("transport_date")
	private Date transportDate;

	/**
	 * Sender contact.
	 */
	@Expose
	@SerializedName("sender_contact")
	private SenderContactResponse senderContact;

	/**
	 * List of packages.
	 */
	@Expose
	@SerializedName("packages")
	private ArrayList<String> packages = new ArrayList<>();

	/**
	 * Batch.
	 */
	@Expose
	@SerializedName("labels_pdf")
	@Size(max = 255)
	private String labelsPdf;
}
