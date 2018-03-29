pragma solidity ^0.4.19;

contract SimpleEventContract {
  uint256 _value;

  event MyEvent(address indexed _address, uint256 _oldValue, uint256 _newValue);

  function setValue(uint256 value) public {
    MyEvent(msg.sender, _value, value);

    _value = value;
  }

  function getValue() public constant returns (uint256) {
    return _value;
  }
}
