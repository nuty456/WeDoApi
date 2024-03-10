package cz.wedo.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Doplnkove sluzby.
 */
@Data
public class AdditionalService {
  /**
   * Hodnota dobírky v CZK.
   */
  @Expose
  @SerializedName("cash_on_delivery")
  private Double cashOnDelivery;

  /**
   * Požadavek na službu „zpětná zásilka“.
   */
  @Expose
  @SerializedName("reverse_order")
  private Boolean reverseOrder;

  /**
   * Požadavek na službu „přímá zásilka“.
   */
  @Expose
  @SerializedName("extedirect_orderrnal_id")
  private Boolean directOrder;

  /**
   * Požadavek na službu „výměnná zásilka“.
   */
  @Expose
  @SerializedName("exchange_order")
  private Boolean exchangeOrder;

  /**
   * Poznámka ke službě výměnná zásilka.
   */
  @Size(max = 12555)
  @Expose
  @SerializedName("exchange_order_note")
  private String exchangeOrderNote;

  /**
   * Požadavek na službu „dokumenty zpět“.
   */
  @Expose
  @SerializedName("document_back")
  private Boolean documentBack;

  /**
   * Poznámka ke službě „dokumenty zpět“.
   */
  @Size(max = 255)
  @Expose
  @SerializedName("dokument_back_note")
  private String dokumentBackNote;

  /**
   * Požadavek na službu „připojištění“.
   */
  @Expose
  @SerializedName("insurance")
  private Boolean insurance;

  /**
   * Požadavek na službu „telefonické potvrzení doručení zásilky“.
   */
  @Expose
  @SerializedName("phone_notification")
  private Boolean phoneNotification;

  /**
   * Telefonní číslo pro „telefonické potvrzení doručení zásilky“ příjemci v mezinárodním formátu (např. 00420123456789).
   */
  @Size(max = 15)
  @Expose
  @SerializedName("phone_notification_number")
  private String phoneNotificationNumber;

  /**
   * Telefonní číslo pro „telefonické potvrzení vyzvednutí zásilky u odesílatele“ v mezinárodním formátu (např. 00420123456789); využívá se pouze v případě přímé zásilky (direct_order).
   */
  @Size(max = 15)
  @Expose
  @SerializedName("phone_notification_number_direct")
  private String phoneNotificationNumberDirect;

  /**
   * Poznámka ke službě „telefonické potvrzení doručení zásilky“.
   */
  @Size(max = 255)
  @Expose
  @SerializedName("phone_notification_note")
  private String phoneNotificationNote;

  /**
   * Požadavek na službu „SMS potvrzení doručení zásilky“.
   */
  @Expose
  @SerializedName("sms_notification")
  private Boolean smsNotification;

  /**
   * Telefonní číslo pro „SMS potvrzení doručení zásilky“ příjemci v mezinárodním formátu (např. 00420123456789).
   */
  @Size(max = 15)
  @Expose
  @SerializedName("sms_notification_number")
  private String smsNotificationNumber;

  /**
   * Telefonní číslo pro „SMS potvrzení vyzvednutí zásilky u odesílatele“ v mezinárodním formátu (např. 00420123456789); využívá se pouze v případě přímé zásilky (direct_order).
   */
  @Size(max = 15)
  @Expose
  @SerializedName("sms_notification_number_direct")
  private String smsNotificationNumberDirect;

  /**
   * Požadavek na službu „emailové potvrzení doručení zásilky“.
   */
  @Expose
  @SerializedName("email_notification")
  private Boolean emailNotification;

  /**
   * Adresa pro „emailové potvrzení doručení zásilky“ příjemci.
   */
  @Size(max = 50)
  @Expose
  @SerializedName("email_notification_address")
  private String emailNotificationAddress;

  /**
   * Adresa pro „emailové potvrzení vyzvednutí zásilky u odesílatele“; využívá se pouze v případě přímé zásilky (direct_order).
   */
  @Size(max = 50)
  @Expose
  @SerializedName("email_notification_address_direct")
  private String emailNotificationAddressDirect;

  /**
   * Požadavek na službu „donos“.
   */
  @Expose
  @SerializedName("carry")
  private Boolean carry;

  /**
   * Požadavek na službu odnos.
   */
  @Expose
  @SerializedName("loss")
  private Boolean loss;

  /**
   * Požadavek na službu „dopravu platí příjemce“.
   */
  @Expose
  @SerializedName("pay_by_receiver")
  private Boolean payByReceiver;

  /**
   * Požadavek na službu „ověření totožnosti příjemce“.
   */
  @Expose
  @SerializedName("authentication")
  private Boolean authentication;

  /**
   * Poznámka ke službě „ověření totožnosti příjemce“.
   */
  @Size(max = 255)
  @Expose
  @SerializedName("authentication_note")
  private String authenticationNote;

  /**
   * Požadavek na službu „osobní odběr“.
   */
  @Expose
  @SerializedName("takeover")
  private Boolean takeover;

  /**
   * Identifikátor požadovaného místa pro osobní odběr.
   */
  @Expose
  @SerializedName("takeover_place")
  private Integer takeoverPlace;
}
