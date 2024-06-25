# Intro

This sdk is a wrapper to interact with the Brifle API. It supports the sandbox and production environments.

# Supported Features

- [x] Check if receiver exists
- [x] Send a simple letter
- [ ] Send an invoice with payment details
- [ ] Send mail with signature field

# Sample Usage

## Check if receiver exists

```java

    Api api = new Api(ApiMode.SANDBOX);

    String token = "from the auth method"

    // read the key and secret from the environment
    String secret = System.getProperty("BRIFLE_SECRET");
    String key = System.getProperty("BRIFLE_KEY");

    try {
        SuccessfulAuthenticationResponse response = api.authenticate(key, secret).getData();

        String token = response.getAccessToken();
    } catch (Exception e) {
    e.printStackTrace();
    }

    ReceiverRequest request = ReceiverRequest
            .byEmail()
            .withEmail("email_not_exist@brifle.de")
            .withDateOfBirth("1970-01-01")
            .withName("Max Mustermann")
            .buildRequest();

    ApiResponse<CheckReceiverResponse> re = api.checkReceiver(token, request);
    // does not exist
    assert re.isError(); 
    assert re.getError().getCode() == 40401;
    assert re.getError().getStatus() == 404;


    // does exist
    assert !re.isError(); 
    assert re.getData() != null;
```