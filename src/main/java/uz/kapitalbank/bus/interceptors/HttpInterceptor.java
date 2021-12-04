package uz.kapitalbank.bus.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import uz.kapitalbank.bus.common.message.MessageSingleton;
import uz.kapitalbank.bus.common.models.Lang;
import uz.kapitalbank.bus.security.JwtTokenProvider;
import uz.kapitalbank.bus.user.User;
import uz.kapitalbank.bus.user.UserService;
import uz.kapitalbank.bus.user.UserState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static uz.kapitalbank.bus.common.ThreadLocalSingleton.*;

/**
 * @author Rustam Khalmatov
 */

@Component
public class HttpInterceptor implements AsyncHandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final MessageSingleton messageSingleton;
    private final UserService userService;

    public HttpInterceptor(JwtTokenProvider jwtTokenProvider,
                           MessageSingleton messageSingleton,
                           UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.messageSingleton = messageSingleton;
        this.userService = userService;
    }

    private List<String> allowedWithoutToken = new CopyOnWriteArrayList<>(Arrays.asList(
            "swagger",
            "/v2/api-docs","/favicon.ico",
            "/configuration/ui",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/csrf",
            "login",
            "refresh-token",
            "create/user",
            "create/admin"
    ));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        final String ip = request.getHeader("X-Real-IP");
        String token = jwtTokenProvider.resolveToken(request);

//        final Lang language = Lang.getByName(Objects.nonNull(lang) ? lang.toUpperCase() : "RU");
        final Lang language =  Lang.EN;
        setLang(language);
        setIpAddress(ip);
        setLogInternalId(UUID.randomUUID().toString());
//
//        if (!versionService.isActiveVersion(appVersion, handler)) {
//            errorResponse(request, response, messageService.upgradeRequired());
//            return false;
//        }
//        setAppVersion(appVersion);
//
//        if (!isAllowedWithoutDeviceId(request) && (deviceIdStr == null || deviceIdStr.isEmpty() || deviceOpt.isEmpty())) {
//            errorResponse(request, response, messageService.unrecognizedDevice());
//            return false;
//        }
//        Long deviceId = deviceOpt.map(Device::getId).orElse(0L);
//

//
        if (token == null || token.isEmpty()) {
            if(!isAllowedWithoutToken(request)){
                errorResponse(request, response, messageSingleton.unauthorized());
                return false;
            }
            else
                return true;
        }

        if (!jwtTokenProvider.validateToken(token)){
            errorResponse(request, response, messageSingleton.unauthorized());
            return false;
        }
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        User user = userService.findByUsername(jwtTokenProvider.getUsername(token)).orElse(new User());

//        Optional<BlockedAccount> blockedAccount = blockedAccountService.isAccountBlocked(user.getId()); //todo make blocked users response
//        if (blockedAccount.isPresent()) {
//            errorResponse(request, response, messageService.phoneNumberIsBlocked());
//            return false;
//        }
        if (user.getUserState() == UserState.DELETED ) {
            errorResponse(request, response, messageSingleton.unauthorized());
            return false;
        }
        setUser(user);
        return true;
    }

    private boolean isAllowedWithoutToken(HttpServletRequest request) {
        for (String allowedUrl : allowedWithoutToken) {
            if (request.getRequestURI().contains(allowedUrl)) {
                return true;
            }
        }
        return false;
    }

    private void errorResponse(HttpServletRequest request, HttpServletResponse response, ResponseEntity responseData) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(responseData.getStatusCodeValue());
        response.getWriter().print(new ObjectMapper().writeValueAsString(responseData.getBody()));
    }
}
