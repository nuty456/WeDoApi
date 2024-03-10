package cz.wedo.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Balik / zasilka.
 */
@Slf4j
@Data
public class Pkg {
	/**
	 * Prijemce.
	 */
	@Expose
	@SerializedName("receiver")
	private Receiver receiver = new Receiver();

	/**
	 * Odesilatel.
	 */
	@Expose
	@SerializedName("sender")
	private Sender sender = new Sender();

	/**
	 * Doplnkove sluzby.
	 */
	@Expose
	@SerializedName("additional_service")
	private AdditionalService additionalService = new AdditionalService();

	/**
	 * referenční číslo/ID zásilky.
	 */
	@Expose
	@SerializedName("reference_number")
	@Size(max = 20)
	private String referenceNumber;

	/**
	 * druhé referenční číslo/ID zásilky.
	 */
	@Expose
	@SerializedName("reference_number2")
	@Size(max = 50)
	private String referenceNumber2;

	/**
	 * počet balíků v zásilce
	 */
	@Expose
	@SerializedName("package_count")
	@NotNull
	private Integer packageCount;

	/**
	 * volitelné číslo balíku zákazníka; pokud je uvedeno, je nutné aby počet elementů odpovídal počtu balíku v elementu package_count.
	 */
	@Expose
	@SerializedName("package_number")
//	@Size(max = 50)
	private List<String> packageNumber;

	/**
	 * celková váha zásilky v kg.
	 */
	@Expose
	@SerializedName("weight")
	@NotNull
	private Double weight;

	/**
	 * celková volumetrická váha zásilky v kg.
	 */
	@Expose
	@SerializedName("volumetric_weight")
	private Double volumetricWeight;

	/**
	 * celková hodnota zásilky v CZK.
	 */
	@Expose
	@SerializedName("value")
	@NotNull
	private Double value;

	/**
	 * poznámka
	 */
	@Expose
	@SerializedName("comment")
	@Size(max = 255)
	private String comment;

	/**
	 * požadavek na umožnění dodatečného přidání/odebrání balíku do zásilky; pokud je uvedena hodnota Y, lze k této zásilce v budoucnu přidávat/odebírat balíky speciálním XML požadavkem.
	 */
	@Expose
	@SerializedName("additive")
	private Boolean additive;

	/**
	 * WEDO produkt, který má být použit při doručení zásilky; v případě, že element není vyplněn, je vybrán automaticky produkt odpovídající parametrům zásilky; seznam možných produktů lze vyžádat na obchodním oddělení WEDO.
	 */
	@Expose
	@SerializedName("product")
	@Size(max = 255)
	private String product;

	/**
	 * Kód výdejního místa; povinné pole, pokud je produktem doručení na výdejní místo.
	 */
	@Expose
	@SerializedName("pup_branch")
	@Size(max = 10)
	private String pupBranch;

	/**
	 * české mobilní telefon příjemce zásilky doručené na výdejní místo; povinné pole, pokud je produktem doručení na výdejní místo.
	 */
	@Expose
	@SerializedName("pup_contact")
	@Size(max = 15)
	private String pupContact;

	/**
	 * Kód boxu; povinné pole, pokud je produktem doručení do boxu.
	 */
	@Expose
	@SerializedName("box")
	@Size(max = 10)
	private String box;

	/**
	 * Mobilní telefon příjemce zásilky doručené na výdejní místo; povinné pole, pokud je produktem doručení do boxu.
	 */
	@Expose
	@SerializedName("box_contact")
	@Size(max = 15)
	private String boxContact;
}
