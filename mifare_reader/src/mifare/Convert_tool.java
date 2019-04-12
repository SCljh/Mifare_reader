package mifare;

public class Convert_tool {
	
	 public static String convertUTF8ToString(String s) {  
	        if (s == null || s.equals("")) {  
	            return null;  
	        }  
	          
	        try {  
	            s = s.toUpperCase();  
	  
	            int total = s.length() / 2;  
	            int pos = 0;  
	  
	            byte[] buffer = new byte[total];  
	            for (int i = 0; i < total; i++) {  
	  
	                int start = i * 2;  
	  
	                buffer[i] = (byte) Integer.parseInt(  
	                        s.substring(start, start + 2), 16);  
	                pos++;  
	            }  
	  
	            return new String(buffer, 0, pos, "UTF-8");  
	              
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return s;  
	    }
	 
	 public static String convertStringToUTF8(String s) {  
	        if (s == null || s.equals("")) {  
	            return null;  
	        }  
	        StringBuffer sb = new StringBuffer();  
	        try {  
	            char c;  
	            for (int i = 0; i < s.length(); i++) {  
	                c = s.charAt(i);  
	                if (c >= 0 && c <= 255) {  
	                    sb.append(c);  
	                } else {  
	                    byte[] b;  
	  
	                    b = Character.toString(c).getBytes("utf-8");  
	  
	                    for (int j = 0; j < b.length; j++) {  
	                        int k = b[j];  
	                        if (k < 0)  
	                            k += 256;  
	                        sb.append(Integer.toHexString(k).toUpperCase());  
	                        // sb.append("%" +Integer.toHexString(k).toUpperCase());  
	                    }  
	                }  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	  
	        }  
	        return sb.toString();  
	    } 

}
