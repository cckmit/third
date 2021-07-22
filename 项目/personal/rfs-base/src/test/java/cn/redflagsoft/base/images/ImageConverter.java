/*
 * $Id: ImageConverter.java 4446 2011-06-30 03:23:10Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
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
