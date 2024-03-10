package cz.wedo.api;

import com.google.gson.reflect.TypeToken;
import cz.wedo.api.definitions.API;
import cz.wedo.api.definitions.Dimensions;
import cz.wedo.api.definitions.Format;
import cz.wedo.api.exceptions.BadRequestException;
import cz.wedo.api.exceptions.UnauthorizedException;
import cz.wedo.api.models.Articles;
import cz.wedo.api.models.Pkg;
import cz.wedo.api.models.errors.ErrorMessageException;
import cz.wedo.api.models.responses.*;
import cz.wedo.api.services.Requester;
import cz.wedo.api.utils.GsonUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

/**
 * This class represents an API client for interacting with the WeDoApi service.
 */
@Slf4j
@Setter
public class WeDoApi {
	/**
	 * Represents the username used for API authentication.
	 */
	private String apiUser;

	/**
	 * The API key used for authentication.
	 */
	private String apiKey;

	/**
	 * Represents the environment of the application.
	 */
	private API env = API.TESTV2;

	/**
	 * The customer ID is a unique identifier assigned to each customer.
	 */
	private String customerId = null;

	private static WeDoApi instance;

	public static synchronized WeDoApi getInstance() {
		if (instance == null) {
			instance = new WeDoApi();
		}
		return instance;
	}

	/**
	 * The Builder class is used to construct an instance of WeDoApi.
	 */
	public static class Builder {
		public Builder() {
			WeDoApi.getInstance().cleanDefault();
		}

		public Builder setApiUser(String apiUser) {
			WeDoApi.getInstance().setApiUser(apiUser);
			return this;
		}

		public Builder setApiKey(String apiKey) {
			WeDoApi.getInstance().setApiKey(apiKey);
			return this;
		}

		public Builder setApi(API env) {
			WeDoApi.getInstance().setEnv(env);
			return this;
		}

		public Builder setCustomerId(String customerId) {
			WeDoApi.getInstance().setCustomerId(customerId);
			return this;
		}

		public WeDoApi build() {
			return WeDoApi.getInstance();
		}
	}

	/**
	 * Sets the API version to API.V2 and clears the values of username, apiKey, eshopName, and serverCode.
	 */
	private void cleanDefault() {
		apiUser = null;
		apiKey = null;
		env = API.TESTV2;
		customerId = null;
	}

