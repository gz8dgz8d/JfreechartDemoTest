

public class ByteUtil
{


    private ByteUtil()
    {
        throw new UnsupportedOperationException();
    }

    public static int getIVal(byte[] in, int offset, int size)
    {
        if (size == 4)
        {
            int ret = (in[offset + 3] & 0xff);
            ret = (ret << 8) | (in[offset + 2] & 0xff);
            ret = (ret << 8) | (in[offset + 1] & 0xff);
            ret = (ret << 8) | (in[offset] & 0xff);
            return (ret);
        }
        else if (size == 3)
        {
            String s = new String(in, offset, size);
            try
            {
                return Integer.parseInt(s);
            }
            catch (NumberFormatException e)
            {
                return -9999;
            }
        }
        else
        {
            return -9999;
        }
    }

    public static short getSVal(byte[] in, int offset)
    {
        short ret = (short) (in[offset + 1] & 0xff);
        ret = (short) ((ret << 8) | (short) (in[offset + 0] & 0xff));
        return (ret);
    }
    
    
	public static byte[] toByteArray(short value) {
		byte[] Result = new byte[2];

        Result[0] = (byte) ((value >>> (8*0)) & 0xFF);
        Result[1] = (byte) ((value >>> (8*1)) & 0xFF);
        
		return Result;
	}
	/**
	 * Converts an integer to a byte array
	 * @param	value	an integer
	 * @return	a byte array representing the integer
	 */	
	
	public static byte[] toByteArray(int value) {
		byte[] Result = new byte[4];

        Result[0] = (byte) ((value >>> (8*0)) & 0xFF);
        Result[1] = (byte) ((value >>> (8*1)) & 0xFF);
        Result[2] = (byte) ((value >>> (8*2)) & 0xFF);
        Result[3] = (byte) ((value >>> (8*3)) & 0xFF);
        
		return Result;
	}

	
	
	/**
	 * Converts a long to a byte array
	 * @param	longvalue	a long integer
	 * @return	a byte array representing the long integer
	 */	
	public static byte[] toByteArray(long value) {	
		byte[] Result = new byte[8];

        Result[0] = (byte) ((value >>> (8*0)) & 0xFF);
        Result[1] = (byte) ((value >>> (8*1)) & 0xFF);
        Result[2] = (byte) ((value >>> (8*2)) & 0xFF);
        Result[3] = (byte) ((value >>> (8*3)) & 0xFF);
        Result[4] = (byte) ((value >>> (8*4)) & 0xFF);
        Result[5] = (byte) ((value >>> (8*5)) & 0xFF);
        Result[6] = (byte) ((value >>> (8*6)) & 0xFF);
        Result[7] = (byte) ((value >>> (8*7)) & 0xFF);
        
		return Result;
	}
    public static int posDiffInFt(double lat1, double lat2, double lon1,
            double lon2)
    {
        double ftlat, ftlon;
        // Get the angle for lat
        ftlat = (Math.abs(lat1 - lat2)) * (7917D * 5280D * Math.PI / 360D);
        // Get the angle for long specified in radians
        ftlon = (Math.abs(lon1 - lon2)) * (7917D * 5280D * Math.PI / 360D)
                * Math.cos(((lat1 + lat2) / 2) * (Math.PI / 180D));

        // Return hypotenouse in feet
        return ((int) Math.sqrt(ftlat * ftlat + ftlon * ftlon));
    }
    
    /**
     * Reverses the contents of the byte array (BYTE ORDER)
     * @param buffer - a byte array
     * @param length - length of buffer
     */
    public static void reverse(byte[] buffer, int length)
    {
        for (int i = 0; i < length / 2; i++)
        {
            buffer[i] ^= buffer[length - i - 1]; //Uses XOR swap
            buffer[length - i - 1] ^= buffer[i];
            buffer[i] ^= buffer[length - i - 1];
        }
    }

}
