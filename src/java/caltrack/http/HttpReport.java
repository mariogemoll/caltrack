package caltrack.http;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpReport extends HttpEntityEnclosingRequestBase {

    public final static String METHOD_NAME = "REPORT";

    public HttpReport() {
        super();
    }

    public HttpReport(final URI uri) {
        super();
        setURI(uri);
    }

    /**
     * @throws IllegalArgumentException if the uri is invalid.
     */
    public HttpReport(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

}