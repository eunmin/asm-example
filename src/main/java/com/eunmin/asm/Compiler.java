package com.eunmin.asm;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class Compiler {

    public static void main(String[] args) throws IOException {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(49, ACC_PUBLIC + ACC_SUPER, "Hello", null, "java/lang/Object", null);
        cw.visitSource("Hello.java", null);

        mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello World!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();

        cw.visitEnd();

        output("/tmp/Hello.class", cw.toByteArray());
    }

    public static void output(String filename, byte[] data) throws IOException {
        FileOutputStream out=new FileOutputStream(filename);
        out.write(data);
        out.close();
    }
}
