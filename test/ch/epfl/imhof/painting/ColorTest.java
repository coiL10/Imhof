package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import org.junit.Test;

public class ColorTest {

    @Test(expected = IllegalArgumentException.class)
    public void GrayOk() {
        Color c = Color.gray(0.5f);
        assertEquals(0.5f, c.getb(), 0);
        assertEquals(0.5f, c.getr(), 0);
        assertEquals(0.5f, c.getg(), 0);

        Color d = Color.gray(5f);
    }

    @Test
    public void rgbOk() {
        Color c = Color.rgb(0.7f, 0.4f, 0.1f);
        assertEquals(0.7f, c.getr(), 0);
        assertEquals(0.4f, c.getg(), 0);
        assertEquals(0.1f, c.getb(), 0);
    }

    @Test
    public void multiplyOk() {
        Color c = Color.rgb(0.7f, 0.4f, 0.1f);
        Color c1 = Color.rgb(0.2f, 0.5f, 0.9f);
        Color c2 = c.multiply(c1);
        assertEquals((0.7f * 0.2f), c2.getr(), 0);
        assertEquals((0.4f * 0.5f), c2.getg(), 0);
        assertEquals((0.1f * 0.9f), c2.getb(), 0);
    }

    @Test
    public void convertOk() {
        java.awt.Color c = new java.awt.Color(0.2f, 0.5f, 0.9f);
        Color c1 = Color.rgb(0.2f, 0.5f, 0.9f);
        assertTrue(c.equals(c1.convert()));
    }

    @Test
    public void rbgIntOk() {
        int b = 0b000000000000000000000000;
        Color c = Color.rgb(b);
        assertEquals(0f, c.getr(), 0);
        assertEquals(0f, c.getg(), 0);
        assertEquals(0f, c.getb(), 0);
    }

}
