package com.steven.util.typehandle;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ByteUtil
 * 
 * @description 字节工具
 */
public class ByteUtil {
	/**
	 * 
	 * @method create
	 * @description 创建数组
	 * @param length
	 *            长度
	 * @param value
	 *            默认值
	 * @return
	 */
	public static byte[] create(int length, byte value) {
		// 空处理
		if (length <= 0) {
			return null;
		}
		// 构造数组
		byte[] create = new byte[length];
		for (int i = 0; i < create.length; i++) {
			create[i] = value;
		}
		return create;
	}

	/**
	 * 
	 * @method split
	 * @description 分割字节数组
	 * @param data
	 * @param length
	 * @return
	 */
	public static byte[][] split(byte[] data, int length) {
		// 空处理
		if (null == data || data.length == 0) {
			return null;
		}

		// 输出结果
		byte[][] split = null;
		// 行
		int row;
		// 列
		int col = length;

		// 构造数组
		if (data.length % length == 0) {
			row = data.length / length;
		} else {
			row = data.length / length + 1;
		}
		split = new byte[row][col];

		// 分割
		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (index < data.length) {
					split[i][j] = data[index];
				} else {
					split[i][j] = (byte) 0x00;
					break;
				}
				index++;
			}
		}

		return split;
	}

	/**
	 * 
	 * @method xor
	 * @description 异或运算
	 * @param x
	 *            数据X
	 * @param y
	 *            数据Y
	 * @return
	 */
	public static byte[] xor(byte[] x, byte[] y)
			throws IllegalArgumentException {
		// 空处理
		if (null == x || x.length == 0 || null == y || y.length == 0) {
			return null;
		}
		// 长度不同，不进行异或运算
		if (x.length != y.length) {
			throw new IllegalArgumentException("传入的两个字节数组长度不同，不进行异或运算");
		}

		byte[] xor = new byte[x.length];

		for (int i = 0; i < xor.length; i++) {
			xor[i] = (byte) (x[i] ^ y[i]);
		}
		return xor;
	}

	/***
	 * 
	 * @method format
	 * @description 格式化字节
	 * @param data
	 *            数据
	 * @param gourp
	 *            按几位分组
	 * @param value
	 *            补位数值1
	 * @param value1
	 *            补位数值2
	 * @param append
	 *            是否强制追补字节
	 * @return
	 */
	public static byte[] format(byte[] data, int gourp, int value1, int value2,
			boolean append) {
		// 空处理
		if (null == data || data.length == 0) {
			return null;
		}
		// 不追补直接返回
		if (!append && data.length % gourp == 0) {
			return data;
		}
		// 构建字节数组
		byte[] format = new byte[data.length + (gourp - data.length % gourp)];
		// 复制字节
		for (int i = 0; i < data.length; i++) {
			format[i] = data[i];
		}

		// 组装字节
		for (int i = 0; i < gourp - data.length % gourp; i++) {
			if (i > 0) {
				format[data.length + i] = (byte) value2;
			} else {
				format[data.length] = (byte) value1;
			}
		}
		return format;
	}

	/***
	 * 
	 * @method format
	 * @description 格式化字节数组
	 * @param data
	 *            数据
	 * @param gourp
	 *            按几位分组
	 * @param value
	 *            补位数值
	 * @param append
	 *            是否强制追补字节
	 * @return
	 */
	public static byte[] format(byte[] data, int gourp, int value,
			boolean append) {
		// 空处理
		if (null == data || data.length == 0) {
			return null;
		}
		// 不追补直接返回
		if (!append && data.length % gourp == 0) {
			return data;
		}
		// 构建字节数组
		byte[] format = new byte[data.length + (gourp - data.length % gourp)];
		// 复制字节
		for (int i = 0; i < data.length; i++) {
			format[i] = data[i];
		}

		// 组装字节
		for (int i = 0; i < gourp - data.length % gourp; i++) {
			format[data.length + i] = (byte) value;
		}
		return format;
	}

	/**
	 * 
	 * @method cut
	 * @description 截取字节数组
	 * @param bytes
	 *            原数组
	 * @param start
	 *            正数从左边开始截，负数从右边开始截
	 * @param length
	 *            长度
	 * @return
	 */
	public static byte[] cut(byte[] data, int start, int length) {
		// 空处理
		if (null == data || data.length == 0) {
			return null;
		}
		// 边界判断
		if ((start >= 0 && (start + length) > data.length)
				|| (start < 0 && (length - start - 1) > data.length)) {
			throw new ArrayIndexOutOfBoundsException("超出原数组边界");
		}

		// 返回的数组
		byte[] sub_byte = new byte[length];

		for (int i = 0; i < sub_byte.length; i++) {
			// 正数从左边开始截取
			if (start >= 0) {
				sub_byte[i] = data[start + i];
			}
			// 负数从右边开始截取
			else {
				sub_byte[sub_byte.length - 1 - i] = data[data.length + start
						- i];
			}

		}
		return sub_byte;
	}

	/**
	 * 
	 * @method replace
	 * @description 替换字节数组中的字节
	 * @param src_bytes
	 *            原始字节数组
	 * @param new_bytes
	 *            新字节
	 * @param start
	 *            正数从左边开始替换，负数从右边开始替换
	 * @return
	 */
	public static byte[] replace(byte[] src_bytes, byte[] new_bytes, int start) {
		// 空处理
		if (null == src_bytes || src_bytes.length == 0) {
			return null;
		}
		if (new_bytes == null || new_bytes.length == 0) {
			return src_bytes;
		}
		if (new_bytes.length >= src_bytes.length) {
			return new_bytes;
		}

		// 开始替换
		for (int i = 0; i < new_bytes.length; i++) {
			// 正数从左边开始替换
			if (start >= 0) {
				src_bytes[start + i] = new_bytes[i];
			}
			// 负数从右边开始替换
			else {
				src_bytes[src_bytes.length + start - i] = new_bytes[new_bytes.length
						- 1 - i];
			}

		}
		return src_bytes;
	}

	/**
	 * 
	 * @method append
	 * @description 追加字节数组
	 * @param data1
	 *            字节数组1
	 * @param data2
	 *            字节数组2
	 * @return
	 */
	public static byte[] append(byte[] data1, byte[] data2) {
		byte[] append = null;
		// 空处理
		if (null == data1 || data1.length == 0) {
			return data2;
		}
		if (null == data2 || data2.length == 0) {
			return data1;
		}
		// 构造数组
		append = new byte[data1.length + data2.length];
		// 复制data1
		for (int i = 0; i < data1.length; i++) {
			append[i] = data1[i];
		}
		// 追加data2
		for (int i = 0; i < data2.length; i++) {
			append[data1.length + i] = data2[i];
		}
		return append;
	}

	/**
	 * 
	 * @method append
	 * @description 追加字节数组
	 * @param data1
	 *            字节
	 * @param data2
	 *            字节数组
	 * @return
	 */
	public static byte[] append(byte data1, byte[] data2) {
		byte[] append = null;
		// 空处理
		if (null == data2 || data2.length == 0) {
			append = new byte[1];
			append[0] = data1;
			return append;
		}
		// 构造数组
		append = new byte[data2.length + 1];
		// 复制data1
		append[0] = data1;
		// 追加data2
		for (int i = 0; i < data2.length; i++) {
			append[i + 1] = data2[i];
		}
		return append;
	}

	/**
	 * 
	 * @method append
	 * @description 追加字节
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static byte[] append(byte[] data1, byte data2) {
		byte[] append = null;
		// 空处理
		if (null == data1 || data1.length == 0) {
			append = new byte[1];
			append[0] = data2;
			return append;
		}
		// 构造数组
		append = new byte[data1.length + 1];
		// 复制data1
		for (int i = 0; i < data1.length; i++) {
			append[i] = data1[i];
		}
		// 追加data2
		append[data1.length] = data2;
		return append;
	}

	/**
	 * 功能描述：把两个字节的字节数组转化为整型数据，高位补零，例如：<br/>
	 * 有字节数组byte[] data = new byte[]{1,2};转换后int数据的字节分布如下：<br/>
	 * 00000000 00000000 00000001 00000010,函数返回258
	 * 
	 * @param lenData
	 *            需要进行转换的字节数组
	 * @return 字节数组所表示整型值的大小
	 */
	public static int bytesToIntWhereByteLengthEquals2(byte lenData[]) {
		if (lenData.length != 2) {
			return -1;
		}
		byte fill[] = new byte[] { 0, 0 };
		byte real[] = new byte[4];
		System.arraycopy(fill, 0, real, 0, 2);
		System.arraycopy(lenData, 0, real, 2, 2);
		int len = byteToInt(real);
		return len;

	}

	/**
	 * 功能描述：将byte数组转化为int类型的数据
	 * 
	 * @param byteVal
	 *            需要转化的字节数组
	 * @return 字节数组所表示的整型数据
	 */
	public static int byteToInt(byte[] byteVal) {
		int result = 0;
		for (int i = 0; i < byteVal.length; i++) {
			int tmpVal = (byteVal[i] << (8 * (3 - i)));
			switch (i) {
			case 0:
				tmpVal = tmpVal & 0xFF000000;
				break;
			case 1:
				tmpVal = tmpVal & 0x00FF0000;
				break;
			case 2:
				tmpVal = tmpVal & 0x0000FF00;
				break;
			case 3:
				tmpVal = tmpVal & 0x000000FF;
				break;
			}

			result = result | tmpVal;
		}
		return result;
	}

	// 1、解析TLV
	public static List<TransInfo> resolveTransInfo(byte[] tlvByte) {
		int iPos = 0;
		List<TransInfo> transInfoList = new ArrayList<TransInfo>();
		for (int i = 0; i < tlvByte.length;) {
			// 类型
			byte[] type = cut(tlvByte, iPos, 2);
			iPos += 2;
			// 长度
			byte len = tlvByte[iPos];
			iPos++;
			if (len != 0) {
				// 数据
				byte[] data = cut(tlvByte, iPos, len);
				iPos += len;
				i += len + 3;
				TransInfo info = new TransInfo(type, data);
				transInfoList.add(info);
			} else {
				// 数据
				iPos += len;
				i += len + 3;
				TransInfo info = new TransInfo(type, HexUtil.toByte(""));
				transInfoList.add(info);
			}

		}
		return transInfoList;
	}

	// 封装TLV
	public static byte[] packTransInfo(List<TransInfo> infoList) {
		byte[] bytes = new byte[1024];
		int iPos = 0;
		for (TransInfo info : infoList) {
			// 类型
			byte[] type = info.getInfoType();
			System.arraycopy(type, 0, bytes, iPos, 2);
			iPos += 2;
			if (info.getInfo() == null) {
				bytes[iPos] = 0x00;
				iPos++;
			} else {
				// 长度
				byte lenbyte = (byte) info.getInfo().length;
				bytes[iPos] = lenbyte;
				iPos++;
				// 数据
				System.arraycopy(info.getInfo(), 0, bytes, iPos,
						info.getInfo().length);
				iPos += info.getInfo().length;
			}
		}
		return cut(bytes, 0, iPos);
	}

	public static int[] getBit(byte src, int bitLen) {
		int[] result = new int[bitLen];
		for (int i = 0; i < bitLen; i++) {
			result[i] = src >> i & 0x01;
		}

		return result;
	}
}
