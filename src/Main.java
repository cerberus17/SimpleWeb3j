import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import rx.Observable;

public class Main {
  public static void main(String[] args) {
    Main.eventTest();
  }

  public static void eventTest() {
    try {
      Web3j web3j = Web3j.build(new HttpService());

      Event event = new Event("MyEvent",
                              Arrays.asList(new TypeReference<Address>() {}),
                              Arrays.asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));

      // Note contract address here. See https://github.com/web3j/web3j/issues/405
      EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, "6dd7c1c13df7594c27e0d191fd8cc21efbfc98b4");

      filter.addSingleTopic(EventEncoder.encode(event));

      web3j.ethLogObservable(filter).subscribe(log -> System.out.println(log.toString()));
    }
    catch (Throwable t) {
      t.printStackTrace();
    }
  }

  public static void transferTest() {
    try {
      Web3j web3j = Web3j.build(new HttpService());

      // Static Ganache test account keys
      String rawPrivateKey = "70f1384b24df3d2cdaca7974552ec28f055812ca5e4da7a0ccd0ac0f8a4a9b00";
      String rawPublicKey = "4c9b23b009630f4ba57cb2ed80916938aaacf752ed2379c42bcd000be37c5316bff7afec6c808d66cd2fd69874a8df97eff5";

//      BigInteger publicKey = new BigInteger(rawPublicKey, 16);
//      BigInteger privateKey = new BigInteger(rawPrivateKey, 16);

//      ECKeyPair pair = new ECKeyPair(privateKey, publicKey);

      EthAccounts accounts = web3j.ethAccounts().send();
      String lastAccount = accounts.getAccounts().get(accounts.getAccounts().size() - 1);
      String firstAccount = web3j.ethAccounts().send().getAccounts().get(0);
      Credentials credentials = Credentials.create(rawPrivateKey);

      EthGetBalance balance = web3j.ethGetBalance(firstAccount, DefaultBlockParameterName.LATEST).send();
      BigDecimal balanceValue = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER);

      System.out.println("Account: " + firstAccount);
      System.out.println("Account balance: " + balanceValue);
      System.out.println("Transfer Gas Limit in Ether: " + Convert.fromWei(Transfer.GAS_LIMIT.toString(), Convert.Unit.ETHER));
      System.out.println("Transfer Gas Price in Ether: " + Convert.fromWei(Transfer.GAS_PRICE.toString(), Convert.Unit.ETHER));

      TransactionReceipt transactionReceipt = Transfer.sendFunds(web3j, credentials, lastAccount, BigDecimal.valueOf(10.0), Convert.Unit.ETHER).send();

      if (transactionReceipt != null) {
        System.out.println("Transaction receipt info: " + transactionReceipt.toString());
      }
    }
    catch (Throwable t) {
      t.printStackTrace();
    }
  }
}

