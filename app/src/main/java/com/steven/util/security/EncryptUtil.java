package com.steven.util.security;




/**
 * 
 * EncryptUtil
 * 
 * @description 加密工具
 */
public class EncryptUtil
{

	/**
	 * 
	 * @method PBOC_3DES_MAC加密
	 * @description  加密
	 * @param data   数据
	 * @param key    密钥(16字节)
	 * @param vector 初始向量（8字节）
	 * @return 
	 */
	public static byte[] PBOC_3DES_MAC(byte[] data, byte[] key, byte[] vector)
	{

		//1、格式化字节(补齐十六进制80)
		byte[] format_byte = ByteUtil.format(data,8,0X80,0X00,true);

		//左密钥
		byte[] key_left= getLeftKey(key);
		//右密钥
		byte[] key_right= getRightKey(key);

		//2、分割数据
		byte[][] split = ByteUtil.split(format_byte,8);
		
		//3、异或运算及DES
		//异或字节(初始为初始向量)
		byte[] xOR = vector;
		
		for (int i = 0; i < split.length; i++)
		{
			
			//进行异或运算
			xOR= ByteUtil.xor(split[i],xOR);

			//使用密钥的左半边进行DES加密
			xOR=DESUtil.encrypt(xOR,key_left);

		}
		
		//结果
		byte[] resault=null;
		//使用密钥的右半边进行DES解密
		resault=DESUtil.decrypt(xOR,key_right);

		//使用密钥的左半边进行DES加密得到最后结果
		resault=DESUtil.encrypt(resault,key_left);

		return resault;
	}


	/**
	 * 
	 * @method TDES_DOUBLE
	 * @description 3DES(双倍长)加密
	 * @param data 数据
	 * @param key  密钥(16字节)
	 * @return
	 */
	public static byte[] TDES_DOUBLE(byte[] data, byte[] key)
	{
		byte[] encrypt=null;
		
		//左密钥
		byte[] key_left = getLeftKey(key);

		//右密钥
		byte[]  key_right= getRightKey(key);

		//1.使用密钥的左半边对数据进行DES加密，得到temp1
		byte[] temp1=DESUtil.encrypt(data,key_left);
		//2.使用密钥的右半边对数据进行DES解密，得到temp2
		byte[] temp2=DESUtil.decrypt(temp1,key_right);
		//3.使用密钥的左半边对数据进行DES加密，得到最终加密结果
		encrypt=DESUtil.encrypt(temp2,key_left);
		
		return encrypt;
	}
	
	
	/**
	 * @description 3DES解密
	 * @author linpeng 2014-06-16
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] TDES_DECRYPT(byte[] data, byte[] key) {
		// 将16字节密钥分解为各8字节的两个子密钥
		byte[] keyleft = new byte[8];
		System.arraycopy(key, 0, keyleft, 0, 8);
		byte[] keyright = new byte[8];
		System.arraycopy(key, 8, keyright, 0, 8);
		
		// 初始化当前密文
		byte[] cursorSrouceBytes = new byte[8];
		System.arraycopy(data, 0, cursorSrouceBytes, 0, 8);
		// 加密步骤一：第一个子密钥对当前密文解密
		byte[] leftencrypt1 = DESUtil.decrypt(cursorSrouceBytes, keyleft);
		// 加密步骤二：第二个子密钥对步骤一加密结果进行加密
		byte[] rightdecrypt2 = DESUtil.encrypt(leftencrypt1, keyright);
		// 加密步骤三A：第一个子密钥对步骤三结果进行解密
		byte[] leftencrypt3 = DESUtil.decrypt(rightdecrypt2, keyleft); 
		
		if (data.length > 8) {// 判断是否含下一个8字节密文数据块
			// 初始化下一个密文数据块
			byte[] subSourceBytes = new byte[data.length - 8];
			System.arraycopy(data, 8, subSourceBytes, 0, data.length - 8);
			// 下一个密文数据库解密结果
			byte[] subResultBytes = TDES_DECRYPT(subSourceBytes, key);
			// 生成解密结果
			byte[] resultBytes = new byte[subResultBytes.length + leftencrypt3.length];
			System.arraycopy(leftencrypt3, 0, resultBytes, 0, leftencrypt3.length);
			System.arraycopy(subResultBytes, 0, resultBytes, leftencrypt3.length, subResultBytes.length);
			return resultBytes;
		}
		
		return leftencrypt3;
	}

	
	/**
	 * 
	 * @method MAC_UnionPay_ECB
	 * @description MAC加密(银联ECB)
	 * @param data  数据
	 * @param key   密钥(16字节)
	 * @return      MAC(16字节)
	 */
	public static byte[] MAC_UnionPay_ECB(byte[] data,byte[] key)
	{
		if(null==data)
		{
			return null;
		}
		//1.格式化字节(补齐十六进制0X00)
		byte[] format_byte = ByteUtil.format(data,8,0x00,false);
		//2、分割数据
		byte[][] split = ByteUtil.split(format_byte,8);
		//异或字节
		byte[] xOR = split[0];
		for (int i = 1; i < split.length; i++)
		{
			//3、进行异或运算
			xOR= ByteUtil.xor(split[i],xOR);
		}
		//4、最后8个字节转换成16个HEXDECIMAL
		xOR= HexUtil.toString(xOR).getBytes();
		byte[] resault=null;
		
		byte[] left= ByteUtil.split(xOR,8)[0];
		byte[] right= ByteUtil.split(xOR,8)[1];
		
		//5、取前8个字节用MAK加密
		resault=TDES_DOUBLE(left, key);
		//6、将加密后的结果与后8个字节异或
		resault= ByteUtil.xor(resault, right);
		//7、用异或的结果再进行一次单倍长密钥算法运算
		resault=TDES_DOUBLE(resault, key);
		//8、将运算后的结果转换成16个HEXDECIMAL
		resault= HexUtil.toString(resault).getBytes();
		
		return resault;
		
	}

