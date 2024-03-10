package cz.wedo.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class represents the receiver of a package.
 */
@Data
public class Receiver {
	/**
	 * Identifikátor adresy z Aplikace Zásilky.
	 */
	@Expose
	@SerializedName("external_id")
	@Size(max = 255)
	private String externalId;

	/**
	 * Označení příjemce zásilky.
	 */
	@Expose
	@SerializedName("name")
	@Size(max = 100)
	@NotNull
	private String name;

	/**
	 * Ulice + číslo popisné.
	 */
	@Expose
	@SerializedName("street")
	@Size(max = 100)
	@NotNull
	private String street;

	/**
	 * Město.
	 */
	@Expose
	@SerializedName("city")
	@Size(max = 50)
	@NotNull
	private String city;

	/**
	 * PSČ.
	 */
	@Expose
	@SerializedName("postal_code")
	@Size(max = 5)
	@NotNull
	private String postalCode;

	/**
	 * Kód země dle ISO 3166-1; obchodní oddělení WEDO dodá seznam možných zemí.
	 */
	@Expose
	@SerializedName("state")
	@Size(max = 2)
	@NotNull
	private String state;

	/**
	 * Křestní jméno.
	 */
	@Expose
	@SerializedName("firstname")
	@Size(max = 30)
	private String firstname;

	/**
	 * Příjmení.
	 */
	@Expose
	@SerializedName("surname")
	@Size(max = 30)
	private String surname;

	/**
	 * Email.
	 */
	@Expose
	@SerializedName("email")
	@Size(max = 50)
	private String email;

	/**
	 * Telefon.
	 */
	@Expose
	@SerializedName("phone")
	@Size(max = 15)
	private String phone;

	/**
	 * Mobilní telefon.
	 */
	@Expose
	@SerializedName("mobile")
	@Size(max = 15)
	private String mobile;

	/**
	 * Fax.
	 */
	@Expose
	@SerializedName("fax")
	@Size(max = 15)
	private String fax;

	/**
	 * Poznamka.
	 */
	@Expose
	@SerializedName("note")
	@Size(max = 75)
	private String note;
}
