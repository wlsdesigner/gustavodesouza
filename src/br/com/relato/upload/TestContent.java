package br.com.relato.upload;


public class TestContent {

	public static boolean test( String content ){
		boolean b = false;
		
		if ( "image/pjpeg".equals(content) )
			b = true;
		else if ( "image/x-png".equals(content) )
			b = true;
		else if ( "image/gif".equals(content) )
			b = true;
		else if ( "image/bmp".equals(content) )
			b = true;
								
		return b; 
	}

}
