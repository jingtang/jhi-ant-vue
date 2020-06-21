package com.aidriveall.cms.web.rest.standalone;

import com.aidriveall.cms.security.SecurityUtils;
import com.aidriveall.cms.service.dto.UserDTO;
import com.aidriveall.cms.service.standalone.mobile.enumeration.ErrorEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.security.UserNotActivatedException;
import com.aidriveall.cms.security.jwt.JWTFilter;
import com.aidriveall.cms.security.jwt.TokenProvider;
import com.aidriveall.cms.service.UserService;
import com.aidriveall.cms.service.standalone.mobile.service.Result;
import com.aidriveall.cms.service.standalone.mobile.service.impl.DaYuSmsService;
import com.aidriveall.cms.web.rest.vm.LoginMobileVM;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserSmsController {

    private final TokenProvider tokenProvider;
    private final DaYuSmsService daYuSmsService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public UserSmsController(TokenProvider tokenProvider, DaYuSmsService daYuSmsService, UserDetailsService userDetailsService, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.daYuSmsService = daYuSmsService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/authenticate/mobile")
    public ResponseEntity<JWTToken> authorizeByMobile(@Valid @RequestBody LoginMobileVM loginMobileVM) {

        // 验证动态口令与图片验证码
        Result<Void> result = daYuSmsService.verify(loginMobileVM.getMobile(), loginMobileVM.getCode(), loginMobileVM.getImageCode(), "JhiAntVue");
        if (result.getSuccess()) {
            // 通过mobile查找用户信息。
            Optional<UserDTO> userDTO = userService.findOneByMobile(loginMobileVM.getMobile());
            if (userDTO.isPresent()) {
                Optional<User> userOptional = userService.getUserWithAuthorities(userDTO.get().getId());
                if (userOptional.isPresent()) {
                    UserDetails user = userDetailsService.loadUserByUsername(userOptional.get().getLogin());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    boolean rememberMe = (loginMobileVM.isRememberMe() == null) ? false : loginMobileVM.isRememberMe();
                    String jwt = tokenProvider.createTokenWithUserId(authenticationToken, rememberMe, userOptional.get().getId());
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
                    return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
                } else {
                    throw new UsernameNotFoundException("未找到指定手机号码用户");
                }

            } else {
                throw new UsernameNotFoundException("未找到指定手机号码用户");
            }

        } else {
            // 未通过
            throw new UserNotActivatedException("验证未通过");
        }
    }

    /**
     *
     * 发送验证码到手机登录时使用
     *
     */
    @GetMapping("/mobile/smscode")
    public ResponseEntity<Result<String>> getSmsCode(String mobile,@RequestParam(name = "imageCode", required = false) String imageCode) {
        Optional<UserDTO> userDTO1 = userService.findOneByMobile(mobile);
        if (!userDTO1.isPresent()) {
            // todo，不应该在这里新注册，应该在登录验证里处理 使用手机号码注册一个新用户
            UserDTO userDTO = new UserDTO();
            userDTO.setActivated(true);
            userDTO.setLogin(mobile);
            userDTO.setCreatedBy("system");
            userDTO.setLastModifiedBy("system");
            userDTO.setEmail(mobile+"@aidriveall.com");
            HashSet<String> authorities = new HashSet<>();
            authorities.add("ROLE_USER");
            userDTO.setAuthorities(authorities);
            userDTO.setMobile(mobile);
            User user = userService.createUser(userDTO);
        }
        // 发送手机短信验证码
        Result<String> sendResult = daYuSmsService.sendVerifyCode(mobile, "JhiAntVue");
        if (sendResult.getSuccess()) {
            return ResponseEntity.ok().body(sendResult);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     *
     * 发送验证码到手机，用户设置手机号码时使用
     *
     */
    @GetMapping("/mobile/smscode/current-user")
    public ResponseEntity<Result<String>> getSmsCodeByCurrentUser(String mobile, @RequestParam(name = "imageCode", required = false) String imageCode) {
        // 首先检查是否已经被别人注册。

        // 未注册的情况下直接发送短信验证码

        // 提交时再注册到用户的person中。
        if (SecurityUtils.getCurrentUserLogin().isPresent()) {
            Boolean existsMobile = userService.existsByMobileAndLoginNot(mobile, SecurityUtils.getCurrentUserLogin().get());
            if (!existsMobile) {
                // 不存在，发送验证码
                Result<String> result = daYuSmsService.sendVerifyCode(mobile, "JhiAntVue");
                if (result.getSuccess()) {
                    return ResponseEntity.ok(result);
                } else {
                    return ResponseEntity.badRequest().body(Result.error(ErrorEnum.UNKNOWN));
                }
            } else {
                return ResponseEntity.badRequest().body(Result.error(ErrorEnum.MOBILE_EXISTED));
            }
        } else {
            return ResponseEntity.badRequest().body(Result.error(ErrorEnum.UNKNOWN));
        }
    }

    /**
     *
     * 保存当前用户设置的手机号码
     *
     */
    @PostMapping("/mobile/current-user")
    public ResponseEntity<Result<String>> saveMobileByCurrentUser(String mobile, String code, @RequestParam(name = "imageCode", required = false) String imageCode) {
        // 验证
        Result<Void> verifyResult = daYuSmsService.verify(mobile, code, imageCode, "test005");
        if (verifyResult.getSuccess()) {
            // 保存
            Optional<User> currentUser = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get());
            if (currentUser.isPresent()) {
                currentUser.get().setMobile(mobile);
                userService.updateUser(new UserDTO(currentUser.get()));
            } else {
                return ResponseEntity.badRequest().body(Result.error(ErrorEnum.SMS_CODE_INCORRECT));
            }
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(Result.error(ErrorEnum.SMS_CODE_INCORRECT));
        }
    }


    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
