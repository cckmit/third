package org.opoo.util;

import java.security.SecureRandom;
import java.util.zip.CRC32;

public class DefaultSIDManager implements SIDManager {

	private static final String CHARACTER_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String BAD_WORDS[] = { "FUCK", "SHIT", "COCK", "DICK", "CUNT", "TWAT", "BITCH", "BASTARD",
			"JIZ", "JISM", "FART", "CRAP", "ASS", "PORN", "PISS", "PUSSY", "BALLS", "TITS", "BOOBS", "COOCH", "CUM",
			"CHOAD", "DILDO", "DOUCHE", "CLIT", "MUFF", "NOB", "PECKER", "PRICK", "POONTANG", "QUEEF", "SNATCH",
			"TWOT", "DYKE", "COON", "NIG", "FAG", "WANKER", "GOOK", "FUDGEPACKER", "QUEER", "RAGHEAD", "SKANK", "SPIC",
			"GOD", "DAMN", "FICK", "SCHEISSE", "SCHWANZ", "FOTZE", "HURE", "SCHWUCHTEL", "SCHWUL", "TITTEN", "ARSCH",
			"IDIOT", "SAU", "ASSHAT", "TURDBURGLAR", "DIRTYSANCHEZ", "FELCH", "BLASEN", "WICKSER", "FEUCHT", "MOESE",
			"MILCHTUETEN" };
	private static final int KEY_LENGTH = 18;
	private static final char CURRENT_VERSION_INITAL_CHAR = 'D';
	private static final String PREVIOUS_VERSIONS_INITIAL_CHARS = "ABC";
	private static final char SEPARATOR_CHAR = '-';
	private final SecureRandom random;

	public DefaultSIDManager() {
		this(null);
	}

	DefaultSIDManager(byte seed[]) {
		if (seed == null) {
			String seedStr = String.valueOf(System.currentTimeMillis());
			seedStr += ":" + System.identityHashCode(seedStr);
			seedStr += ":" + System.getProperties().toString();
			seed = seedStr.getBytes();
		}
		random = new SecureRandom(seed);
	}

	public String generateSID() {
		StringBuffer res;
		do {
			res = new StringBuffer();
			res.append(CURRENT_VERSION_INITAL_CHAR);
			int charCount = 1;
			for (int i = 1; i < KEY_LENGTH; i++){
				if (charCount == 4) {
					res.append(SEPARATOR_CHAR);
					charCount = 0;
				} else {
					int index = (int) (random.nextDouble() * (double) CHARACTER_POOL.length());
					res.append(CHARACTER_POOL.charAt(index));
					charCount++;
				}
			}

			res.append(getCharacterForCRC(res.toString().getBytes()));
		} while (!isKeyClean(res.toString()));
		return res.toString();
	}

	public boolean isValidSID(String sid) {
		boolean valid = validateStringSyntax(sid);
		if (valid) {
			String keyStr = sid.substring(0, KEY_LENGTH);
			char crcChar = getCharacterForCRC(keyStr.getBytes());
			char checkChar = sid.charAt(KEY_LENGTH);
			valid = checkChar == crcChar;
		}
		return valid;
	}

	private boolean validateStringSyntax(String sid) {
		boolean valid = true;
		if (sid == null)
			valid = false;
		else if (sid.length() != 19)
			valid = false;
		else if (sid.charAt(0) != CURRENT_VERSION_INITAL_CHAR
				&& PREVIOUS_VERSIONS_INITIAL_CHARS.indexOf(sid.charAt(0)) == -1)
			valid = false;
		else if (sid.charAt(4) != SEPARATOR_CHAR 
				|| sid.charAt(9) != SEPARATOR_CHAR 
				|| sid.charAt(14) != SEPARATOR_CHAR)
			valid = false;
		return valid;
	}

	private char getCharacterForCRC(byte bytes[]) {
		CRC32 crc32 = new CRC32();
		crc32.update(bytes);
		long crcValue = crc32.getValue();
		int index = (int) (crcValue % (long) CHARACTER_POOL.length());
		return CHARACTER_POOL.charAt(index);
	}

	private boolean isKeyClean(String key) {
		String charKey = stripDashesAndNumbers(key);
		for (int i = 0; i < BAD_WORDS.length; i++) {
			String badWord = BAD_WORDS[i];
			if (charKey.indexOf(badWord) != -1)
				return false;
		}

		return true;
	}

	private String stripDashesAndNumbers(String key) {
		String res = replaceAll(key, "-", "");
		res = replaceAll(res, "1", "I");
		res = replaceAll(res, "2", "Z");
		res = replaceAll(res, "3", "E");
		res = replaceAll(res, "4", "A");
		res = replaceAll(res, "5", "S");
		res = replaceAll(res, "6", "G");
		res = replaceAll(res, "7", "T");
		res = replaceAll(res, "8", "B");
		res = replaceAll(res, "9", "P");
		res = replaceAll(res, "0", "O");
		return res;
	}

	public static String replaceAll(String str, String oldPattern, String newPattern) {
		if (str == null)
			return null;
		if (oldPattern == null || oldPattern.equals(""))
			return str;
		String remainder = str;
		StringBuffer buf = new StringBuffer(str.length() * 2);
		do {
			int i = remainder.indexOf(oldPattern);
			if (i != -1) {
				buf.append(remainder.substring(0, i));
				buf.append(newPattern);
				remainder = remainder.substring(i + oldPattern.length());
			} else {
				buf.append(remainder);
				return buf.toString();
			}
		} while (true);
	}
	
	
	public static void main(String[] args){
		//System.out.println(BAD_WORDS.length);
		
		String sid = new DefaultSIDManager().generateSID();
		System.out.println(sid);
		
		System.out.println(new DefaultSIDManager().isValidSID("BW47-6H4N-NN28-CMKZ"));
	}
}
