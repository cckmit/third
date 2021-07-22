package org.opoo.apps.web.servlet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ImageConfirmationServlet2 extends HttpServlet {
	private static final long serialVersionUID = 4884127653917718531L;

	public static final String PROPERTY_USER_VALIDATION_KEY = "validationKey";

    private static final Logger log = LogManager.getLogger(ImageConfirmationServlet.class);
    public static final int WIDTH = 290;
    public static final int HEIGHT = 80;
    SecureRandom random = new SecureRandom();

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        ServletOutputStream sos = resp.getOutputStream();
        resp.setContentType("image/jpg");

        String validation = (String) req.getSession()
                .getAttribute(PROPERTY_USER_VALIDATION_KEY);

        if(StringUtils.isBlank(validation)){
            log.error("Captcha Image Key is null/blank. Cannot generate a valid image for Captcha.");
            validation =  randomString(6).toUpperCase();
            req.getSession().setAttribute(PROPERTY_USER_VALIDATION_KEY, validation);
        }
        // Create an image to output
        RenderedImage rendImage = myCreateImage(validation);

        try {
            ImageIO.write(rendImage, "jpg", sos);
        }
        catch (IOException e) {
            log.error("Error:" + e.getMessage());
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        doGet(req, resp);
    }

    /**
     * @return RenderedImage
     */
    private RenderedImage myCreateImage(final String pString) {

        int computedWidth = WIDTH;

        // need approximately 40 pixels of additional width for every character after 6
        if (pString != null && pString.length() > 6) {
            int diff = pString.length() - 6;
            computedWidth = WIDTH + (diff * 40);
        }


        // Create a buffered image in which to draw
        BufferedImage bufferedImage = new BufferedImage(computedWidth, HEIGHT, BufferedImage.TYPE_INT_RGB);

        // Create a graphics contents on the buffered image
        Graphics2D g2d = bufferedImage.createGraphics();

        // We're going to fill the next letter using a TexturePaint, which
        // repeatedly tiles an image. The first step is to obtain the image.
        // We could load it from an image file, but here we create it
        // ourselves by drawing a into an off-screen image.  Note that we use
        // a GradientPaint to fill the off-screen image, so the fill pattern
        // combines features of both Paint classes.
        BufferedImage tile = // Create an image
                new BufferedImage(25, 25, BufferedImage.TYPE_INT_RGB);
        Graphics2D tg = tile.createGraphics(); // Get its Graphics for drawing
        tg.setPaint(new GradientPaint(5, 0, Color.GRAY, // diagonal gradient
                0, 5, Color.BLACK));
        tg.fillRect(0, 0, 25, 25);     // Draw a rectangle with this gradient

        // Paint the entire background using the .
        g2d.setPaint(new TexturePaint(tile, new Rectangle(0, 0, 5, 5)));
        // fill the background
        g2d.fillRect(0, 0, computedWidth, HEIGHT);

        // Use a different GradientPaint to draw a border.
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));         // use wide lines
        g2d.drawRect(0, 0, computedWidth, HEIGHT);  // draw the box

        // Setup various font choices
        Font[] fonts = new Font[15];
        fonts[0] = new Font("Serif", Font.PLAIN, 36);
        fonts[1] = new Font("SansSerif", Font.PLAIN, 36);
        fonts[2] = new Font("Monospaced", Font.PLAIN, 36);
        fonts[3] = new Font("Dialog", Font.PLAIN, 36);
        fonts[4] = new Font("DialogInput", Font.PLAIN, 36);
        fonts[5] = new Font("Serif", Font.BOLD, 36);
        fonts[6] = new Font("SansSerif", Font.BOLD, 36);
        fonts[7] = new Font("Monospaced", Font.BOLD, 36);
        fonts[8] = new Font("Dialog", Font.BOLD, 36);
        fonts[9] = new Font("DialogInput", Font.BOLD, 36);
        fonts[10] = new Font("Serif", Font.ITALIC, 36);
        fonts[11] = new Font("SansSerif", Font.ITALIC, 36);
        fonts[12] = new Font("Monospaced", Font.ITALIC, 36);
        fonts[13] = new Font("Dialog", Font.ITALIC, 36);
        fonts[14] = new Font("DialogInput", Font.ITALIC, 36);

        // The glyphs of fonts can be used as Shape objects, which enables
        // us to use Java2D techniques with letters Just as we would with
        // any other shape.
        GlyphVector gv = fonts[random.nextInt(fonts.length)].
                createGlyphVector(g2d.getFontRenderContext(), pString);

        // Enabled anti-aliasing for smoother image
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // set the random outline of the letter using different strokes
        Stroke[] strokes = new Stroke[5];
        strokes[0] = new DefaultStroke();
        strokes[1] = new HumanStroke(1.0f, 2.0f);
        strokes[2] = new HumanStroke(2.0f, 1.0f);
        strokes[3] = new DoubleStroke(2.0f, 1.0f);
        strokes[4] = new DoubleStroke(1.0f, 1.0f);

        // set the paint objects
        Paint fillPaint = new Color(255, 255, 255, 100);
        Paint linePaint = Color.WHITE;

        // set the random transformation of the letter
        AffineTransform[] transforms = new AffineTransform[5];
        transforms[0] = new AffineTransform();
        transforms[1] = AffineTransform.getShearInstance(-0.4, 0.1);
        transforms[2] = AffineTransform.getShearInstance(0.4, 0.1);
        transforms[3] = AffineTransform.getShearInstance(0.2, -0.1);
        transforms[4] = AffineTransform.getShearInstance(-0.2, -0.1);

        Shape letter;

        for (int i = 0; i < pString.length(); i++) {
            AffineTransform save = g2d.getTransform();
            letter = gv.getGlyphOutline(i); // Shape of the current letter

            g2d.translate(i * 20 + 20, 50);
            Stroke stroke = strokes[random.nextInt(strokes.length)];
            g2d.setStroke(stroke);
            AffineTransform transform = transforms[random.nextInt(transforms.length)];
            g2d.transform(transform);
            g2d.setPaint(fillPaint);
            g2d.fill(letter);
            g2d.setPaint(linePaint);
            g2d.draw(letter);
            g2d.setTransform(save);
        }

        return bufferedImage;
    }

    /**
     * A Custom Stroke that is equivalent to drawing the shape.
     */
    class DefaultStroke implements Stroke {

        public Shape createStrokedShape(Shape shape) {
            return shape;
        }
    }

    /**
     * A Custom Stroke that will outline the shape with two strokes
     */
    class DoubleStroke implements Stroke {
        BasicStroke stroke1, stroke2; //stroke objects

        DoubleStroke(float width1, float width2) {
            stroke1 = new BasicStroke(width1);
            stroke2 = new BasicStroke(width2);
        }

        public Shape createStrokedShape(Shape shape) {
            // outline the shape
            Shape outline = stroke1.createStrokedShape(shape);
            // outline the outline
            return stroke2.createStrokedShape(outline);
        }
    }

    /**
     * A Custom Stroke that is equivalent to drawing the shape.
     */
    class HumanStroke implements Stroke {
        BasicStroke stroke;
        float distortion;

        HumanStroke(float width, float distortion) {
            this.stroke = new BasicStroke(width);
            this.distortion = distortion;
        }

        public Shape createStrokedShape(Shape shape) {
            GeneralPath path = new GeneralPath();

            // Distort the current shape
            float[] coords = new float[6];
            for (PathIterator iterator = shape.getPathIterator(null); !iterator.isDone(); iterator .next()) {
                int type = iterator.currentSegment(coords);
                switch (type) {
                    case PathIterator.SEG_MOVETO:
                        distort(coords, 2);
                        path.moveTo(coords[0], coords[1]);
                        break;
                    case PathIterator.SEG_LINETO:
                        distort(coords, 2);
                        path.lineTo(coords[0], coords[1]);
                        break;
                    case PathIterator.SEG_QUADTO:
                        distort(coords, 4);
                        path.quadTo(coords[0], coords[1], coords[2], coords[3]);
                        break;
                    case PathIterator.SEG_CUBICTO:
                        distort(coords, 6);
                        path.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
                        break;
                    case PathIterator.SEG_CLOSE:
                        path.closePath();
                        break;
                }
            }

            // create the new shape from the path and return it
            return stroke.createStrokedShape(path);
        }

        // Randomly distort the current shape
        void distort(float[] coords, int numCoords) {
            for (int i = 0; i < numCoords; i++) {
                coords[i] += (float) ((Math.random() * 2 - 1.0) * distortion);
            }
        }
    }
    
    
    /**
     * Pseudo-random number generator object for use with randomString(). The Random class is not considered to be
     * cryptographically secure, so only use these random Strings for low to medium security applications.
     */
    private static SecureRandom randGen = new SecureRandom();

    /**
     * Array of numbers and letters of mixed case. Numbers appear in the list twice so that there is a more equal chance
     * that a number will be picked. We can use the array to get a random number or letter by picking a random array
     * index.
     */
    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
            + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    /**
     * Returns a random String of numbers and letters (lower and upper case) of the specified length. The method uses
     * the Random class that is built-in to Java which is suitable for low to medium grade security uses. This means
     * that the output is only pseudo random, i.e., each number is mathematically generated so is not truly random.<p>
     * <p/>
     * The specified length must be at least one. If not, the method will return null.
     *
     * @param length the desired length of the random String to return.
     * @return a random String of numbers and letters of the specified length.
     */
    public static String randomString(int length) {
        if (length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
}