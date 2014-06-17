package info.tongrenlu.support;

import javax.servlet.http.HttpServletRequest;

public class IPSupport {
    private String address;
    private boolean forwarded;

    public IPSupport() {
    }

    public IPSupport(final HttpServletRequest request) {
        this.address = IPSupport.getClientAddress(request);
        if (!this.address.equals(request.getRemoteAddr())) {
            this.forwarded = true;
        }
    }

    public boolean isForwarded() {
        return this.forwarded;
    }

    public void setForwarded(final boolean forwarded) {
        this.forwarded = forwarded;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return this.address;
    }

    public static String getClientAddress(final HttpServletRequest request) {
        String addr = request.getRemoteAddr();
        final String fwdHeader = request.getHeader("X-Forwarded-For");
        if (fwdHeader != null) {
            addr = fwdHeader.split(",")[0];
        }
        return addr;
    }
}
