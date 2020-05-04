package com.mxg.security.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jiangxiao
 * @Title: AuthenticationProperties
 * @Package
 * @Description: yml自定义属性
 * @date 2020/3/2011:25
 */
public class AuthenticationProperties {
    //给默认值
    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/from";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**","/modules/**","/plugins/**"};
    private String imageCodeUrl = "/code/image";
    private String mobileCodeUrl = "/code/mobile";
    private String mobilePage = "/mobile/page";
    private Integer tokenValiditySeconds = 604800;

    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    public void setLoginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public String[] getStaticPaths() {
        return staticPaths;
    }

    public void setStaticPaths(String[] staticPaths) {
        this.staticPaths = staticPaths;
    }

    public String getImageCodeUrl() {
        return imageCodeUrl;
    }

    public void setImageCodeUrl(String imageCodeUrl) {
        this.imageCodeUrl = imageCodeUrl;
    }

    public String getMobileCodeUrl() {
        return mobileCodeUrl;
    }

    public void setMobileCodeUrl(String mobileCodeUrl) {
        this.mobileCodeUrl = mobileCodeUrl;
    }

    public String getMobilePage() {
        return mobilePage;
    }

    public void setMobilePage(String mobilePage) {
        this.mobilePage = mobilePage;
    }

    public Integer getTokenValiditySeconds() {
        return tokenValiditySeconds;
    }

    public void setTokenValiditySeconds(Integer tokenValiditySeconds) {
        this.tokenValiditySeconds = tokenValiditySeconds;
    }

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }
}