	/**
	 * 
	 * @method MAC_ECB
	 * @description MAC加密(ECB)
	 * @param data  数据
	 * @param key   密钥(16字节)
	 * @return
	 */
	public static byte[] MAC_ECB(byte[] data,byte[] key)
	{
		if(null==data)
		{
			return null;
		}
		
		
		//1.格式化字节(补齐十六进制0X00)
		byte[] format_byte = ByteUtil.format(data,8,0x00,false);
		
		//2、分割数据
		byte[][] split = ByteUtil.split(format_byte,8);
		//异或字节
		byte[] xOR = split[0];
		for (int i = 1; i < split.length; i++)
		{
			//3、进行异或运算
			xOR= ByteUtil.xor(split[i],xOR);
		}
		
		
		byte[] resault=null;
		
		byte[] left= ByteUtil.split(key,8)[0];
		byte[] right= ByteUtil.split(key,8)[1];
		
		//用MAK的左半部分对异或运算后的最后8个字节做单倍长加密
		resault=DESUtil.encrypt(xOR, left);
		//将加密后的结果异或0x8000000000000000
		byte[] temp={(byte) 0x80,0x00,0x00,0x00,
				     0x00,0x00,0x00,0x00};
		resault= ByteUtil.xor(resault,temp);
		
		//用MAK的左半部分对异或后的结果进行单倍长加密
		resault=DESUtil.encrypt(resault,left);
		//用MAK的右半部分对上一步加密后的结果进行单倍长解密
		resault=DESUtil.decrypt(resault,right);
		//用MAK的左半部分对上一步解密的结果进行单倍长加密
		resault=DESUtil.encrypt(resault,left);
		
		
		return resault;
	}
	
	
	
	/**
	 * 
	 * @method SwipeCardMode_2
	 * @description 磁道信息加密(银联加密方式)
	 * @param bcd  磁道数据(BCD)
	 * @param key	密钥(16字节)
	 * @return     
	 */
	public static byte[] SwipeCardMode_2(byte[] bcd,byte[] key)
	{
		byte[] tdb=null;
		//磁道数据
		if(bcd!=null&&bcd.length>0)
		{
			try
			{
				//还原编码
				String bcd_str= HexUtil.toString(bcd);
				//System.out.println("【BCD】"+bcd_str);
				
				//磁道数据
				String tdb_str=bcd_str.substring(bcd_str.length()-1-16,bcd_str.length()-1);
				char last_char=bcd_str.charAt(bcd_str.length()-1);
				boolean last_flag=false;
				if('F'==last_char)
				{
					last_flag=true;
					tdb_str=bcd_str.substring(bcd_str.length()-2-16,bcd_str.length()-2);
				}
				//System.out.println("【TBD】"+tdb_str);
				byte[] temp_bcd= HexUtil.toBCD(tdb_str);
				
				//加密
				byte[] tdes= EncryptUtil.TDES_DOUBLE(temp_bcd, key);
				String teds_str= HexUtil.toString(tdes);
				//System.err.println("【TDES_TBD】"+teds_str);
				
				if(last_flag)
				{
					tdb= ByteUtil.replace(bcd_str.getBytes(),teds_str.getBytes(),-3);
				}
				else
				{
					tdb= ByteUtil.replace(bcd_str.getBytes(),teds_str.getBytes(),-2);
				}
			
				tdb= HexUtil.toBCD(tdb);
				//System.out.println("【TDES_DATA】"+HexUtil.toString(tdb));
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				return null;
			}
		}
		return tdb;
	}
	

