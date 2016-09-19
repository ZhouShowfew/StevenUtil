package com.steven.util.typehandle;


/**
 * 
 * HexUtil
 *
 * @description 十六进制工具
 */
public class HexUtil
{
	/**
	 * 
	 * @method toASCII
	 * @description 字节数组转ASCII字符
	 * @param data  
	 * @return
	 */
	public static String toASCII(byte[] data) throws IllegalArgumentException
	{
		// NULL处理
		if (null == data)
		{
			return null;
		}
		// 空处理
		if (data.length == 0)
		{
			return "";
		}
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < data.length; i++)
		{
			//对0xFF处理
			if(data[i]==-1)
			{
				buff.append("F");
			}
			else if(data[i]>=32&&data[i]<=126)
			{
				buff.append((char) data[i]);
			}
			else 
			{
				throw new IllegalArgumentException("无法转为可见的ASCII字符");
			}
		}
		return buff.toString();

	}

	/**
	 * 
	 * @method toByte
	 * @description ASCII字符转字节数组
	 * @param asc ASCII字符
	 * @return
	 */
	public static byte[] toByte(String asc)
	{
		
		// NULL处理
		if (null == asc||asc.trim().equals(""))
		{
			return null;
		}
		return asc.getBytes();
	}
	
	/***
	 * 
	 * @method toString
	 * @description BCD码转换成阿拉伯数字 
	 * 				例如有数组{0x12,0x34,0x56}
	 *              转换为阿拉伯数字字符串后为123456
	 * @param data
	 * @return
	 */
	public static String toString(byte[] bcd)
	{

		// NULL处理
		if (null == bcd)
		{
			return null;
		}
		// 空处理
		if (bcd.length == 0)
		{
			return "";
		}

		StringBuffer buffer = new StringBuffer(bcd.length * 2);
		for (int i = 0; i < bcd.length; i++)
		{

			switch ((bcd[i] & 0xf0) >>> 4)
			{
			case 10:
				buffer.append("A");
				break;
			case 11:
				buffer.append("B");
				break;
			case 12:
				buffer.append("C");
				break;
			case 13:
				buffer.append("D");
				break;
			case 14:
				buffer.append("E");
				break;
			case 15:
				buffer.append("F");
				break;
			default:
				buffer.append((byte) ((bcd[i] & 0xf0) >>> 4));
				break;
			}
			switch (bcd[i] & 0x0f)
			{
			case 10:
				buffer.append("A");
				break;
			case 11:
				buffer.append("B");
				break;
			case 12:
				buffer.append("C");
				break;
			case 13:
				buffer.append("D");
				break;
			case 14:
				buffer.append("E");
				break;
			case 15:
				buffer.append("F");
				break;
			default:
				buffer.append((byte) (bcd[i] & 0x0f));
				break;
			}

		}
		return buffer.toString();
	}
	
	
	
	
	/**
	 * 
	 * @method isBCD
	 * @description 是否为BCD字符串
	 * @param asc BCD字符串
	 * @return
	 */
	public static boolean isBCD(String bcd)
	{
		boolean ans = false;
		// 化为大写
		bcd = bcd.toUpperCase();
		for (int i = 0; i < bcd.length(); i++)
		{
			ans = false;
			char c = bcd.charAt(i);
			if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || 
				c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'E' || c == 'F')
			{
				ans = true;
			}
			else 
			{
				break;
			}
		}
		return ans;
	}
	
	
	/**
	 * 
	 * @method toBCD
	 * @description ASCII字节数组转BCD编码
	 * @param asc   ASCII字节
	 * @return
	 */
	public static byte[] toBCD(byte[] asc) throws IllegalArgumentException
	{
		// 空处理
		if (null == asc)
		{
			return null;
		}
		// 获得原始字节长度
		int len = asc.length;
		byte[] tmp = null;
		// 补位
		if (len % 2 != 0)
		{
			tmp = new byte[len + 1];
			//复制字节
			for (int i = 0; i < asc.length; i++)
			{
				tmp[i] = asc[i];
			}
			//补十六进制0
			tmp[len] =0x0;
			
		} 
		else
		{
			tmp = new byte[len];
			//复制字节
			for (int i = 0; i < asc.length; i++)
			{
				tmp[i] = asc[i];
			}
		}
		
		// 输出的bcd字节数组
		byte bcd[] = new byte[tmp.length / 2];

		
		int j, k;
		
		for (int p = 0; p < tmp.length / 2; p++)
		{
			// 双
			//处理0
			if(tmp[2 * p]==0x0)
			{
				j =0x0;
			}
			else if(tmp[2 * p]==0xf)
			{
				j =0xf;
			}
			//处理字符0-9
			else if ((tmp[2 * p] >= '0') && (tmp[2 * p] <= '9'))
			{
				j = tmp[2 * p] - '0';
			}
			//处理字符A
			else if (tmp[2 * p] == 'a' || tmp[2 * p] == 'A')
			{
				j = 0xa;
			}
			//处理字符B
			else if (tmp[2 * p] == 'b' || tmp[2 * p] == 'B')
			{
				j = 0xb;
			}
			//处理字符C
			else if (tmp[2 * p] == 'c' || tmp[2 * p] == 'C')
			{
				j = 0xc;
			}
			//处理字符D以及字符=
			else if (tmp[2 * p] == 'D' || tmp[2 * p] == 'd' || tmp[2 * p] == '=')
			{
				j = 0xd;
			}
			//处理字符E
			else if (tmp[2 * p] == 'e' || tmp[2 * p] == 'E')
			{
				j = 0xe;
			}
			//处理字符F
			else if (tmp[2 * p] == 'f' || tmp[2 * p] == 'F')
			{
				j = 0xf;
			}
			else
			{
				throw new IllegalArgumentException("非BCD字节");
			}

			// 单
			//处理0x0
			if(tmp[2 * p + 1]==0x0)
			{
				k =0x0;
			}
			//处理0xf
			else if(tmp[2 * p + 1]==0xf)
			{
				k =0xf;
			}
			//处理字符0-9
			else if((tmp[2 * p + 1] >= '0') && (tmp[2 * p + 1] <= '9'))
			{
				k = tmp[2 * p + 1] - '0';
			}
			//处理字符A
			else if (tmp[2 * p + 1] == 'a' || tmp[2 * p + 1] == 'A')
			{
				k = 0xa;
			}
			//处理字符B
			else if (tmp[2 * p + 1] == 'b' || tmp[2 * p + 1] == 'B')
			{
				k = 0xb;
			}
			//处理字符C
			else if (tmp[2 * p + 1] == 'c' || tmp[2 * p + 1] == 'C')
			{
				k = 0xc;
			}
			//处理字符D以及字符=
			else if (tmp[2 * p + 1] == 'D' || tmp[2 * p + 1] == 'd' || tmp[2 * p + 1]  == '=' )
			{
				k = 0xd;
			}
			//处理字符E
			else if (tmp[2 * p + 1] == 'e' || tmp[2 * p + 1] == 'E')
			{
				k = 0xe;
			}
			//处理字符F
			else if (tmp[2 * p + 1] == 'f' || tmp[2 * p + 1] == 'F')
			{
				k = 0xf;
			}
			else
			{
				throw new IllegalArgumentException("非BCD字节");
			}

			int a = (j << 4) + k;
			byte b = (byte) a;

			bcd[p] = b;
		}

		return bcd;

	}
	
	

	/**
	 * 
	 * @method toBCD
	 * @description 字符串转化为BCD压缩码
	 *              例如字符串"12345678",
	 *              压缩之后的字节数组内容为{0x12,0x34,0x56,0x78}
	 * @param asc   需要进行压缩的字符串
	 * @return
	 */
	public static byte[] toBCD(String asc) throws IllegalArgumentException
	{
		// 空处理
		if (null == asc || asc.trim().equals(""))
		{
			return null;
		}
		//替换=号
		asc = asc.replace('=', 'D').toUpperCase();
		// 非BCD编码处理
		if (!isBCD(asc))
		{
			throw new IllegalArgumentException("非BCD字符串");
		}
		return toBCD(asc.getBytes());
	}
	
	
	/**
	 * 
	 * @method printBCD
	 * @description 打印BCD
	 * @param bcd BCD字符串
	 */
	public static void printBCD(String bcd)
	{
		//空处理
		if(null==bcd||bcd.trim().equals(""))
		{
			throw new NullPointerException("打印的BCD字符串为NULL");
		}
		if(!isBCD(bcd))
		{
			throw new IllegalArgumentException("非BCD字符串");
		}
		for (int i = 0; i < bcd.length(); i++)
		{
			if(i>0&&i%2==0)
			{
				//打空格
				System.out.print(" ");
			}
			System.out.print(bcd.charAt(i));
			
		}
		System.out.println();
	}
	
	
	/**
	 * 
	 * @method printBCD
	 * @description 打印BCD
	 * @param tip 标签
	 * @param bcd BCD字符串
	 */
	public static void printBCD(String tip, String bcd)
	{
		//空处理
		if(null==bcd||bcd.trim().equals(""))
		{
			throw new NullPointerException("打印的BCD字符串为NULL");
		}
		if(!isBCD(bcd))
		{
			throw new IllegalArgumentException("非BCD字符串");
		}
		System.out.print(tip);
		for (int i = 0; i < bcd.length(); i++)
		{
			if(i>0&&i%2==0)
			{
				//打空格
				System.out.print(" ");
			}
			System.out.print(bcd.charAt(i));
			
		}
		System.out.println();
	}

	/* byte to  int */
	public final static int getInt(byte[] buf, boolean asc, int len) {
	    if (buf == null) {
	      throw new IllegalArgumentException("byte array is null!");
	    }
	    if (len > 4) {
	      throw new IllegalArgumentException("byte array size > 4 !");
	    }
	    int r = 0;
	    if (asc)
	      for (int i = len - 1; i >= 0; i--) {
	        r <<= 8;
	        r |= (buf[i] & 0x000000ff);
	      }
	    else
	      for (int i = 0; i < len; i++) {
	        r <<= 8;
	        r |= (buf[i] & 0x000000ff);
	      }
	    return r;
	}
}
