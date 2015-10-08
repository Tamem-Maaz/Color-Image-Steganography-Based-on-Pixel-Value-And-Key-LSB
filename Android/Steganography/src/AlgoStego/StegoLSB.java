package AlgoStego;

import android.graphics.Color;
import android.util.Log;

public class StegoLSB
{
    int index_Of_All_Pixel = 0;
    public void stego(int[] all_Piexl_Color, String theSecretTOLSB)
    {
        String thePixelUsedSaveLSB = toBinaryString(String.valueOf(theSecretTOLSB.length()) + ";");
        index_Of_All_Pixel = all_Piexl_Color.length - 1;
        int indexInSecret = 0;
        int whereStop = 0;
        for (int i = index_Of_All_Pixel; i > 0; i--, index_Of_All_Pixel--)
        {
            //Color pixel = all_Piexl_Color[i];
            int red = Color.red(all_Piexl_Color[i]), green = Color.green(all_Piexl_Color[i]), blue = Color.blue(all_Piexl_Color[i]);
            for (int j = 0; j < 3; j++)
            {
                if (indexInSecret >= thePixelUsedSaveLSB.length())
                    break;
                if (j == 0)
                {
                    String theLSB = Integer.toBinaryString(Color.red(all_Piexl_Color[i]));
                    char[] theCharLSB = theLSB.toCharArray();
                    theCharLSB[theCharLSB.length - 1] = thePixelUsedSaveLSB.charAt(indexInSecret++);
                    //theLSB = "";
                    //for (int k = 0; k < theCharLSB.Length; k++)
                    //theLSB += theCharLSB[k];
                    red = convert_Piexl_To_Dec(theCharLSB);
                    whereStop = j;
                }
                else if (j == 1)
                {
                	String theLSB = Integer.toBinaryString(Color.green(all_Piexl_Color[i]));
                    char[] theCharLSB = theLSB.toCharArray();
                    theCharLSB[theCharLSB.length - 1] = thePixelUsedSaveLSB.charAt(indexInSecret++);
                    //theLSB = "";
                    //for (int k = 0; k < theCharLSB.Length; k++)
                    //    theLSB += theCharLSB[k];
                    green = convert_Piexl_To_Dec(theCharLSB);
                    whereStop = j;
                }
                else if (j == 2)
                {
                	String theLSB = Integer.toBinaryString(Color.blue(all_Piexl_Color[i]));
                    char[] theCharLSB = theLSB.toCharArray();
                    theCharLSB[theCharLSB.length - 1] = thePixelUsedSaveLSB.charAt(indexInSecret++);
                    //theLSB = "";
                    //for (int k = 0; k < theCharLSB.Length; k++)
                    //    theLSB += theCharLSB[k];
                    blue = convert_Piexl_To_Dec(theCharLSB);
                    whereStop = j;
                }
            }
            all_Piexl_Color[i] = Color.rgb(red, green, blue);
            if (indexInSecret >= thePixelUsedSaveLSB.length())
            {
                if (whereStop == 2)
                {
                    index_Of_All_Pixel--;
                    whereStop = 0;
                }
                else if (whereStop == 0)
                    whereStop = 1;
                else if (whereStop == 1)
                    whereStop = 2;
                break;
            }
        }
        //MessageBox.Show(">>>>>>>>>>>>   " + indexInSecret + " ,, " + index_Of_All_Pixel);
        // LSB for the Information
        indexInSecret = 0;
        for (int i = index_Of_All_Pixel; i > 0; i--, index_Of_All_Pixel--)
        {
            //Color pixel = all_Piexl_Color[i];
        	int red = Color.red(all_Piexl_Color[i]), green = Color.green(all_Piexl_Color[i]), blue = Color.blue(all_Piexl_Color[i]);
            for (int j = whereStop; j < 3; j++)
            {
                if (indexInSecret >= theSecretTOLSB.length())
                    break;
                if (j == 0)
                {
                	String theLSB = Integer.toBinaryString(Color.red(all_Piexl_Color[i]));
                    char[] theCharLSB = theLSB.toCharArray();
                    theCharLSB[theCharLSB.length - 1] = theSecretTOLSB.charAt(indexInSecret++);
                    //theLSB = "";
                    //for (int k = 0; k < theCharLSB.Length; k++)
                    //theLSB += theCharLSB[k];
                    red = convert_Piexl_To_Dec(theCharLSB);
                }
                else if (j == 1)
                {
                	String theLSB = Integer.toBinaryString(Color.green(all_Piexl_Color[i]));
                    char[] theCharLSB = theLSB.toCharArray();
                    theCharLSB[theCharLSB.length - 1] = theSecretTOLSB.charAt(indexInSecret++);
                    //theLSB = "";
                    //for (int k = 0; k < theCharLSB.Length; k++)
                    //    theLSB += theCharLSB[k];
                    green = convert_Piexl_To_Dec(theCharLSB);
                }
                else if (j == 2)
                {
                	String theLSB = Integer.toBinaryString(Color.blue(all_Piexl_Color[i]));
                    char[] theCharLSB = theLSB.toCharArray();
                    theCharLSB[theCharLSB.length - 1] = theSecretTOLSB.charAt(indexInSecret++);
                    //theLSB = "";
                    //for (int k = 0; k < theCharLSB.Length; k++)
                    //    theLSB += theCharLSB[k];
                    blue = convert_Piexl_To_Dec(theCharLSB);
                }
            }
            all_Piexl_Color[i] = Color.rgb(red, green, blue);
            if (indexInSecret >= theSecretTOLSB.length())
                break;
            whereStop = 0;
        }
        //MessageBox.Show(">>>>>>>>>>>>   " + indexInSecret + " ,, " + index_Of_All_Pixel);
        // empededk
    }


