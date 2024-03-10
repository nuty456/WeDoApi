package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Package state response.
 */
@Data
public class PkgResponseState {
	/**
	 * ID stavu.
	 */
	@Expose
	@SerializedName("id")
	private Integer id;

	/**
	 * Code.
	 */
	@Expose
	@SerializedName("code")
	@Size(max = 255)
	private String code;

	/**
	 * Created.
	 */
	@Expose
	@SerializedName("created")
	private Date created;
}
