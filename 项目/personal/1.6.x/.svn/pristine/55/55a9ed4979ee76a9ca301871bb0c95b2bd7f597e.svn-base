package org.opoo.apps.license;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import Aladdin.Hasp;
import Aladdin.HaspStatus;
import junit.framework.TestCase;

public class HaspTest extends TestCase {
	public static final String scope1 = new String(
    "<haspscope />\n");
	
	public static final String scope = new String(
		      "<haspscope>\n" +
		      " <license_manager hostname=\"localhost\" />\n" +
		      "</haspscope>\n");

	public static final String view = new String(
    "<haspformat root=\"root\">\n" +
    "  <hasp>\n" +
    "    <attribute name=\"id\" />\n" +
    "    <attribute name=\"type\" />\n" +
    "    <feature>\n" +
    "      <attribute name=\"id\" />\n" +
    "      <element name=\"concurrency\" />\n" +
    "      <element name=\"license\" />\n" +
    "      <session>\n" +
    "        <element name=\"username\" />\n" +
    "        <element name=\"hostname\" />\n" +
    "        <element name=\"ip\" />\n" +
    "        <element name=\"apiversion\" />\n" +
    "      </session>\n" +
    "    </feature>\n" +
    "  </hasp>\n" +
    "</haspformat>\n");
	
