package cz.wedo.api.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Sender contact response.
 */
@Slf4j
@Data
public class SenderContactResponse {
  /**
   * Depot email.
   */
  @Size(max = 255)
  @Expose
  @SerializedName("depot_email")
  private String depotEmail;

  /**
   * Depot name.
   */
  @Size(max = 255)
  @Expose
  @SerializedName("depot_name")
  private String depotName;

  /**
   * Označení příjemce zásilky.
   */
  @Size(max = 100)
  @Expose
  @SerializedName("name")
  @NotNull
  private String name;

  /**
   * Poznámka.
   */
  @Size(max = 75)
  @Expose
  @SerializedName("note")
  private String note;

  /**
   * Telefon.
   */
  @Size(max = 15)
  @Expose
  @SerializedName("phone")
  private String phone;

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
   * Customer name.
   */
  @Expose
  @SerializedName("customer_name")
  @NotNull
  private String customerName;

}
