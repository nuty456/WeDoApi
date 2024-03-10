package cz.wedo.api.models.errors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * Custom exception class for handling error messages.
 */
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class ErrorMessageException extends Exception implements Serializable {
	/**
	 * Message.
	 */
	@Expose
	@SerializedName("message")
	private String message;

	/**
	 * Detail.
	 */
	@Expose
	@SerializedName("detail")
	private List<String> detail;

	/**
	 * Status code.
	 */
	@Expose
	@SerializedName("status_code")
	private Integer statusCode;
}
