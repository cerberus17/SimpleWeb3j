import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class SimpleEventContract extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b61010e8061001e6000396000f30060606040526004361060485763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663209652558114604d5780635524107714606f575b600080fd5b3415605757600080fd5b605d6084565b60405190815260200160405180910390f35b3415607957600080fd5b6082600435608a565b005b60005490565b3373ffffffffffffffffffffffffffffffffffffffff167f64c1bdb4deb5c10f058552a398f2d5089ccd06b7beb76c2a6bd1d8cb0a848af26000548360405191825260208201526040908101905180910390a26000555600a165627a7a723058205f0bfde701bdd06faa625ae8b1506cf765d808e04ab888dc692beeaa3292ec4f0029";

    protected SimpleEventContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SimpleEventContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<MyEventEventResponse> getMyEventEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("MyEvent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<MyEventEventResponse> responses = new ArrayList<MyEventEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            MyEventEventResponse typedResponse = new MyEventEventResponse();
            typedResponse._address = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._oldValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._newValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<MyEventEventResponse> myEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("MyEvent", 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, MyEventEventResponse>() {
            @Override
            public MyEventEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                MyEventEventResponse typedResponse = new MyEventEventResponse();
                typedResponse._address = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._oldValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._newValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<BigInteger> getValue() {
        Function function = new Function("getValue", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setValue(BigInteger value) {
        Function function = new Function(
                "setValue", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<SimpleEventContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleEventContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SimpleEventContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleEventContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static SimpleEventContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleEventContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SimpleEventContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleEventContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class MyEventEventResponse {
        public String _address;

        public BigInteger _oldValue;

        public BigInteger _newValue;
    }
}
