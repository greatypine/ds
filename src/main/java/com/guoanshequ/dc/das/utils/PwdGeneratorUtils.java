package com.guoanshequ.dc.das.utils;

public class PwdGeneratorUtils {
	/**
	 * 产生随机密码(字母，数字，符号)
	 * @param num	产生密码的位数
	 * @return
	 */
	public static String generateRandomPwd(int num){
		char[] c= new char[num];   
        for(int i=0;i<num;i++){
        	c[i]=(char)((int)(Math.random()*43+48)); 
        }
        String password= new String(c);   
		return password;
	}
	public static void main(String[] args) {
		System.out.println((char)92);
	}
}
