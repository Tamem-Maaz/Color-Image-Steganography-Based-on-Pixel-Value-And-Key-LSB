package AlgoStego;

import android.graphics.Color;

public class PVDColor extends StegoPVD {

	@Override
	public int[] embeding_Secret() {
		int[] theReturnArrayInfo = new int[3];
		int how_many_piexl_used = 0;
		int theLastColorStego = 0;
		boolean bit_discarded  = false;
        int[] Tow_Pixel_Original = get_next_Partition_Tow_Pixel();					// 1
        int[]pi_1 = new int[3]; int []pi_2 = new int[3];
        int index_Of_RGB=0;
        int theLastStegoBitsSize = 0;
        boolean mustReturn = false;
        int[] Tow_new_Pixel = null;
        index_OF_NEW_Stego_Pixel = 0;
        while(Tow_Pixel_Original != null){
        	how_many_piexl_used++;
        	bit_discarded = false;
        	index_Of_RGB = 0;
        	int colorRGB = 0;
           
            while(colorRGB < 3){
            	  bit_discarded = false;
	              if(colorRGB == 0){index_Of_RGB=0; pi_1[index_Of_RGB] = Color.red(Tow_Pixel_Original[0]); pi_2[index_Of_RGB] = Color.red(Tow_Pixel_Original[1]);}
	              else if(colorRGB == 1){index_Of_RGB=1; pi_1[index_Of_RGB] = Color.green(Tow_Pixel_Original[0]); pi_2[index_Of_RGB] = Color.green(Tow_Pixel_Original[1]);}
	              else if(colorRGB == 2){index_Of_RGB=2; pi_1[index_Of_RGB] = Color.blue(Tow_Pixel_Original[0]); pi_2[index_Of_RGB] = Color.blue(Tow_Pixel_Original[1]);}
//	              Log.d("in 1,  " + String.valueOf(index_Of_RGB), String.valueOf(pi_1[index_Of_RGB]) + " ,P2 " + String.valueOf(pi_2[index_Of_RGB]));
	              int di = compute_difference_Di(pi_1[index_Of_RGB], pi_2[index_Of_RGB]);						// 2
	              int[] opt_Range = optimal_Range(di);								// 3
	              int wi = compute_Wi(opt_Range[1], opt_Range[0]);					// 4
	              int t = (int) compute_Number_Of_Bits(wi);							// 4
	              
	              if (colorRGB == 0 && t > 5)
                  {
	            	  int[] tempSkip = {how_many_piexl_used, 0};
                      theSkipColorPixels.add(tempSkip);
                     // MessageBox.Show("Red: " + how_many_piexl_used.ToString());
                      colorRGB++; continue;
                  }									// 5
                  else if (colorRGB == 1 && t > 3)
                  {
                	  int[] tempSkip = {how_many_piexl_used, 1};
                      theSkipColorPixels.add(tempSkip);
                      //MessageBox.Show("Green: " + how_many_piexl_used.ToString());
                      colorRGB++; continue;
                  }
	              
	              int[] t_bits = read_T_Bit_From_Secret(t);							// 6
	              if(t_bits != null && t_bits.length < 3){
	            	  theLastStegoBitsSize = t_bits.length;
	              }
	              mustReturn = false;
	              if(t_bits == null)	// when return 1 this mean the secret finshed and all secret embeding
	              { 
//	            	  theLastColorStego = index_Of_RGB;
	            	  if(index_Of_RGB == 0){
	            		  how_many_piexl_used--;
	            		  theLastColorStego = 2;
	            	  }
	            	  else if(index_Of_RGB == 1) theLastColorStego = 0;
	            	  else if(index_Of_RGB == 2) theLastColorStego = 1;
	            	  
	            	  mustReturn = true;
	              }
	              if(!mustReturn){
		              int b = convert_Secret_To_Dec(t_bits);							// 6
		              int di_new = opt_Range[0] + b;				// 6
		              //***************************************** 7 ****************************************************
		              Tow_new_Pixel = original_PVD(di, di_new, pi_1[index_Of_RGB], pi_2[index_Of_RGB] );	// 7
		              if(Tow_new_Pixel[0] > 255 || Tow_new_Pixel[1] > 255 || Tow_new_Pixel[0] < 0 || Tow_new_Pixel[1] < 0){									// 8
		            	  if(t_bits[0] == 1){																// 8.1
		            		  t_bits[0] = 0;
		            		  bit_discarded = true;
		            		  b = convert_Secret_To_Dec(t_bits);
		            		  di_new = opt_Range[0] + b;
		            	  }
		            	  Tow_new_Pixel = original_PVD(di, di_new, pi_1[index_Of_RGB], pi_2[index_Of_RGB] );// 8.2
		            	  if(Tow_new_Pixel[0] > 255 || Tow_new_Pixel[1] > 255 || Tow_new_Pixel[0] < 0 || Tow_new_Pixel[1] < 0){								// 8.2
			            		  int m= Math.abs(di_new - di);
			            		  if(Tow_new_Pixel[1] >= Tow_new_Pixel[0] && Tow_new_Pixel[1] > 255){
			            			  Tow_new_Pixel[0] = pi_1[index_Of_RGB] - m;
			            			  Tow_new_Pixel[1] = pi_2[index_Of_RGB];
			            		  } 
			            		  else if(Tow_new_Pixel[1] < Tow_new_Pixel[0] && Tow_new_Pixel[0] > 255){
			            			  Tow_new_Pixel[0] = pi_1[index_Of_RGB];
			            			  Tow_new_Pixel[1] = pi_2[index_Of_RGB] - m;
			            		  }
			            		  else if(Tow_new_Pixel[1] >= Tow_new_Pixel[0] && Tow_new_Pixel[0] < 0){
			            			  Tow_new_Pixel[0] = pi_1[index_Of_RGB];
			            			  Tow_new_Pixel[1] = pi_2[index_Of_RGB] + m;
			            		  } 
			            		  else if(Tow_new_Pixel[1] < Tow_new_Pixel[0] && Tow_new_Pixel[1] < 0){
			            			  Tow_new_Pixel[0] = pi_1[index_Of_RGB] + m;
			            			  Tow_new_Pixel[1] = pi_2[index_Of_RGB];
			            		  }
		            		  }
		            	  }
			              // Now, the pixel block (pi, pi+1) is replaced by (pi' , pi+1')				// 9
		        		  String temp_New_Piexl_1 = String.valueOf(Integer.toBinaryString(Tow_new_Pixel[0]));
		        		  String temp_New_Piexl_2 = String.valueOf(Integer.toBinaryString(Tow_new_Pixel[1]));
		        		  if(bit_discarded == false){													// 10
		        			  if(temp_New_Piexl_1.charAt(temp_New_Piexl_1.length()-1) == '0' && temp_New_Piexl_2.charAt(temp_New_Piexl_2.length()-1) == '0')		// i
		        				  Tow_new_Pixel[1]+=1;
		        			  else if(temp_New_Piexl_1.charAt(temp_New_Piexl_1.length()-1) == '0' && temp_New_Piexl_2.charAt(temp_New_Piexl_2.length()-1) == '1')	// ii
		        			  {
		        				  if(Tow_new_Pixel[1] < 255 && Tow_new_Pixel[0] >= 0)	Tow_new_Pixel[1]+=1;		// a
		        				  else if(Tow_new_Pixel[0] > 0  && Tow_new_Pixel[1] == 255){						// b
		        					  Tow_new_Pixel[0]-=2;
		        					  Tow_new_Pixel[1]-=1;
		        				  }
		        				  else if(Tow_new_Pixel[0] == 0 && Tow_new_Pixel[1] == 255)							// c
		        					  Tow_new_Pixel[1] = Tow_new_Pixel[1];
		        			  }
		        			  else if(temp_New_Piexl_1.charAt(temp_New_Piexl_1.length()-1) == '1' && temp_New_Piexl_2.charAt(temp_New_Piexl_2.length()-1) == '0')	// iii
		        				  Tow_new_Pixel[0]-= 1;
		        			  else if(temp_New_Piexl_1.charAt(temp_New_Piexl_1.length()-1) == '1' && temp_New_Piexl_2.charAt(temp_New_Piexl_2.length()-1) == '1')	// iv
		        			  {
		        				  Tow_new_Pixel[0]-= 1;
//		        				  Log.d("In 10.1.4", String.valueOf(Tow_new_Pixel[0]));
		        			  }
		        			  
		        		  }
		        		  else{
		        			  if(temp_New_Piexl_1.charAt(temp_New_Piexl_1.length()-1) == '0' && temp_New_Piexl_2.charAt(temp_New_Piexl_2.length()-1) == '0'){		// i
		        				  Tow_new_Pixel[0]+=1;
		        			  }
		        			  else if(temp_New_Piexl_1.charAt(temp_New_Piexl_1.length()-1) == '0' && temp_New_Piexl_2.charAt(temp_New_Piexl_2.length()-1) == '1')	// ii
		        				  Tow_new_Pixel[0]+=1;
		        			  else if(temp_New_Piexl_1.charAt(temp_New_Piexl_1.length()-1) == '1' && temp_New_Piexl_2.charAt(temp_New_Piexl_2.length()-1) == '0')	// iii
		        			  {
		        				  if(Tow_new_Pixel[1] > 0 && Tow_new_Pixel[0] <= 255)	Tow_new_Pixel[1]-=1;		// a
		        				  else if(Tow_new_Pixel[0] < 255  && Tow_new_Pixel[1] == 0){						// b
		        					  Tow_new_Pixel[0]+=2;
		        					  Tow_new_Pixel[1]+=1;
		        				  }
		        			  }
		        			  else if(temp_New_Piexl_1.charAt(temp_New_Piexl_1.length()-1) == '1' && temp_New_Piexl_2.charAt(temp_New_Piexl_2.length()-1) == '1')	// iv
		        				  Tow_new_Pixel[1]-= 1;
		        		  }
		        		  // Now we get the stego blocks	11
		              //***************************************** 7 ****************************************************
	              }
	              
	              pi_1[index_Of_RGB] = Tow_new_Pixel[0];	// fill with stego pixel color
	              pi_2[index_Of_RGB] = Tow_new_Pixel[1];	// fill with stego pixel color
	              if(mustReturn) break;
	              //index_Of_RGB++;
	              colorRGB++;
            }
            all_Piexl_Color[index_OF_NEW_Stego_Pixel++] = Color.rgb(pi_1[0], pi_1[1], pi_1[2]);
            all_Piexl_Color[index_OF_NEW_Stego_Pixel++] = Color.rgb(pi_2[0], pi_2[1], pi_2[2]);
            theReturnArrayInfo[0] = how_many_piexl_used;
            theReturnArrayInfo[1] = theLastStegoBitsSize;
            theReturnArrayInfo[2] = theLastColorStego;
            if(mustReturn) return theReturnArrayInfo;
            Tow_Pixel_Original = get_next_Partition_Tow_Pixel();
        }
        theReturnArrayInfo[0] = -1;
        theReturnArrayInfo[1] = theLastStegoBitsSize;
        theReturnArrayInfo[2] = theLastColorStego;
		return theReturnArrayInfo;		// when return -1 this mean the Photo is fineshed and some secret not embeding
	}
	