	public static final 	String vendorCode = 
	"wEhXhoO9HlBec5mkDHQWqyCT3MtQanxDB5aMWRORQXQIAjamsrQOMkeOWHdVmDWUbK28Qdtg5GBEjcf9" + 
	"kLXxGqYwckLQYM44feW+Zmg0RVEI1hRWJH0xS3/kWfLnoXA1H6sh9UgWZN93QRMovKF1xDm/WSebXEES" + 
	"LWtBYYOeR22E+Ze9D5Eep1eOrZurRix7qTVlCuYmUxaFP/V6vASf+18QtnLOzzsJud5F3AV9aUYgNFAn" + 
	"jOGK41m55uD+8YaG39eF9okDXzJR9Jr3emPADRb1JFKyxOL0pQCQ2yZHZGQallQEe/0IXHKssm/fcTEt" + 
	"bKYMBXdkBNSk/T15HdE0oMrIQVCKPtHxsTYYSym0+4VODqd/vsfrHjVDjbQGHDYItW6kIya6vxxUpL8l" + 
	"FymlT4fb84WTyO0Fi4z94AyYakqJ/NoJLn++WTR0AImYX2kYkWXEslfQdDJ7BTFrM36fMRTtGUWsCcCg" + 
	"+flf83AMhrLUhF2Cv42XXXqxpyqRgdT+6kmYttrPJwAs9wiLdhBonk/e9OIVFGhBOikOsV9G2sNJ0NKK" + 
	"hTeWpn/mJ26KeNFqRPICxMJk3sTzP7sBu327KS/t7/CSJH3+fmV9rTJMTfJwntB7PePILWvXTPpJYp+o" + 
	"pvWEKnhJlp7UmaRjXKLFwkhLRR2Uu62l4FyrqO6mc9CEvFNWTT0mYW2zGEcJQKbP62Cttq3G2WaGvFsr" + 
	"s5EsqS2K7jBlFdAxMlN9CnZqjHWwICi88/KhVFPiMvLA3Au8IYYgXoXuheSMevfOOuziFKuObph+hGJY" + 
	"XjXqBt5ufmZgbeHOBJ4G8h664zTAkcC9HwrbvjBZ08SQ2qhD0gvcfX0IOxU9kXL8GRxnKR4Cy5jSJRZe" + 
	"gFFcH9GTnD43pd3qeBLOsVdwyn0IkHvWuYv884GYOqT9L7QaZcahGSxUTUs=";
	
	
	//String vendorCode = "VQVidmhY7C7YMz7d1XjGbZJyQUtCDLvA5JNLri+9we2RZuunZjVZEmwkgIuOK4YthWpZ6SNkN1kh2YzHgBNGQKCll5YgFfQAG758jZiuKRtCBPcFSgrQk9BmMqdM9zp1QW0Y+/jCVTFtKYTtFHlp2beVSFvYSjtS2u6TjZkcESMlPy3Rw6jZinjJWTYcyLjungiUbtxZHhX31JFZfCm6Tq7UG8gU+4MUxzoCn5HwTUiOYHDYhJahgJmU0YUg8vHK2dY4yOQvsRMi56Btu0bFG7dMPZfp4EU3/BPpO+EdXy207vsnUhjEHV4fOF4QTC0xVfFrWGc4Di/v6DpezMgjDHZu9yqpPY5nsnAbKhWxBNEqVyJhvKHl3hZeZ7PspdkbhVJle6sQXosTxz8wzEw5UHSN2orIpav+DOHxQIIvFgTHpZRFizRyYNd6AhTZkYSDebKEZvCgkGLt/pKV26DJN+FEihLZ2FrWpnJBSTvSJ4DdHrZEt9D4dozENK4pGQ1JpMcItu6/u5vrGwuwEH7u/7Jkxl7C0365fMB0nbuHExyIvjcu3YmjOv3dqYK9g+l3Zo21dtDJy4foMdVaOMEkS+KZfJxQGNouhCDcdRU0dTaOMGhp7QvWbhbGQnIEpr6AtflAnd4e+jdTUACsLGf9eAxdGAl1y/SHLaGjatH99Ttau/ZpZahQGYkKbuT13WkF6r6t1GzDZSCeXJTChDhAbLZV2+HA125piUYeD6K2m0X1+9rat1h0PNJKMxJfe4256BF/4pf5GFSEDqRqW4GgFJlu26Gwhg3FYbUIrR2ZNh37rSrNoEqR99NeDbQiDneo7CWGqd1cW3jXWJS0aBBkNxOCpyYgVPFt+TTn9L6vZXBwb6cUNEDtTMY6yZomAuZdZWg04HU0iNH7e3XpKu8YLLSWqXrhgRSFy4FFQVJntpk=";
		//"8j1l06Eu2mZXK21qjUAClizul0sg4KgUKlGnCVbXg8IQ8ihfEHkzv16HDn3YNHH0smLLDWbrGbTnI/+PpQxe105B4r/yxwzNo2/FFl2+8j2ymjdds6nLp+MtmZ07D6wRT+cnqXisx3VsI4jZojSPIktjZxBuhjgfLqiksYQj+GLdfT5zHF4EEvMCVkjv9ZTGiJLGQ0F1jNL81qa5Vny+J7Fmfm7ymVwY09k90k4mVTEPZ3nu94t5PfCLnqFWAdgj+AcVazcaAx7yJ69OGEvE6esaPmcI5somlqY6nw+qA4F14w/8LtvcDGdILq6rB6D9R9WPDkYgFRiOP2mlztkUCqNUUmO8q42JgcYPtI2qvJw0OXkM9pl/k7xpPbpqkVjDXcZDoijd2NU3Re1K5LIBKzCVNmsIlK+ANkv/0I+RCmejTfjmOBe05noUapeUY/QLuxHWf0QVhlVBQLaxdBuon/CCmibKH+AFylB5a6kbkSlMWHTbc2c6L3uE/n1WYmmLdlnlSE3G9VYQe7K6mCOuaRFL1GsbGfGiocOh/0zDynD/gtTKv3pqWC09gvDcCrbfV8v9Wnz2C2d53CzzLTCw7t8W/T7NWccAxgLO/puUiULTeAcSDxlkHypB3phVYZ8UvFLLLwKUuQhbDTSmGCogQ4PIhjY9ebuu0c6wh0z+AOVe3pshCPtIBph6QhbEfXrDyO3wXDOEHwfpx1Rb3YUITxLrtAhw0aN4j1T+SOfdJm1Ae90wqdEyipnzOENK4xVH0JDDmjq+clnFJTbXNhuQtjE9d/HPB9RXs9RyDpbq4JHOf9qn44Z76/V4x6KU9I8j1Kug6v9B9PqZvkKvzG3V5CaFrkHV6mSFNgkzEh0CzPt9xEtLK0iNzsvjjBNgKACph3AzQ/ToCUjlO0YIsRbJ1tIFPl+84IjBugYSDrTtHNg=";
	//"n9o3KyAk5GqtJYJicYJq3qXh6kh2cimTDuTYIXKjdkfFcx/R+r6wAbbYVMSqKuWraQj8/sj5SplaBPknZpVop5yAyiy2jlvQqcrk8KkgOIGCowThisX2J/az431kXrlBJMARUiZof6FO0VF5fkRgZuHfqhBIFqT4aPvStYUD7J3EqrTmclIIq6t67WpdB7G10F9N0aDrglLdOtFNhk1aBVeqrbsBN60Qt5T7zJiYnCnUpB9l/CP8mXaFeaCdsPIXkKcm7R3uRaU2NiAGwdz57iJwaEZ6501BECzoRtuhKVXJJqIYbmAll+mv+fHbRimhRTeGruI3J7aHF7PqI7MhUwaEf1S419L29nrVXhOWnOEaifYgqZVYALhAE9UIev/1RRiB3Buga3SeQJrxQDidcOOSQuHY/q8E5boQu1sikgQ/j5uVaFc2bhhpi4H5XEPbaDJGEDLPOIHui1cOlBGUu/M91rfC3Vhhe+wyskrJ6u03Hawxg0cJ7823efNDIdpMuGoWzis4Zhvi8wU9uWQrXsGL9pBaC9JR5rOIo7+DCSh9P8chqzpolEON+I+Cq7B39TlyGW7t+YkUrXDMivN1r7paXv/i28b5nLe+v5fvkL0s1gdXyoMX0k0LB/JLYt87PCmw/rHdJzi2Fwa/NzOVrNSIEqpxA68jTgL6n4D6moIM6tUAcICbdxjOsVw9ZSEXoSZCPsq5rTNxAg85mObofT4nrOkVBF/2Chj+vTJ28607BalUzh3E2T5jawP7vYtFkm7x9NBc/+ZEBnRn7u4QGS69dSZptPdypHhX8RI/2pDP67hqSg7cduYyxLd9O6gfGr8DbTMfNuDk921DO+Jm26JQbPrGe1dkQojUtmChZHhRE/vC/85YtXHZK5gjOa4yLUbTWggfRTqXMYL+uXaPPVEttYm/EvpfHKXUDdRQkYQ=";

	
	
