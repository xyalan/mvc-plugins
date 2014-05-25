package com.hialan.hv.struts2.json;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * User: Alan Yang
 * Date: 1/4/13
 * Time: 5:27 PM
 */
public class ResponseWrapper {

    private String contentType = "application/json";
    private String characterEncoding = "utf-8";
    private boolean gzip;
    private boolean noCache;
    private int statusCode = 200;

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public void setGzip(boolean gzip) {
        this.gzip = gzip;
    }

    public void setNoCache(boolean noCache) {
        this.noCache = noCache;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void writeResult(HttpServletRequest request,
                            HttpServletResponse response, String result) throws IOException {
        response.setContentType(contentType);
        response.setStatus(statusCode);
        response.setCharacterEncoding(characterEncoding);

        if (noCache) {
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Pragma", "No-cache");
        }

        if (gzip && isGzipInRequest(request)) {
            response.addHeader("Content-Encoding", "gzip");
            GZIPOutputStream out = null;
            try {
                out = new GZIPOutputStream(response.getOutputStream());
                out.write(result.getBytes(characterEncoding));
            } finally {
                if (out != null) {
                    out.finish();
                    out.close();
                }
            }
        } else {
            byte[] bytes = result.getBytes(characterEncoding);
            response.setContentLength(bytes.length);
            OutputStream out = null;
            try {
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
    }

    /**
     * check whether gzip encoding is accepted by the browser again borrowed
     * from struts json plugin
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    private boolean isGzipInRequest(HttpServletRequest request) {
        String header = request.getHeader("Accept-Encoding");
        return header != null && header.contains("gzip");
    }
}
