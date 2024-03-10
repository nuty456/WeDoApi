package cz.wedo.api;

import cz.wedo.api.definitions.API;
import cz.wedo.api.definitions.Dimensions;
import cz.wedo.api.definitions.Format;
import cz.wedo.api.models.*;
import cz.wedo.api.models.errors.ErrorMessageException;
import cz.wedo.api.models.responses.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestWeDoProd {
  private String apiUser = "";
  private String apiKey = "";
  private String customerId = "";

  public TestWeDoProd() {
    WeDoApi.Builder builder = new WeDoApi.Builder();
    builder.setApiUser(apiUser)
        .setApiKey(apiKey)
        .setApi(API.TESTV2)
        .setCustomerId(customerId)
        .build();
  }

  /**
   * Vrací seznam vlastních zásilek. V URL je možné použít filtrační parametry.
   */
//	@Test
  public void testPackageGet() {
    try {
      ArrayList<PkgList> packageList = WeDoApi.getInstance().getPackageList();
      packageList.forEach(p -> log.info(String.format("Pkg: %s", p)));
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Vrací seznam vlastních zásilek pro pickup point. V URL je možné použít filtrační parametry.
   */
//	@Test
  public void testPackageGetPP() {
    try {
      ArrayList<PkgList> packageList = WeDoApi.getInstance().getPackageList("N2079");
      packageList.forEach(p -> log.info(String.format("Pkg: %s", p)));
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Vytvoří novou zásilku. V těle požadavku je datová struktura.
   */
//	@Test
  public void testPackagePost() {
    Pkg pkg = new Pkg();

    Receiver r = new Receiver();
    r.setName("Test");
    r.setStreet("Testovaci 45");
    r.setCity("Praha");
    r.setPostalCode("11000");
    r.setState("CZ");
    r.setFirstname("Tester");
    r.setSurname("Testovic");
    r.setEmail("tester@testovic.net");
    r.setPhone("+420 111 222 333");
    r.setMobile(null);
    r.setFax(null);
    r.setNote("křehké!!");
    pkg.setReceiver(r);

    Sender s = new Sender();
    s.setName("Firma.cz");
    s.setStreet("Minská 48");
    s.setCity("Brno");
    s.setPostalCode("61600");
    s.setState("CZ");
    s.setFirstname(null);
    s.setSurname(null);
    s.setEmail("tester@testovic.cz");
    s.setPhone("+420 222 333 444");
    s.setMobile(null);
    s.setFax(null);
    s.setNote(null);
    pkg.setSender(s);

    AdditionalService as = new AdditionalService();
    as.setCashOnDelivery(126.0);
    pkg.setAdditionalService(as);

    pkg.setReferenceNumber("777888993");
    pkg.setReferenceNumber2("111222335");
    pkg.setPackageCount(1);
    pkg.setPackageNumber(null);
    pkg.setWeight(1.0);
    pkg.setVolumetricWeight(1.0);
    pkg.setValue(100.0);
    pkg.setComment("Nejaky komentar");
    pkg.setAdditive(false);
    pkg.setProduct("S-24-CZ");
    try {
      PkgResponse pr = WeDoApi.getInstance().createPackage(pkg, "N2079");
      log.info(String.format("Package response: %s", pr));
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Vrátí informace o existující zásilce.
   */
//	@Test
  public void testPackageGetOrderNumber() {
    try {
      String orderNumber = "46F00051075";
      PkgResponse pkgResponse = WeDoApi.getInstance().getPackageListByOrderNumber(orderNumber);
      log.info(String.format("Package response: %s", pkgResponse));
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Zruší zasilku, která ještě nebyla odeslána ke zpracování.
   */
//	@Test
  public void testPackageDeleteOrderNumber() {
    try {
      String orderNumber = "46F00000008";
      ArrayList<DeleteResponse> deleteResponses = WeDoApi.getInstance().deletePackageByOrderNumber(orderNumber);
      deleteResponses.forEach(dr -> log.info(String.format("Delete response: %s", dr)));
    } catch (ErrorMessageException e) {
      log.info(String.valueOf(e));
    }
  }

  /**
   * Získá štítky pro zásilku v zadaném formátu (pdf, zpl).
   */
//	 @Test
  public void testPackageGetOrderNumberIdLabels() {
    try {
      String id = "51225";
      WeDoApi.getInstance().savePdfLabelsForIdAs(Format.PDF, Dimensions.LABEL, id, "./data/id_labels.pdf");
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Získá štítky pro zásilku v zadaném formátu (pdf, zpl).
   */
//	@Test
  public void testPackageGetOrderNumberLabels() {
    try {
      String orderNumber = "46F00051924";
      WeDoApi.getInstance().savePdfLabelsForIdAs(Format.PDF, Dimensions.LABELA6, orderNumber, "./data/id_labels_v2_38.pdf");
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Vytvoří a odešle dávku zásilek ke zpracování.
   */
//	 @Test
  public void testPackagePostBatch() {
    try {
      Articles articles = new Articles(new ArrayList<>(List.of("46F00000009")));
      BatchResponse batch = WeDoApi.getInstance().createBatch(articles);
      log.info(String.format("Batch response: %s", batch));
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Vytvoří a odešle dávku zásilek ke zpracování.
   */
//	@Test
  public void testOrderPickup() {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      PickupResponse pickup = WeDoApi.getInstance().orderPickup(sdf.parse("2023-09-14"), "N14684");
      log.info(String.format("Pickup response: %s", pickup));
    } catch (ErrorMessageException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Získá detaily o odeslané dávce (seznam zásilek v dávce, odkazy na svozový protokol a štítky).
   */
//	 @Test
  public void testPackageGetBatchNumber() {
    try {
      String number = "IT-46F-20230412212520";
      BatchDetailResponse batchDetailResponse = WeDoApi.getInstance().getBatchByNumber(number);
      log.info(String.format("Batch detail response: %s", batchDetailResponse));
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Získá svozový protokol pro zadanou dávku jako PDF.
   */
  // @Test
  public void testPackageGetBatchNumberManifestPdf() {
    try {
      String number = "IT-46F-20230412212520";
      WeDoApi.getInstance().saveBatchManifestPdfByNumber(number, "./data/manifest.pdf");
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Získa štítky pro zadanou dávku jako PDF.
   */
  // @Test
  public void testPackageGetBatchNumberLabelsPdf() {
    try {
      String number = "IT-46F-20230412212520";
      WeDoApi.getInstance().saveBatchLabelsPdfByNumber(number, Dimensions.LABEL, "./data/labels.pdf");
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns customers pickup places.
   */
//	@Test
  public void testGetPickupPlaceList() {
    try {
      PickupPlaceResponse pickupPlaceResponse = WeDoApi.getInstance().getPickupPlaceList();
      log.info(String.format("PickupPlaceResponse: %s", pickupPlaceResponse));
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Returns customers distribution points places.
   * NOT FINISHED
   */
//	@Test
  public void testGetDistributionPointList() {
    try {
      PickupPlaceResponse pickupPlaceResponse = WeDoApi.getInstance().getDistributionPointList();
      log.info(String.format("PickupPlaceResponse: %s", pickupPlaceResponse));
    } catch (ErrorMessageException e) {
      throw new RuntimeException(e);
    }
  }
}
