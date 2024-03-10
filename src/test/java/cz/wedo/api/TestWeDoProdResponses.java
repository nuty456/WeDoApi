package cz.wedo.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import cz.wedo.api.models.responses.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

@Slf4j
public class TestWeDoProdResponses {

  public TestWeDoProdResponses() {
  }

  @Test
  public void testPostPackageResponseV2() {
    final String response = "{\n" +
        "  \"order_number\": \"46F00000012\",\n" +
        "  \"reference_number\": \"777888999\",\n" +
        "  \"barcode\": [\n" +
        "    \"46FS00000012*001001\"\n" +
        "  ],\n" +
        "  \"sorting_code\": \"S1PR053\",\n" +
        "  \"product_name\": \"Medium Colli 24-CZ - hmotnost do 30 kg\",\n" +
        "  \"delivery_price\": 0,\n" +
        "  \"product_code\": \"M-24-CZ\"\n" +
        "}\n\n";
    Gson gson = new GsonBuilder().create();
    PkgResponse pkgResponse = gson.fromJson(response, PkgResponse.class);
    log.info(String.format("Package response: %s", pkgResponse));
    Assert.assertEquals("S1PR053", pkgResponse.getSortingCode());
  }

  @Test
  public void testGetOrderNumberPackageResponseV2() {
    final String response = "{\n" +
        "  \"sorting_code\": \"S550400\",\n" +
        "  \"barcode\": [\n" +
        "    \"46FO00051075*001000\"\n" +
        "  ],\n" +
        "  \"product_code\": \"M-24-CZ\",\n" +
        "  \"labels_pdf\": [\n" +
        "    \"https://api.wedo.cz/its-rapi/v2/v2/its-rapi/v2/package/46F00051075/46FO00051075%2A001000/label.pdf\"\n" +
        "  ],\n" +
        "  \"order_number\": \"46F00051075\",\n" +
        "  \"product_name\": \"Medium Colli 24-CZ - hmotnost do 30 kg\",\n" +
        "  \"reference_number\": \"7169582\",\n" +
        "  \"delivery_price\": null,\n" +
        "  \"last_state\": {\n" +
        "    \"id\": 99983622,\n" +
        "    \"code\": \"DELIVERED\",\n" +
        "    \"created\": \"2023-08-28 13:47:34\"\n" +
        "  }\n" +
        "}\n\n";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    PkgResponse pkgResponse = gson.fromJson(response, PkgResponse.class);
    log.info(String.valueOf(pkgResponse));
    Assert.assertEquals("M-24-CZ", pkgResponse.getProductCode());
  }

  @Test
  public void testPackageDeleteOrderNumberResponse() {
    final String response = "[\n" +
        "    {\n" +
        "        \"code\": \"0\", \n" +
        "        \"order_number\": \"46F00000007\", \n" +
        "        \"barcode\": []\n" +
        "    }\n" +
        "]";
    Gson gson = new GsonBuilder().create();
    ArrayList<DeleteResponse> deleteResponse = gson.fromJson(response, new TypeToken<ArrayList<DeleteResponse>>() {
    }.getType());
    log.info(String.valueOf(deleteResponse));
    Assert.assertEquals("46F00000007", deleteResponse.get(0).getOrderNumber());
  }

  @Test
  public void testPackagePostBatchResponse() {
    final String response = "{\n" +
        "    \"articles\": [\n" +
        "        {\n" +
        "            \"code\": \"0\", \n" +
        "            \"order_number\": \"46F00000001\"\n" +
        "        }\n" +
        "    ], \n" +
        "    \"batch\": {\n" +
        "        \"protocol_url\": \"http://zasilky3.intime.cz/test/protocol/20230412MYZUkf.html\", \n" +
        "        \"id\": \"51225\", \n" +
        "        \"number\": \"IT-46F-20230412212520\"\n" +
        "    }\n" +
        "}";
    Gson gson = new GsonBuilder().create();
    BatchResponse batchResponse = gson.fromJson(response, BatchResponse.class);
    log.info(String.valueOf(batchResponse));
    Assert.assertEquals("46F00000001", batchResponse.getArticles().get(0).getOrderNumber());
    Assert.assertEquals("51225", batchResponse.getBatch().getId());
  }

  @Test
  public void testPackageOrderPickupResponse() {
    final String response = "{\n" +
        "  \"pickup_address\": {\n" +
        "    \"street\": \"Testovaci\",\n" +
        "    \"building_number\": \"493/81\",\n" +
        "    \"city\": \"Brno jih\",\n" +
        "    \"postal_code\": \"61900\",\n" +
        "    \"country_code\": \"CZ\"\n" +
        "  },\n" +
        "  \"id\": \"I9999\",\n" +
        "  \"pickup_type\": \"IRREGULAR\",\n" +
        "  \"pickup_from\": \"14:24:35\",\n" +
        "  \"pickup_to\": null,\n" +
        "  \"created\": \"2023-08-07T14:24:35\",\n" +
        "  \"is_deletable\": true\n" +
        "}";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    PickupResponse pickupResponse = gson.fromJson(response, PickupResponse.class);
    log.info(String.valueOf(pickupResponse));
    Assert.assertEquals("Brno jih", pickupResponse.getPickupAddress().getCity());
  }