    public String extract(int[] all_Piexl_Color) throws Exception
    {
        index_Of_All_Pixel = all_Piexl_Color.length - 1;
        StringBuilder theTemp = new StringBuilder();
        StringBuilder thePixelUsed = new StringBuilder();
        Log.d(";", toBinaryString(";"));
        int whereStop = 0;
        int index8 = 0;
        for (int i = index_Of_All_Pixel; i > 0; i--, index_Of_All_Pixel--)
        {
            // Red
            String theLSB = Integer.toBinaryString(Color.red(all_Piexl_Color[i]));
            char[] theCharLSB = theLSB.toCharArray();
            theTemp.append(theCharLSB[theCharLSB.length - 1]);
            index8++;
            if (index8 == 8)
            {
                char c = toChar(theTemp.toString());
                if (c == ';')
                {
                    whereStop = 1;
                    break;
                }

                thePixelUsed.append(c);
                index8 = 0;
                theTemp = new StringBuilder();
            }
            // Green
            theLSB = Integer.toBinaryString(Color.green(all_Piexl_Color[i]));
            theCharLSB = theLSB.toCharArray();
            theTemp.append(theCharLSB[theCharLSB.length - 1]);
            index8++;
            if (index8 == 8)
            {
                char c = toChar(theTemp.toString());
                if (c == ';')
                {
                    whereStop = 2;
                    break;
                }
                thePixelUsed.append(c);
                index8 = 0;
                theTemp = new StringBuilder();
            }
            // Blue
            theLSB = Integer.toBinaryString(Color.blue(all_Piexl_Color[i]));
            theCharLSB = theLSB.toCharArray();
            theTemp.append(theCharLSB[theCharLSB.length - 1]);
            index8++;
            if (index8 == 8)
            {
                char c = toChar(theTemp.toString());
                if (c == ';')
                {
                    whereStop = 0;
                    index_Of_All_Pixel--;
                    break;
                }
                thePixelUsed.append(c);
                index8 = 0;
                theTemp = new StringBuilder();
            }

            
        }

        // after get who many bits are saved in the LSB
        Log.d("the theLSBPixelUsed", thePixelUsed.toString());
        int theLSBPixelUsed = Integer.parseInt(thePixelUsed.toString());
        index8 = 0;
        theTemp = new StringBuilder();
        thePixelUsed = new StringBuilder();
        for (int i = index_Of_All_Pixel, j = 0; j <= theLSBPixelUsed; i--)
        {
            for (int k = whereStop; k < 3; k++)
            {
                if (k == 0)
                {
                    // Red
                	String theLSB = Integer.toBinaryString(Color.red(all_Piexl_Color[i]));
                    char[] theCharLSB = theLSB.toCharArray();
                    theTemp.append(theCharLSB[theCharLSB.length - 1]);
                    index8++;
                    j++;
                    if (index8 == 8)
                    {
                        char c = toChar(theTemp.toString());
                        //if (c == '1')
                        //    MessageBox.Show("aaaaa");

                        thePixelUsed.append(c);
                        index8 = 0;
                        theTemp = new StringBuilder();
                        if (j == theLSBPixelUsed) break;
                    }
                }
                else if (k == 1)
                {
                    // Green
                	String theLSB = Integer.toBinaryString(Color.green(all_Piexl_Color[i]));
                    char[] theCharLSB = theLSB.toCharArray();
                    theTemp.append(theCharLSB[theCharLSB.length - 1]);
                    index8++;
                    j++;
                    if (index8 == 8)
                    {
                        char c = toChar(theTemp.toString());
                        //if (c == '1')
                        //    MessageBox.Show("aaaaa");
                        thePixelUsed.append(c);
                        index8 = 0;
                        theTemp = new StringBuilder();
                        if (j == theLSBPixelUsed) break;
                    }
                }
                else if (k == 2)
                {
                    // Blue
                	String theLSB = Integer.toBinaryString(Color.blue(all_Piexl_Color[i]));
                    char[] theCharLSB = theLSB.toCharArray();
                    theTemp.append(theCharLSB[theCharLSB.length - 1]);
                    index8++;
                    j++;
                    if (index8 == 8)
                    {
                        char c = toChar(theTemp.toString());
                        //if (c == '1')
                        //    MessageBox.Show("aaaaa");
                        thePixelUsed.append(c);
                        index8 = 0;
                        theTemp = new StringBuilder();
                        if (j == theLSBPixelUsed) break;
                    }
                }
            }
            whereStop = 0;
        }
        //MessageBox.Show(thePixelUsed.ToString());
        return thePixelUsed.toString();
    }
    
