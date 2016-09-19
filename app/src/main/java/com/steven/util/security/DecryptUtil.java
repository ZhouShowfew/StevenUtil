package com.steven.util.security;

/**
 * 
 * DecryptUtil
 *
 * @description 解密工具
 */
public class DecryptUtil
{
	/**
	 * 
	 * @method TDES_DOUBLE
	 * @description 3DES(双倍长)加密
	 * @param data 3DES加密数据
	 * @param key  密钥
	 * @return
	 */
	public static byte[] TDES_DOUBLE(byte[] encrypted_data, byte[] key)
	{
		byte[] resault=null;
		
		//左密钥
		byte[] key_left = getLeftKey(key);

		//右密钥
		byte[]  key_right= getRightKey(key);

		//1.使用密钥的左半边对数据进行DES解密，得到temp1
		byte[] temp1=DESUtil.decrypt(encrypted_data,key_left);
		//2.使用密钥的右半边对数据进行DES加密，得到temp2
		byte[] temp2=DESUtil.encrypt(temp1,key_right);
		//3.使用密钥的左半边对数据进行DES解密，得到最终解密结果
		resault=DESUtil.decrypt(temp2,key_left);
		
		return resault;
	}

	
	/**
	 * 
	 * @method getLeftKey
	 * @description 获得左密钥
	 * @param data
	 * @return
	 */
	private static byte[] getLeftKey(byte[] data)
	{
		byte[] left=new byte[data.length/2];
		for (int i = 0; i < data.length/2; i++)
		{
			left[i]=data[i];
		}
		return left;
	}
	
	/**
	 * 
	 * @method getRightKey
	 * @description 获得右密钥
	 * @param data
	 * @return
	 */
	private static byte[] getRightKey(byte[] data)
	{
		byte[] right=new byte[data.length/2];
		for (int i = 0; i < data.length/2; i++)
		{
			right[i]=data[data.length/2+i];
		}
		return right;
	}
	
}
