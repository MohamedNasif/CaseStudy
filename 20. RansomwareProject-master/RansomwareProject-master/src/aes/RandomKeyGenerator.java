package aes;

public class RandomKeyGenerator {
	//원하는 길이만큼 랜덤한 키를 생성하는 함수
	String getRandomKey(int length) {
		//모든 알파벳과 숫자를 저장해둠.
        String AlphaNumericString 
        	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        
        //StringBuilder를 생성.
        StringBuilder sb = new StringBuilder();
        
        //입력된 길이만큼 index에 저장된 모든 알파벳 숫자의 길이 * 랜덤으로 생성된 소수값을 곱하여
        //0 ~ length내의 임의 숫자를 int형식으로 index에 저장.
        for (int i = 0; i < length; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random()); 
            //sb에서 랜덤으로 반환된 char값을 받아와서 붙힘. 
            sb.append(AlphaNumericString.charAt(index)); 
        }
        //sb의 문자열값을 반환함.
		return sb.toString(); 
	}
}
