package com.kafka.models;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
//	String value = "\"[\"\"Women's Shoes\"\",\"\"Women's Clothing\"\"]\",EUR,Yasmine,Yasmine Bryan,FEMALE,43,Bryan,,Friday,4,yasmine@bryan-family.zzz,,Asia,SA,,\"[\"\"Angeldale\"\",\"\"Pyramidustries\"\"]\",\"March 29th 2019, 19:43:41.000\",571795,\"[{\"\"base_price\"\":64.99,\"\"discount_percentage\"\":0,\"\"quantity\"\":1,\"\"manufacturer\"\":\"\"Angeldale\"\",\"\"tax_amount\"\":0,\"\"product_id\"\":22045,\"\"category\"\":\"\"Women's Shoes\"\",\"\"sku\"\":\"\"ZO0665206652\"\",\"\"taxless_price\"\":64.99,\"\"unit_discount_amount\"\":0,\"\"min_price\"\":33.79,\"\"_id\"\":\"\"sold_product_571795_22045\"\",\"\"discount_amount\"\":0,\"\"created_on\"\":\"\"2016-12-16T19:43:41+00:00\"\",\"\"product_name\"\":\"\"Ballet pumps - rose \"\",\"\"price\"\":64.99,\"\"taxful_price\"\":64.99,\"\"base_unit_price\"\":64.99},{\"\"base_price\"\":20.99,\"\"discount_percentage\"\":0,\"\"quantity\"\":1,\"\"manufacturer\"\":\"\"Pyramidustries\"\",\"\"tax_amount\"\":0,\"\"product_id\"\":20866,\"\"category\"\":\"\"Women's Clothing\"\",\"\"sku\"\":\"\"ZO0181101811\"\",\"\"taxless_price\"\":20.99,\"\"unit_discount_amount\"\":0,\"\"min_price\"\":10.91,\"\"_id\"\":\"\"sold_product_571795_20866\"\",\"\"discount_amount\"\":0,\"\"created_on\"\":\"\"2016-12-16T19:43:41+00:00\"\",\"\"product_name\"\":\"\"Sweatshirt - black\"\",\"\"price\"\":20.99,\"\"taxful_price\"\":20.99,\"\"base_unit_price\"\":20.99}]\",\"[\"\"December 16th 2016, 19:43:41.000\"\",\"\"December 16th 2016, 19:43:41.000\"\"]\",\"[\"\"ZO0665206652\"\",\"\"ZO0181101811\"\"]\",$85.98 ,85.98,2,2,order,yasmine";
//	System.out.println(value);
//	String regex = ",(?![^\\(\\[]*[\\]\\)])(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)";
//	List<String> list = Arrays.asList(value.split(regex));
	
	
	String regex = "[\\[\\]\"]";
	String value = "\"[\"\"Women's Shoes\"\",\"\"Women's Clothing\"\"]\"";
	System.out.println(value);
	List<String> category = Arrays.asList(value.replaceAll("[\\[\\]\"]", "").split(","));
	
	for(String str : category) {
	    System.out.println(str);
	}
	
    }
  
}
