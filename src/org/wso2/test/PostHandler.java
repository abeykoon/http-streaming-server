/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.test;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream requestStream = httpExchange.getRequestBody();
        File targetFile = new File("/Users/hasitha/temp/file-streaming/targetFile/targetFile.mkv");
        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(targetFile);

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = requestStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
                outStream.flush();
            }
        } finally {
            if(outStream != null) {
                outStream.close();
            }
        }

        //Send a static response
        String response = "Done writing the sent content";
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
