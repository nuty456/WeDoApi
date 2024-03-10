package cz.wedo.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The Sender class represents the sender information for a package.
 */
@Data
public class Sender {
  /**
   * Identifikátor adresy z Aplikace Zásilky.
   */
  @Size(max = 255)
  @Expose
  @SerializedName("external_id")
  private String externalId;

  /**
   * Označení příjemce zásilky.
   */
  @Size(max = 100)
  @Expose
  @SerializedName("name")
  @NotNull
  private String name;

  /**
   * Ulice + číslo popisné.
   */
  @Size(max = 100)
  @Expose
  @SerializedName("street")
  @NotNull
  private String street;

  /**
   * Město.
   */
  @Size(max = 50)
  @Expose
  @SerializedName("city")
  @NotNull
  private String city;

  /**
   * PSČ.
   */
  @Size(max = 5)
  @Expose
  @SerializedName("postal_code")
  @NotNull
  private String postalCode;

  /**
   * Kód země dle ISO 3166-1; obchodní oddělení WEDO dodá seznam možných zemí.
   */
  @Size(max = 2)
  @Expose
  @SerializedName("state")
  @NotNull
  private String state;

  /**
   * Křestní jméno.
   */
  @Size(max = 30)
  @Expose
  @SerializedName("firstname")
  private String firstname;

  /**
   * Příjmení.
   */
  @Size(max = 30)
  @Expose
  @SerializedName("surname")
  private String surname;

  /**
   * Email.
   */
  @Size(max = 50)
  @Expose
  @SerializedName("email")
  private String email;

  /**
   * Telefon.
   */
  @Size(max = 15)
  @Expose
  @SerializedName("phone")
  private String phone;

  /**
   * Mobilní telefon.
   */
  @Size(max = 15)
  @Expose
  @SerializedName("mobile")
  private String mobile;

  /**
   * Fax.
   */
  @Size(max = 15)
  @Expose
  @SerializedName("fax")
  private String fax;

  /**
   * Poznámka.
   */
  @Size(max = 75)
  @Expose
  @SerializedName("note")
  private String note;

}
