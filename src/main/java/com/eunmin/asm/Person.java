package com.eunmin.asm;

public class Person {
    int age = 10;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

/*
javap -c com/eunmin/asm/Person

Compiled from "Person.java"
public class com.eunmin.asm.Person {
  int age;

  public com.eunmin.asm.Person();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: bipush        10
       7: putfield      #2                  // Field age:I
      10: return

  public int getAge();
    Code:
       0: aload_0
       1: getfield      #2                  // Field age:I
       4: ireturn

  public void setAge(int);
    Code:
       0: aload_0
       1: iload_1
       2: putfield      #2                  // Field age:I
       5: return
}
 */