  @Test
  public void testPackageGetBatchNumberResponse() {
    final String response = "{\n" +
        "    \"transport_date\": \"2023-04-13 17:00:00\", \n" +
        "    \"sender_contact\": {\n" +
        "        \"city\": \"Brno\", \n" +
        "        \"depot_email\": \"dispecer.brno@intime.cz\", \n" +
        "        \"name\": \"Nazev.cz\", \n" +
        "        \"depot_name\": \"Brno 06\", \n" +
        "        \"note\": null, \n" +
        "        \"phone\": \"\", \n" +
        "        \"street\": \"Testerska 48\", \n" +
        "        \"postal_code\": \"61600\", \n" +
        "        \"customer_name\": \"C s.r.o.\"\n" +
        "    }, \n" +
        "    \"packages\": [\n" +
        "        \"46F00000001\"\n" +
        "    ], \n" +
        "    \"labels_pdf\": \"https://bridge.intime.cz/api-test/v1/batch/IT-46F-20230412212520/8d385e7f89fe18f099807a831f604e6216735559b5a7c95c9223c0a4b492580f/labels.pdf\"\n" +
        "}";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    BatchDetailResponse batchDetailResponse = gson.fromJson(response, BatchDetailResponse.class);
    log.info(String.format("Batch detail response: %s", batchDetailResponse));
    Assert.assertEquals("Brno", batchDetailResponse.getSenderContact().getCity());
  }

  @Test
  public void testGetPickupPlaceListResponse() {
    final String response = "{\n" +
        "  \"wedo\": {\n" +
        "    \"pickup_places\": [\n" +
        "      {\n" +
        "        \"name\": \"Nazev.cz\",\n" +
        "        \"code\": \"N96726\",\n" +
        "        \"type\": \"COURIER\",\n" +
        "        \"regular_pickup\": true,\n" +
        "        \"contact\": {\n" +
        "          \"name\": \"karel@loprais.cz\",\n" +
        "          \"phone\": null,\n" +
        "          \"mobile\": \"+420607056512\",\n" +
        "          \"email\": \"karel@loprais.cz\"\n" +
        "        },\n" +
        "        \"address\": {\n" +
        "          \"street\": \"Sokolnicka\",\n" +
        "          \"city\": \"Praha\",\n" +
        "          \"postal_code\": \"18600\",\n" +
        "          \"building_number\": \"18\",\n" +
        "          \"country_code\": \"CZ\"\n" +
        "        }\n" +
        "      },\n" +
        "      {\n" +
        "        \"name\": \"Nazev.cz\",\n" +
        "        \"code\": \"N96727\",\n" +
        "        \"type\": \"COURIER\",\n" +
        "        \"regular_pickup\": true,\n" +
        "        \"contact\": {\n" +
        "          \"name\": \"karel@loprais.cz\",\n" +
        "          \"phone\": \"\",\n" +
        "          \"mobile\": \"+420111222333\",\n" +
        "          \"email\": \"karel@loprais.cz\"\n" +
        "        },\n" +
        "        \"address\": {\n" +
        "          \"street\": \"Testerska\",\n" +
        "          \"city\": \"Brno\",\n" +
        "          \"postal_code\": \"61600\",\n" +
        "          \"building_number\": \"48\",\n" +
        "          \"country_code\": \"CZ\"\n" +
        "        }\n" +
        "      },\n" +
        "      {\n" +
        "        \"name\": \"delikatesy.online\",\n" +
        "        \"code\": \"N96728\",\n" +
        "        \"type\": \"COURIER\",\n" +
        "        \"regular_pickup\": false,\n" +
        "        \"contact\": {\n" +
        "          \"name\": \"karel@loprais.cz\",\n" +
        "          \"phone\": null,\n" +
        "          \"mobile\": \"+420111222333\",\n" +
        "          \"email\": \"karel@loprais.cz\"\n" +
        "        },\n" +
        "        \"address\": {\n" +
        "          \"street\": \"Testovaci\",\n" +
        "          \"city\": \"Brno jih\",\n" +
        "          \"postal_code\": \"61900\",\n" +
        "          \"building_number\": \"493/81\",\n" +
        "          \"country_code\": \"CZ\"\n" +
        "        }\n" +
        "      }\n" +
        "    ]\n" +
        "  }\n" +
        "}";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    PickupPlaceResponse pickupPlaceResponse = gson.fromJson(response, PickupPlaceResponse.class);
    log.info(String.format("PickupPlaceResponse: %s", pickupPlaceResponse));
    Assert.assertEquals("N96726", pickupPlaceResponse.getWedo().getPickupPlaces().get(0).getCode());
  }
}
