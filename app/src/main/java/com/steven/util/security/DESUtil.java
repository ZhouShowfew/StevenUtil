package com.steven.util.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * 
 * DESUtil
 *
 * @description DESUtil工具
 */
public class DESUtil
{
	/**
	 * 
	 * @method encrypt
	 * @description 加密
	 * @param data 数据
	 * @param key  密钥
	 * @return
	 */
	public static byte[] encrypt(byte[] data,byte[] key)
	{
		try
		{
			//空处理
			if(null==data||data.length==0||null==key||key.length==0)
			{
				return null;
			}
			if(data.length%8!=0)
			{
				throw new IllegalArgumentException("加密数据非8字节的倍数"); 
			}
			// DES算法要求有一个可信任的随机数源
			SecureRandom secureRandom = new SecureRandom();
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec desKeySpec = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, secureRandom);
			// 获取数据并加密,执行加密操作
			return cipher.doFinal(data);
		} 
		catch (Exception e)
		{
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		}

	}
	
	/**
	 * 
	 * @method decrypt
	 * @description 解密
	 * @param data 加密数据
	 * @param key  密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] encrypted_data,byte[] key)
	{
		try
		{
			//空处理
			if(null==encrypted_data||encrypted_data.length==0||null==key||key.length==0)
			{
				return null;
			}
			if(encrypted_data.length%8!=0)
			{
				throw new IllegalArgumentException("解密数据非8字节的倍数"); 
			}
			// DES算法要求有一个可信任的随机数源
			SecureRandom secureRandom = new SecureRandom();
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec desKeySpec = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, secretKey, secureRandom);
			// 执行解密操作
			return cipher.doFinal(encrypted_data);
		} 
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			return null;
		}

	}

}
