package org.opoo.apps.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.lifecycle.Application;

import com.octo.captcha.engine.image.ImageCaptchaEngine;
import com.octo.captcha.engine.image.gimpy.HotmailEngine;
import com.octo.captcha.image.ImageCaptcha;


/**
 * 使用 JCaptcha 创建验证码。
 * 
 * @author lcj
 */
public class JCaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = -4832176269033843985L;
	public static final String PROPERTY_USER_CAPTCHA_KEY = "jcaptchaKey";
    private static final Logger log = LogManager.getLogger(JCaptchaServlet.class);

    //private SecureRandom random = new SecureRandom();
    private ImageCaptchaEngine engine = new HotmailEngine();
    private String imageCaptchaEngineClassName = engine.getClass().getName();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String classname = getInitParameter("imageCaptchaEngine");
		if(StringUtils.isNotBlank(classname)){
			try {
				engine = (ImageCaptchaEngine) ClassUtils.getClass(classname).newInstance();
			} catch (Exception e) {
				log.error("无法创建ImageCaptchaEngine的实例‘" + classname + "’，将使用默认的引擎。", e);
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException, IOException {
		if(log.isDebugEnabled()){
			log.debug("Create Captcha...");
		}
		// Set to expire far in the past.
		httpServletResponse.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		httpServletResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		httpServletResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		httpServletResponse.setHeader("Pragma", "no-cache");

		// return a jpeg
		httpServletResponse.setContentType("image/jpeg");

		// create the image with the text
		BufferedImage bi = createImage(httpServletRequest, httpServletResponse);
		//service.getImageChallengeForID(httpServletRequest.getSession(true).getId());

		ServletOutputStream out = httpServletResponse.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}
	
	
	
	protected BufferedImage createImage(HttpServletRequest req, HttpServletResponse resp){
		ImageCaptcha imageCaptcha = getImageCaptchaEngine().getNextImageCaptcha();
		req.getSession().setAttribute(PROPERTY_USER_CAPTCHA_KEY, imageCaptcha);
//		if(log.isDebugEnabled()){
//			log.debug("Create image captcha for session key: " + PROPERTY_USER_CAPTCHA_KEY);
//		}
		return imageCaptcha.getImageChallenge();
	}
	
	protected ImageCaptchaEngine getImageCaptchaEngine(){
		if(Application.isInitialized()){
			try{
				String className = AppsGlobals.getProperty("jcaptcha.imageCaptchaEngine.className");
				if(className != null && !className.equals(imageCaptchaEngineClassName)){
					log.info("ImageCaptchaEngine change to: " + className);
					
					engine = (ImageCaptchaEngine) ClassUtils.getClass(className).newInstance();
					imageCaptchaEngineClassName = className;
					
					return engine;
				}
			}catch(Exception e){
				log.error("Change ImageCaptchaEngine error", e);
			}
		}
		return engine;
	}
	
	
	public static boolean validateResponse(HttpServletRequest request, String userCaptchaResponse){
        //if no session found
		HttpSession session = request.getSession(false);
//		if(session == null){
//			return false;
//		}
//		ImageCaptcha imageCaptcha = (ImageCaptcha) session.getAttribute(PROPERTY_USER_CAPTCHA_KEY);
//		return imageCaptcha != null && imageCaptcha.validateResponse(userCaptchaResponse);
		return validateResponse(session, userCaptchaResponse);
	}
	
	public static boolean validateResponse(HttpSession session, String userCaptchaResponse){
		if(session == null){
			return false;
		}
		ImageCaptcha imageCaptcha = (ImageCaptcha) session.getAttribute(PROPERTY_USER_CAPTCHA_KEY);
		session.removeAttribute(PROPERTY_USER_CAPTCHA_KEY);
		return imageCaptcha != null && imageCaptcha.validateResponse(userCaptchaResponse); 
	}
	
	/*
	public static boolean validateResponse(HttpServletRequest request, String userCaptchaResponse){
        //if no session found
		HttpSession session = request.getSession(false);
		if(session == null){
			return false;
		}
		String validation = (String) session.getAttribute(PROPERTY_USER_VALIDATION_KEY);
		return validation != null && validation.equalsIgnoreCase(userCaptchaResponse);
    }
	
	
    
	private BufferedImage createImage(HttpServletRequest req, HttpServletResponse resp) {
		String validation = (String) req.getSession().getAttribute(PROPERTY_USER_VALIDATION_KEY);

		if (StringUtils.isBlank(validation)) {
			log.debug("Captcha Image Key is null/blank. Cannot generate a valid image for Captcha.");
			validation = generateText();
			req.getSession().setAttribute(PROPERTY_USER_VALIDATION_KEY, validation);
		}
		
		// Create an image to output
        return buildHotmailImage(validation);
	}
    
    protected String generateText(){
    	//word generator
        WordGenerator dictionnaryWords = new RandomWordGenerator("ABCDEGHJKLMNRSTUWXY235689");
        
        //length
        Integer wordLength = getRandomLength(minLength, maxLength);
        String word = dictionnaryWords.getWord(wordLength);
        
        return word;
    }
    
    protected BufferedImage buildHotmailImage(String word) {
		// wordtoimage components
		TextPaster randomPaster = new GlyphsPaster(minLength, maxLength,
				new SingleColorGenerator(new Color(0, 0, 80)),
				new GlyphsVisitors[] {
						new TranslateGlyphsVerticalRandomVisitor(5),
						new RotateGlyphsRandomVisitor(Math.PI / 32),
						new ShearGlyphsRandomVisitor(0.2, 0.2),
						new HorizontalSpaceGlyphsVisitor(4),
						new TranslateAllToRandomPointVisitor() },
				new GlyphsDecorator[] {
						new RandomLinesGlyphsDecorator(1.2,
								new SingleColorGenerator(new Color(0, 0, 80)),
								2, 25),
						new RandomLinesGlyphsDecorator(1,
								new SingleColorGenerator(new Color(238, 238,
										238)), 1, 25) });

		BackgroundGenerator back = new UniColorBackgroundGenerator(218, 48,
				new Color(238, 238, 238));

		FontGenerator shearedFont = new RandomFontGenerator(30, 35,
				new Font[] { new Font("Caslon", Font.BOLD, 30) }, false);

		SwimFilter swim = new SwimFilter();

		swim.setScale(30);
		swim.setStretch(1);
		swim.setTurbulence(1);
		swim.setAmount(2);
		swim.setTime(0);
		swim.setEdgeAction(ImageFunction2D.CLAMP);

		java.util.List<ImageDeformation> def = new ArrayList<ImageDeformation>();
		def.add(new ImageDeformationByBufferedImageOp(swim));

		WordToImage word2image = new DeformedComposedWordToImage(false,
				shearedFont, back, randomPaster,
				new ArrayList<ImageDeformation>(),
				new ArrayList<ImageDeformation>(), def);

		// Build image
		BufferedImage image = null;
		try {
			image = word2image.getImage(word);
		} catch (Throwable e) {
			throw new CaptchaException(e);
		}
		return image;
	}
	
	protected Integer getRandomLength(int minLength, int maxLength) {
		int range = maxLength - minLength;
		int randomRange = range != 0 ? random.nextInt(range + 1) : 0;
		return randomRange + minLength;
	}
	
	*/
}
