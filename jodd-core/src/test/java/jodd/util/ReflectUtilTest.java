// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.

package jodd.util;

import jodd.mutable.MutableInteger;
import jodd.typeconverter.TypeConverterManager;
import jodd.util.subclass.*;
import jodd.util.testdata.A;
import jodd.util.testdata.B;
import jodd.util.testdata.C;
import jodd.util.testdata.JavaBean;
import jodd.util.testdata2.D;
import jodd.util.testdata2.E;
import jodd.util.testdata2.En;
import org.junit.Test;
import sun.reflect.Reflection;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ReflectUtilTest {

	@Test
	public void testInvoke() {
		TFooBean bean = new TFooBean();

		String result;
		try {
			result = (String) ReflectUtil.invoke(TFooBean.class, bean, "getPublic", null, null);
			assertEquals("public", result);
			result = (String) ReflectUtil.invoke(bean, "getPublic", null, null);
			assertEquals("public", result);
			result = (String) ReflectUtil.invoke(bean, "getPublic", null);
			assertEquals("public", result);
		} catch (Exception e) {
			fail("ReflectUtil.invoke() failed " + e.toString());
		}

		try {
			ReflectUtil.invoke(TFooBean.class, bean, "getDefault", null, null);
			fail("ReflectUtil.invoke() works irregular!");
		} catch (Exception e) {
		}

		try {
			ReflectUtil.invoke(TFooBean.class, bean, "getProtected", null, null);
			fail("ReflectUtil.invoke() works irregular!");
		} catch (Exception e) {
		}

		try {
			ReflectUtil.invoke(TFooBean.class, bean, "getPrivate", null, null);
			fail("ReflectUtil.invoke() works irregular!");
		} catch (Exception e) {
		}
	}


	@Test
	public void testInvokeEx() {
		TFooBean bean = new TFooBean();

		String result;
		try {
			result = (String) ReflectUtil.invokeDeclared(TFooBean.class, bean, "getPublic", null, null);
			assertEquals("public", result);
			result = (String) ReflectUtil.invokeDeclared(bean, "getPublic", null, null);
			assertEquals("public", result);
			result = (String) ReflectUtil.invokeDeclared(bean, "getPublic", null);
			assertEquals("public", result);
		} catch (Exception e) {
			fail("ReflectUtil.invoke() failed " + e.toString());
		}

		try {
			result = (String) ReflectUtil.invokeDeclared(TFooBean.class, bean, "getDefault", null, null);
			assertEquals("default", result);
			result = (String) ReflectUtil.invokeDeclared(bean, "getDefault", null, null);
			assertEquals("default", result);
		} catch (Exception e) {
			fail("ReflectUtil.invoke() failed " + e.toString());
		}

		try {
			result = (String) ReflectUtil.invokeDeclared(TFooBean.class, bean, "getProtected", null, null);
			assertEquals("protected", result);
			result = (String) ReflectUtil.invokeDeclared(bean, "getProtected", null, null);
			assertEquals("protected", result);
		} catch (Exception e) {
			fail("ReflectUtil.invoke() failed " + e.toString());
		}

		try {
			result = (String) ReflectUtil.invokeDeclared(TFooBean.class, bean, "getPrivate", null, null);
			assertEquals("private", result);
			result = (String) ReflectUtil.invokeDeclared(bean, "getPrivate", null);
			assertEquals("private", result);
		} catch (Exception e) {
			fail("ReflectUtil.invoke() failed " + e.toString());
		}

	}

	@Test
	public void testInvoke2() {
		TFooBean bean = new TFooBean();
		String result;
		try {
			result = (String) ReflectUtil.invoke(TFooBean.class, bean, "getMore", new Class[]{String.class, Integer.class}, new Object[]{"qwerty", new Integer(173)});
			assertEquals("qwerty173", result);
			result = (String) ReflectUtil.invoke(TFooBean.class, bean, "getMore", new Object[]{"Qwerty", new Integer(173)});
			assertEquals("Qwerty173", result);
			result = (String) ReflectUtil.invoke(bean, "getMore", new Class[]{String.class, Integer.class}, new Object[]{"QWerty", new Integer(173)});
			assertEquals("QWerty173", result);
			result = (String) ReflectUtil.invoke(bean, "getMore", new Object[]{"QWErty", new Integer(173)});
			assertEquals("QWErty173", result);

			result = (String) ReflectUtil.invokeDeclared(TFooBean.class, bean, "getMore", new Class[]{String.class, Integer.class}, new Object[]{"qwerty", new Integer(173)});
			assertEquals("qwerty173", result);
			result = (String) ReflectUtil.invokeDeclared(TFooBean.class, bean, "getMore", new Object[]{"Qwerty", new Integer(173)});
			assertEquals("Qwerty173", result);
			result = (String) ReflectUtil.invokeDeclared(bean, "getMore", new Class[]{String.class, Integer.class}, new Object[]{"QWerty", new Integer(173)});
			assertEquals("QWerty173", result);
			result = (String) ReflectUtil.invokeDeclared(bean, "getMore", new Object[]{"QWErty", new Integer(173)});
			assertEquals("QWErty173", result);
		} catch (Exception e) {
			fail("ReflectUtil.invoke() failed " + e.toString());
		}
	}


	@Test
	public void testMethod0() {
		TFooBean bean = new TFooBean();
		Method m;
		m = ReflectUtil.getMethod0(TFooBean.class, "getMore", String.class, Integer.class);
		assertNotNull(m);

		m = ReflectUtil.getMethod0(bean.getClass(), "getMore", String.class, Integer.class);
		assertNotNull(m);

		m = ReflectUtil.getMethod0(bean.getClass(), "getXXX", String.class, Integer.class);
		assertNull(m);

		m = ReflectUtil.getMethod0(bean.getClass(), "getPublic");
		assertNotNull(m);

		m = ReflectUtil.getMethod0(bean.getClass(), "getDefault");
		assertNull(m);

		m = ReflectUtil.getMethod0(bean.getClass(), "getProtected");
		assertNull(m);

		m = ReflectUtil.getMethod0(bean.getClass(), "getPrivate");
		assertNull(m);
	}


	@Test
	public void testMethod() {
		TFooBean bean = new TFooBean();
		Method m;
		m = ReflectUtil.findMethod(TFooBean.class, "getMore");
		assertNotNull(m);

		m = ReflectUtil.findMethod(bean.getClass(), "getMore");
		assertNotNull(m);

		m = ReflectUtil.findMethod(bean.getClass(), "getXXX");
		assertNull(m);
	}


	@Test
	public void testMatchClasses() {
		TFooBean a = new TFooBean();
		TFooBean b = new TFooBean();
		TFooBean2 c = new TFooBean2();

		assertTrue(TFooBean.class.isInstance(a));
		assertTrue(ReflectUtil.isSubclass(TFooBean.class, a.getClass()));
		assertTrue(ReflectUtil.isSubclass(TFooBean.class, b.getClass()));
		assertTrue(ReflectUtil.isSubclass(a.getClass(), b.getClass()));
		assertTrue(ReflectUtil.isSubclass(b.getClass(), a.getClass()));

		assertTrue(ReflectUtil.isSubclass(TFooBean2.class, c.getClass()));
		assertTrue(ReflectUtil.isSubclass(TFooBean2.class, TFooBean.class));
		assertFalse(ReflectUtil.isSubclass(TFooBean.class, TFooBean2.class));
		assertTrue(ReflectUtil.isSubclass(c.getClass(), TFooBean.class));
		assertFalse(ReflectUtil.isSubclass(a.getClass(), TFooBean2.class));

		assertTrue(ReflectUtil.isSubclass(TFooBean.class, Serializable.class));
		assertTrue(Serializable.class.isInstance(c));
		//noinspection ConstantConditions
		assertTrue(c instanceof Serializable);
		assertTrue(ReflectUtil.isInstanceOf(c, Serializable.class));
		assertTrue(ReflectUtil.isSubclass(TFooBean2.class, Serializable.class));
		assertTrue(ReflectUtil.isSubclass(TFooBean2.class, Comparable.class));
		assertFalse(ReflectUtil.isSubclass(TFooBean.class, Comparable.class));

		assertTrue(ReflectUtil.isSubclass(TFooBean.class, TFooIndyEx.class));
		assertTrue(ReflectUtil.isSubclass(TFooBean2.class, TFooIndyEx.class));
		assertTrue(ReflectUtil.isSubclass(TFooBean.class, TFooIndy.class));
	}


	@Test
	public void testAccessibleA() {
		Method[] ms = ReflectUtil.getAccessibleMethods(A.class, null);
		assertEquals(4 + 11, ms.length);            // there are 11 accessible Object methods (9 public + 2 protected)
		ms = ReflectUtil.getAccessibleMethods(A.class);
		assertEquals(4, ms.length);
		ms = A.class.getMethods();
		assertEquals(1 + 9, ms.length);                // there are 9 public Object methods
		ms = A.class.getDeclaredMethods();
		assertEquals(4, ms.length);
		ms = ReflectUtil.getSupportedMethods(A.class, null);
		assertEquals(4 + 12, ms.length);            // there are 12 total Object methods (9 public + 2 protected + 1 private)
		ms = ReflectUtil.getSupportedMethods(A.class);
		assertEquals(4, ms.length);


		Field[] fs = ReflectUtil.getAccessibleFields(A.class);
		assertEquals(4, fs.length);
		fs = A.class.getFields();
		assertEquals(1, fs.length);
		fs = A.class.getDeclaredFields();
		assertEquals(4, fs.length);
		fs = ReflectUtil.getSupportedFields(A.class);
		assertEquals(4, fs.length);
	}

	@Test
	public void testAccessibleB() {
		Method[] ms = ReflectUtil.getAccessibleMethods(B.class, null);
		assertEquals(3 + 11, ms.length);
		ms = ReflectUtil.getAccessibleMethods(B.class);
		assertEquals(3, ms.length);
		ms = B.class.getMethods();
		assertEquals(1 + 9, ms.length);
		ms = B.class.getDeclaredMethods();
		assertEquals(0, ms.length);
		ms = ReflectUtil.getSupportedMethods(B.class, null);
		assertEquals(4 + 12, ms.length);
		ms = ReflectUtil.getSupportedMethods(B.class);
		assertEquals(4, ms.length);


		Field[] fs = ReflectUtil.getAccessibleFields(B.class);
		assertEquals(3, fs.length);
		fs = B.class.getFields();
		assertEquals(1, fs.length);
		fs = B.class.getDeclaredFields();
		assertEquals(0, fs.length);
		fs = ReflectUtil.getSupportedFields(B.class);
		assertEquals(4, fs.length);
	}

	@Test
	public void testAccessibleC() {
		Method[] ms = ReflectUtil.getAccessibleMethods(C.class, null);
		assertEquals(5 + 11, ms.length);
		ms = ReflectUtil.getAccessibleMethods(C.class);
		assertEquals(5, ms.length);
		ms = C.class.getMethods();
		assertEquals(2 + 9, ms.length);
		ms = C.class.getDeclaredMethods();
		assertEquals(5, ms.length);
		ms = ReflectUtil.getSupportedMethods(C.class, null);
		assertEquals(5 + 12, ms.length);
		ms = ReflectUtil.getSupportedMethods(C.class);
		assertEquals(5, ms.length);


		Field[] fs = ReflectUtil.getAccessibleFields(C.class);
		assertEquals(5, fs.length);
		fs = C.class.getFields();
		assertEquals(3, fs.length);
		fs = C.class.getDeclaredFields();
		assertEquals(5, fs.length);
		fs = ReflectUtil.getSupportedFields(C.class);
		assertEquals(5, fs.length);
	}

	@Test
	public void testAccessibleD() {
		Method[] ms = ReflectUtil.getAccessibleMethods(D.class, null);
		assertEquals(3 + 11, ms.length);
		ms = ReflectUtil.getAccessibleMethods(D.class);
		assertEquals(3, ms.length);
		ms = D.class.getMethods();
		assertEquals(2 + 9, ms.length);
		ms = D.class.getDeclaredMethods();
		assertEquals(0, ms.length);
		ms = ReflectUtil.getSupportedMethods(D.class, null);
		assertEquals(5 + 12, ms.length);
		ms = ReflectUtil.getSupportedMethods(D.class);
		assertEquals(5, ms.length);

		Field[] fs = ReflectUtil.getAccessibleFields(D.class);
		assertEquals(3, fs.length);
		fs = D.class.getFields();
		assertEquals(3, fs.length);
		fs = D.class.getDeclaredFields();
		assertEquals(0, fs.length);
		fs = ReflectUtil.getSupportedFields(D.class);
		assertEquals(5, fs.length);
	}

	@Test
	public void testAccessibleE() {
		Method[] ms = ReflectUtil.getAccessibleMethods(E.class, null);
		assertEquals(5 + 11, ms.length);
		ms = ReflectUtil.getAccessibleMethods(E.class);
		assertEquals(5, ms.length);
		ms = E.class.getMethods();
		assertEquals(2 + 9, ms.length);
		ms = E.class.getDeclaredMethods();
		assertEquals(4, ms.length);
		ms = ReflectUtil.getSupportedMethods(E.class, null);
		assertEquals(5 + 12, ms.length);
		ms = ReflectUtil.getSupportedMethods(E.class);
		assertEquals(5, ms.length);

		Field[] fs = ReflectUtil.getAccessibleFields(E.class);
		assertEquals(5, fs.length);
		fs = E.class.getFields();
		assertEquals(4, fs.length);
		fs = E.class.getDeclaredFields();
		assertEquals(4, fs.length);
		fs = ReflectUtil.getSupportedFields(E.class);
		assertEquals(5, fs.length);
	}


	@Test
	public void testCast() {

		String s = "123";
		Integer d = TypeConverterManager.convertType(s, Integer.class);
		assertEquals(123, d.intValue());

		s = TypeConverterManager.convertType(d, String.class);
		assertEquals("123", s);

		MutableInteger md = TypeConverterManager.convertType(s, MutableInteger.class);
		assertEquals(123, md.intValue());

		B b = new B();
		A a = TypeConverterManager.convertType(b, A.class);
		assertEquals(a, b);
	}

	@Test
	public void testCastEnums() {

		En en = TypeConverterManager.convertType("ONE", En.class);
		assertEquals(En.ONE, en);
		en = TypeConverterManager.convertType("TWO", En.class);
		assertEquals(En.TWO, en);
	}


	@Test
	public void testIsSubclassAndInterface() {
		assertTrue(ReflectUtil.isSubclass(SBase.class, SBase.class));

		assertTrue(ReflectUtil.isSubclass(SOne.class, SBase.class));
		assertTrue(ReflectUtil.isSubclass(SOne.class, IOne.class));
		assertTrue(ReflectUtil.isInterfaceImpl(SOne.class, IOne.class));
		assertTrue(ReflectUtil.isSubclass(SOne.class, Serializable.class));
		assertTrue(ReflectUtil.isInterfaceImpl(SOne.class, Serializable.class));
		assertTrue(ReflectUtil.isSubclass(SOne.class, SOne.class));

		assertTrue(ReflectUtil.isSubclass(STwo.class, SBase.class));
		assertTrue(ReflectUtil.isSubclass(STwo.class, IOne.class));
		assertTrue(ReflectUtil.isInterfaceImpl(STwo.class, IOne.class));
		assertTrue(ReflectUtil.isSubclass(STwo.class, Serializable.class));
		assertTrue(ReflectUtil.isInterfaceImpl(STwo.class, Serializable.class));
		assertTrue(ReflectUtil.isSubclass(STwo.class, ITwo.class));
		assertTrue(ReflectUtil.isInterfaceImpl(STwo.class, ITwo.class));
		assertTrue(ReflectUtil.isSubclass(STwo.class, IBase.class));
		assertTrue(ReflectUtil.isInterfaceImpl(STwo.class, IBase.class));
		assertTrue(ReflectUtil.isSubclass(STwo.class, IExtra.class));
		assertTrue(ReflectUtil.isInterfaceImpl(STwo.class, IExtra.class));
		assertTrue(ReflectUtil.isSubclass(STwo.class, STwo.class));
		assertFalse(ReflectUtil.isInterfaceImpl(STwo.class, STwo.class));
	}

	@Test
	public void testBeanPropertyNames() {
		String name = ReflectUtil.getBeanPropertyGetterName(ReflectUtil.findMethod(JavaBean.class, "getOne"));
		assertEquals("one", name);

		name = ReflectUtil.getBeanPropertySetterName(ReflectUtil.findMethod(JavaBean.class, "setOne"));
		assertEquals("one", name);

		name = ReflectUtil.getBeanPropertyGetterName(ReflectUtil.findMethod(JavaBean.class, "isTwo"));
		assertEquals("two", name);

		name = ReflectUtil.getBeanPropertySetterName(ReflectUtil.findMethod(JavaBean.class, "setTwo"));
		assertEquals("two", name);

		name = ReflectUtil.getBeanPropertyGetterName(ReflectUtil.findMethod(JavaBean.class, "getThree"));
		assertEquals("three", name);

		name = ReflectUtil.getBeanPropertySetterName(ReflectUtil.findMethod(JavaBean.class, "setThree"));
		assertEquals("three", name);

		name = ReflectUtil.getBeanPropertyGetterName(ReflectUtil.findMethod(JavaBean.class, "getF"));
		assertEquals("f", name);

		name = ReflectUtil.getBeanPropertySetterName(ReflectUtil.findMethod(JavaBean.class, "setF"));
		assertEquals("f", name);

		name = ReflectUtil.getBeanPropertyGetterName(ReflectUtil.findMethod(JavaBean.class, "getG"));
		assertEquals("g", name);

		name = ReflectUtil.getBeanPropertySetterName(ReflectUtil.findMethod(JavaBean.class, "setG"));
		assertEquals("g", name);

		name = ReflectUtil.getBeanPropertyGetterName(ReflectUtil.findMethod(JavaBean.class, "getURL"));
		assertEquals("URL", name);

		name = ReflectUtil.getBeanPropertySetterName(ReflectUtil.findMethod(JavaBean.class, "setURL"));
		assertEquals("URL", name);

		name = ReflectUtil.getBeanPropertyGetterName(ReflectUtil.findMethod(JavaBean.class, "getBIGsmall"));
		assertEquals("BIGsmall", name);

		name = ReflectUtil.getBeanPropertySetterName(ReflectUtil.findMethod(JavaBean.class, "setBIGsmall"));
		assertEquals("BIGsmall", name);
	}

	@Test
	public void testIsSubClassForCommonTypes() {
		assertTrue(ReflectUtil.isSubclass(Long.class, Long.class));
		assertFalse(ReflectUtil.isSubclass(Long.class, long.class));
	}

	@Test
	public void testGetCallerClass() {
		assertFalse(Reflection.getCallerClass(0).equals(ReflectUtil.getCallerClass(0)));

		assertEquals(Reflection.getCallerClass(1), ReflectUtil.getCallerClass(1));
		assertEquals(Reflection.getCallerClass(2), ReflectUtil.getCallerClass(2));
		assertEquals(Reflection.getCallerClass(3), ReflectUtil.getCallerClass(3));

		assertEquals(ReflectUtilTest.class, ReflectUtil.getCallerClass(1));
	}

	@Test
	public void testGetCallerClass2() throws NoSuchFieldException, IllegalAccessException {
		Field field = ReflectUtil.class.getDeclaredField("SECURITY_MANAGER");
		field.setAccessible(true);
		Object value = field.get(null);
		field.set(null, null);

		assertFalse(Reflection.getCallerClass(0).equals(ReflectUtil.getCallerClass(0)));

		assertEquals(Reflection.getCallerClass(1), ReflectUtil.getCallerClass(1));
		assertEquals(Reflection.getCallerClass(2), ReflectUtil.getCallerClass(2));
		assertEquals(Reflection.getCallerClass(3), ReflectUtil.getCallerClass(3));

		assertEquals(ReflectUtilTest.class, ReflectUtil.getCallerClass(1));

		field.set(null, value);
	}

	// ---------------------------------------------------------------- field concrete type

	public static class BaseClass<A, B> {
		public A f1;
		public B f2;
		public String f3;
		public A[] array1;
	}

	public static class ConcreteClass extends BaseClass<String, Integer> {
		public Long f4;
		public List<Long> f5;
	}

	public static class BaseClass2<X> extends BaseClass<X, Integer> {
	}

	public static class ConcreteClass2 extends BaseClass2<String> {
	}

	@Test
	public void testGetFieldConcreteType() throws NoSuchFieldException {
		Field f1 = BaseClass.class.getField("f1");
		Field f2 = BaseClass.class.getField("f2");
		Field f3 = BaseClass.class.getField("f3");
		Field f4 = ConcreteClass.class.getField("f4");
		Field f5 = ConcreteClass.class.getField("f5");
		Field array1 = BaseClass.class.getField("array1");

		assertEquals(String.class, ReflectUtil.getGenericSupertype(ConcreteClass.class, 0));
		assertEquals(Integer.class, ReflectUtil.getGenericSupertype(ConcreteClass.class, 1));

		assertEquals(String.class, ReflectUtil.getRawType(f1.getGenericType(), ConcreteClass.class));
		assertEquals(Integer.class, ReflectUtil.getRawType(f2.getGenericType(), ConcreteClass.class));
		assertEquals(String.class, ReflectUtil.getRawType(f3.getGenericType(), ConcreteClass.class));
		assertEquals(Long.class, ReflectUtil.getRawType(f4.getGenericType(), ConcreteClass.class));
		assertEquals(List.class, ReflectUtil.getRawType(f5.getGenericType(), ConcreteClass.class));
		assertEquals(String[].class, ReflectUtil.getRawType(array1.getGenericType(), ConcreteClass.class));

		assertEquals(Object.class, ReflectUtil.getRawType(f1.getGenericType()));
		assertNull(ReflectUtil.getComponentType(f1.getGenericType()));
		assertEquals(Long.class, ReflectUtil.getComponentType(f5.getGenericType()));
	}

	@Test
	public void testGetFieldConcreteType2() throws Exception {
		Field array1 = BaseClass.class.getField("array1");
		Field f2 = ConcreteClass2.class.getField("f2");

		assertEquals(String[].class, ReflectUtil.getRawType(array1.getGenericType(), ConcreteClass2.class));
		assertEquals(Integer.class, ReflectUtil.getRawType(f2.getGenericType(), ConcreteClass2.class));
		assertEquals(Integer.class, ReflectUtil.getRawType(f2.getGenericType(), BaseClass2.class));
	}

	// ---------------------------------------------------------------- test raw

	public static class Soo {
		public List<String> stringList;
		public String[] strings;
		public String string;

		public List<Integer> getIntegerList() {return null;}
		public Integer[] getIntegers() {return null;}
		public Integer getInteger() {return null;}
		public <T> T getTemplate(T foo) {return null;}
		public Collection<? extends Number> getCollection() {return null;}
		public Collection<?> getCollection2() {return null;}
	}

	@Test
	public void testGetRawAndComponentType() throws NoSuchFieldException {

		Class<Soo> sooClass = Soo.class;

		Field stringList = sooClass.getField("stringList");
		assertEquals(List.class, ReflectUtil.getRawType(stringList.getType()));
		assertEquals(String.class, ReflectUtil.getComponentType(stringList.getGenericType()));

		Field strings = sooClass.getField("strings");
		assertEquals(String[].class, ReflectUtil.getRawType(strings.getType()));
		assertEquals(String.class, ReflectUtil.getComponentType(strings.getGenericType()));

		Field string = sooClass.getField("string");
		assertEquals(String.class, ReflectUtil.getRawType(string.getType()));
		assertNull(ReflectUtil.getComponentType(string.getGenericType()));

		Method integerList = ReflectUtil.findMethod(sooClass, "getIntegerList");
		assertEquals(List.class, ReflectUtil.getRawType(integerList.getReturnType()));
		assertEquals(Integer.class, ReflectUtil.getComponentType(integerList.getGenericReturnType()));

		Method integers = ReflectUtil.findMethod(sooClass, "getIntegers");
		assertEquals(Integer[].class, ReflectUtil.getRawType(integers.getReturnType()));
		assertEquals(Integer.class, ReflectUtil.getComponentType(integers.getGenericReturnType()));

		Method integer = ReflectUtil.findMethod(sooClass, "getInteger");
		assertEquals(Integer.class, ReflectUtil.getRawType(integer.getReturnType()));
		assertNull(ReflectUtil.getComponentType(integer.getGenericReturnType()));

		Method template = ReflectUtil.findMethod(sooClass, "getTemplate");
		assertEquals(Object.class, ReflectUtil.getRawType(template.getReturnType()));
		assertNull(ReflectUtil.getComponentType(template.getGenericReturnType()));

		Method collection = ReflectUtil.findMethod(sooClass, "getCollection");
		assertEquals(Collection.class, ReflectUtil.getRawType(collection.getReturnType()));
		assertEquals(Number.class, ReflectUtil.getComponentType(collection.getGenericReturnType()));

		Method collection2 = ReflectUtil.findMethod(sooClass, "getCollection2");
		assertEquals(Collection.class, ReflectUtil.getRawType(collection2.getReturnType()));
		assertEquals(Object.class, ReflectUtil.getComponentType(collection2.getGenericReturnType()));
	}

	public static class Base2<N extends Number, K> {
		public N getNumber() {return null;}
		public K getKiko() {return null;}
	}
	public static class Impl1<N extends Number> extends Base2<N, Long> {
	}
	public static class Impl2 extends Impl1<Integer> {
	}

	@Test
	public void testGetRawWithImplClass() throws NoSuchFieldException {
		Method number = ReflectUtil.findMethod(Base2.class, "getNumber");
		Method kiko = ReflectUtil.findMethod(Base2.class, "getKiko");

		assertEquals(Number.class, ReflectUtil.getRawType(number.getReturnType()));
		assertEquals(Number.class, ReflectUtil.getRawType(number.getGenericReturnType()));

		assertEquals(Object.class, ReflectUtil.getRawType(kiko.getReturnType()));
		assertEquals(Object.class, ReflectUtil.getRawType(kiko.getGenericReturnType()));

		assertEquals(Number.class, ReflectUtil.getRawType(number.getReturnType(), Impl1.class));
		assertEquals(Number.class, ReflectUtil.getRawType(number.getGenericReturnType(), Impl1.class));

		assertEquals(Object.class, ReflectUtil.getRawType(kiko.getReturnType(), Impl1.class));
		assertEquals(Long.class, ReflectUtil.getRawType(kiko.getGenericReturnType(), Impl1.class));

		assertEquals(Number.class, ReflectUtil.getRawType(number.getReturnType(), Impl2.class));
		assertEquals(Integer.class, ReflectUtil.getRawType(number.getGenericReturnType(), Impl2.class));

		assertEquals(Object.class, ReflectUtil.getRawType(kiko.getReturnType(), Impl2.class));
		assertEquals(Long.class, ReflectUtil.getRawType(kiko.getGenericReturnType(), Impl2.class));
	}

	// ---------------------------------------------------------------- type2string

	public static class FieldType<K extends Number, V extends List<String> & Collection<String>> {
		List fRaw;
		List<Object> fTypeObject;
		List<String> fTypeString;
		List<?> fWildcard;
		List<? super List<String>> fBoundedWildcard;
		Map<String, List<Set<Long>>> fTypeNested;
		Map<K, V> fTypeLiteral;
		K[] fGenericArray;
	}

	@Test
	public void testFieldTypeToString() {
		Field[] fields = FieldType.class.getDeclaredFields();

		Arrays.sort(fields, new Comparator<Field>() {
			public int compare(Field o1, Field o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		String result = "";
		for (Field field : fields) {
			Type type = field.getGenericType();
			result += field.getName() + " - " + ReflectUtil.typeToString(type) + '\n';
		}

		assertEquals(
				"fBoundedWildcard - java.util.List<? super java.util.List<java.lang.String>>\n" +
				"fGenericArray - K[]\n" +
				"fRaw - java.util.List\n" +
				"fTypeLiteral - java.util.Map<K extends java.lang.Number>, <V extends java.util.List<java.lang.String> & java.util.Collection<java.lang.String>>\n" +
				"fTypeNested - java.util.Map<java.lang.String>, <java.util.List<java.util.Set<java.lang.Long>>>\n" +
				"fTypeObject - java.util.List<java.lang.Object>\n" +
				"fTypeString - java.util.List<java.lang.String>\n" +
				"fWildcard - java.util.List<? extends java.lang.Object>\n",
				result);
	}

	public static class MethodReturnType {
		List mRaw() {return null;}
		List<String> mTypeString() {return null;}
		List<?> mWildcard() {return null;}
		List<? extends Number> mBoundedWildcard() {return null;}
		<T extends List<String>> List<T> mTypeLiteral() {return null;}
	}

	@Test
	public void testMethodTypeToString() {
		Method[] methods = MethodReturnType.class.getDeclaredMethods();

		Arrays.sort(methods, new Comparator<Method>() {
			public int compare(Method o1, Method o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		String result = "";
		for (Method method : methods) {
			Type type = method.getGenericReturnType();
			result += method.getName() + " - " + ReflectUtil.typeToString(type) + '\n';
		}

		assertEquals(
				"mBoundedWildcard - java.util.List<? extends java.lang.Number>\n" +
				"mRaw - java.util.List\n" +
				"mTypeLiteral - java.util.List<T extends java.util.List<java.lang.String>>\n" +
				"mTypeString - java.util.List<java.lang.String>\n" +
				"mWildcard - java.util.List<? extends java.lang.Object>\n",
				result);
	}

	public static class MethodParameterType<A> {
		<T extends List<T>> void m(A a, String p1, T p2, List<?> p3, List<T> p4) { }
	}

	public static class Mimple extends MethodParameterType<Long>{}

	@Test
	public void testMethodParameterTypeToString() {
		String result = "";
		Method method = null;
		for (Method m : MethodParameterType.class.getDeclaredMethods()) {
			for (Type type : m.getGenericParameterTypes()) {
				result += m.getName() + " - " + ReflectUtil.typeToString(type) + '\n';
			}
			method = m;
		}

		assertEquals(
				"m - A extends java.lang.Object\n" +
				"m - java.lang.String\n" +
				"m - T extends java.util.List<T>\n" +
				"m - java.util.List<? extends java.lang.Object>\n" +
				"m - java.util.List<T extends java.util.List<T>>\n",
				result);


		Type[] types = method.getGenericParameterTypes();
		assertEquals(Object.class, ReflectUtil.getRawType(types[0], MethodParameterType.class));
		assertEquals(String.class, ReflectUtil.getRawType(types[1], MethodParameterType.class));
		assertEquals(List.class, ReflectUtil.getRawType(types[2], MethodParameterType.class));
		assertEquals(List.class, ReflectUtil.getRawType(types[3], MethodParameterType.class));
		assertEquals(List.class, ReflectUtil.getRawType(types[4], MethodParameterType.class));

		// same methods, using different impl class
		assertEquals(Long.class, ReflectUtil.getRawType(types[0], Mimple.class));		// change!
		assertEquals(String.class, ReflectUtil.getRawType(types[1], Mimple.class));
		assertEquals(List.class, ReflectUtil.getRawType(types[2], Mimple.class));
		assertEquals(List.class, ReflectUtil.getRawType(types[3], Mimple.class));
		assertEquals(List.class, ReflectUtil.getRawType(types[4], Mimple.class));
	}
}