package cz.wedo.api.definitions;

import lombok.extern.slf4j.Slf4j;

/**
 * Represents the type of an HTTP request.
 * This enum provides constants for commonly used HTTP request methods: GET, POST, PUT, DELETE.
 */
@Slf4j
public enum RequestType {
	/**
	 * HTTP GET.
	 */
	GET("get"),

	/**
	 * HTTP POST.
	 */
	POST("post"),

	/**
	 * HTTP PUT.
	 */
	PUT("put"),

	/**
	 * HTTP DELETE.
	 */
	DELETE("delete");

	public final String label;

	/**
	 * Represents the type of an HTTP request.
	 * This enum provides constants for commonly used HTTP request methods: GET, POST, PUT, DELETE.
	 */
	RequestType(String label) {
		this.label = label;
	}

	/**
	 * Returns the enum constant with the specified label.
	 *
	 * @param label the label of the enum constant to be returned
	 * @return the enum constant with the specified label, or {@code null} if no constant with the specified label is found
	 */
	public static RequestType valueOfLabel(String label) {
		for (RequestType e : values()) {
			if (e.label.equals(label)) {
				return e;
			}
		}
		return null;
	}

}
