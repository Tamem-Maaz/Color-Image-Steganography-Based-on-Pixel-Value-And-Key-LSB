//package AlgoStego;
//
//public class PVDOriginal extends StegoPVD {
//
//	@Override
//	public int[] embeding_Secret() {
//		
//		int di = compute_difference_Di(pi_1, pi_2);						// 2
//		int di_new = compute_difference_Di(opt_Range[0], b);				// 6
//		int m = Math.abs(di_new - di);
//		
//		if ( pi_1 >= pi_2 && di_new > di ){
//			pi_1 = (int) (pi_1 + Math.ceil(m/2));
//			pi_2 = (int) (pi_2 - Math.floor(m/2));
//		}
//		else if (pi_1 < pi_2 && di_new > di){   
//			pi_1 = (int) (pi_1 - Math.floor(m/2));
//			pi_2 = (int) (pi_2 + Math.ceil(m/2));
//		}
//		else if (pi_1 >= pi_2 && di_new <= di){   
//			pi_1 = (int) (pi_1 - Math.ceil(m/2));
//			pi_2 = (int) (pi_2 + Math.floor(m/2));
//		}
//		else if (pi_1 < pi_2  &&  di_new <= di){   
//			pi_1 = (int) (pi_1 + Math.ceil(m/2));
//			pi_2 = (int) (pi_2 - Math.floor(m/2));
//		}
//			
//		int[] Tow_Stego_Pixel = {pi_1, pi_2};
//		return Tow_Stego_Pixel;
//	}
//
//	@Override
//	public int extracting_Secret(int[] pixel) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//}
