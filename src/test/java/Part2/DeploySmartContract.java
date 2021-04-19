package Part2;

import ether.Constants;
import ether.Part1.EthAccountHandler;
import contracts.DocumentRegistry;
import ether.Part2.SmartContracts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DeploySmartContract {

    String contractAdress = ""; // TODO if this is still blank, create the contract via deployToEther
//    String contractAdress = "0x216b519f71428ed6d9ec9a673f8471b6fc27581d"; // TODO if this is still blank, create the contract via deployToEther
    String myDocument = "I owe you one Million Bicoins. Signed, me";

    @Test
    void deployToEther() throws Exception {

        Credentials credentials = EthAccountHandler.loadWallet(Constants.LOCAL_WALLET_PASSWORD, Constants.LOCAL_WALLET_JSON);
        String contractAdress = SmartContracts.deployContractToEther(Constants.LOCAL_ENDPOINT, credentials);
        System.out.println("contract was created on adress: " + contractAdress);
    }

    @Test
    void loadContractFromEther() throws Exception {

        Credentials credentials = EthAccountHandler.loadWallet(Constants.LOCAL_WALLET_PASSWORD, Constants.LOCAL_WALLET_JSON);
        DocumentRegistry registry = SmartContracts.loadContractFromEther(Constants.LOCAL_ENDPOINT, credentials, contractAdress);
        Assertions.assertNotNull(registry); // later we can work with this instance
    }

    @Test
    void notarizeDocument() throws Exception {

        Credentials credentials = EthAccountHandler.loadWallet(Constants.LOCAL_WALLET_PASSWORD, Constants.LOCAL_WALLET_JSON);

        DocumentRegistry registry = SmartContracts.loadContractFromEther(Constants.LOCAL_ENDPOINT, credentials, contractAdress);

        TransactionReceipt receipt = registry.notarizeDocument(getDocumentHash(myDocument)).send();

        String txHash = receipt.getTransactionHash();
        Assertions.assertFalse(txHash.isBlank());
        System.out.println(txHash);
    }

    @Test
    void isDocumentNotarized() throws Exception {

        Credentials credentials = EthAccountHandler.loadWallet(Constants.LOCAL_WALLET_PASSWORD, Constants.LOCAL_WALLET_JSON);
        DocumentRegistry registry = SmartContracts.loadContractFromEther(Constants.LOCAL_ENDPOINT, credentials, contractAdress);

        Assertions.assertTrue(registry.isNotarized(getDocumentHash(myDocument)).send());
        Assertions.assertFalse(registry.isNotarized(getDocumentHash("I owe you one Billion Bitcoins. Signed, me")).send());
    }

    private byte[] getDocumentHash(String document) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(document.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void listenForEvents() throws Exception {

        Credentials credentials = EthAccountHandler.loadWallet(Constants.LOCAL_WALLET_PASSWORD, Constants.LOCAL_WALLET_JSON);
        DocumentRegistry registry = SmartContracts.loadContractFromEther(Constants.LOCAL_ENDPOINT, credentials, contractAdress);

        registry.notarizedEventFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
                .subscribe(event -> {
                    final String notary = event._signer;
                    System.out.println("notary was: " + notary);
                });

    }

}
