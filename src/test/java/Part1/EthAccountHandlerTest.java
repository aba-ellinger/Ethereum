package Part1;

import ether.Constants;
import ether.Part1.EthAccountHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

class EthAccountHandlerTest {

    EthAccountHandler accountHandler;

    @BeforeEach
    void setUp() {
        accountHandler = new EthAccountHandler();
    }


    @Test
    void printBalanceOfAccount_public() throws IOException {

        System.out.println("binance account: ");
        accountHandler.printBalanceOfAccount(Constants.PUBLIC_ENDPOINT, Constants.PUBLIC_HUGE_WALLET);
    }


    @Test
    void printBalanceOfAccount_local() throws IOException {

        System.out.println("my local account: ");
        accountHandler.printBalanceOfAccount(Constants.LOCAL_ENDPOINT, Constants.LOCAL_WALLET_ADRESS);
    }

    @Test
    void createWallet() throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

        // do not start the debugger here, this kills the process for whatever reason
        accountHandler.createWallet(""); // todo choose a secure password here

    }

    @Test
    void loadWallet() throws CipherException, IOException {

        EthAccountHandler.loadWallet(Constants.LOCAL_WALLET_PASSWORD, Constants.LOCAL_WALLET_JSON);

    }

    @Test
    void sendFundsTest() throws Exception {

        Credentials miningAccount = EthAccountHandler.loadWallet(Constants.LOCAL_WALLET_PASSWORD, Constants.LOCAL_WALLET_JSON);
        String recipient = ""; // todo take this from above

        accountHandler.sendFundsFromOneAccountToAnother(miningAccount, recipient, BigDecimal.ONE, Convert.Unit.ETHER);

    }
}