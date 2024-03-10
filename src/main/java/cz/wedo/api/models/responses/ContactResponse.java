package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Contact response.
 */
@Data
public class ContactResponse {
	/**
	 * Name.
	 */
	@Expose
	@SerializedName("name")
	@NotNull
	private String name;

	/**
	 * Phone.
	 */
	@Expose
	@SerializedName("phone")
	@NotNull
	private String phone;

	/**
	 * Mobile.
	 */
	@Expose
	@SerializedName("mobile")
	@NotNull
	private String mobile;

	/**
	 * Email.
	 */
	@Expose
	@SerializedName("email")
	@NotNull
	private String email;
}