/*
 * $Id: IdMultiPartHashGenerator.java 3817 2010-01-10 09:54:12Z lcj $
 * 
 * Copyright 2007-2008 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * 未经深圳市红旗信息技术有限公司许可，任何人不得擅自（包括但不限于：
 * 以非法的方式复制、传播、展示、镜像、上载、下载、引用）使用。
 */
package cn.redflagsoft.base.util;

import java.util.ArrayList;
import java.util.List;

public class IdMultiPartHashGenerator
{

    private static final int SPLIT_MAX_SIZE = 9;
    private int split;
    private int modulo;
    private int parts;

    public IdMultiPartHashGenerator(int split, int modulo, int parts)
    {
        if(split < 1 || split > SPLIT_MAX_SIZE)
            throw new IllegalArgumentException("The split parameter must be in the range 1 - 9");
        if(modulo < 1)
            throw new IllegalArgumentException("The modulo parameter must be greater than 0");
        if(parts < 1)
        {
            throw new IllegalArgumentException("The parts parameter must be greater than 0");
        } else
        {
            this.split = split;
            this.modulo = modulo;
            this.parts = parts;
            return;
        }
    }

    public List<Integer> generate(long value)
    {
        if(value < 0L)
            throw new IllegalArgumentException("The value to split must be larger than 0");
        String strValue = String.valueOf(value);
        int strPos = strValue.length();
        List<Integer> multipartHash = new ArrayList<Integer>(parts);
        int i = 0;
        do
        {
            if(i >= parts)
                break;
            strPos -= split;
            if(strPos > 0)
            {
                String subPart = strValue.substring(strPos, strPos + split);
                multipartHash.add(Integer.valueOf(modulo(subPart)));
            } else
            {
                String subPart = strValue.substring(0, strPos + split);
                multipartHash.add(Integer.valueOf(modulo(subPart)));
                padToSize(multipartHash);
                break;
            }
            i++;
        } while(true);
        return multipartHash;
    }

	private int modulo(String value) {
		int intValue = Integer.parseInt(value);
		return intValue % modulo;
	}

	private List<Integer> padToSize(List<Integer> multiPartHash) {
		for (int i = multiPartHash.size(); i < parts; i++)
			multiPartHash.add(Integer.valueOf(0));

		return multiPartHash;
	}

	public int getSplit() {
		return split;
	}
	
	
	public static void main(String[] args){
		IdMultiPartHashGenerator generator = new IdMultiPartHashGenerator(2, 100, 3);
		List<Integer> list = generator.generate(18001343L);
		System.out.println(list);
	}
}
