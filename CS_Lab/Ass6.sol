//SPDX-License-Identifier:MIT
pragma solidity ^0.8.0;

contract OrganizationContract {
    address public superadmin;

    struct Organization {
        string name;
        address admin;
        bool exists;
        mapping(address => bool) users;
        mapping(address => bool) pendingRequests;
        mapping(address => bool) acceptedRequests;
        address[] requests; // Store user addresses who sent requests
    }

    struct User {
        address userAddress;
        string name;
        bool exists;
    }

    mapping(address => Organization) public organizations;
    mapping(address => User) public users;

    constructor() {
        superadmin = msg.sender;
    }

    modifier onlySuperadmin() {
        require(msg.sender == superadmin, "Only superadmin can perform this action");
        _;
    }

    modifier onlyRegisteredOrganization(address _orgAddress) {
        require(organizations[_orgAddress].exists, "Organization does not exist");
        _;
    }

    event OrganizationAdded(address indexed orgAddress, string name, address indexed admin);
    event UserRequested(address indexed orgAddress, address indexed user);
    event RequestRejected(address indexed orgAddress, address indexed user);
    event RequestAccepted(address indexed orgAddress, address indexed user);

    function addOrganization(address _orgAddress, string memory _name) public onlySuperadmin {
        require(!organizations[_orgAddress].exists, "Organization already exists");
        Organization storage org = organizations[_orgAddress];
        org.name = _name;
        org.admin = msg.sender;
        org.exists = true;
        emit OrganizationAdded(_orgAddress, _name, msg.sender);
    }

    function registerUser(address _userAddress, string memory _name) public {
        require(!users[_userAddress].exists, "User already registered");
        User storage user = users[_userAddress];
        user.userAddress = _userAddress;
        user.name = _name;
        user.exists = true;
    }

    function sendRequest(address _orgAddress) public onlyRegisteredOrganization(_orgAddress) {
        require(users[msg.sender].exists, "User is not registered");
        require(!organizations[_orgAddress].users[msg.sender], "User already sent a request");
        Organization storage org = organizations[_orgAddress];
        org.pendingRequests[msg.sender] = true;
        org.requests.push(msg.sender); // Add user to the requests array
        emit UserRequested(_orgAddress, msg.sender);
    }

    function getRequests(address _orgAddress) public view onlyRegisteredOrganization(_orgAddress) returns (address[] memory) {
        return organizations[_orgAddress].requests;
    }

    function acceptRequest(address _orgAddress, address _user) public onlyRegisteredOrganization(_orgAddress) {
        Organization storage org = organizations[_orgAddress];
        require(org.pendingRequests[_user], "No pending request from this user");
        org.pendingRequests[_user] = false;
        org.users[_user] = true;
        org.acceptedRequests[_user] = true; // Mark the request as accepted
        removeRequest(_orgAddress, _user); // Remove the request from the organization's requests array
        emit RequestAccepted(_orgAddress, _user);
    }

    function rejectRequest(address _orgAddress, address _user) public onlyRegisteredOrganization(_orgAddress) {
        Organization storage org = organizations[_orgAddress];
        require(org.pendingRequests[_user], "No pending request from this user");
        org.pendingRequests[_user] = false;
        org.acceptedRequests[_user] = false; // Mark the request as rejected
        removeRequest(_orgAddress, _user); // Remove the request from the organization's requests array
        emit RequestRejected(_orgAddress, _user);
    }

    // Helper function to remove a request from the organization's requests array
    function removeRequest(address _orgAddress, address _user) internal {
        Organization storage org = organizations[_orgAddress];
        for (uint i = 0; i < org.requests.length; i++) {
            if (org.requests[i] == _user) {
                // Swap the element to delete with the last element
                org.requests[i] = org.requests[org.requests.length - 1];
                org.requests.pop(); // Remove the last element
                break;
            }
        }
    }

    function getRequestStatus(address _orgAddress, address _user) public view onlyRegisteredOrganization(_orgAddress) returns (string memory) {
    Organization storage org = organizations[_orgAddress];
    
    if (org.acceptedRequests[_user]) {
        return "Accepted";
    } else if (org.pendingRequests[_user]) {
        return "Pending";
    } else {
        return "Rejected";
    }
}
}
