using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Steganography.StegoAlgoCode
{
    public class StegoLSB
    {
        int index_Of_All_Pixel = 0;
        public void stego(Color[] all_Piexl_Color, String theSecretTOLSB)
        {
            String thePixelUsedSaveLSB = toBinaryString(theSecretTOLSB.Length.ToString() + ";");
            index_Of_All_Pixel = all_Piexl_Color.Length - 1;
            int indexInSecret = 0;
            int whereStop = 0;
            for (int i = index_Of_All_Pixel; i > 0; i--, index_Of_All_Pixel--)
            {
                //Color pixel = all_Piexl_Color[i];
                int red = all_Piexl_Color[i].R, green = all_Piexl_Color[i].G, blue = all_Piexl_Color[i].B;
                for (int j = 0; j < 3; j++)
                {
                    if (indexInSecret >= thePixelUsedSaveLSB.Length)
                        break;
                    if (j == 0)
                    {
                        String theLSB = Convert.ToString(all_Piexl_Color[i].R, 2);
                        char[] theCharLSB = theLSB.ToCharArray();
                        theCharLSB[theCharLSB.Length - 1] = thePixelUsedSaveLSB[indexInSecret++];
                        //theLSB = "";
                        //for (int k = 0; k < theCharLSB.Length; k++)
                        //theLSB += theCharLSB[k];
                        red = convert_Piexl_To_Dec(theCharLSB);
                        whereStop = j;
                    }
                    else if (j == 1)
                    {
                        String theLSB = Convert.ToString(all_Piexl_Color[i].G, 2);
                        char[] theCharLSB = theLSB.ToCharArray();
                        theCharLSB[theCharLSB.Length - 1] = thePixelUsedSaveLSB[indexInSecret++];
                        //theLSB = "";
                        //for (int k = 0; k < theCharLSB.Length; k++)
                        //    theLSB += theCharLSB[k];
                        green = convert_Piexl_To_Dec(theCharLSB);
                        whereStop = j;
                    }
                    else if (j == 2)
                    {
                        String theLSB = Convert.ToString(all_Piexl_Color[i].B, 2);
                        char[] theCharLSB = theLSB.ToCharArray();
                        theCharLSB[theCharLSB.Length - 1] = thePixelUsedSaveLSB[indexInSecret++];
                        //theLSB = "";
                        //for (int k = 0; k < theCharLSB.Length; k++)
                        //    theLSB += theCharLSB[k];
                        blue = convert_Piexl_To_Dec(theCharLSB);
                        whereStop = j;
                    }
                }
                all_Piexl_Color[i] = Color.FromArgb(red, green, blue);
                if (indexInSecret >= thePixelUsedSaveLSB.Length)
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
                int red = all_Piexl_Color[i].R, green = all_Piexl_Color[i].G, blue = all_Piexl_Color[i].B;
                for (int j = whereStop; j < 3; j++)
                {
                    if (indexInSecret >= theSecretTOLSB.Length)
                        break;
                    if (j == 0)
                    {
                        String theLSB = Convert.ToString(all_Piexl_Color[i].R, 2);
                        char[] theCharLSB = theLSB.ToCharArray();
                        theCharLSB[theCharLSB.Length - 1] = theSecretTOLSB[indexInSecret++];
                        //theLSB = "";
                        //for (int k = 0; k < theCharLSB.Length; k++)
                        //theLSB += theCharLSB[k];
                        red = convert_Piexl_To_Dec(theCharLSB);
                    }
                    else if (j == 1)
                    {
                        String theLSB = Convert.ToString(all_Piexl_Color[i].G, 2);
                        char[] theCharLSB = theLSB.ToCharArray();
                        theCharLSB[theCharLSB.Length - 1] = theSecretTOLSB[indexInSecret++];
                        //theLSB = "";
                        //for (int k = 0; k < theCharLSB.Length; k++)
                        //    theLSB += theCharLSB[k];
                        green = convert_Piexl_To_Dec(theCharLSB);
                    }
                    else if (j == 2)
                    {
                        String theLSB = Convert.ToString(all_Piexl_Color[i].B, 2);
                        char[] theCharLSB = theLSB.ToCharArray();
                        theCharLSB[theCharLSB.Length - 1] = theSecretTOLSB[indexInSecret++];
                        //theLSB = "";
                        //for (int k = 0; k < theCharLSB.Length; k++)
                        //    theLSB += theCharLSB[k];
                        blue = convert_Piexl_To_Dec(theCharLSB);
                    }
                }
                all_Piexl_Color[i] = Color.FromArgb(red, green, blue);
                if (indexInSecret >= theSecretTOLSB.Length)
                    break;
                whereStop = 0;
            }
            //MessageBox.Show(">>>>>>>>>>>>   " + indexInSecret + " ,, " + index_Of_All_Pixel);
            // empededk
        }


        public String extract(Color[] all_Piexl_Color)
        {
            index_Of_All_Pixel = all_Piexl_Color.Length - 1;
            StringBuilder theTemp = new StringBuilder();
            StringBuilder thePixelUsed = new StringBuilder();
            int whereStop = 0;
            int index8 = 0;
            for (int i = index_Of_All_Pixel; i > 0; i--, index_Of_All_Pixel--)
            {
                // Red
                String theLSB = Convert.ToString(all_Piexl_Color[i].R, 2);
                char[] theCharLSB = theLSB.ToCharArray();
                theTemp.Append(theCharLSB[theCharLSB.Length - 1]);
                index8++;
                if (index8 == 8)
                {
                    char c = toChar(theTemp.ToString());
                    if (c == ';')
                    {
                        whereStop = 1;
                        break;
                    }

                    thePixelUsed.Append(c);
                    index8 = 0;
                    theTemp.Clear();
                }
                // Green
                theLSB = Convert.ToString(all_Piexl_Color[i].G, 2);
                theCharLSB = theLSB.ToCharArray();
                theTemp.Append(theCharLSB[theCharLSB.Length - 1]);
                index8++;
                if (index8 == 8)
                {
                    char c = toChar(theTemp.ToString());
                    if (c == ';')
                    {
                        whereStop = 2;
                        break;
                    }
                    thePixelUsed.Append(c);
                    index8 = 0;
                    theTemp.Clear();
                }
                // Blue
                theLSB = Convert.ToString(all_Piexl_Color[i].B, 2);
                theCharLSB = theLSB.ToCharArray();
                theTemp.Append(theCharLSB[theCharLSB.Length - 1]);
                index8++;
                if (index8 == 8)
                {
                    char c = toChar(theTemp.ToString());
                    if (c == ';')
                    {
                        whereStop = 0;
                        index_Of_All_Pixel--;
                        break;
                    }
                    thePixelUsed.Append(c);
                    index8 = 0;
                    theTemp.Clear();
                }

                
            }

            // after get who many bits are saved in the LSB
            int theLSBPixelUsed = int.Parse(thePixelUsed.ToString());
            index8 = 0;
            theTemp.Clear();
            thePixelUsed.Clear();
            for (int i = index_Of_All_Pixel, j = 0; j <= theLSBPixelUsed; i--)
            {
                for (int k = whereStop; k < 3; k++)
                {
                    if (k == 0)
                    {
                        // Red
                        String theLSB = Convert.ToString(all_Piexl_Color[i].R, 2);
                        char[] theCharLSB = theLSB.ToCharArray();
                        theTemp.Append(theCharLSB[theCharLSB.Length - 1]);
                        index8++;
                        j++;
                        if (index8 == 8)
                        {
                            char c = toChar(theTemp.ToString());
                            //if (c == '1')
                            //    MessageBox.Show("aaaaa");

                            thePixelUsed.Append(c);
                            index8 = 0;
                            theTemp.Clear();
                            if (j == theLSBPixelUsed) break;
                        }
                    }
                    else if (k == 1)
                    {
                        // Green
                        String theLSB = Convert.ToString(all_Piexl_Color[i].G, 2);
                        char[] theCharLSB = theLSB.ToCharArray();
                        theTemp.Append(theCharLSB[theCharLSB.Length - 1]);
                        index8++;
                        j++;
                        if (index8 == 8)
                        {
                            char c = toChar(theTemp.ToString());
                            //if (c == '1')
                            //    MessageBox.Show("aaaaa");
                            thePixelUsed.Append(c);
                            index8 = 0;
                            theTemp.Clear();
                            if (j == theLSBPixelUsed) break;
                        }
                    }
                    else if (k == 2)
                    {
                        // Blue
                        String theLSB = Convert.ToString(all_Piexl_Color[i].B, 2);
                        char[] theCharLSB = theLSB.ToCharArray();
                        theTemp.Append(theCharLSB[theCharLSB.Length - 1]);
                        index8++;
                        j++;
                        if (index8 == 8)
                        {
                            char c = toChar(theTemp.ToString());
                            //if (c == '1')
                            //    MessageBox.Show("aaaaa");
                            thePixelUsed.Append(c);
                            index8 = 0;
                            theTemp.Clear();
                            if (j == theLSBPixelUsed) break;
                        }
                    }
                }
                whereStop = 0;
            }
            //MessageBox.Show(thePixelUsed.ToString());
            return thePixelUsed.ToString();
        }
        
        // convert the b who readed from secret to Dec.
        public int convert_Piexl_To_Dec(char[] Tsecret_bits)
        {
            int b = 0;
            int bin = 1;
            for (int i = Tsecret_bits.Length - 1; i >= 0; i--)
            {
                b += int.Parse(Tsecret_bits[i].ToString()) * bin;
                bin = bin * 2;
            }
            //MessageBox.Show(b.ToString());
            return b;
        }

        public String toBinaryString(String s)
        {
            char[] cArray = s.ToCharArray();

            StringBuilder sb = new StringBuilder();

            foreach (char c in cArray)
            {
                String cBinaryString = Convert.ToString(c, 2);

                if (cBinaryString.Length != 8)
                {
                    String tempStr = new String('0', 8);
                    char[] bin_Of_b = tempStr.ToCharArray();

                    for (int i = 8 - 1, j = cBinaryString.Length - 1; i >= 0; i--, j--)
                    {
                        if (j >= 0)
                            bin_Of_b[i] = cBinaryString[j];
                    }
                    cBinaryString = "";
                    for (int i = 0; i < bin_Of_b.Length; i++)
                    {
                        cBinaryString += bin_Of_b[i];
                    }

                    //MessageBox.Show(c + " = " +cBinaryString + " L= " + cBinaryString.Length);
                }

                sb.Append(cBinaryString);
                //cBinaryString = Convert.ToString('#', 2);
                //sb.Append(cBinaryString);
                //cBinaryString = Convert.ToString('@', 2);
                //sb.Append(cBinaryString);
            }

            return sb.ToString();
        }

        public char toChar(String thestring)
        {
            int b = Convert.ToInt32(thestring, 2);
            return (char)b;
        }
    }
}
