package com.unclechen.sp.mongodb.index;

import com.unclechen.sp.mongodb.domain.Locus;



public class LocusIndex {

	/**
	 * ����locus
	 * @param date
	 * @param ip
	 * @param productid
	 * @author chenyz
	 */
	public static void createLocus(String date,String ip,String productid,String city){
		Locus locus = new Locus();
		locus.setDate(date);
		locus.setIp(ip);
		locus.setProductid(productid);
		locus.setCity(city);
//		MongoTemplate mt = Constant.FACADE.mongoTemplate;
//		mt.insert(locus);
	}
	public static void main(String[] args) {
		System.out.println("start");
		createLocus("2016-01-25", "127.0.0.1", "00011", "jinan");
		System.out.println("end");
	}
}
