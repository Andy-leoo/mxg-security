package com.mxg.security.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @author jiangxiao
 * @Title: MobileAuthenticationToken
 * @Package
 * @Description: 手机验证生成Token
 * @date 2020/4/812:35
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 认证之前放入手机号， 认证之后放用户信息
     */
    private final Object principal;

    // ~ Constructors
    // ===================================================================================================

    /**
     * 开始认证时,创建一个MobileAuthenticationToken实例 接收的是手机号码, 并且 标识未认证
     *principal手机号
     */
    public MobileAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal; // 手机号
        setAuthenticated(false);
    }

    /**
     * 当认证通过后,会重新创建一个新的MobileAuthenticationToken,来标识它已经认证通过,
     * principal  用户信息
     * authorities  权限
     */
    public MobileAuthenticationToken(Object principal,
                                               Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal; // 手机号
        super.setAuthenticated(true); // 标识已通过
    }


    /**
     * 在父类中是一个抽象方法,所以要实现, 但是它是密码,而当前不需要,则直接返回  null
     * @return
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * 手机号
     * @return
     */
    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
