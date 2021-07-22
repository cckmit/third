/*
 * $Id: ImageConverter.java 4446 2011-06-30 03:23:10Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.images;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

/**
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class ImageConverter {

	public static void main(String[] args) throws Exception{
		ImageReader reader = null;
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("JPG");
		if(readers.hasNext()){
			reader = readers.next();
		}
		reader.setInput(ImageIO.createImageInputStream(new FileInputStream("e:/test.jpg")));
		
		int thumbnails = reader.getNumThumbnails(0);
		System.out.println(thumbnails);
	}
}