	private int[] original_PVD(int di, int di_new, int pi_1, int pi_2) {
		int m = Math.abs(di_new - di);
		
		if ( pi_1 >= pi_2 && di_new > di ){
			pi_1 = (int) (pi_1 + Math.ceil((double)m/2));
			pi_2 = (int) (pi_2 - Math.floor((double)m/2));
		}
		else if (pi_1 < pi_2 && di_new > di){   
			pi_1 = (int) (pi_1 - Math.floor((double)m/2));
			pi_2 = (int) (pi_2 + Math.ceil((double)m/2));
		}
		else if (pi_1 >= pi_2 && di_new <= di){   
			pi_1 = (int) (pi_1 - Math.ceil((double)m/2));
			pi_2 = (int) (pi_2 + Math.floor((double)m/2));
		}
		else if (pi_1 < pi_2  &&  di_new <= di){   
//			Log.d("I'm hearrrrrr  1", String.valueOf(pi_1) + " , " + String.valueOf(pi_2) );
			pi_1 = (int) (pi_1 + Math.ceil((double)m/2));
			pi_2 = (int) (pi_2 - Math.floor((double)m/2));
//			Log.d("I'm hearrrrrr  2", String.valueOf(pi_1) + " , " + String.valueOf(pi_2) );
		}
			
		
		int[] Tow_Stego_Pixel = {pi_1, pi_2};
		return Tow_Stego_Pixel;
	}
	@Override
	public String extracting_Secret(int pixel_num_used, int theLastStegoBitsSize, int theLastColorStego) {
		int index_pixel_used=0;
        int[] Tow_Pixel_Original = get_next_Partition_Tow_Pixel();					// 1
        int[]pi_1 = new int[3]; int []pi_2 = new int[3];
        int index_Of_RGB=0;
        
        while( (index_pixel_used < pixel_num_used) && (Tow_Pixel_Original != null) ){
        	index_pixel_used++;
        	index_Of_RGB = 0;
        	int colorRGB = 0;
        	while(colorRGB < 3){
        		 if(colorRGB == 0){index_Of_RGB=0; pi_1[index_Of_RGB] = Color.red(Tow_Pixel_Original[0]); pi_2[index_Of_RGB] = Color.red(Tow_Pixel_Original[1]);}
	             else if(colorRGB == 1){index_Of_RGB=1; pi_1[index_Of_RGB] = Color.green(Tow_Pixel_Original[0]); pi_2[index_Of_RGB] = Color.green(Tow_Pixel_Original[1]);}
	             else if(colorRGB == 2){index_Of_RGB=2; pi_1[index_Of_RGB] = Color.blue(Tow_Pixel_Original[0]); pi_2[index_Of_RGB] = Color.blue(Tow_Pixel_Original[1]);}
        		
        		 // Step 2:	 Check the LSB positions of Pi (first pixel  of the block) for each block and do the following
        		 String pi_1_LSB = Integer.toBinaryString(pi_1[index_Of_RGB]).toString();
        		 int temp_Pi_1 = pi_1[index_Of_RGB];
 				 if(pi_1_LSB.charAt(pi_1_LSB.length()-1) == '0')
 					temp_Pi_1++;
 				 else
 					temp_Pi_1--;
 				 // Step 3: 	Now calculate the difference value di of two consecutive pixels of each block by using the formula di=|pi-pi+1| .
 				int di = compute_difference_Di(temp_Pi_1, pi_2[index_Of_RGB]);
 				// Step 4: 	Find the appropriate range Ri for the difference di.
 				int[] opt_Range = optimal_Range(di);
 				// Step 5
 				int wi = compute_Wi(opt_Range[1], opt_Range[0]);
	            int t = (int) compute_Number_Of_Bits(wi);
	            // Step 6
	            int[] theMustSkip= {-1,-1};
	            if(!theSkipColorPixels.isEmpty())
	            	theMustSkip= theSkipColorPixels.getFirst();
	            
	            if (colorRGB == 0 && t > 5) 
                {
	            	theSkipColorPixels.removeFirst();
                    //MessageBox.Show("Red: " + index_pixel_used.ToString());
                    colorRGB++; continue; 
                }
                else if (colorRGB == 1 && t > 3)
                {
                	theSkipColorPixels.removeFirst();  
                    if(index_pixel_used == 209 && colorRGB == 1)
    	            {
    	            	int deee = 0;
    	            	deee++;
    	            	//int[] SkipError3 = {index_pixel_used, colorRGB};
    	            }
                    //MessageBox.Show("Green: " + index_pixel_used.ToString()); 
                    colorRGB++; continue;
                }
                else if (theMustSkip[0] == index_pixel_used && theMustSkip[1] == colorRGB)
                {
                	theSkipColorPixels.removeFirst();
                    colorRGB++; continue; 
                }
//                else if (Main.theSkipColorPixels.contains(SkipError))
//                {
//                    //MessageBox.Show("the Skip Green: " + index_pixel_used.ToString());
//                    int index = Main.theSkipColorPixels.indexOf(SkipError);
//                    Main.theSkipColorPixels.remove(index);
//                    colorRGB++; continue;
//                }
//	            else if(colorRGB == 2 && t > 7){colorRGB++; continue;}
	            // Step 7: 	Extract ‘t’ bits, by the extracting method of original PVD just STEP 5.
	            //  b = li – di  = 32 – 46 = 14
	   	      	//	b = 01110
	            int b = Math.abs(opt_Range[0] - di);
	            char[] bin_Of_b = new char[t];
	            // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	            for(int i=0 ;i<bin_Of_b.length;i++)bin_Of_b[i]='0';
	            String newBin =Integer.toBinaryString(b);
	            for(int i= t-1, j= newBin.length()-1 ; i >= 0 ; i--, j--){
	            	if(j>=0)
	            		bin_Of_b[i] = newBin.charAt(j);
	            }
	            newBin = String.valueOf(bin_Of_b);
	            // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&	            
	            // Step 8:
	            String withChange = newBin;
	            if(pi_1_LSB.charAt(pi_1_LSB.length()-1) == '1'){
	            	char MSB = '1';
	            	withChange = newBin.substring(1, newBin.length());
	            	withChange = MSB + withChange;
	            }
	            if(index_pixel_used == pixel_num_used && theLastColorStego == index_Of_RGB){
	            	if(theLastStegoBitsSize != 0)
	            		withChange = withChange.substring(withChange.length()-theLastStegoBitsSize, withChange.length());
	            	Tsecret_Bits_All+= withChange;
	            	
	            	break;
	            }
	            else{
	            	Tsecret_Bits_All+= withChange;
	            }
 				colorRGB++;
        	}
        	Tow_Pixel_Original = get_next_Partition_Tow_Pixel();
        	//index_pixel_used+= 2;
        }
		return  Tsecret_Bits_All;		// when return 2 this mean the Photo is fineshed and some secret not embeding
	}
	
	


}
