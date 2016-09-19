package com.steven.util.prompt;


public class Log {
	
	public int VERBOSE = 2;
	public int DEBUG = 3;
	public int INFO = 4;
	public int WARN = 5;
	public int ERROR = 6;
	public int ASSERT = 2;
	public static int CLOSE_LOG = 7;
	
	public static int level = 4;
			
	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		Log.level = level;
	}

	public static void i(String msg){
		if(level < 5 && level != 2){
			android.util.Log.i(Log.class.getName(), msg);
		}
	}
	public static void i(String tag, String msg){
		if(level < 5 && level != 2){
			android.util.Log.i(tag, msg);
		}
	}
	
	public static void i(Class cls , String msg){
		if(level < 5 && level != 2){
			android.util.Log.i(cls.getName(), msg);
		}
	}
	
	public static void i(Class cls , String msg, Throwable tr){
		if(level < 5 && level != 2){
			android.util.Log.i(cls.getName(), msg,tr);
		}
	}

	public static void d(String msg){
		if(level < 4 && level != 2){
			android.util.Log.d(Log.class.getName(), msg);
		}
	}
	public static void d(String tag, String msg){
		if(level < 4 && level != 2){
			android.util.Log.d(tag, msg);
		}
	}
	
	public static void d(Class cls , String msg){
		if(level < 4 && level != 2){
			android.util.Log.d(cls.getName(), msg);
		}
	}
	
	public static void d(Class cls , String msg, Throwable tr){
		if(level < 4 && level != 2){
			android.util.Log.d(cls.getName(), msg,tr);
		}
	}
	
	public static void e(String msg){
		if(level < 7 && level != 2){
			android.util.Log.d(Log.class.getName(), msg);
		}
	}
	public static void e(String tag, String msg){
		if(level < 7 && level != 2){
			android.util.Log.d(tag, msg);
		}
	}
	
	public static void e(Class cls , String msg){
		if(level < 7 && level != 2){
			android.util.Log.d(cls.getName(), msg);
		}
	}
	
	public static void e (Class cls , String msg, Throwable tr){
		if(level < 7 && level != 2){
			android.util.Log.d(cls.getName(), msg,tr);
		}
	}
}
