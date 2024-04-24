package com.coderfromscratch.httpserver.http;

public class HttpRequest extends HttpMessage {

    private HttpMethod method;
    private String rquestTarget;
    private String httpVersion;

    HttpRequest() {
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(String methodName) throws HttpParsingException {

        for (HttpMethod method : HttpMethod.values()){

            if (methodName.equals(method.name())){
                this.method = method;
                return;
            }
        }

        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }
}
