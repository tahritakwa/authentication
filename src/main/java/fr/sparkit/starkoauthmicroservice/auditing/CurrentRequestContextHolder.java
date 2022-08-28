package fr.sparkit.starkoauthmicroservice.auditing;

import javax.servlet.http.HttpServletRequest;

public final class CurrentRequestContextHolder {

    private static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<>();

    private CurrentRequestContextHolder() {
        super();
    }

    public static void removeCurrentHttpServletRequest() {
        threadLocal.remove();
    }

    public static HttpServletRequest getCurrentHttpServletRequest() {
        return threadLocal.get();
    }

    public static void setCurrentHttpServletRequest(HttpServletRequest httpServletRequest) {
        threadLocal.set(httpServletRequest);
    }

}