	/**
	 * Vrací seznam vlastních zásilek. V URL je možné použít filtrační parametry.
	 *
	 * @return An ArrayList of PkgList objects representing the packages.
	 * @throws ErrorMessageException If an error occurs while retrieving the package list.
	 */
	public ArrayList<PkgList> getPackageList() throws ErrorMessageException {
		try {
			Requester requester = new Requester<Pkg, ArrayList<PkgList>>(apiUser, apiKey, false);
      return (ArrayList<PkgList>) requester.callGet(env, "package", null, false, false, new TypeToken<ArrayList<PkgList>>() {}.getType(), GsonUtils.getGsonTDate(), customerId, null);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Vrací seznam vlastních zásilek pro pickup point. V URL je možné použít filtrační parametry.
	 *
	 * @param department the department to filter the packages by
	 * @return a list of package information
	 * @throws ErrorMessageException if there is an error retrieving the package list
	 */
	public ArrayList<PkgList> getPackageList(String department) throws ErrorMessageException {
		try {
			Requester requester = new Requester<Pkg, ArrayList<PkgList>>(apiUser, apiKey, false);
      return (ArrayList<PkgList>) requester.callGet(env, "package", null, false, false, new TypeToken<ArrayList<PkgList>>() {}.getType(), GsonUtils.getGsonTDate(), customerId, department);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Vytvoří novou zásilku. V těle požadavku je datová struktura.
	 *
	 * @param pkg The package to be created.
	 * @param department The department associated with the package.
	 * @return The response containing information about the created package.
	 * @throws ErrorMessageException If an error occurs while creating the package.
	 */
	public PkgResponse createPackage(Pkg pkg, String department) throws ErrorMessageException {
		try {
			Requester requester = new Requester<Pkg, ArrayList<PkgResponse>>(apiUser, apiKey, false);
      return (PkgResponse) requester.callPostObject(env, "package", pkg, false, false, new TypeToken<PkgResponse>() {}.getType(), customerId, department);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Zruší zasilku, která ještě nebyla odeslána ke zpracování.
	 *
	 * @param orderNumber the order number of the package to be deleted
	 * @return an ArrayList of DeleteResponse objects representing the result of the delete operation
	 * @throws ErrorMessageException if an error occurs during the delete operation
	 */
	public ArrayList<DeleteResponse> deletePackageByOrderNumber(String orderNumber) throws ErrorMessageException {
		try {
			Requester requester = new Requester<Pkg, ArrayList<DeleteResponse>>(apiUser, apiKey, false);
      return (ArrayList<DeleteResponse>) requester.callDelete(env, String.format("package/%s", orderNumber), null, false, false, new TypeToken<ArrayList<DeleteResponse>>() {}.getType(), customerId, null);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Vrátí informace o existující zásilce.
	 *
	 * @param orderNumber the order number to retrieve packages for.
	 * @return the response containing the list of packages.
	 * @throws ErrorMessageException if there is an error retrieving the packages.
	 */
	public PkgResponse getPackageListByOrderNumber(String orderNumber) throws ErrorMessageException {
		try {
			Requester requester = new Requester<Pkg, ArrayList<PkgResponse>>(apiUser, apiKey, false);
      return (PkgResponse) requester.callGet(env, String.format("package/%s", orderNumber), null, false, false, new TypeToken<PkgResponse>() {}.getType(), customerId, null);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Získá štítky pro zásilku v zadaném formátu (pdf, zpl).
	 * GET parametr skip_first určuje, kolik pozicí na archu se přeskočí.
	 * GET parametr format určuje velikost štítku pro formát PDF. Možné varianty jsou: label, a4_2x2, a4, a6.
	 * Limity:
	 * Štítky lze získat vždy pro celou zásilku. Nelze vrátit štítek pro konkrétní balík v zásilce. Lze max. určit pozici na archu štítků, které se na daném archu mají vynechat.
	 *
	 * @param format          The format of the label. If null, the default format is PDF.
	 * @param dimensions      The dimensions of the label. If null, the default dimensions are LABEL.
	 * @param id              The ID for which the labels should be saved.
	 * @param targetFilename  The filename for the saved labels.
	 * @return True if the labels were successfully saved, false otherwise.
	 * @throws ErrorMessageException  If there is an error while saving the labels.
	 */
	public boolean savePdfLabelsForIdAsV1(Format format, Dimensions dimensions, String id, String targetFilename) throws ErrorMessageException {
		try {
			format = Optional.ofNullable(format).orElse(Format.PDF);
			dimensions = Optional.ofNullable(dimensions).orElse(Dimensions.LABEL);
			Requester requester = new Requester(apiUser, apiKey, false);
			HashMap<Object, Object> data = new HashMap<>();
			data.put("format", dimensions.label); // label, a4_2x2, a4, a6.
			return requester.callGetSaveResponseToFile(env, String.format("package/%s/colli/labels.%s", id, format.label), data, false, false, targetFilename);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Získá štítky pro zásilku v zadaném formátu (pdf, zpl).
	 * GET parametr skip_first určuje, kolik pozicí na archu se přeskočí.
	 * GET parametr format určuje velikost štítku pro formát PDF. Možné varianty jsou: label, a4_2x2, a4, a6.
	 * Limity:
	 * Štítky lze získat vždy pro celou zásilku. Nelze vrátit štítek pro konkrétní balík v zásilce. Lze max. určit pozici na archu štítků, které se na daném archu mají vynechat.
	 *
	 * @param format          The format of the labels (PDF, A4_2x2, A4, A6). If null, PDF will be used.
	 * @param dimensions      The dimensions of the labels (LABEL, A4_2x2, A4, A6). If null, LABEL will be used.
	 * @param id              The ID of the package.
	 * @param targetFilename  The filename to save the PDF labels as.
	 * @return True if the PDF labels were saved successfully, false otherwise.
	 * @throws ErrorMessageException If there is an error while saving the PDF labels.
	 */
	public boolean savePdfLabelsForIdAs(Format format, Dimensions dimensions, String id, String targetFilename) throws ErrorMessageException {
		try {
			format = Optional.ofNullable(format).orElse(Format.PDF);
			dimensions = Optional.ofNullable(dimensions).orElse(Dimensions.LABEL);
			Requester requester = new Requester(apiUser, apiKey, false);
			HashMap<Object, Object> data = new HashMap<>();
			data.put("format", dimensions.label); // label, a4_2x2, a4, a6.
			// /package/{order_number}/labels.{return_type}
			return requester.callGetSaveResponseToFile(env, String.format("package/%s/labels.%s", id, format.label), data, false, false, targetFilename);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Vytvoří a odešle dávku zásilek ke zpracování.
	 *
	 * @param articles the articles to be included in the batch
	 * @return the response containing the result of the batch creation
	 * @throws ErrorMessageException if there is an error message returned by the API
	 */
	public BatchResponse createBatch(Articles articles) throws ErrorMessageException {
		try {
			Requester requester = new Requester<Articles, BatchResponse>(apiUser, apiKey, false);
      return (BatchResponse) requester.callPostObject(env, "batch", articles, false, false, new TypeToken<BatchResponse>() {}.getType(), customerId, null);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Orders pickup of packages by courier.
	 *
	 * @param date The pickup date in the format "yyyy-MM-dd".
	 * @param department The department for which the pickup is requested.
	 * @return The pickup response object that contains the details of the pickup request.
	 * @throws ErrorMessageException If there is an error in processing the pickup request.
	 */
	public PickupResponse orderPickup(Date date, String department) throws ErrorMessageException {
		try {
			Requester requester = new Requester<Articles, PickupResponse>(apiUser, apiKey, false);
			HashMap<Object, Object> data = new HashMap<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			data.put("pickup_date", sdf.format(date));
      return (PickupResponse) requester.callPostGsonTDate(env, "pickup", data, false, false, new TypeToken<PickupResponse>() {}.getType(), customerId, department);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Získá detaily o odeslané dávce (seznam zásilek v dávce, odkazy na svozový protokol a štítky).
	 *
	 * @param number  The batch number to retrieve the details for.
	 * @return The batch details response containing the batch information.
	 * @throws ErrorMessageException If an error occurs while retrieving the batch details.
	 */
	public BatchDetailResponse getBatchByNumber(String number) throws ErrorMessageException {
		try {
			Requester requester = new Requester<Pkg, BatchDetailResponse>(apiUser, apiKey, false);
      return (BatchDetailResponse) requester.callGet(env, String.format("batch/%s", number), null, false, false, new TypeToken<BatchDetailResponse>() {}.getType(), customerId, null);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Získá svozový protokol pro zadanou dávku jako PDF.
	 *
	 * @param number The batch number.
	 * @param targetFilename The name of the target file to save the manifest PDF.
	 * @return true if the batch manifest PDF file was saved successfully, false otherwise.
	 * @throws ErrorMessageException if there is an error message exception.
	 */
	public boolean saveBatchManifestPdfByNumber(String number, String targetFilename) throws ErrorMessageException {
		try {
			Requester requester = new Requester(apiUser, apiKey, false);
			return requester.callGetSaveResponseToFile(env, String.format("batch/%s/manifest.pdf", number), null, false, false, targetFilename);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Získa štítky pro zadanou dávku jako PDF.
	 *
	 * @param number The number of the batch for which the labels are to be saved as a PDF.
	 * @param dimensions The dimensions of the labels. If null, the default dimensions (Dimensions.LABEL) will be used.
	 * @param targetFilename The filename to save the PDF to.
	 * @return true if the PDF is successfully generated and saved, false otherwise.
	 * @throws ErrorMessageException if there is an error generating the PDF.
	 */
	public boolean saveBatchLabelsPdfByNumber(String number, Dimensions dimensions, String targetFilename) throws ErrorMessageException {
		try {
			if (dimensions == null) {
				dimensions = Dimensions.LABEL;
			}
			HashMap<Object, Object> data = new HashMap<>();
			data.put("format", dimensions.label); // label, a4_2x2, a4, a6.
			Requester requester = new Requester(apiUser, apiKey, false);
			return requester.callGetSaveResponseToFile(env, String.format("batch/%s/labels.pdf", number), data, false, false, targetFilename);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Returning json containing list of customer pickup places.
	 *
	 * @return The response object containing the pickup places.
	 * @throws ErrorMessageException If there is an error retrieving the pickup places.
	 */
	public PickupPlaceResponse getPickupPlaceList() throws ErrorMessageException {
		try {
			Requester requester = new Requester<Pkg, PickupPlaceResponse>(apiUser, apiKey, false);
      return (PickupPlaceResponse) requester.callGet(env, "pickup-place", null, false, false, new TypeToken<PickupPlaceResponse>() {}.getType(), GsonUtils.getGsonTDate(), customerId, null);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

	/**
	 * Returns all distribution points.
	 *
	 * @return The response containing the list of distribution points.
	 * @throws ErrorMessageException if there is an error while retrieving the distribution points.
	 */
	public PickupPlaceResponse getDistributionPointList() throws ErrorMessageException {
		try {
			Requester requester = new Requester<Pkg, PickupPlaceResponse>(apiUser, apiKey, false);
      return (PickupPlaceResponse) requester.callGet(env, "distribution-point", null, false, false, new TypeToken<PickupPlaceResponse>() {}.getType(), GsonUtils.getGsonTDate(), customerId, null);
		} catch (UnauthorizedException e) {
			log.error(String.format("Exception auth: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (BadRequestException e) {
			log.error(String.format("Exception bad request: %s", e.getMessage()), e);
			throw new RuntimeException(e);
		} catch (ErrorMessageException e) {
			log.error(String.format("Exception error message request: %s", e.getMessage()), e);
			throw e;
		}
	}

}
