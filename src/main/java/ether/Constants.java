package ether;

import java.math.BigDecimal;

public final class Constants {

    private Constants() {
    }

    // popular public node https://infura.io/
    public static final String PUBLIC_ENDPOINT = "https://mainnet.infura.io/v3/d8e26a4b8a99419582c4b62f3b210e35";

    public static final String LOCAL_ENDPOINT = "http://localhost:8545";

    public static final String LOCAL_WALLET_ADRESS = "0x8b61d3ffd7ce1472cabc1e62262229eef93b2a98";
    public static final String LOCAL_WALLET_JSON = "UTC--2021-03-21T08-46-09.807512000Z--8b61d3ffd7ce1472cabc1e62262229eef93b2a98.json";
    public static final String LOCAL_WALLET_PASSWORD = "superSecretPassword"; // high security stuff

    // biggest account from https://etherscan.io/accounts
    public static final String PUBLIC_HUGE_WALLET = "0xbe0eb53f46cd790cd13851d5eff43d12404d33e8"; // binance


    // current exchange rate as of 17.04.2021 in CHF
    public static final BigDecimal EXCHANGE_RATE = BigDecimal.valueOf(2271.76);




}
