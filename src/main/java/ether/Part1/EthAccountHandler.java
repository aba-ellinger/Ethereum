package ether.Part1;

import ether.Constants;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class EthAccountHandler {


    public void printBalanceOfAccount(String endpoint, String walletAdress) throws IOException {


        Web3j web3 = Web3j.build(new HttpService(endpoint));

        EthGetBalance balanceWei = web3.ethGetBalance(walletAdress, DefaultBlockParameterName.LATEST).send();
        System.out.println("balance in wei: " + balanceWei.getBalance().toString());

        BigDecimal balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Convert.Unit.ETHER);
        System.out.println("balance in ether: " + balanceInEther);

        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(walletAdress, DefaultBlockParameterName.LATEST).send();
        System.out.println(ethGetTransactionCount.getTransactionCount()); // transactionCount, also known as nonce

    }


    public void createWallet(String walletPassword) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {

        String walletDirectory = "src/main/resources/wallet";

        String walletName = WalletUtils.generateNewWalletFile(walletPassword, new File(walletDirectory));
        System.out.println("wallet location: " + walletDirectory + "/" + walletName);


        Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory + "/" + walletName);

        System.out.println("Account address: " + credentials.getAddress());
    }

    public static Credentials loadWallet(String walletPassword, String walletJsonFile) throws IOException, CipherException {

        String walletDirectory = "src/main/resources/wallet";

        // Load the JSON encryted wallet
        Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletDirectory + "/" + walletJsonFile);

        // Get the account address
        String accountAddress = credentials.getAddress();
        System.out.println("my Public Key is: " + credentials.getEcKeyPair().getPublicKey().toString(16));
        System.out.println("my Adress  is: " + accountAddress);

        // Get the unencrypted private key into hexadecimal
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        System.out.println("my private key is: " + privateKey);

        return credentials;
    }

    public void sendFundsFromOneAccountToAnother(Credentials fromAccount, String recipientAddress, BigDecimal value, Convert.Unit unit) throws Exception {

        Web3j web3 = Web3j.build(new HttpService(Constants.LOCAL_ENDPOINT)); // our local running node

        EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(fromAccount.getAddress(), DefaultBlockParameterName.LATEST).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        // Value to Transfer
//        BigInteger value = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();

        // A transfer cost 21,000 units of gas
        BigInteger gasLimit = BigInteger.valueOf(21000);

        // I am willing to pay 1Gwei (1,000,000,000 wei or 0.000000001 ether) for each unit of gas consumed by the transaction.
        BigInteger gasPrice = Convert.toWei("1", Convert.Unit.GWEI).toBigInteger();

        // here is where the real stuff happens
        TransactionReceipt receipt = Transfer.sendFunds(web3, fromAccount, recipientAddress, value, unit).send();

        System.out.println(receipt);
    }
}
