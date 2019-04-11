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
import org.wso2.carbon.authenticator.stub.LoginAuthenticationExceptionException;
import org.wso2.carbon.authenticator.stub.LogoutAuthenticationExceptionException;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceUserStoreExceptionException;

import java.rmi.RemoteException;

public class AdminServiceClientManager {
    public static void main (String[] args) {

        System.setProperty("javax.net.ssl.trustStore",
              //  "/home/isuru/wso2/work/qa_hackathon/wso2am-2.1.0/repository/resources/security/client-truststore.jks");
"C:\\Program Files\\WSO2\\API Manager\\2.6.0\\repository\\resources\\security\\client-truststore.jks");

        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");

        try {
            LoginAdminServiceClient loginAdminServiceClient =
                    new LoginAdminServiceClient("https://localhost:9443");
            String sessionId = loginAdminServiceClient.authenticate("admin", "admin");
            RemoteUserStoreServiceAdminClient remoteUserStoreServiceAdminClient = new
                    RemoteUserStoreServiceAdminClient("https://localhost:9443", sessionId);
            String[] users = remoteUserStoreServiceAdminClient.listUsers();

            if(users != null){
                System.out.println("Listing user names of Carbon server...... ");
                for(String user : users){
                    System.out.println("User Name : " + user);
                }
            }

            remoteUserStoreServiceAdminClient.addUser("testACL15");
            users = remoteUserStoreServiceAdminClient.listUsers();

            if(users != null){
                System.out.println("Listing user names of Carbon server...... ");
                for(String user : users){
                    System.out.println("User Name : " + user);
                }
            }

            loginAdminServiceClient.logOut();

        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
            throw new RuntimeException(axisFault);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (LoginAuthenticationExceptionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (RemoteUserStoreManagerServiceUserStoreExceptionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (LogoutAuthenticationExceptionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