    // convert the b who readed from secret to Dec.
    public int convert_Piexl_To_Dec(char[] Tsecret_bits)
    {
        int b = 0;
        int bin = 1;
        for (int i = Tsecret_bits.length - 1; i >= 0; i--)
        {
            b += Integer.parseInt(String.valueOf(Tsecret_bits[i])) * bin;
            bin = bin * 2;
        }
        //MessageBox.Show(b.ToString());
        return b;
    }

    public String toBinaryString(String s) {
        char[] cArray = s.toCharArray();
        
        StringBuilder sb = new StringBuilder();
        for (char c : cArray)
        {
        	int ch = (int)c;
            String cBinaryString = Integer.toBinaryString(ch);

            if (cBinaryString.length() != 8)
            {
                String tempStr = "00000000";
                char[] bin_Of_b = tempStr.toCharArray();

                for (int i = 8 - 1, j = cBinaryString.length() - 1; i >= 0; i--, j--)
                {
                    if (j >= 0)
                        bin_Of_b[i] = cBinaryString.charAt(j);
                }
                cBinaryString = "";
                for (int i = 0; i < bin_Of_b.length; i++)
                {
                    cBinaryString += bin_Of_b[i];
                }
            }

            sb.append(cBinaryString);
            //cBinaryString = Convert.ToString('#', 2);
            //sb.Append(cBinaryString);
            //cBinaryString = Convert.ToString('@', 2);
            //sb.Append(cBinaryString);
        }

        return sb.toString();
    }

    public char toChar(String thestring)
    {
    	int b = 0;
        int bin = 1;
        for (int i= thestring.length()-1; i >= 0; i--){
            b+= Integer.parseInt(String.valueOf(thestring.charAt(i))) * bin;
            bin = bin * 2;
        }
        return (char)b;
    }
}
