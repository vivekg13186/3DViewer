package org.viewer1;

import org.joml.Matrix4f;
import org.junit.jupiter.api.Test;

public class TestViewer {
    @Test
    public void test1() throws Exception {
        Matrix4f m = new Matrix4f();
        m.translate(10,10,10);
        System.out.println(m);
        m.translate(10,10,10);
        System.out.println(m);
    }
}
