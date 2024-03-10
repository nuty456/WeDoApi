# WeDo API

Offers implementation of WeDo API v2 described in the [https://bridge.intime.cz/doc/restapi.html](#V2)

## Usage example

> *All the code snippets shown here are modified for clarity, so they may not be executable.*

#### Setup service

```java
// credentials
final String apiUser = "USER";
final String apiKey  = "API_KEY";
final String customerId = "CUSTOMER_ID";

// init wedo api class and getting instance
WeDoApi.Builder builder = new WeDoApi.Builder();
WeDoApi api = builder.setApiUser(apiUser)
                    .setApiKey(apiKey)
                    .setApi(API.TESTV2)
                    .setCustomerId(customerId)
                    .build();

// Or getting instance from singleton
WeDoApi.getInstance()

```

#### Vrací seznam vlastních zásilek.

```java
ArrayList<PkgList> packageList = WeDoApi.getInstance().getPackageList();
    
// GET https://api.intime.cz/package
/*
Response:
[
    {
        "reference_number": "777888999",
        "order_number": "46F00000001",
        "last_change": "2023-04-11T11:32:34",
        "delivered": null
    }
]
*/

```

#### Vrací seznam vlastních zásilek pro pickup point.

```java
ArrayList<PkgList> packageList = WeDoApi.getInstance().getPackageList("N2079");
    
// GET https://api.intime.cz/package
/*
Response:
[
    {
        "reference_number": "777888999",
        "order_number": "46F00000001",
        "last_change": "2023-04-11T11:32:34",
        "delivered": null
    }
]
*/
```

#### Vytvoří novou zásilku. V těle požadavku je datová struktura.

```java
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
} catch (ErrorMessageException e) {
}

// POST https://api.intime.cz/package
/*
Response:
{
  "order_number": "46F00000012",
  "reference_number": "777888999",
  "barcode": [
    "46FS00000012*001001"
  ],
  "sorting_code": "S1PR053",
  "product_name": "Medium Colli 24-CZ - hmotnost do 30 kg",
  "delivery_price": 0,
  "product_code": "M-24-CZ"
}
*/

```

#### Vrátí informace o existující zásilce.

```java
String orderNumber = "46F00051075";
PkgResponse pkgList = WeDoApi.getInstance().getPackageListByOrderNumber(orderNumber);

// GET https://api.intime.cz/package/:order_number
```

#### Zruší zasilku, která ještě nebyla odeslána ke zpracování.

```java
String orderNumber = "46F00000008";
ArrayList<DeleteResponse> deleteResponses = WeDoApi.getInstance().deletePackageByOrderNumber(orderNumber);
```

#### Získá štítky pro zásilku v zadaném formátu (pdf, zpl).

```java
String id = "XXX";
WeDoApi.getInstance().savePdfLabelsForIdAs(Format.PDF, Dimensions.LABEL, id, "./data/id_labels.pdf");
WeDoApi.getInstance().savePdfLabelsForIdAs(Format.PDF, Dimensions.LABELA6, id, "./data/id_labels_v2_38.pdf");
```

#### Vytvoří a odešle dávku zásilek ke zpracování.

```java
Articles articles = new Articles(new ArrayList<>(List.of("46F00000009")));
BatchResponse batch = WeDoApi.getInstance().createBatch(articles);

/*
Response:
{
    "articles": [
        {
            "code": "0", 
            "order_number": "46F00000001"
        }
    ], 
    "batch": {
        "protocol_url": "http://zasilky3.intime.cz/test/protocol/20230412MYZUkf.html", 
        "id": "51225", 
        "number": "IT-46F-20230412212520"
    }
}
*/
```

#### Vytvoří a odešle dávku zásilek ke zpracování.

```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
PickupResponse pickup = WeDoApi.getInstance().orderPickup(sdf.parse("2023-09-14"), "XXXX");

/*
Response:
{
  "pickup_address": {
    "street": "Testovaci",
    "building_number": "41",
    "city": "As",
    "postal_code": "11100",
    "country_code": "CZ"
  },
  "id": "I27682",
  "pickup_type": "IRREGULAR",
  "pickup_from": "14:24:35",
  "pickup_to": null,
  "created": "2023-08-07T14:24:35",
  "is_deletable": true
}
*/
```

#### Získá detaily o odeslané dávce (seznam zásilek v dávce, odkazy na svozový protokol a štítky).

```java
String number = "IT-46F-20230412212520";
BatchDetailResponse batchDetailResponse = WeDoApi.getInstance().getBatchByNumber(number);

/*
Response:
{
    "transport_date": "2023-04-13 17:00:00", 
    "sender_contact": {
        "city": "Brno", 
        "depot_email": "dispecer.brno@intime.cz", 
        "name": "Nazev.cz", 
        "depot_name": "Brno 06", 
        "note": null, 
        "phone": "", 
        "street": "Testovaci", 
        "postal_code": "22600", 
        "customer_name": "C s.r.o."
    }, 
    "packages": [
        "46F00000001"
    ], 
    "labels_pdf": "https://bridge.intime.cz/api-test/v1/batch/IT-46F-20230412212520/8d385e7f89fe1s8f099807a831f604e621673f5559b5a7c95c9223c0a4b492580f/labels.pdf"
}
*/
```

#### Získá svozový protokol pro zadanou dávku jako PDF.

```java
String number = "IT-46F-91230412292520";
WeDoApi.getInstance().saveBatchManifestPdfByNumber(number, "./data/manifest.pdf");
```

#### Získa štítky pro zadanou dávku jako PDF.

```java
String number = "IT-46F-91230412272520";
WeDoApi.getInstance().saveBatchLabelsPdfByNumber(number, Dimensions.LABEL, "./data/labels.pdf");
```

#### Returns customers pickup places.

```java
PickupPlaceResponse pickupPlaceResponse = WeDoApi.getInstance().getPickupPlaceList();
```

#### Returns customers distribution point places.

```java
WeDoApi.getInstance().getDistributionPointList();
// not finished
```

## System requirements

* [Java 11](https://www.oracle.com/cz/java/technologies/javase/jdk11-archive-downloads.html)
* [Maven](https://maven.apache.org/)

## Installation

Run:
```
mvn clean install
```

## Version

Support for WeDo API V2


## Contributing

Please see [CONTRIBUTING][link-contributing] and [CODE_OF_CONDUCT][link-code-of-conduct] for details.


## Security

If you discover any security related issues, please email jirka.bendl@gmail.com instead of using the issue tracker.


## Credits

- [Jiří Bendl](https://github.com/nuty456)
- [All Contributors][link-contributors]


## License

The MIT License (MIT). Please see [License File][link-licence] for more information.