	private Hasp hasp = null;
	
	private static int i = 0;
	protected void setUp() throws Exception {
		super.setUp();
		hasp = new Hasp(Hasp.HASP_DEFAULT_FID);
		hasp.loginScope(scope1, vendorCode);
		if(HaspStatus.HASP_STATUS_OK != hasp.getLastError()){
			throw new IllegalAccessError("Login failed.");
		}
		System.out.println((++i) + ": login\n-------------------------------");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		hasp.logout();
		if(HaspStatus.HASP_STATUS_OK != hasp.getLastError()){
			throw new IllegalAccessError("Logout failed.");
		}
		System.out.println("-------------------------------\n" + i + ": logout\n\n");
	}
	
	public void testLogin(){
	}
	
	public void testGetInfo() throws DocumentException, UnknownHostException{
		String infos = hasp.getInfo(scope, view, vendorCode);
		if(HaspStatus.HASP_STATUS_OK == hasp.getLastError()){
			System.out.println(infos);
			Document document = DocumentHelper.parseText(infos);
			Element node = (Element) document.selectSingleNode("/root/hasp");
			System.out.println(node.attributeValue("id"));
			
		}else{
			fail("get info");
		}
		
		System.out.println(InetAddress.getLocalHost().getHostAddress());
	}
	
	public void testEncrypt(){
		final byte[] data = "This is a test string90.".getBytes();
		System.out.println(data.length);
		hasp.encrypt(data);
		checkStatus();
		System.out.println(data.length);
		System.out.println(new String(Base64.encodeBase64(data)));
	}

	
	
	private void checkStatus(){
		if(HaspStatus.HASP_STATUS_OK != hasp.getLastError()){
			fail();
		}
	}
}