	/**
	 * 
	 * @method SwipeCardWithMethodThree
	 * @description 磁道信息加密(迷你付加密方式)
	 * @param bcd  磁道数据(BCD)
	 * @param key	密钥(16字节)
	 */
	public static byte[] SwipeCardMode_3(byte[] bcd,byte[] key)
	{
		byte[] tdb=null;
		//磁道数据
		if(bcd!=null&&bcd.length>0)
		{
			//凑足十六进制FF
			//System.out.println("【DATA】"+HexUtil.toString(bcd));
			byte[] TDB= ByteUtil.format(bcd,8,0xFF,false);
			//System.out.println("【TDB】"+HexUtil.toString(TDB));
			byte[] TDES_TDB= EncryptUtil.TDES_DOUBLE(TDB, key);
			tdb=TDES_TDB;
			//System.out.println("【TDES_TDB】"+HexUtil.toString(tdb));
			return tdb;
		}
		return tdb;
	}
	
	
	
	/**
	 * 
	 * @method SwipeCardMode_4（银联电话支付加密）
	 * @description 磁道信息加密(银联电话支付加密)
	 * @param tdb2_bcd 二磁道数据(BCD)
	 * @param tdb3_bcd 三磁道数据(BCD)
	 * @param key      密钥(16字节)
	 * @return
	 */
	public static byte[] SwipeCardMode_4(byte[] tdb2_bcd,byte[] tdb3_bcd,byte[] key)
	{
		byte[] tdb =null;
		
		try
		{
			//格式化二磁道数据
			byte[] default_tbd2= ByteUtil.create(48,(byte) 0xF);
			byte[] format_tdb2= ByteUtil.replace(default_tbd2, HexUtil.toString(tdb2_bcd).getBytes(),0);
			
			//格式化三磁道数据
			byte[] format_tdb3=null;
			if(null!=tdb3_bcd&&tdb3_bcd.length>0)
			{
				format_tdb3= ByteUtil.format(HexUtil.toString(tdb3_bcd).getBytes(),16,0xF, true);
			}
			else 
			{
				format_tdb3= ByteUtil.create(16,(byte) 0xF);
			}
			
			//合并数据
			byte[] temp_tdb= ByteUtil.append(format_tdb2, format_tdb3);
			//转为BCD
			byte[] tdb_temp_bcd= HexUtil.toBCD(temp_tdb);
			
			
			//System.out.println("【TDB_BCD】"+HexUtil.toString(tdb_temp_bcd));
			
			byte[][] split= ByteUtil.split(tdb_temp_bcd,8);
			
			//分组加密
			tdb =TDES_DOUBLE(split[0], key);
			for (int i = 1; i < split.length; i++)
			{
				tdb= ByteUtil.append(tdb, TDES_DOUBLE(split[i], key));
			}
			//System.out.println("【TDES_TDB】"+HexUtil.toString(tdb));
		}
		catch (Exception e)
		{
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		}
		return tdb;
	}
	
	
	/**
	 * 
	 * @method PINBLOCK
	 * @description  PINBLOCK加密
	 * @param pan    处过的卡号(BCD)
	 * @param pin    密码(BCD)
	 * @param pinKey 密钥(十六字节)
	 * @return 
	 */
	public static byte[] PINBLOCK(byte[] pan,byte[] pin,byte[] key)
	{
		byte[] des_pin_block=null;
		try
		{
			//处理PAN
			String pan_str= HexUtil.toString(pan);
			//从右边第二位开始向左截取12位
			pan_str="00"+"00"+pan_str.substring(pan_str.length()-1-12,pan_str.length()-1);
			pan= HexUtil.toBCD(pan_str);
			
			//处理pin
			String pin_str= HexUtil.toString(pin);
			String pin_len= String.format("%02d",pin_str.length());
			String temp_pin=pin_len+pin_str;
			byte[] temp_hex_pin= HexUtil.toBCD(temp_pin);
			
			
			byte[] hex_pin= ByteUtil.create(8,(byte) 0xFF);
			for (int i = 0; i < temp_hex_pin.length; i++)
			{
				hex_pin[i]=temp_hex_pin[i];
			}
			//System.out.println("【PIN】"+HexUtil.toString(hex_pin));
			byte[] pin_block= ByteUtil.xor(pan, hex_pin);
			//System.out.println("【PINBLOCK】"+HexUtil.toString(pin_block));
			des_pin_block=TDES_DOUBLE(pin_block, key);
			//System.out.println("【TDES_PINBLOCK】"+HexUtil.toString(des_pin_block));
		}
		catch (Exception e)
		{
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		}
		return des_pin_block;
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
