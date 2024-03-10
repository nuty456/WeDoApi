package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Adresa.
 */
@Data
public class AddressResponse {
  /**
   * Ulice.
   */
  @Size(max = 100)
  @Expose
  @SerializedName("street")
  @NotNull
  private String street;

  /**
   * Císlo popisné.
   */
  @Size(max = 100)
  @Expose
  @SerializedName("building_number")
  @NotNull
  private String buildingNumber;

  /**
   * Město.
   */
  @Size(max = 50)
  @Expose
  @SerializedName("city")
  @NotNull
  private String city;

  /**
   * Kód země dle ISO 3166-1; obchodní oddělení WEDO dodá seznam možných zemí.
   */
  @Size(max = 2)
  @Expose
  @SerializedName("country_code")
  @NotNull
  private String countryCode;

  /**
   * Phone.
   */
  @Expose
  @SerializedName("phone")
  @NotNull
  private String phone;

  /**
   * Email.
   */
  @Expose
  @SerializedName("email")
  @NotNull
  private String email;

}
