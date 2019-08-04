/**  
 * @Title: UsernamePasswordToken.java  
 * @Package com.eigpay.supp.general.admin.manager.security  
 * @Description: 自定义shiro的用户名，密码功能
 * @author xb12369  
 * @date 2018年4月3日  
 * @version V1.0  
 */
package com.example.springbootshiro.utils;
 
/**  
 * @ClassName: UsernamePasswordToken  
 * @Description: 自定义shiro的用户名，密码功能
 * @author xb12369  
 * @date 2018年4月3日  
 *    
 */
 
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken{
 
	private static final long serialVersionUID = 1L;
 
	private String captcha;
 
	public UsernamePasswordToken() {
		super();
	}
 
	public UsernamePasswordToken(String username, String password,String captcha) {
		super(username, password);
		this.captcha = captcha;
	}
 
	public UsernamePasswordToken(String username, char[] password,
			boolean rememberMe, String host, String captcha) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}
 
	public String getCaptcha() {
		return captcha;
	}
 
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}