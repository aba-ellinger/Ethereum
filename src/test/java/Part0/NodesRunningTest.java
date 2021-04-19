package Part0;

import ether.Constants;
import ether.Part0.NodeRunningVerificator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class NodesRunningTest {

    private NodeRunningVerificator verificator;

    @BeforeEach
    void setUp() {
        verificator = new NodeRunningVerificator();
    }

    @Test
    void isNodeRunning() {


        verificator.printNodeInfos(Constants.PUBLIC_ENDPOINT);

        verificator.printNodeInfos(Constants.LOCAL_ENDPOINT);

    }

    @Test
    void HowMuchIsSendingOneEther() {

        BigDecimal amountInEther = Convert.fromWei("150000000000", Convert.Unit.ETHER);
        System.out.println(amountInEther);
        System.out.println(amountInEther.multiply(Constants.EXCHANGE_RATE).multiply(BigDecimal.valueOf(21_000)));

    }
}
