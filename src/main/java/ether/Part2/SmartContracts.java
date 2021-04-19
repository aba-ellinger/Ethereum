package ether.Part2;

import contracts.DocumentRegistry;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

public class SmartContracts {

    public static String deployContractToEther(String etherEndpoint, Credentials credentials) throws Exception {
        Web3j web3 = Web3j.build(new HttpService(etherEndpoint));
        DocumentRegistry documentContract = DocumentRegistry.deploy(web3, credentials, new DefaultGasProvider()).send();
        return documentContract.getContractAddress();
    }

    public static DocumentRegistry loadContractFromEther(String etherEndpoint, Credentials credentials, String contractAdress) {
        Web3j web3 = Web3j.build(new HttpService(etherEndpoint));
        return DocumentRegistry.load(contractAdress, web3, credentials, new DefaultGasProvider());
    }

}
