package org.opoo.apps.license;

import java.security.SecureRandom;
import java.util.zip.CRC32;

import org.apache.commons.lang.StringUtils;

public class DefaultSIDManager {

	private static final String CHARACTER_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String BAD_WORDS[] = { "FUCK", "SHIT", "COCK", "DICK", "CUNT", "TWAT", "BITCH", "BASTARD",
			"JIZ", "JISM", "FART", "CRAP", "ASS", "PORN", "PISS", "PUSSY", "BALLS", "TITS", "BOOBS", "COOCH", "CUM",
			"CHOAD", "DILDO", "DOUCHE", "CLIT", "MUFF", "NOB", "PECKER", "PRICK", "POONTANG", "QUEEF", "SNATCH",
			"TWOT", "DYKE", "COON", "NIG", "FAG", "WANKER", "GOOK", "FUDGEPACKER", "QUEER", "RAGHEAD", "SKANK", "SPIC",
			"GOD", "DAMN", "FICK", "SCHEISSE", "SCHWANZ", "FOTZE", "HURE", "SCHWUCHTEL", "SCHWUL", "TITTEN", "ARSCH",
			"IDIOT", "SAU", "ASSHAT", "TURDBURGLAR", "DIRTYSANCHEZ", "FELCH", "BLASEN", "WICKSER", "FEUCHT", "MOESE",
			"MILCHTUETEN" };
	private static final int KEY_LENGTH = 18;
	private static final char CURRENT_VERSION_INITAL_CHAR = 66;
	private static final String PREVIOUS_VERSIONS_INITIAL_CHARS = "A";
	private static final char SEPARATOR_CHAR = 45;
	private final SecureRandom random;

	public DefaultSIDManager() {
		this(null);
	}

	DefaultSIDManager(byte seed[]) {
		if (seed == null) {
			String seedStr = String.valueOf(System.currentTimeMillis());
			seedStr = (new StringBuilder()).append(seedStr).append(":").append(System.identityHashCode(seedStr))
					.toString();
			seedStr = (new StringBuilder()).append(seedStr).append(":").append(System.getProperties().toString())
					.toString();
			System.out.println(seedStr);
			seed = seedStr.getBytes();
		}
		random = new SecureRandom(seed);
	}

	public String generateSID() {
		StringBuffer res;
		do {
			res = new StringBuffer();
			res.append('B');
			int charCount = 1;
			for (int i = 1; i < KEY_LENGTH; i++){
				if (charCount == 4) {
					res.append('-');
					charCount = 0;
				} else {
					int index = (int) (random.nextDouble() * (double) CHARACTER_POOL.length());
					res.append(CHARACTER_POOL.charAt(index));
					charCount++;
				}
			}
			System.out.println(res);

			res.append(getCharacterForCRC(res.toString().getBytes()));
		} while (!isKeyClean(res.toString()));
		return res.toString();
	}

	public boolean isValidSID(String sid) {
		boolean valid = validateStringSyntax(sid);
		if (valid) {
			String keyStr = sid.substring(0, 18);
			char crcChar = getCharacterForCRC(keyStr.getBytes());
			char checkChar = sid.charAt(18);
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
		else if (sid.charAt(0) != 'B' && "A".indexOf(sid.charAt(0)) == -1)
			valid = false;
		else if (sid.charAt(4) != '-' || sid.charAt(9) != '-' || sid.charAt(14) != '-')
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
//		System.out.println(key);
		String charKey = stripDashesAndNumbers(key);
//		System.out.println(charKey);
		for (int i = 0; i < BAD_WORDS.length; i++) {
			String badWord = BAD_WORDS[i];
			if (charKey.indexOf(badWord) != -1)
				return false;
		}

		return true;
	}

	private String stripDashesAndNumbers(String key) {
		String res = StringUtils.replace(key, "-", "");
		res = StringUtils.replace(res, "1", "I");
		res = StringUtils.replace(res, "2", "Z");
		res = StringUtils.replace(res, "3", "E");
		res = StringUtils.replace(res, "4", "A");
		res = StringUtils.replace(res, "5", "S");
		res = StringUtils.replace(res, "6", "G");
		res = StringUtils.replace(res, "7", "T");
		res = StringUtils.replace(res, "8", "B");
		res = StringUtils.replace(res, "9", "P");
		res = StringUtils.replace(res, "0", "O");
		return res;
	}

	
	public static void main(String[] args){
		DefaultSIDManager sidManager = new DefaultSIDManager();
		String string = sidManager.generateSID();
		System.out.println(string);
	}
}
