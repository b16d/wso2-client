/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.acl.test.wso2;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.wso2.carbon.um.ws.api.stub.ClaimValue;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceStub;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceUserStoreExceptionException;

import java.rmi.RemoteException;

public class RemoteUserStoreServiceAdminClient {

    private final String serviceName = "RemoteUserStoreManagerService";
    private RemoteUserStoreManagerServiceStub remoteUserStoreManagerServiceStub;
    private String endPoint;

    public RemoteUserStoreServiceAdminClient(String backEndUrl, String sessionCookie) throws AxisFault {
        this.endPoint = backEndUrl + "/services/" + serviceName;
        remoteUserStoreManagerServiceStub = new RemoteUserStoreManagerServiceStub(endPoint);
        //Authenticate Your stub from sessionCooke
        ServiceClient serviceClient;
        Options option;

        serviceClient = remoteUserStoreManagerServiceStub._getServiceClient();
        option = serviceClient.getOptions();
        option.setManageSession(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, sessionCookie);
    }


    public String[] listUsers()
            throws RemoteException, RemoteUserStoreManagerServiceUserStoreExceptionException {

       Object test =  remoteUserStoreManagerServiceStub.getUserClaimValues("admin", "admin");
        return remoteUserStoreManagerServiceStub.listUsers("*", 100);
    }


    public void addUser(String userName)
            throws RemoteException, RemoteUserStoreManagerServiceUserStoreExceptionException {
        String roleList[] = {"admin"};
        ClaimValue claimValue = new ClaimValue();
        claimValue.setClaimURI("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress");
        claimValue.setValue("wso2Demomail@gmail.com");

        ClaimValue claimValues[] = { claimValue };

        remoteUserStoreManagerServiceStub.addUser(userName, "test5", roleList,
                claimValues,"default",true);
    }
//
  //  public void addUser(String userName234, String credential235, String[] roleList236, ClaimValue[] claims237, String profileName238, boolean requirePasswordChange239) throws RemoteException, RemoteUserStoreManagerServiceUserStoreExceptionException {

    }
