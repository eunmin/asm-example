package com.eunmin.asm;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class Compiler {

    static class MyClassLoader extends ClassLoader {
        public Class defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        ClassWriter cw = new ClassWriter(0);

        cw.visit(49, ACC_PUBLIC + ACC_SUPER, "Hello", null, "java/lang/Object", new String[] {"com/eunmin/asm/Printer"});
        cw.visitSource("Hello.java", null);

        MethodVisitor mv;

        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC, "print", "()V", null, null);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello World!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();

        cw.visitEnd();

        output("/tmp/Hello.class", cw.toByteArray());

        MyClassLoader classLoader = new MyClassLoader();
        Class c = classLoader.defineClass("Hello", cw.toByteArray());

        Printer p = (Printer)c.newInstance();
        p.print();
    }

    public static void output(String filename, byte[] data) throws IOException {
        FileOutputStream out=new FileOutputStream(filename);
        out.write(data);
        out.close();
    }
}
